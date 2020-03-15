package com.xmut.modules.coordination.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author whf
 */
@Data
@TableName("tb_report_indx_user")
public class ReportIndxUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;

	/**
	 * 报告id
	 */
	private String reportId;

	/**
	 * 指标id
	 */
	private String indxId;

	/**
	 * 执行者id
	 */
	private String userId;

}
