package com.boot.dao;

import com.boot.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wgc
 */
@Mapper
public interface UserInfoDao {
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
