package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/acl/permission")
@AllArgsConstructor
@Api(tags = "菜单接口")
public class PermissionController {

    private PermissionService permissionService;

    //查询所有菜单（树形结构）
    @GetMapping()
    @ApiOperation("查询所有菜单（树形结构）")
    public Result getPermissions(){
       List<Permission> list=permissionService.queryAllPermission();
       return Result.success(list);
    }
    //添加菜单
    @PostMapping("save")
    @ApiOperation("添加菜单")
    public Result savePermission(@RequestBody Permission permission){
        boolean save = permissionService.save(permission);
        return Result.success(save);
    }
    //修改菜单
    @PutMapping("update")
    @ApiOperation("修改菜单")
    public Result updatePermission(@RequestBody Permission permission){
        boolean save = permissionService.updateById(permission);
        return Result.success(save);
    }
    //删除菜单
    @PutMapping("remove/{id}")
    @ApiOperation("删除菜单")
    public Result deletePermission(@PathVariable Long id){
        ArrayList<Long> sourceIdList = new ArrayList<>();
        permissionService.removeChildById(id,sourceIdList);
        return Result.success(null);
    }

}
