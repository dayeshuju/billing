package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysMenus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */

//@Component
@Repository
public interface SysMenusMapper extends BaseMapper<SysMenus> {
    List<SysMenus> getGnfls();
}
