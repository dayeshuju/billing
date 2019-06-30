package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.TbCbjl;
import com.daye.sys.entity.vt.VT_Cbjl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 抄表记录 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbCbjlMapper extends BaseMapper<TbCbjl> {

    Integer findCount(@Param("vt_cbjl") VT_Cbjl vt_cbjl,@Param("sSearch") String sSearch);

    List<VT_Cbjl> findObject(@Param("vt_cbjl") VT_Cbjl vt_cbjl, @Param("iDisplayStart") Integer iDisplayStart, @Param("iDisplayLength") Integer iDisplayLength, @Param("sSearch") String sSearch);
}
