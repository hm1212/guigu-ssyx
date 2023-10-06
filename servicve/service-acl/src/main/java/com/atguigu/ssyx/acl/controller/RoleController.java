package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/role")
@AllArgsConstructor
@Api(tags = "角色接口")
@CrossOrigin
public class RoleController {

    private RoleService roleService;

    //角色列表(条件分页查询)
    @GetMapping("/{current}/{limit}")
    @ApiOperation("角色列表(条件分页查询)")
    public Result roleList(@PathVariable Long current,
                           @PathVariable Long limit,
                           RoleQueryVo roleQueryVo
    ) {
        Page<Role> pageParam = new Page<>(current, limit);
        IPage<Role> page = roleService.selectPage(pageParam, roleQueryVo);
        return Result.success(page);
    }

    //根据ID查询角色
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询角色")
    public Result roleList(@PathVariable String id
    ) {
        Role role = roleService.getBaseMapper().selectById(id);
        return Result.success(role);
    }

    //保存角色
    @PostMapping("/save")
    @ApiOperation("保存角色")
    public Result save(@RequestBody Role role) {
        boolean flag = roleService.save(role);
        return flag == true ? Result.success(role) : Result.fail("保存失败");
    }

    //修改角色
    @PutMapping("/update")
    @ApiOperation("修改角色")
    public Result update(@RequestBody Role role) {
        boolean flag = roleService.updateById(role);
        return flag == true ? Result.success(role) : Result.fail("修改失败");
    }

    //根据ID删除角色
    @DeleteMapping("/remove/{id}")
    @ApiOperation("根据ID删除角色")
    public Result deleteById(@PathVariable Long id
    ) {
        Role role = roleService.getById(id);
        boolean flag = false;
        if (role != null) {
            flag = roleService.removeById(role.getId());
        }
        return flag == true ? Result.success(role) : Result.fail("删除失败");
    }

    //根据ID批量删除角色
    @DeleteMapping ("/batchRemove")
    @ApiOperation("根据ID集合删除角色")
    public Result deleteByIdList(@RequestBody List<Long> idList
    ) {
        boolean flag = roleService.removeByIds(idList);
        return flag == true ? Result.success("批量删除成功") : Result.fail("删除失败");
    }


}
