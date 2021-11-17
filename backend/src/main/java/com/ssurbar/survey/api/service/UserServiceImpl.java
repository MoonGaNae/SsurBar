package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.exception.BadRequestException;
import com.ssurbar.survey.api.exception.user.UserNotfoundException;
import com.ssurbar.survey.api.exception.user.UserUnauthorizedException;
import com.ssurbar.survey.api.request.UserJoinPostReq;
import com.ssurbar.survey.api.request.UserJoinPutReq;
import com.ssurbar.survey.api.request.UserLoginPostReq;
import com.ssurbar.survey.api.response.UserDetail;
import com.ssurbar.survey.api.response.UserUncertifiedGetRes;
import com.ssurbar.survey.common.model.common.UserInfo;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.User;
import com.ssurbar.survey.db.entity.UserRole;
import com.ssurbar.survey.db.repository.UserRepository;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RandomIdUtil randomIdUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String join(UserJoinPostReq req) {

        String userId = randomIdUtil.makeRandomId(13);
        System.out.println(req.getEmployeeNumber());

        String encodePassword = passwordEncoder.encode(req.getPassword());
        User newUser = userRepository.save(User.builder()
                        .userId(userId)
                        .name(req.getName())
                        .email(req.getEmail())
                        .employeeNumber(req.getEmployeeNumber())
                        .userType(UserRole.UNCERTIFIED)
                        .password(encodePassword)
                .build());


        return newUser.getUserId();
    }

    @Override
    public UserInfo login(UserLoginPostReq userLoginPostReq) {

        User user = userRepository.findByEmail(userLoginPostReq.getEmail()).orElse(null);

        if(user == null || !passwordEncoder.matches(userLoginPostReq.getPassword(), user.getPassword())){
            throw new UserUnauthorizedException();
        }

        return UserInfo.builder().userId(user.getUserId()).userType(user.getUserType()).build();
    }

    @Override
    public void certified(HttpServletRequest request, UserJoinPutReq userJoinPutReq) {

        // 관리자인지 확인
        String adminId = (String) request.getAttribute("userId");

        if(adminId == null) throw new BadRequestException();

        User admin = userRepository.findById(adminId).orElse(null);

        if(admin == null) throw new UserNotfoundException();
        else if(admin.getUserType() != UserRole.ADMIN) throw new UserUnauthorizedException();


        // 특정 유저의 접근권한 변경
        User user = userRepository.findById(userJoinPutReq.getUserId()).orElse(null);

        if(user == null) throw new UserNotfoundException();

        user.changeUserType(userJoinPutReq.getUserType());
        userRepository.save(user);

    }

    @Override
    public UserUncertifiedGetRes uncertifiedList(HttpServletRequest request) {

        // 관리자인지 확인
        String adminId = (String) request.getAttribute("userId");

        if(adminId == null) throw new BadRequestException();

        User admin = userRepository.findById(adminId).orElse(null);

        if(admin == null) throw new UserNotfoundException();
        else if(admin.getUserType() != UserRole.ADMIN) throw new UserUnauthorizedException();

        List<User> users = userRepository.findUsersByUserType(UserRole.UNCERTIFIED);


        return UserUncertifiedGetRes.builder()
                .users(users.stream().map(UserDetail::of).collect(Collectors.toList()))
                .build();
    }
}
