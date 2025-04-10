package app.netlify.sachin1008.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@GetMapping
	private ResponseEntity<String> hi(){
		return ResponseEntity.ok("hii");
	}
}
