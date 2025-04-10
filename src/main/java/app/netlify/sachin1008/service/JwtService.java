package app.netlify.sachin1008.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	public String ExtractUserName(String token);
	public String genrateToken(UserDetails userdetails);
	public boolean tokenValidate(String token,UserDetails userDetails);
}
