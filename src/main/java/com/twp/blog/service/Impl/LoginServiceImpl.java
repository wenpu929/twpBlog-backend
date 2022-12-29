package com.twp.blog.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.twp.blog.dao.pojo.SysUser;
import com.twp.blog.service.LoginService;
import com.twp.blog.service.SysUserService;
import com.twp.blog.utils.JWTUtils;
import com.twp.blog.vo.ErrorCode;
import com.twp.blog.vo.Result;
import com.twp.blog.vo.params.LoginParam;
import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private static final String salt = "mszlu!@#";
    @Override
    public Result login(LoginParam loginParam) {
        /*1检查参数是否合法
        2 根据用户名和密码去user表中查询是否存在
        3如果不存在，登录失败
        4如果存在，使用jwt 生成token返回给前端
        5token放入redis
        * */

        String account= loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password))
        {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password= DigestUtils.md5Hex(password+salt);
        SysUser sysUser=sysUserService.finduser(account,password);
        if(sysUser==null)
        {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        //redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);

    }
}
