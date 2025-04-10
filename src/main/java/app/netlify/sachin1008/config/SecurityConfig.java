package app.netlify.sachin1008.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import app.netlify.sachin1008.entity.Role;
import app.netlify.sachin1008.service.UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	
	@Autowired
	private JwtAuthenticationFilter jwtauthfiler;
	
	@Autowired
	private UserService userService;
	
	
	@Bean
	public org.springframework.security.web.SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).
		authorizeHttpRequests(request->request.requestMatchers("/api/v1/auth/**").permitAll()
				.requestMatchers("/api/v1/admin")
				.hasAuthority(Role.ADMIN.name())
				.requestMatchers("/api/v1/user")
				.hasAuthority(Role.USER.name())
				.anyRequest().authenticated())
		.sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
		authenticationProvider(authenticationProvider()).addFilterBefore(jwtauthfiler, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
		
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authprovider=new DaoAuthenticationProvider();
		authprovider.setUserDetailsService(userService.userDetailService());
		authprovider.setPasswordEncoder(PasswordEncoder());
		return authprovider;
	}
	
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
