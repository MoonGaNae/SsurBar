package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.exception.BadRequestException;
import com.ssurbar.survey.api.exception.user.UserNotfoundException;
import com.ssurbar.survey.api.exception.user.UserUnauthorizedException;
import com.ssurbar.survey.api.request.UserJoinPostReq;
import com.ssurbar.survey.api.request.UserJoinPutReq;
import com.ssurbar.survey.api.request.UserLoginPostReq;
import com.ssurbar.survey.common.model.common.UserInfo;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.User;
import com.ssurbar.survey.db.entity.UserRole;
import com.ssurbar.survey.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RandomIdUtil randomIdUtil;

    @Override
    public String join(UserJoinPostReq req) {

        String userId = randomIdUtil.makeRandomId(13);
        System.out.println(req.getEmployeeNumber());
        User newUser = userRepository.save(User.builder()
                        .userId(userId)
                        .name(req.getName())
                        .email(req.getEmail())
                        .employeeNumber(req.getEmployeeNumber())
                        .userType(UserRole.UNCERTIFIED)
                        .password(req.getPassword())
                .build());


        return newUser.getUserId();
    }

    @Override
    public UserInfo login(UserLoginPostReq userLoginPostReq) {

        User user = userRepository.findByEmail(userLoginPostReq.getEmail()).orElse(null);

//        if (user == null) return null;

        if (!user.getPassword().equals(userLoginPostReq.getPassword())){
            System.out.println("비번틀림");
            return null;
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
}
