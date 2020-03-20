package com.xmut.modules.coordination.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * @author whf
 */
@Data
@TableName("tb_office")
public class OfficeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * office365账号
	 */
	private String loginName;
	/**
	 * office365密码
	 */
	private String password;
	/**
	 * office365账号昵称
	 */
	private String name;
	/**
	 * 预留字段
	 */
	private String type;

}
