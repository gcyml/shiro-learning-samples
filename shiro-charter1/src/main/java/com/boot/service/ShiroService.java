package com.boot.service;


import java.util.List;

import com.boot.domain.SysPermission;
import com.boot.domain.SysRole;
import com.boot.domain.SysUserRole;
import com.boot.domain.UserInfo;

/**
 * @author wgc
 */
public interface ShiroService {

	/**
	 * 通过用户id查找用户
	 */
	UserInfo selectUserById(Integer userId);

	/**
	 * 通过用户名查找用户
	 */
	UserInfo selectUserInfoByUsername(String username);
	
	/**
	 * 通过用户名查找角色列表
	 */
	List<SysRole> findSysRoleListByUsername(String username);
	
	/**
	 *通过用户名查找用户角色关联表
	 */
	List<SysUserRole> findSysUserRoleListByUsername(String username);
	
	/**
	 * 查找某角色的权限列表
	 */
	List<SysPermission> findSysPermissionListByRoleId(String roleId);

	
}
