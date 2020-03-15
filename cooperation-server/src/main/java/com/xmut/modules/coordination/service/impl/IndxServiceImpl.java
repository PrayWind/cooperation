package com.xmut.modules.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.Query;
import com.xmut.modules.coordination.dao.IndxDao;
import com.xmut.modules.coordination.entity.IndxEntity;
import com.xmut.modules.coordination.entity.ReportEntity;
import com.xmut.modules.coordination.service.IndxService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("indxService")
public class IndxServiceImpl extends ServiceImpl<IndxDao, IndxEntity> implements IndxService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("search");
        IPage<IndxEntity> page = this.page(
                new Query<IndxEntity>().getPage(params),
                new QueryWrapper<IndxEntity>().lambda().like(IndxEntity::getName, name)
        );

        return new PageUtils(page);
    }

}