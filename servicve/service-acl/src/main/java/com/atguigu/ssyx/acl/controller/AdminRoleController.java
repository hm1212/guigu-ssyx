package com.atguigu.ssyx.acl.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/admin/acl/admin/role")
@AllArgsConstructor
@Api(tags = "用户角色接口")
public class AdminRoleController {
}
