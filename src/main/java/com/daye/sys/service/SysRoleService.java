package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getObject();

    Map<String, Object> getAuthoritylist(Map<String, String> aoData);


    JsonResult addAuthority(SysRole sysRole, Integer[] menuIds);

    JsonResult findOne(Integer id);

    JsonResult getrolemenus(Integer id);

    JsonResult updateObject(SysRole sysRole,Integer... menuIds);

/*    JsonResult deleteAuth(Integer id);*/
}
