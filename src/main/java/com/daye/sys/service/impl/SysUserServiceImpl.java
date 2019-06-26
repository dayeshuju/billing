package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.exception.ServiceException;
import com.daye.common.util.ShiroUtils;
import com.daye.sys.entity.SysUser;
import com.daye.sys.mapper.SysUserMapper;
import com.daye.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
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
    public Map<String,Object> findObject(Map<String,String> aoData) {

        SysUser user = new SysUser();
        if(!StringUtils.isEmpty(aoData.get("sSearch_0").trim())){
            Integer id = Integer.valueOf(aoData.get("sSearch_0"));
            user.setId(id);
        }
        if(!StringUtils.isEmpty(aoData.get("sSearch_1").trim())) user.setNickname(aoData.get("sSearch_1"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_2").trim())) user.setName(aoData.get("sSearch_2"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_3").trim())) user.setMobile(aoData.get("sSearch_3"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_4").trim())) user.setEmail(aoData.get("sSearch_4"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_5").trim())) user.setNote(aoData.get("sSearch_5"));

        String sSearch = aoData.get("sSearch");
        Object iDisplayStartObj = aoData.get("iDisplayStart");
        Integer iDisplayStart = (Integer) iDisplayStartObj;
        Object iDisplayLengthsObj = aoData.get("iDisplayLength");
        Integer iDisplayLength = (Integer) iDisplayLengthsObj;
        Object sEchoStr = aoData.get("sEcho");
        Integer sEcho = (Integer) sEchoStr;
        Integer count = sysUserMapper.findCount(user,sSearch);
        List<SysUser> sysUserList = sysUserMapper.findObject(user,iDisplayStart,iDisplayLength,sSearch);
        Map<String,Object> map = new HashMap<>();
        map.put("iTotalDisplayRecords",count);
        map.put("iTotalRecords",count);
        map.put("sEcho",sEcho);
        map.put("aaData",sysUserList);
        return map;
    }
}
