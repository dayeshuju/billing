package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.TbYhlx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户类型 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbYhlxMapper extends BaseMapper<TbYhlx> {

    Integer findCount(@Param("yhlx") TbYhlx yhlx, @Param("sSearch") String sSearch);

    List<TbYhlx> findObject(@Param("yhlx") TbYhlx yhlx,
                            @Param("iDisplayStart") Integer iDisplayStart,
                            @Param("iDisplayLength") Integer iDisplayLength,
                            @Param("sSearch") String sSearch);

    TbYhlx findYhlxByUserType(String userType);
}
