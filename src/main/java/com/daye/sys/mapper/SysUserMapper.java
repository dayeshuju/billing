package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findUserByNickname(String nickname);

    Integer findRoleIdById(Integer id);

    List<SysUser> findObject(Map<String,String> aoData);

    @Select({"SELECT count(1) FROM sysUsers"})
    Integer findCount();
}
