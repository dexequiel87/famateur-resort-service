package com.degg.famateur.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.degg.famateur.exception.NoSuchUserException;
import com.degg.famateur.model.User;

public interface UserService extends UserDetailsService{

	/**
	 * Saves a {@link User}
	 * @param user	The User to be saved
	 * @see User
	 */
	User save(User user);
	
	/**
	 * Returns a list of all the existing {@link User}
	 * @return 	a list of all the existing Sport Users
	 * @see		User
	 */
	List<User> findAll();
	
	/**
	 * Deletes a {@link User} for a given id
	 * @param id	the id of the {@link User} to delete
	 * @see 		User
	 */
	void delete(Long id);
	
	/**
	 * Returns a {@link User} for a given id
	 * @param id	the id of the {@link User} to get
	 * @return		an Optional<User> instance for the given id.
	 */
	User getOne(Long id) throws NoSuchUserException;

	/**
	 * Updates a {@link User} for a given
	 * @param id the id of the User to update
	 * @param user	The User to be saved
	 * @see User
	 */
	User update(Long id, User user);
}
