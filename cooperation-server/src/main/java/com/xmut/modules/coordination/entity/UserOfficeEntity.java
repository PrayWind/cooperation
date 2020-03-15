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
@TableName("tb_user_office")
public class UserOfficeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * office账号id
	 */
	private String officeId;
	/**
	 * 预留字段
	 */
	private String type;

}
