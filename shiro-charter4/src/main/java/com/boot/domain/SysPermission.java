/**
 * 
 */
package com.boot.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 权限信息
 * @author wgc
 * @date 2018年3月19日
 */
@TableName("t_permission")
public class SysPermission implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 权限信息主键id
	 */
	private String id;
    /**
     * url地址
     */
	private String url;
    /**
     * url描述
     */
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SysPermission [id=" + id + ", url=" + url + ", name=" + name + "]";
	}
}
