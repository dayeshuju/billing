package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.TbJlsb;
import com.daye.sys.entity.TbYdyh;
import com.daye.sys.entity.vt.VT_Jlsb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 计量设备 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbJlsbMapper extends BaseMapper<TbJlsb> {

    TbYdyh findYdyhByIdCode(String idCode);

    TbJlsb findJlsbByDoubleParams(@Param("meterId") String meterId, @Param("meterBoxId") String meterBoxId);

    Integer findCount(@Param("jlsb") VT_Jlsb jlsb, @Param("sSearch") String sSearch);

    List<VT_Jlsb> findObject(@Param("jlsb") VT_Jlsb jlsb,
                             @Param("iDisplayStart") Integer iDisplayStart,
                             @Param("iDisplayLength") Integer iDisplayLength,
                             @Param("sSearch") String sSearch);

    VT_Jlsb selectJlsbWithIdCodeById(Long id);
}
