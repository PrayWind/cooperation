package com.xmut.modules.coordination.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;
import com.xmut.modules.sys.entity.SysUserEntity;
import lombok.Data;

@Data
@TableName("tb_indx")
public class IndxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 指标id
	 */
	@TableId
	private Long id;

	/**
	 * 指标名称
	 */
	private String name;

	/**
	 * 指标优先级
	 */
	private String level;

	/*
	 * 执行者，在报告详情页有用到
	 */
	@TableField(exist = false)
	private List<SysUserEntity> executors;

	/*
	 * 执行者字符串，把username用 "、" 拼接
	 */
	@TableField(exist = false)
	private String executorsStr;

	/*
	 * 指标状态，在报告详情页有用到
	 */
	@TableField(exist = false)
	private String status;

	/*
	 * 指标完成时间，在报告详情页有用到
	 */
	@TableField(exist = false)
	private String finishTime;
}
