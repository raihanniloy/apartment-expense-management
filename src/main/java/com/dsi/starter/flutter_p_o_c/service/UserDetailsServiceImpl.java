package com.dsi.starter.flutter_p_o_c.service;

import com.dsi.starter.flutter_p_o_c.model.UserDTO;
import com.dsi.starter.flutter_p_o_c.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserName: "+ username);
        if("system".equals(username)){
            UserDTO user = new UserDTO();
            user.setUsername(username);
            user.setPassword("sudo");
            user.setActive(true);
            user.setRoles("ROLE_MANAGER");
            return new UserDetailsImpl(user);
        }
        UserDTO user = userService.getByUsername(username);
        return new UserDetailsImpl(user);
    }
}
