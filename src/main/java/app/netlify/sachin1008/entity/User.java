package app.netlify.sachin1008.entity;



import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank
	@NotNull
	private String firstName;
	
	@NotNull
	@NotBlank
	private String lastName;
	
	
	@NotNull
	@NotBlank
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	

	@Override
	public String getUsername() {
	
		return email;
	}
}
