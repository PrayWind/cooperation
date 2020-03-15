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
@TableName("tb_report_user")
public class ReportUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 报告id
	 */
	private String reportId;

}
