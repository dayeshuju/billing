package com.daye.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daye.sys.entity.SysLog;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    void cleanUp2Y();
}
