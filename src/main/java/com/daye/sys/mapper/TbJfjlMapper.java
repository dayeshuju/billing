package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.TbJfjl;
import com.daye.sys.entity.vt.VT_Jfjl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 缴费记录 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbJfjlMapper extends BaseMapper<TbJfjl> {

    Integer findCount(@Param("vt_jfjl") VT_Jfjl vt_jfjl, @Param("sSearch") String sSearch);

    List<VT_Jfjl> findObject(@Param("vt_jfjl") VT_Jfjl vt_jfjl,
                             @Param("iDisplayStart") Integer iDisplayStart,
                             @Param("iDisplayLength") Integer iDisplayLength,
                             @Param("sSearch") String sSearch);

    Integer findCountByid(
            @Param("meterId") String meterId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime);

    List<VT_Jfjl> findObjectById(
            @Param("meterId") String meterId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("iDisplayStart") Integer iDisplayStart,
            @Param("iDisplayLength") Integer iDisplayLength);

    VT_Jfjl getJfjl(@Param("id") Long id);

    List<VT_Jfjl> selectByParameters(@Param("payStatus") String payStatus,
                                     @Param("meterId") String meterId,
                                     @Param("idCode") String idCode,
                                     @Param("startTime") String startTime,
                                     @Param("endTime") String endTime);

    void cleanUp2Y();
}
