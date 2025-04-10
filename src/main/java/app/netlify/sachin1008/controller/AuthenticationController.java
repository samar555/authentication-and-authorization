package app.netlify.sachin1008.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.sachin1008.dto.Message;
import app.netlify.sachin1008.dto.SignupDto;
import app.netlify.sachin1008.entity.User;
import app.netlify.sachin1008.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("signup")
	private ResponseEntity<Message> signup(@RequestBody SignupDto userdata){
		Message message=new Message();
		if(userdata.getEmail().trim().isBlank()||userdata.getEmail().isEmpty()) {
			message.setSuccess(false);
			message.setMessage(List.of("email can't be null"));
			message.setData(userdata);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
			
		}
		else if(userdata.getFirstName().trim().isBlank()) {
			message.setSuccess(false);
			message.setMessage(List.of("first name can't be null"));
			message.setData(userdata);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		else if(userdata.getLastName().trim().isBlank()) {
			message.setSuccess(false);
			message.setMessage(List.of("last name can't be null"));
			message.setData(userdata);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		else if(userdata.getPassword().isBlank()) {
			message.setSuccess(false);
			message.setMessage(List.of("password can't be null"));
			message.setData(userdata);
			if(userdata.getPassword().trim().length()<=7) {
				message.setMessage(List.of("password can't be null","password must be 8 charactor"));	
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		else if(userdata.getFirstName().trim().isBlank()) {
			message.setSuccess(false);
			message.setMessage(List.of("first name can't be null"));
			message.setData(userdata);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}else {
			User user= authenticationService.signup(userdata);
			if(user!=null) {
				message.setSuccess(true);
				message.setMessage(List.of("signup success"));
				message.setData(user);
				return ResponseEntity.status(HttpStatus.CREATED).body(message);
			}else {
				message.setSuccess(true);
				message.setMessage(List.of("backend error"));
				message.setData(user);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
			}
		}
	}
	
	
}
