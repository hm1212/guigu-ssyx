package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminMapper;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

@Service
@AllArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private AdminRoleService adminRoleService;

    private RoleService roleService;

    @Override
    public IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String name = adminQueryVo.getName();
        String username = adminQueryVo.getUsername();
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(username))
            adminLambdaQueryWrapper.eq(Admin::getUsername, username);
        if (!StringUtils.isEmpty(name))
            adminLambdaQueryWrapper.eq(Admin::getName, name);
        return this.getBaseMapper().selectPage(pageParam, adminLambdaQueryWrapper);
    }

    @Override
    public Result getRoles(Long adminId) {
        //获取所有角色
        List<Role> allRolesList = roleService.list();
        //根据用户id获取用户和角色关联信息
        LambdaQueryWrapper<AdminRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminRole::getAdminId, adminId);
        List<AdminRole> adminRoleList = adminRoleService.list(queryWrapper);
        //判断角色信息中是否存在关联的角色信息
        ArrayList<Role> assignRoles = new ArrayList<>();
        allRolesList.listIterator()
                    .forEachRemaining(role -> {
                        adminRoleList.listIterator()
                                     .forEachRemaining(adminRole -> {
                                         if (adminRole.getRoleId() == role.getId())
                                             assignRoles.add(role);
                                     });
                    });
        WeakHashMap<String, Object> map = new WeakHashMap<>();
        map.put("allRolesList", allRolesList);
        map.put("assignRoles", assignRoles);
        return Result.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAdminRoles(Long adminId, List<Long> roleIdList) {
        //根据用户ID删除用户角色id
        adminRoleService.removeById(adminId);
        //重新分配
        //根据roleIdList查询角色
        ArrayList<AdminRole> adminRoles = new ArrayList<>();
        roleIdList.listIterator()
              .forEachRemaining(roleId -> {
                  Role role = roleService.getById(roleId);
                  if (role != null) {
                      AdminRole adminRole = new AdminRole();
                      adminRole.setAdminId(adminId);
                      adminRole.setRoleId(role.getId());
                      adminRoles.add(adminRole);
                  }

              });
        boolean flag = adminRoleService.saveBatch(adminRoles);
    }
}
