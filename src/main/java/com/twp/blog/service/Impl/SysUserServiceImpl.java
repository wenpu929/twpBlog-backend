package com.twp.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.twp.blog.dao.mapper.SysUserMapper;
import com.twp.blog.dao.pojo.SysUser;
import com.twp.blog.service.LoginService;
import com.twp.blog.service.SysUserService;
import com.twp.blog.vo.ErrorCode;
import com.twp.blog.vo.LoginUserVo;
import com.twp.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    LoginService loginService;
    @Override
    public SysUser findUserById(Long id) {


        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser==null)
        {
            sysUser=new SysUser();
            sysUser.setNickname("无名氏");
        }
        return sysUser;
    }

    @Override
    public SysUser finduser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /*
        * 1 token合法性校验 是否为空 解析是否成功 redis 是否存在
        * 2 如果校验失败， 返回错误
        * 3 如果成功 返回对应结果
        * */

        SysUser sysUser=loginService.checkToken(token);
        if(sysUser==null)
        {
            return Result.fail(ErrorCode.Token_error.getCode(), ErrorCode.Token_error.getMsg());
        }
        LoginUserVo loginUserVo=new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);
    }

}

