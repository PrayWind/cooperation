package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.IndxEntity;

import java.util.Map;

/**
 * @author whf
 */
public interface IndxService extends IService<IndxEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

