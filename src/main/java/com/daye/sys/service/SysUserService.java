package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.PageInfo;
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

    boolean updateObject(String oldpwd, String newpwd);

    PageInfo<SysUser> findObject(Map<String,String> aoData);
}
