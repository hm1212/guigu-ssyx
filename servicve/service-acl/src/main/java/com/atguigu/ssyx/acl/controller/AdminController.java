package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/acl/user")
@AllArgsConstructor
@Api(tags = "用户接口")
public class AdminController {

    private AdminService adminService;


    //获取用户角色信息
    @GetMapping("/toAssign/{adminId}")
    public  Result toAssign(@PathVariable Long adminId){
       return adminService.getRoles(adminId);
    }

    //分配用户角色信息
    @PostMapping("doAssign")
    public  Result doAssign(@RequestParam Long adminId,
                            @RequestParam("roleId") List<Long> roleIdList){
        adminService.saveAdminRoles(adminId,roleIdList);
        return Result.success(null);

    }
    //用户列表(条件分页查询)
    @GetMapping("/{current}/{limit}")
    @ApiOperation("用户列表(条件分页查询)")
    public Result userList(@PathVariable Long current,
                           @PathVariable Long limit,
                           AdminQueryVo adminQueryVo
    ) {
        Page<Admin> pageParam = new Page<>(current, limit);
        IPage<Admin> page = adminService.selectPage(pageParam, adminQueryVo);
        return Result.success(page);
    }

    //根据ID查询用户
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询用户")
    public Result roleList(@PathVariable String id
    ) {
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    //保存用户
    @PostMapping("/save")
    @ApiOperation("保存用户")
    public Result save(@RequestBody Admin admin) {
        boolean flag = adminService.save(admin);
        return flag == true ? Result.success(admin) : Result.fail("保存失败");
    }

    //修改用户
    @PutMapping("/update")
    @ApiOperation("修改用户")
    public Result update(@RequestBody Admin admin) {
        boolean flag = adminService.updateById(admin);
        return flag == true ? Result.success(admin) : Result.fail("修改失败");
    }

    //根据ID删除用户
    @DeleteMapping("/remove/{id}")
    @ApiOperation("根据ID删除用户")
    public Result deleteById(@PathVariable Long id
    ) {
        Admin admin = adminService.getById(id);
        boolean flag = false;
        if (admin != null) {
            flag = adminService.removeById(admin.getId());
        }
        return flag == true ? Result.success(admin) : Result.fail("删除失败");
    }

    //根据ID批量删除用户
    @DeleteMapping ("/batchRemove")
    @ApiOperation("根据ID集合删除用户")
    public Result deleteByIdList(@RequestBody List<Long> idList
    ) {
        boolean flag = adminService.removeByIds(idList);
        return flag == true ? Result.success("批量删除成功") : Result.fail("删除失败");
    }
}
