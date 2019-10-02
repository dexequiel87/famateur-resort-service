package com.degg.famateur.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.degg.famateur.model.User;
import com.degg.famateur.model.UserNotification;


//@RepositoryRestResource(exported = false)
@CrossOrigin(methods = RequestMethod.GET, origins = "http://localhost:4300")
public interface IUserNotificationJpaRepository extends JpaRepository<UserNotification, Long> {

	@Query("select n from UserNotifiacation n where n.user = ?1 and not n.read")
	Optional<User> findUnreadNotificationsByUser(User user);

}
