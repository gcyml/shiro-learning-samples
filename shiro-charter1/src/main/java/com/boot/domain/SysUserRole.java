/**
 * 
 */
package com.boot.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户角色关联表
 * @author wgc
 * @date 2018年3月19日
 */
@TableName("t_user_role")
public class SysUserRole implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
    /**
     * 用户ID
     */
	private String userId;
    /**
     * 角色ID
     */
	private String roleId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "SysUserRole [id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
	}
}
