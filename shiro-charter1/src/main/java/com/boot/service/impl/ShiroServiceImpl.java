package com.boot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.boot.dao.SysPermissionDao;
import com.boot.dao.SysRoleDao;
import com.boot.dao.SysRolePermissionDao;
import com.boot.dao.SysUserRoleDao;
import com.boot.dao.UserInfoDao;
import com.boot.domain.SysPermission;
import com.boot.domain.SysRole;
import com.boot.domain.SysRolePermission;
import com.boot.domain.SysUserRole;
import com.boot.domain.UserInfo;
import com.boot.service.ShiroService;

/**
 * shiro 安全认证service
 * @author wgc
 */
@Service
public class ShiroServiceImpl implements ShiroService {
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private SysRolePermissionDao sysRolePermissionDao;
	
	@Autowired
	private SysPermissionDao sysPermissionDao;

	@Override
	public UserInfo selectUserById(Integer userId) {
		return userInfoDao.selectById(userId);
	}

	@Override
	public List<SysRole> findSysRoleListByUsername(String username) {
		List<SysRole> sysRoleList = new ArrayList<>();	
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user = userInfoDao.selectOne(user);
		List<SysUserRole> sysUserRoles = sysUserRoleDao.selectList(new EntityWrapper<SysUserRole>().eq("user_id", user.getId()));	
		for(SysUserRole sysUserRole : sysUserRoles) {
			sysRoleList.add(sysRoleDao.selectById(sysUserRole.getRoleId()));
		}
		return sysRoleList;
	}

	@Override
	public UserInfo selectUserInfoByUsername(String username) {
		UserInfo user = new UserInfo();
		user.setUsername(username);
		return userInfoDao.selectOne(user);
	}

	@Override
	public List<SysPermission> findSysPermissionListByRoleId(String roleId) {
		List<SysPermission> sysPermissionList = new ArrayList<>();	
		List<SysRolePermission> sysRolePermissionList = sysRolePermissionDao.selectList(new EntityWrapper<SysRolePermission>().eq("role_id", roleId));	
		for(SysRolePermission sysRolePermission : sysRolePermissionList) {
			sysPermissionList.add(sysPermissionDao.selectById(sysRolePermission.getPermissionId()));
		}
		return sysPermissionList;
	}

	@Override
	public List<SysUserRole> findSysUserRoleListByUsername(String username) {
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user = userInfoDao.selectOne(user);
		return sysUserRoleDao.selectList(new EntityWrapper<SysUserRole>().eq("user_id", user.getId()));	
	}

}
