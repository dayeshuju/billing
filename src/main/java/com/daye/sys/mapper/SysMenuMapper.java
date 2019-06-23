package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> findPermissions(@Param("menuIds") Integer... menuIds);

    @Select({"SELECT * FROM sysMenus"})
    List<SysMenu> findObject();
}
