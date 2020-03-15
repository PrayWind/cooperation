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

	/**
	 * 指标状态
	 */
	private String status;

	/**
	 * 指标是否已被分配，分配1未分配0，在获取模板执行者有用到
	 */
	private String flag;

	/**
	 * 预留
	 */
	private String type;

}
