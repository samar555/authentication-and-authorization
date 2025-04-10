package app.netlify.sachin1008.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.netlify.sachin1008.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	User findByEmail(String Email);

}
