package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbYhlx;

import java.util.Map;

/**
 * <p>
 * 用户类型 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbYhlxService extends IService<TbYhlx> {

    //2019-06-29 刘博 添加查询用户类型数据方法
    Map<String,Object> findObject(Map<String, String> aoData);

    JsonResult addYhlx(TbYhlx yhlx);

    JsonResult getYhlx(Long id);

    JsonResult updateYhlx(TbYhlx yhlx);

/*    JsonResult deleteYhlx(Long id);*/
}
