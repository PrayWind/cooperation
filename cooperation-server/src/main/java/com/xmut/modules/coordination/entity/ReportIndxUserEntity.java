package com.xmut.modules.coordination.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

@Data
@TableName("tb_report_indx_user")
public class ReportIndxUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;

	/**
	 * 报告id
	 */
	private Long reportId;

	/**
	 * 指标id
	 */
	private Long indxId;

	/**
	 * 执行者id
	 */
	private Long userId;

}
