package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<SysUser> findObject(@Param("user") SysUser user,@Param("iDisplayStart") Integer iDisplayStart,@Param("iDisplayLength") Integer iDisplayLength,@Param("sSearch") String sSearch);

    Integer findCount(@Param("user") SysUser user,@Param("sSearch") String sSearch);
}
