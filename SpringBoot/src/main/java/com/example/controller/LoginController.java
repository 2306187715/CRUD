package com.example.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;
import com.example.pojo.Login;
import com.example.pojo.LoginResult;
import com.example.pojo.Result;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    @PostMapping
    public Result login(@RequestBody Login login) {
        try {
            LoginResult loginResult = loginService.loginValidation(login);
            return loginResult.getMsg() == null ? Result.success(loginResult) : Result.error(loginResult.getMsg());
        } catch (Exception e) {
            return Result.error("未知错误");
        }
    }
    
    
}
