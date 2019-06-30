package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.TbYdyh;
import com.daye.sys.entity.TbYhlx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用电用户 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbYdyhMapper extends BaseMapper<TbYdyh> {

    List<TbYhlx> getYhlxlist();

    Integer findCount(@Param("ydyh") TbYdyh ydyh, @Param("sSearch") String sSearch);

    List<TbYdyh> getYdyhList(@Param("ydyh") TbYdyh ydyh, @Param("iDisplayStart") Integer iDisplayStart, @Param("iDisplayLength") Integer iDisplayLength, @Param("sSearch") String sSearch);

    TbYdyh getYdyhByIdCode(String idCode);

    Integer getQfjl(Integer id);
}
