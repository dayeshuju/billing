package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

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

    Integer findCount(@Param("role") SysRole role, @Param("sSearch") String sSearch);

    List<SysRole> findRoles(@Param("role") SysRole role, @Param("iDisplayStart") Integer iDisplayStart,@Param("iDisplayLength") Integer iDisplayLength,@Param("sSearch") String sSearch);


    Integer findOneByName(String name);

    SysRole findObjectByName(String name);
}
