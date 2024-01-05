package com.example.service;

import com.example.pojo.Login;
import com.example.pojo.LoginResult;

public interface LoginService {
    /**
     * 用于验证登录是否正确
     */
    LoginResult loginValidation(Login login);
}
