package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.sys.entity.SysRole;
import com.daye.sys.mapper.SysRoleMapper;
import com.daye.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    @RequiredLog(operation = "获得所有角色信息")
    public List<SysRole> getObject() {
        List<SysRole> sysRoleList = sysRoleMapper.findObject();
        return sysRoleList;
    }
}
