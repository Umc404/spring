package com.ezen.spring.repository;

import com.ezen.spring.domain.AuthVO;
import com.ezen.spring.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int register(UserVO uvo);

    void insertAuth(UserVO uvo);

    UserVO selectEmail(String username);

    List<AuthVO> selectAuths(String username);

    List<UserVO> getList();

    int userUpdatePwdEmpty(UserVO uvo);

    int userUpdate(UserVO uvo);

    int userDelete(String email);
}
