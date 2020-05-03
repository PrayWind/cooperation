package com.xmut.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.modules.sys.entity.SysLogEntity;
import com.xmut.common.utils.PageUtils;

import java.util.Map;

/**
 * 系统日志
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
