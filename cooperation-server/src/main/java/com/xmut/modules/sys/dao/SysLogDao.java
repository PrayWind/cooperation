package com.xmut.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmut.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
