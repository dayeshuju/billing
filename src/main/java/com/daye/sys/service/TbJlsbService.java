package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbJlsb;
import com.daye.sys.entity.vt.VT_Jlsb;

import java.util.Map;

/**
 * <p>
 * 计量设备 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbJlsbService extends IService<TbJlsb> {

    JsonResult getJlsb(Long id);

    JsonResult addJlsb(VT_Jlsb jlsb);

    JsonResult updateJlsb(VT_Jlsb jlsb);

    JsonResult deleteJlsb(Long id);

    Map<String,Object> findObject(Map<String, String> aoData);
}
