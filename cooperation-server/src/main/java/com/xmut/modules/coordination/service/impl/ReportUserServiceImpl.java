package com.xmut.modules.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.Query;
import com.xmut.modules.coordination.dao.ReportUserDao;
import com.xmut.modules.coordination.entity.ReportUserEntity;
import com.xmut.modules.coordination.service.ReportUserService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("reportUserService")
public class ReportUserServiceImpl extends ServiceImpl<ReportUserDao, ReportUserEntity> implements ReportUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReportUserEntity> page = this.page(
                new Query<ReportUserEntity>().getPage(params),
                new QueryWrapper<ReportUserEntity>()
        );

        return new PageUtils(page);
    }

}