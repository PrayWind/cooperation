package com.xmut.modules.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.common.utils.PageUtils;
import com.xmut.modules.coordination.entity.IndxEntity;

import java.util.List;
import java.util.Map;

/**
 * @author whf
 */
public interface IndxService extends IService<IndxEntity> {

    PageUtils queryPage(Map<String, Object> params);

    // 因为涉及到两个表的分页及模糊查询，所以自己写分页
    List<IndxEntity> getIndxsHavePagination(String reportId, String search, Integer start, Integer pageSize);

    // 获取getIndxsHavePagination方法的总数(totalCount)，前端需要
    Integer getIndxsCountHavePagination(String reportId, String search, Integer start, Integer pageSize);
}

