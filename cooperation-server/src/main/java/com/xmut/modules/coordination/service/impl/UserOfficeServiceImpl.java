package com.xmut.modules.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.Query;
import com.xmut.modules.coordination.dao.UserOfficeDao;
import com.xmut.modules.coordination.entity.UserOfficeEntity;
import com.xmut.modules.coordination.service.UserOfficeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userOfficeService")
public class UserOfficeServiceImpl extends ServiceImpl<UserOfficeDao, UserOfficeEntity> implements UserOfficeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserOfficeEntity> page = this.page(
                new Query<UserOfficeEntity>().getPage(params),
                new QueryWrapper<UserOfficeEntity>()
        );

        return new PageUtils(page);
    }

}