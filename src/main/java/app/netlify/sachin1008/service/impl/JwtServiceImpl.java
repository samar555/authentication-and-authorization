package app.netlify.sachin1008.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.netlify.sachin1008.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	
	@Value("$JWT_SECURITY_KEY")
	private String Stringkey;
	
	public String genrateToken(UserDetails userdetails) {
		return Jwts.builder()
				.setSubject(userdetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis())).
				setExpiration(new Date(System.currentTimeMillis()+1000*60*24)).signWith(getSignInKey(),SignatureAlgorithm.HS256).compact();
	}
	public String ExtractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	private <T> T extractClaims(String token,Function<Claims,T> claimsResolver){
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}
	private Key getSignInKey() {
		byte[] key=Decoders.BASE64.decode(Stringkey);
		return Keys.hmacShaKeyFor(key);
	}
	public boolean tokenValidate(String token,UserDetails userDetails) {
		final String username=ExtractUserName(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
		
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}
}
