package app.netlify.sachin1008.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.netlify.sachin1008.dto.SignupDto;
import app.netlify.sachin1008.entity.Role;
import app.netlify.sachin1008.entity.User;
import app.netlify.sachin1008.repos.UserRepo;
import app.netlify.sachin1008.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User signup(SignupDto user) {
		User saveUser=new User();
		saveUser.setEmail(user.getEmail());
		saveUser.setFirstName(user.getFirstName());
		saveUser.setLastName(user.getLastName());
		saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
		saveUser.setRole(Role.USER);
		return userrepo.save(saveUser);
	}
}
