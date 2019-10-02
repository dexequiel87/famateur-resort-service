package com.degg.famateur.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.degg.famateur.model.User;


//@RepositoryRestResource(exported = false)
@CrossOrigin(methods = RequestMethod.GET, origins = "http://localhost:4300")
public interface IUserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
