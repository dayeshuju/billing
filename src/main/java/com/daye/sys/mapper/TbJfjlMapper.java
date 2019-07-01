package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.TbJfjl;
import com.daye.sys.entity.vt.VT_Cbjl;
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

    List<VT_Jfjl> findObject(VT_Jfjl vt_jfjl, Integer iDisplayStart, Integer iDisplayLength, String sSearch);

}
