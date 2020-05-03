package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.ReportIndxEntity;

import java.util.Map;

public interface ReportIndxService extends IService<ReportIndxEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

