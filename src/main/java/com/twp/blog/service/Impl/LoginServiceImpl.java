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
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
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
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),15, TimeUnit.DAYS);
        return Result.success(token);

    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap==null){
            return null;
        }

        String userJson= redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);

        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /*
        * 1 判断参数是否合法
        * 2 判断账户是否存在， 存在 返回账户已经被注册
        * 3 如果账户不存在 注册用户
        * 4 生成token
        * 5 存入redis 并返回
        * 6 加上
        * */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(nickname))
        {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser= sysUserService.findUserByAccount(account);
        if(sysUser!=null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), "账户已经被注册");
        }
        sysUser=new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
//        sysUser.setAdmin(1); //1 为true
//        sysUser.setDeleted(0); // 0 为false
//        sysUser.setSalt("");
//        sysUser.setStatus("");
//        sysUser.setEmail("");
        this.sysUserService.save(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),15, TimeUnit.DAYS);
        return Result.success(token);
    }
}
