package com.xmut.modules.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.Query;
import com.xmut.modules.coordination.dao.ReportIndxUserDao;
import com.xmut.modules.coordination.entity.ReportIndxUserEntity;
import com.xmut.modules.coordination.service.ReportIndxUserService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("projectIndxUserService")
public class ReportIndxUserServiceImpl extends ServiceImpl<ReportIndxUserDao, ReportIndxUserEntity> implements ReportIndxUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReportIndxUserEntity> page = this.page(
                new Query<ReportIndxUserEntity>().getPage(params),
                new QueryWrapper<ReportIndxUserEntity>()
        );

        return new PageUtils(page);
    }

}