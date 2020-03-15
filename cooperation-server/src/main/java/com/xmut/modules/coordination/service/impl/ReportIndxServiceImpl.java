package com.xmut.modules.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.Query;
import com.xmut.modules.coordination.dao.ReportIndxDao;
import com.xmut.modules.coordination.entity.ReportIndxEntity;
import com.xmut.modules.coordination.service.ReportIndxService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("projectIndxService")
public class ReportIndxServiceImpl extends ServiceImpl<ReportIndxDao, ReportIndxEntity> implements ReportIndxService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReportIndxEntity> page = this.page(
                new Query<ReportIndxEntity>().getPage(params),
                new QueryWrapper<ReportIndxEntity>()
        );

        return new PageUtils(page);
    }

}