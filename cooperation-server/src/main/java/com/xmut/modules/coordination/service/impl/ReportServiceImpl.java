package com.xmut.modules.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.Query;
import com.xmut.modules.coordination.dao.ReportDao;
import com.xmut.modules.coordination.entity.ReportEntity;
import com.xmut.modules.coordination.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("reportService")
public class ReportServiceImpl extends ServiceImpl<ReportDao, ReportEntity> implements ReportService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("search");
        IPage<ReportEntity> page = this.page(
                new Query<ReportEntity>().getPage(params),
                new QueryWrapper<ReportEntity>().lambda().like(ReportEntity::getName, name)
        );

        return new PageUtils(page);
    }

    @Override
    public Integer insertReport(ReportEntity report) {
        return baseMapper.insertReport(report);
    }

}