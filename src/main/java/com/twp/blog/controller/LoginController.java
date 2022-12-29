package com.twp.blog.controller;

import com.twp.blog.service.LoginService;
import com.twp.blog.vo.Result;
import com.twp.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam)
    {
        return loginService.login(loginParam);
    }
}
