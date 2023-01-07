package com.twp.blog.service;

import com.twp.blog.dao.pojo.SysUser;
import com.twp.blog.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser finduser(String account, String password);
    //根据token查询用户信息
    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);
}
