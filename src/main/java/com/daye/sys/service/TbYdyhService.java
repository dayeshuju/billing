package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbYdyh;

import java.util.Map;

/**
 * <p>
 * 用电用户 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbYdyhService extends IService<TbYdyh> {

    JsonResult getYhlxlist();

    Map<String, Object> getYdyhList(Map<String, String> aoData);

    JsonResult getYdyh(Long id);

    JsonResult updateYdyh(TbYdyh ydyh);

    JsonResult addYdyh(TbYdyh ydyh);

/*    JsonResult deleteYdyh(Integer id);*/
}
