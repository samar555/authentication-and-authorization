package app.netlify.sachin1008.service;

import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;

import app.netlify.sachin1008.dto.JWTAuthenticationResponse;
import app.netlify.sachin1008.dto.RefreshTokenDto;

public interface JwtService {
	public String ExtractUserName(String token);

	public String genrateToken(UserDetails userdetails);

	public boolean tokenValidate(String token, UserDetails userDetails);

	public String genrateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userdetails);

	

}
