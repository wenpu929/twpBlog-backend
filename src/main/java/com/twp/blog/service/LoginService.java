package com.twp.blog.service;

import com.twp.blog.dao.pojo.SysUser;
import com.twp.blog.vo.Result;
import com.twp.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {
//登录功能
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParam loginParam);

}
