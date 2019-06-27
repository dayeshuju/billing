package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysMenu;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysMenuService extends IService<SysMenu> {

    JsonResult loadIntexMenus();

    JsonResult findZtreeMenuNodes();
}
