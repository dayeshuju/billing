package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<Integer> findMenuIdsByRoleId(@Param("roleIds") Integer... roleIds);
}
