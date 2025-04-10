package app.netlify.sachin1008.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.netlify.sachin1008.service.JwtService;
import app.netlify.sachin1008.service.UserService;
import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private  JwtService jwtService;
	@Autowired
	private  UserService userservice;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader=request.getHeader("Authorization");
		final String jwt;
	  final String userEmail;
	  if(StringUtil.isNullOrEmpty(authHeader)|| !authHeader.startsWith("Bearer")) {
		  filterChain.doFilter(request, response);
		  return;
	  }
		jwt=authHeader.substring(7);
		userEmail=jwtService.ExtractUserName(jwt);
		if(userEmail.isEmpty()&& SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetail=userservice.userDetailService().loadUserByUsername(userEmail);
		if(jwtService.tokenValidate(userEmail, userdetail)) {
			SecurityContext context=SecurityContextHolder.createEmptyContext();
			
			UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userdetail, null,userdetail.getAuthorities());
			token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			context.setAuthentication(token);
			SecurityContextHolder.setContext(context);
		}
		
		}
		filterChain.doFilter(request, response);
	}
	

}
