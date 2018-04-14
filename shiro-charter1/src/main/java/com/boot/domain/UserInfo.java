package com.boot.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户
 * @author wgc
 */
@TableName("t_user")
public class UserInfo implements Serializable{

	private static final long serialVersionUID = 8096767137845964189L;
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 * 用户名
	 */
    private String username;
    /**
     * 密码
     */
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
