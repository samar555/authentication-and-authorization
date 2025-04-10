package app.netlify.sachin1008.service;

import app.netlify.sachin1008.dto.SignupDto;
import app.netlify.sachin1008.entity.User;

public interface AuthenticationService {
	public User signup(SignupDto user);
}
