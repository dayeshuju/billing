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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    @Autowired
    HttpServletRequest request;

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
    //@Transactional
    public JsonResult addAuthority(SysRole sysRole, Integer[] menuIds) {
        String lang = request.getHeader("Accept-Language").substring(0,2);
        if("zh".equals(lang)){
            if(StringUtils.isEmpty(sysRole.getName().trim())){
                return new JsonResult(new Throwable("权限名称不能为空"));
            }
            if(menuIds==null||menuIds.length==0){
                return new JsonResult(new Throwable("请分配权限"));
            }

            Integer count= sysRoleMapper.findOneByName(sysRole.getName().trim());
            if(count>0){
                return new JsonResult(new Throwable("权限名称重复"));
            }
            sysRole.setName(sysRole.getName().trim());
            sysRoleMapper.insert(sysRole);
            sysRoleMenuMapper.insertRoleMenus(menuIds,sysRole.getId());
            return new JsonResult("新增成功");
        }else {
            if(StringUtils.isEmpty(sysRole.getName().trim())){
                return new JsonResult(new Throwable("No pueda estar vacío nombre del permiso "));
            }
            if(menuIds==null||menuIds.length==0){
                return new JsonResult(new Throwable("Asigne permisos"));
            }

            Integer count= sysRoleMapper.findOneByName(sysRole.getName().trim());
            if(count>0){
                return new JsonResult(new Throwable("Nombre de permiso repetido"));
            }
            sysRole.setName(sysRole.getName().trim());
            sysRoleMapper.insert(sysRole);
            sysRoleMenuMapper.insertRoleMenus(menuIds,sysRole.getId());
            return new JsonResult("Añadido con éxito ");
        }

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

    @Override
    //@Transactional
    @RequiredLog(operation = "修改权限类型")
    public JsonResult updateObject(SysRole sysRole, Integer... menuIds) {
        String lang = request.getHeader("Accept-Language").substring(0,2);
        String json = "";
        if("zh".equals(lang)){
            if(StringUtils.isEmpty(sysRole.getName().trim())) return new JsonResult(new Throwable("权限类型名称不能为空"));
            if(menuIds == null || menuIds.length == 0) return new JsonResult(new Throwable("请分配权限"));
            SysRole oldRole = sysRoleMapper.findObjectByName(sysRole.getName().trim());
            if(oldRole != null && oldRole.getId() != sysRole.getId()) return new JsonResult(new Throwable("修改后的权限名称已存在"));
            json="修改成功！";
        }else {//西班牙语
            if(StringUtils.isEmpty(sysRole.getName().trim())) return new JsonResult(new Throwable("No pueda estar vacío el nombre del tipo de permiso "));
            if(menuIds == null || menuIds.length == 0) return new JsonResult(new Throwable("Asigne permisos"));
            SysRole oldRole = sysRoleMapper.findObjectByName(sysRole.getName().trim());
            if(oldRole != null && oldRole.getId() != sysRole.getId()) return new JsonResult(new Throwable("Ya existe nombre de permiso modificado "));
            json="Modificado exitosamente！";
        }

        sysRole.setName(sysRole.getName().trim());
        sysRole.setModifiedTime(new Date());
        sysRoleMapper.updateById(sysRole);
        sysRoleMenuMapper.deleteByRoleId(sysRole.getId());
        sysRoleMenuMapper.insertRoleMenus(menuIds,sysRole.getId());
        return new JsonResult(json);
    }

/*    @Override
    @RequiredLog(operation = "根据id删除权限")
    @Transactional
    public JsonResult deleteAuth(Integer id) {
        if(sysRoleMapper.deleteById(id)==0) return new JsonResult(new Throwable("删除失败！！！"));
        if(sysRoleMenuMapper.deleteByRoleId(id)==0) return new JsonResult(new Throwable("删除失败！！！"));
        return new JsonResult("删除成功");
    }*/
}
