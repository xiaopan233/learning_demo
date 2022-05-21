package com.Service;

import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserSerivce userSerivce;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //校验用户名
        User userByUsername = userSerivce.getUserByUsername(username);

        //组装权限，Spring Security的权限默认以ROLE_开头，如果数据库中没有写ROLE_，需要手动在这组装
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + userByUsername.getRole()));

        //返回UserDetails实例，让Spring Security帮我们自动判断
        return new org.springframework.security.core.userdetails.User(
            userByUsername.getUsername(),
            passwordEncoder.encode(userByUsername.getPassword()),
            authorityList
        );
    }
}
