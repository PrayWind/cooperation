package com.xmut.modules.coordination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmut.modules.coordination.entity.IndxEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IndxDao extends BaseMapper<IndxEntity> {

    @Select("select i.* from tb_indx i join tb_report_indx ri on i.ID=ri.INDX_ID " +
            "where ri.REPORT_ID=#{reportId} and i.NAME like CONCAT('%',#{search},'%') limit #{start}, #{pageSize}")
    List<IndxEntity> getIndxsHavePagination(String reportId, String search, Integer start, Integer pageSize);

    @Select("select count(*) from tb_indx i join tb_report_indx ri on i.ID=ri.INDX_ID " +
            "where ri.REPORT_ID=#{reportId} and i.NAME like CONCAT('%',#{search},'%') limit #{start}, #{pageSize}")
    Integer getIndxsCountHavePagination(String reportId, String search, Integer start, Integer pageSize);
}
