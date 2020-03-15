package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.UserOfficeEntity;

import java.util.Map;

/**
 * @author whf
 */
public interface UserOfficeService extends IService<UserOfficeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

