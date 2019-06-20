package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.sys.entity.SysMenus;
import com.daye.sys.mapper.SysMenusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */

public interface SysMenusService extends IService<SysMenus> {
    List<SysMenus> getGnfls();

}
