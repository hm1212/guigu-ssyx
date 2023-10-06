package com.atguigu.ssyx.acl.service.impl;


import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> queryAllPermission() {
        return null;
    }


    /**
     * 递归删除子菜单
     * @param id
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeChildById(Long id,List<Long> sourceIdList) {
        //删除当前菜单
        sourceIdList.add(id);
        getChildPermission(id,sourceIdList);
        this.removeByIds(sourceIdList);
    }

    private void getChildPermission(Long id,List<Long> sourceIdList) {
        Permission permission = this.getById(id);
        //获取当前菜单的子菜单
        LambdaQueryChainWrapper<Permission> query = lambdaQuery();
        query.eq(Permission::getPid,permission.getId());
        List<Permission> childPermissionList = this.list(query);
        //所有子菜单的id集合
        childPermissionList.stream().forEach(item->{
            sourceIdList.add(item.getId());
            this.getChildPermission(item.getId(),sourceIdList);
        });

    }

}
