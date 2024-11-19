package com.ezen.spring.service;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public int register(UserVO uvo) {
        int isOk = userMapper.register(uvo);
        if(isOk > 0) {
            userMapper.insertAuth(uvo);
        }
        return isOk;
    }
}
