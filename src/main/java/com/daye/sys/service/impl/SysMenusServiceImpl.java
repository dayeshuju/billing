package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.sys.entity.SysMenus;
import com.daye.sys.mapper.SysMenusMapper;
import com.daye.sys.service.SysMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class SysMenusServiceImpl extends ServiceImpl<SysMenusMapper, SysMenus> implements SysMenusService {

    @Autowired
    SysMenusMapper sysMenusMapper;

    @Override
    public List<SysMenus> getGnfls() {
        return sysMenusMapper.getGnfls();
    }
}
