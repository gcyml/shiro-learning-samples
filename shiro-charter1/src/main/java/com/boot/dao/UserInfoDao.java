package com.boot.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.boot.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wgc
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo>{
}  
