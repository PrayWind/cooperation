package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.ReportIndxUserEntity;

import java.util.Map;

public interface ReportIndxUserService extends IService<ReportIndxUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

