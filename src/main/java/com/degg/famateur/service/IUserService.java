package com.degg.famateur.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.degg.famateur.exception.NoSuchUserException;
import com.degg.famateur.model.User;

public interface IUserService extends UserDetailsService{

	/**
	 * Saves a {@link User}
	 * @param user	The User to be saved
	 * @throws NoSuchUserException 
	 * @see User
	 */
	public User save(User user) throws NoSuchUserException;
	
	/**
	 * Returns a list of all the existing {@link User}
	 * @return 	a list of all the existing Sport Users
	 * @see		User
	 */
	public List<User> findAll();
	
	/**
	 * Deletes a {@link User} for a given id
	 * @param id	the id of the {@link User} to delete
	 * @see 		User
	 */
	public void delete(Long id);
	
	/**
	 * Returns a {@link User} for a given id
	 * @param id	the id of the {@link User} to get
	 * @return		an Optional<User> instance for the given id.
	 */
	public User getOne(Long id) throws NoSuchUserException;

	/**
	 * Updates a {@link User} for a given 
	 * @param sportUser	The SportUser to be saved
	 * @see User
	 */
	public User update(Long id, User user);
}
