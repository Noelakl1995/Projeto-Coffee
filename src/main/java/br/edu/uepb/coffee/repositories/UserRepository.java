package br.edu.uepb.coffee.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.uepb.coffee.models.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    UserDetails findByEmail(String email);
}
