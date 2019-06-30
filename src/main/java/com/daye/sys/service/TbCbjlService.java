package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.sys.entity.TbCbjl;

import java.util.Map;

/**
 * <p>
 * 抄表记录 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbCbjlService extends IService<TbCbjl> {

    Map<String, Object> findObject(Map<String, String> aoData);

    Map<String, Object> getHistoryCbjlList(Integer meterId);
}
