package com.project.springBlog.Service;

import com.project.springBlog.Repository.UserRepositry;
import com.project.springBlog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepositry userRepositry;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositry.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("No User Found with these Credentials"));
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),true,true,true,true,
                getAuthorities("ROLE_USER"));
    }

    /*
    *
    * By returning a GrantedAuthority object with the role "ROLE_USER",
    * the code is indicating that the user being loaded has the authority or role of a "user."
    * This allows Spring Security to apply appropriate security measures and access control based on the assigned role.
    * */

    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }
}
