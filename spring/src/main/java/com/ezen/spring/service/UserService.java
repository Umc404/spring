package com.ezen.spring.service;

import com.ezen.spring.domain.UserVO;

import java.util.List;

public interface UserService {
    int register(UserVO uvo);

    List<UserVO> getList();

    int userUpdatePwdEmpty(UserVO uvo);

    int userUpdate(UserVO uvo);

    int userDelete(String email);
}
