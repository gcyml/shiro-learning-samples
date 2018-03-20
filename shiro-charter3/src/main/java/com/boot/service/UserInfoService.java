package com.boot.service;


import com.boot.domain.UserInfo;

/**
 * @author wgc
 */
public interface UserInfoService {
	/**
	 * 查找用户
	 * @param userId
	 * @return
	 */
	UserInfo selectUserById(Integer userId);

	/**
	 * 查找用户
	 * @param username
	 * @return
	 */
	UserInfo findByUsername(String username);

}
