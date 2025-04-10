package app.netlify.sachin1008.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthenticationResponse {
	private String token;
	private String refreshToken;

}
