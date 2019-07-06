package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbJfjl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 缴费记录 服务类
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface TbJfjlService extends IService<TbJfjl> {

    Map<String, Object> findObject(Map<String, String> aoData);

    JsonResult deleteById(Integer id);

    Map<String, Object> getHistoryJfjlList(String startTime, String endTime, String meterId, Map<String, String> aoData);

    JsonResult getJfjl(Long id);

    void exportJfjl(HttpServletRequest request, HttpServletResponse response, String payStatus, String meterId, String idCode, String startTime, String endTime);
}
