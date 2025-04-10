package app.netlify.sachin1008.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.netlify.sachin1008.repos.UserRepo;
import app.netlify.sachin1008.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserRepo userrepo;

	@Override
	public UserDetailsService userDetailService() {
		// TODO Auto-generated method stub
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return userrepo.findByEmail(username);
			}
		};
	}
}
