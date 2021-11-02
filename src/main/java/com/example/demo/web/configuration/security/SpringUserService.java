package com.example.demo.web.configuration.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.web.entity.AppUserEntity;
import com.example.demo.web.service.AppUserService;

@Service
public class SpringUserService implements UserDetailsService{
	
	@Autowired
	private AppUserService appUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUserEntity user = appUserService.getAppUser(username);
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = null;
		if(null != user && null != user.getRoleId()) {
			simpleGrantedAuthorities = createAuthorities(user.getRoleId());
			return new User(user.getAccount(), user.getPassword(), simpleGrantedAuthorities);
		} else {
			return new User("1", "1", new ArrayList<SimpleGrantedAuthority>());
		}
	}
	
	private List<SimpleGrantedAuthority> createAuthorities(String roleStr){
        String[] roles = roleStr.split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }
}
