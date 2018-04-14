/**
 * 
 */
package com.boot.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 角色信息
 * @author wgc
 * @date 2018年3月19日
 */
@TableName("t_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 角色名称
     */
	private String rolename;
	
	/**
	 * 角色描述,UI界面显示使用
	 */
	private String description; 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	@Override
	public String toString() {
		return "SysRole [id=" + id + ", rolename=" + rolename + ", description=" + description + "]";
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
