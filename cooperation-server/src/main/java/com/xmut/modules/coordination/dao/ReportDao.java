package com.xmut.modules.coordination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmut.modules.coordination.entity.ReportEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @author whf
 */
@Mapper
public interface ReportDao extends BaseMapper<ReportEntity> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into tb_report(name,report_info,leader,type,create_time,predict_time,status) " +
            "values(#{name},#{reportInfo},#{leader},#{type},#{createTime},#{predictTime},0)")
    Integer insertReport(ReportEntity report);
}
