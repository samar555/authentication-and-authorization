package app.netlify.sachin1008.service;

import app.netlify.sachin1008.dto.JWTAuthenticationResponse;
import app.netlify.sachin1008.dto.RefreshTokenDto;
import app.netlify.sachin1008.dto.SiginDto;
import app.netlify.sachin1008.dto.SignupDto;
import app.netlify.sachin1008.entity.User;

public interface AuthenticationService {
	public User signup(SignupDto user);
	public JWTAuthenticationResponse signin(SiginDto credentials) throws IllegalArgumentException;
	public JWTAuthenticationResponse RefreshToken(RefreshTokenDto refreshToken);

}
