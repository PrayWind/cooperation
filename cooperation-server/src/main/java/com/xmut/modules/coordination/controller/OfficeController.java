package com.xmut.modules.coordination.controller;

import com.alibaba.fastjson.JSONObject;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.service.OfficeService;
import com.xmut.modules.sys.entity.SysUserEntity;
import com.xmut.modules.sys.service.SysUserService;
import com.xmut.modules.sys.service.SysUserTokenService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/office")
public class OfficeController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 测试方法
     */
    @RequestMapping("/test")
    public String test() {

        String test = "测试测试";

        return test;
    }

//    @RequestMapping("/user")
//    public Object userList() {
//        List<User> userList = userService.list();
//        return userList;
//    }

    /**
     * 登录验证
     */
    @PostMapping("/login")
    public R login(@RequestBody JSONObject data) {
        String username = data.getString("username");
        String password = data.getString("password");

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(username);

        //账号不存在、密码错误
        if (null == user || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(user.getUserId());

        return r;
    }
}
