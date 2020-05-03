package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.ReportEntity;

import java.util.Map;

public interface ReportService extends IService<ReportEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer insertReport(ReportEntity report);
}

