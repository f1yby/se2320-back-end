package com.example.back_end.serviceImpl;

import com.example.back_end.entity.UserEntity;
import com.example.back_end.repository.UserRepository;
import com.example.back_end.utility.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s)  {
        Optional<UserEntity> userEntity = userRepository.findByName(s);
        UserEntity user;
        if (userEntity.isPresent())
            user = userEntity.get();
        else return null;
        List<SimpleGrantedAuthority> collect = new ArrayList<>();
        collect.add(new SimpleGrantedAuthority("user"));
        return new UserAuth(user.getName(), user.getPassword(), collect);
    }
}
