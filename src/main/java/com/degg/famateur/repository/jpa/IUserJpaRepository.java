package com.degg.famateur.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.degg.famateur.model.User;

public interface IUserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
