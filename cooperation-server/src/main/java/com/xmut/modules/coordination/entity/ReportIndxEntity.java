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
@TableName("tb_report_indx")
public class ReportIndxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
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
	 * 指标状态，未分配0 进行中1 完成2，进行中表示已分配
	 */
	private String status;

	/**
	 * 完成时间
	 */
	private String finishTime;
}
