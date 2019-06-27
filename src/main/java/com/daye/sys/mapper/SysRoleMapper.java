package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findObject();

    Integer findCount(SysRole role, String sSearch);

    List<SysRole> findRoles(SysRole role, Integer iDisplayStart, Integer iDisplayLength, String sSearch);
}
