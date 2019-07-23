package com.daye.sys.service;

import com.daye.common.vo.JsonResult;

import java.util.Map;

public interface SytService {
    Map<String, Object> findObject(Map<String, String> aoData);

    JsonResult getJfyh(Integer id);

    JsonResult saveJfyh(Integer id, Double actualAmount, String note,Integer cashier);

    Map<String, Object> printFactura(Integer id);
}
