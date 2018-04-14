/**
 * 
 */
package com.boot.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 角色权限关联表
 * @author wgc
 * @date 2018年3月19日
 */
@TableName("t_role_permission")
public class SysRolePermission implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
    /**
     * 角色ID
     */
	private String roleId;
    /**
     * 权限ID
     */
	private String permissionId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	@Override
	public String toString() {
		return "SysRolePermission [id=" + id + ", roleId=" + roleId + ", permissionId=" + permissionId + "]";
	}

}
