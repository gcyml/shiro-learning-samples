package com.boot.service.impl;

import com.boot.dao.UserInfoDao;
import com.boot.domain.UserInfo;
import com.boot.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wgc
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoDao userInfoDao;


	@Override
	public UserInfo selectUserById(Integer userId) {
		return userInfoDao.selectUserById(userId);
	}

	@Override
	public UserInfo findByUsername(String username) {
		return userInfoDao.findByUsername(username);
	}
	

}
