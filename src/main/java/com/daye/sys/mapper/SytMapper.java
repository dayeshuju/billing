package com.daye.sys.mapper;

import com.daye.sys.entity.vt.VT_Jfjl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 收银台 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SytMapper {
    Integer findCount(@Param("vt_jfjl") VT_Jfjl vt_jfjl, @Param("sSearch") String sSearch);

    List<VT_Jfjl> findObject(@Param("vt_jfjl") VT_Jfjl vt_jfjl,
                             @Param("iDisplayStart") Integer iDisplayStart,
                             @Param("iDisplayLength") Integer iDisplayLength,
                             @Param("sSearch") String sSearch);

    VT_Jfjl getJfyhById(Integer id);
}
