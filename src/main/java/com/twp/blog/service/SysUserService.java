package com.twp.blog.service;

import com.twp.blog.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser finduser(String account, String password);

}
