package com.xmut.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmut.modules.sys.entity.SysRoleEntity;
import com.xmut.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 角色
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
