package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.OfficeEntity;

import java.util.Map;

/**
 * @author whf
 */
public interface OfficeService extends IService<OfficeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

