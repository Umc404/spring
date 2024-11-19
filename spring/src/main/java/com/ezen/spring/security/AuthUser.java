package com.ezen.spring.security;

import com.ezen.spring.domain.UserVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AuthUser extends User {

    private UserVO uvo;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    // principal
    public AuthUser(UserVO uvo) {
        super(uvo.getEmail(), uvo.getPwd(),
                uvo.getAuthList().stream()
                        .map(authVO -> new SimpleGrantedAuthority(authVO.getAuth()))
                        .collect(Collectors.toList())
                );
        this.uvo = uvo;
    }

}
