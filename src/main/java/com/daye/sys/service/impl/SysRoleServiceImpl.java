package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysRole;
import com.daye.sys.mapper.SysRoleMapper;
import com.daye.sys.mapper.SysRoleMenuMapper;
import com.daye.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @RequiredLog(operation = "获得所有角色信息")
    public List<SysRole> getObject() {
        List<SysRole> sysRoleList = sysRoleMapper.findObject();
        return sysRoleList;
    }

    @Override
    @RequiredLog(operation = "获得所有权限类型")
    public Map<String, Object> getAuthoritylist(Map<String, String> aoData) {
        SysRole role = new SysRole();
        if(!StringUtils.isEmpty(aoData.get("sSearch_0").trim())){
            Integer id = Integer.valueOf(aoData.get("sSearch_0"));
            role.setId(id);
        }
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) role.setName(aoData.get("sSearch_1"));
        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;
        Integer count = sysRoleMapper.findCount(role,sSearch);
        List<SysRole> sysRoleList = sysRoleMapper.findRoles(role,iDisplayStart,iDisplayLength,sSearch);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",sysRoleList);
        return map;
    }

    @Override
    @RequiredLog(operation = "添加权限类型")
    public JsonResult getpername(SysRole sysRole, Integer[] menuIds) {
        if(sysRole.getName()==null||sysRole.getName()==""){
            return new JsonResult(new Throwable("权限名称不能为空"));
        }
        if(menuIds==null||menuIds.length==0){
            return new JsonResult(new Throwable("请分配权限"));
        }

        Integer count= sysRoleMapper.findOneByName(sysRole.getName());
        if(count>0){
            return new JsonResult(new Throwable("权限名称重复"));
        }

        sysRoleMapper.insert(sysRole);


        sysRoleMenuMapper.insertRoleMenus(menuIds,sysRole.getId());


        return new JsonResult("新增成功");
    }

    @Override
    @RequiredLog(operation = "根据Id获得权限类型")
    public JsonResult findOne(Integer id) {
        SysRole sysRole= sysRoleMapper.selectById(id);
        return new JsonResult(sysRole);
    }

    @Override
    @RequiredLog(operation = "根据RoleId获得菜单Id")
    public JsonResult getrolemenus(Integer id) {
       List<Integer> list= sysRoleMenuMapper.findMenuIdsByRoleId(id);
        return new JsonResult(list);
    }
}
