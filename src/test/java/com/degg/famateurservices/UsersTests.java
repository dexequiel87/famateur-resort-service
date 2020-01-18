package com.degg.famateurservices;

import com.degg.famateur.FamateurApplication;
import com.degg.famateur.exception.NoSuchUserException;
import com.degg.famateur.model.User;
import com.degg.famateur.security.SecurityConfig;
import com.degg.famateur.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { FamateurApplication.class, SecurityConfig.class } )
public class UsersTests {
	
	@Autowired
	IUserService userService;

	@Test
	public void addUserTest() {
//		User user = new User();
//		user.setFirstName("damian");
//		user.setLastName("garcia");
//		user.setUsername("damian");
//		user.setAge(32);
//		user.setEnabled(1);
//		user.setPassword("4113");
//		User result;
//		try {
//			result = userService.save(user);
//			assertTrue(result.getPassword().equals(user.getPassword()));
//		} catch (NoSuchUserException e) {
//			fail("User creation faled.", e);
//		}
	}

}
