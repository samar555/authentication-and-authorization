package app.netlify.sachin1008.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.netlify.sachin1008.dto.JWTAuthenticationResponse;
import app.netlify.sachin1008.dto.RefreshTokenDto;
import app.netlify.sachin1008.dto.SiginDto;
import app.netlify.sachin1008.dto.SignupDto;
import app.netlify.sachin1008.entity.Role;
import app.netlify.sachin1008.entity.User;
import app.netlify.sachin1008.repos.UserRepo;
import app.netlify.sachin1008.service.AuthenticationService;
import app.netlify.sachin1008.service.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepo userrepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	public User signup(SignupDto user) {
		User saveUser = new User();
		saveUser.setEmail(user.getEmail());
		saveUser.setFirstName(user.getFirstName());
		saveUser.setLastName(user.getLastName());
		saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
		saveUser.setRole(Role.USER);
		return userrepo.save(saveUser);
	}

	public JWTAuthenticationResponse signin(SiginDto credentials) throws IllegalArgumentException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail().trim(),
				credentials.getPassword().trim()));
		var user = userrepo.findByEmail(credentials.getEmail().trim());
		var jwt = jwtService.genrateToken(user);
		var refreshToken = jwtService.genrateRefreshToken(new HashMap<>(), user);
		JWTAuthenticationResponse authenticationResponse = new JWTAuthenticationResponse();
		authenticationResponse.setToken(jwt);

		authenticationResponse.setRefreshToken(refreshToken);
		return authenticationResponse;
	}
	
	public JWTAuthenticationResponse RefreshToken(RefreshTokenDto refreshToken) {
		String userEmail=jwtService.ExtractUserName(refreshToken.getToken());
		JWTAuthenticationResponse authenticationResponse = new JWTAuthenticationResponse();

		User user=userrepo.findByEmail(userEmail);
		if(jwtService.tokenValidate(refreshToken.getToken(), user)) {
			var jwt = jwtService.genrateToken(user);
			var resultantrefreshToken = jwtService.genrateRefreshToken(new HashMap<>(), user);
			authenticationResponse.setToken(jwt);

			authenticationResponse.setRefreshToken(resultantrefreshToken);
			return authenticationResponse;
		}else {
			authenticationResponse.setToken("token not expired");

			authenticationResponse.setRefreshToken(null);
			return authenticationResponse;
		}
		
	}
}
