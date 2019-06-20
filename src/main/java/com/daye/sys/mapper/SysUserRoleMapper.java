package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<Integer> findRoleIdsByUserId(Integer id);
}
