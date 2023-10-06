package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
@Api(tags = "登录接口")
@CrossOrigin//跨域
public class IndexController {

    /**
     * 1、请求登陆的login
     */
    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result login() {
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.success(map);
    }

    /**
     * 2、get user info
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取信息")
    public Result info() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.success(map);
    }

    /**
     * 2、logout
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出")
    public Result logout() {
        Map<String,Object> map = new HashMap<>();
        return Result.success(map);
    }

}
