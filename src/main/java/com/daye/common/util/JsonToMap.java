package com.daye.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class JsonToMap {

    public static Map<String,String> jsonToMap(String jsonStr){
        JSONArray jsonArray = (JSONArray) JSON.parse(jsonStr);
        Map<String,String> jsonMap = new HashMap<>();
        for (int i=0;i<jsonArray.size();i++){
           Object object = jsonArray.get(i);
           Map<String,String> map = (Map<String, String>) object;
           jsonMap.put(map.get("name"),map.get("value"));
        }
        return jsonMap;
    }
}
