package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.ReportUserEntity;

import java.util.Map;

/**
 * @author whf
 */
public interface ReportUserService extends IService<ReportUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

