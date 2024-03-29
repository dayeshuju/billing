package com.daye.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daye.common.vo.JsonResult;
import com.daye.sys.entity.TbCbjl;
import org.springframework.web.multipart.MultipartFile;

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

    Map<String, Object> getHistoryCbjlList(String startTime,String endTime,String meterId, Map<String,String> aoData);

/*    JsonResult deleteCbjl(Integer id);*/

    JsonResult uploadCbjl(MultipartFile file);
}
