package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.exception.ServiceException;
import com.daye.common.util.ShiroUtils;
import com.daye.common.vo.PageInfo;
import com.daye.sys.entity.SysUser;
import com.daye.sys.mapper.SysUserMapper;
import com.daye.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    @RequiredLog(value = 0, operation = "修改个人密码")
    public boolean updateObject(String oldpwd, String newpwd) {
        if(StringUtils.isEmpty(oldpwd) || StringUtils.isEmpty(newpwd)){
            throw new ServiceException("数据不合法！！！");
        }
        SysUser user = sysUserMapper.selectById(ShiroUtils.getPrincipal().getId());

        SimpleHash oldM5pwd = new SimpleHash("MD5",oldpwd,user.getSalt());
        if(oldM5pwd.toHex().equals(user.getPassword())){
            String newSalt = UUID.randomUUID().toString();
            user.setSalt(newSalt);
            SimpleHash newM5pwd = new SimpleHash("MD5",newpwd,newSalt);
            user.setPassword(newM5pwd.toHex());
        }else {
            throw new ServiceException("旧密码错误");
        }
        sysUserMapper.updateById(user);
        return true;
    }

    @Override
    @RequiredLog(value = 0, operation = "获得所有用户信息")
    public PageInfo<SysUser> findObject(Map<String,String> aoData) {
        Integer count = sysUserMapper.findCount();
        PageInfo<SysUser> pageInfo = new PageInfo<>();
        pageInfo.setITotalDisplayRecords(count);
        pageInfo.setITotalRecords(count);
        Object drawStr = aoData.get("sEcho");
        Integer draw = (Integer) drawStr;
        pageInfo.setDraw(draw);
        List<SysUser> sysUserList = sysUserMapper.findObject(aoData);
        pageInfo.setAaData(sysUserList);
        return pageInfo;
    }
}
