package com.example.demo.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session= attr.getRequest().getSession(true);
			session.setAttribute("nickName", user.getLineUserName());
			session.setAttribute("authority", user.getRoleId());
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
