package com.xmut.modules.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.Query;
import com.xmut.modules.coordination.dao.OfficeDao;
import com.xmut.modules.coordination.entity.OfficeEntity;
import com.xmut.modules.coordination.service.OfficeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("officeService")
public class OfficeServiceImpl extends ServiceImpl<OfficeDao, OfficeEntity> implements OfficeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OfficeEntity> page = this.page(
                new Query<OfficeEntity>().getPage(params),
                new QueryWrapper<OfficeEntity>()
        );

        return new PageUtils(page);
    }

}