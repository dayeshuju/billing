package com.daye.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daye.common.annotation.RequiredLog;
import com.daye.common.exception.ServiceException;
import com.daye.common.util.ShiroUtils;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.SysUser;
import com.daye.sys.mapper.SysUserMapper;
import com.daye.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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
            user.setModifiedTime(new Date());
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
        if(!StringUtils.isEmpty(aoData.get("sSearch_4").trim())) user.setOfficePhone(aoData.get("sSearch_4"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_5").trim())) user.setEmail(aoData.get("sSearch_5"));
        if(!StringUtils.isEmpty(aoData.get("sSearch_6").trim())) user.setNote(aoData.get("sSearch_6"));

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

    @Override
    @RequiredLog(operation = "添加系统用户")
    public JsonResult addUser(SysUser user) {
        if(StringUtils.isEmpty(user.getNickname())) return new JsonResult(new Throwable("登录名不能为空"));
        if(StringUtils.isEmpty(user.getName())) return new JsonResult(new Throwable("真实姓名不能为空"));
        if(user.getRoleId()==null || user.getRoleId() == 0) return new JsonResult(new Throwable("权限类型不能为空"));

        SysUser oldUser = sysUserMapper.findUserByNickname(user.getNickname());
        if(oldUser != null) return new JsonResult(new Throwable("系统用户已存在，请更改登录名"));
        String salt = UUID.randomUUID().toString();
        String password = new SimpleHash("MD5","c92bf378km",salt).toHex();
        user.setSalt(salt);
        user.setPassword(password);

        sysUserMapper.insert(user);
        return new JsonResult("添加成功");
    }

    @Override
    @RequiredLog(operation = "根据用户ID获取系统用户")
    public JsonResult getUser(Integer id) {
        if(id == null || id== 0) return new JsonResult(new Throwable("用户不存在"));
        SysUser user = sysUserMapper.selectById(id);
        user.setPassword("");
        user.setSalt("");
        return new JsonResult(user);
    }

    @Override
    @RequiredLog(operation = "修改系统用户信息")
    public JsonResult updateUser(SysUser user) {
        if(StringUtils.isEmpty(user.getNickname())) return new JsonResult(new Throwable("登录名不能为空"));
        if(StringUtils.isEmpty(user.getName())) return new JsonResult(new Throwable("真实姓名不能为空"));
        if(user.getRoleId()==null || user.getRoleId() == 0) return new JsonResult(new Throwable("权限类型不能为空"));
        user.setModifiedTime(new Date());
        if(sysUserMapper.updateById(user)==1){
            return new JsonResult("修改成功");
        }
        return new JsonResult(new Throwable("修改失败"));
    }

    @Override
    @RequiredLog(operation = "删除系统用户")
    public JsonResult deleteUser(Integer id) {
        SysUser user = sysUserMapper.selectById(id);
        if(user.getRoleId() == 1) return new JsonResult(new Throwable("禁止删除超级管理员"));
        if(sysUserMapper.deleteById(id)==1){
            return new JsonResult("删除成功");
        }
        return new JsonResult(new Throwable("删除失败"));
    }

    @Override
    @RequiredLog(operation = "重置密码")
    public JsonResult resetPassword(Integer id) {
        SysUser user = sysUserMapper.selectById(id);
        String salt = UUID.randomUUID().toString();
        String password = new SimpleHash("MD5","c92bf378km",salt).toHex();
        user.setSalt(salt);
        user.setPassword(password);
        user.setModifiedTime(new Date());
        if(sysUserMapper.updateById(user)==1){
            return new JsonResult("重置成功");
        }
        return new JsonResult(new Throwable("重置失败"));
    }

    @Override
    @RequiredLog(operation = "更改用户锁定状态")
    public JsonResult resetStatus(Integer id) {
        SysUser user = sysUserMapper.selectById(id);
        if(user.getRoleId() == 1) return new JsonResult(new Throwable("禁止锁定超级管理员"));
        Map<String,Object> map = new HashMap<>();
        String message = null;
        if(user.getValid() == null || user.getValid() == 0){
            user.setValid(1);
            message = "解锁成功";
            map.put("message",message);
            map.put("valid",1);
        }else{
            user.setValid(0);
            message = "锁定成功";
            map.put("message",message);
            map.put("valid",0);
        }
        user.setModifiedTime(new Date());
        if(sysUserMapper.updateById(user)==1) return new JsonResult(map);
        return new JsonResult(new Throwable("操作失败"));
    }
}
