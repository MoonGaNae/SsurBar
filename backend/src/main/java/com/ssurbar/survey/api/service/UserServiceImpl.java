package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.UserJoinPostReq;
import com.ssurbar.survey.api.request.UserLoginPostReq;
import com.ssurbar.survey.common.model.common.UserInfo;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.User;
import com.ssurbar.survey.db.entity.UserRole;
import com.ssurbar.survey.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
