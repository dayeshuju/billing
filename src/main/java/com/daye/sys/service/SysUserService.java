package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysUser;

import java.util.Map;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysUserService extends IService<SysUser> {

    JsonResult updateObject(String oldpwd, String newpwd);

    Map<String,Object> findObject(Map<String,String> aoData);

    JsonResult addUser(SysUser user);

    JsonResult getUser(Integer id);

    JsonResult updateUser(SysUser user);

/*    JsonResult deleteUser(Integer id);*/

    JsonResult resetPassword(Integer id);

    JsonResult resetStatus(Integer id);
}
