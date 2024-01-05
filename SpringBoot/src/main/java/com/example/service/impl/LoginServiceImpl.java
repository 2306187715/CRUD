package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.example.config.stpInterfaceImpl;
import com.example.mapper.StuMapper;
import com.example.mapper.TeacherMapper;
import com.example.pojo.Login;
import com.example.pojo.LoginResult;
import com.example.pojo.StpStuUtil;
import com.example.pojo.StpTeacherUtil;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    
    @Autowired
    private StuMapper stuMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private stpInterfaceImpl stpInterface;
    
    @Override
    public LoginResult loginValidation(Login login) {
        Integer userId = login.getUserId();
        String password = login.getPassword();
        
        switch (login.getRadio()) {
            case 3:
                if (stuMapper.selectByIdAndPassword(login)) {
                    StpStuUtil.login(userId);
                    String loginType = "stu";
                    return new LoginResult(StpStuUtil.getTokenInfo().tokenValue, stpInterface.getPermissionList(userId, loginType),null);
                }
                break;
            case 6:
                if (teacherMapper.selectByIdAndPassword(login)){
                    StpTeacherUtil.login(userId);
                    String loginType = "teacher";
                    return new LoginResult(StpTeacherUtil.getTokenInfo().tokenValue, stpInterface.getPermissionList(userId, loginType),null);
                }
                break;
            case 9:
                if (userId.equals(123123) && password.equals("123123")) {
                    StpUtil.login(userId);
                    String loginType = "admin";
                    return new LoginResult(StpUtil.getTokenInfo().tokenValue, stpInterface.getPermissionList(userId, loginType),null);
                    
                } else {
                    return new LoginResult(null,null,"账号或密码错误");
                }
        }
        return new LoginResult(null,null,"账号或密码错误");
    }
}
