package com.degg.famateur.service;

import com.degg.famateur.exception.ResortNotFoundException;
import com.degg.famateur.model.Resort;

import java.util.List;

public interface ResortService {

    /**
     * Get a list containing all the existing {@link Resort}
     *
     * @return a list of Resort
     */
    List<Resort> findAll();


    /**
     * Find a Resort for the given id or throws a {@link ResortNotFoundException}
     *
     * @param id the id of the Resort
     * @return a Resort for the specified id
     */
    Resort findById(String id);


    /**
     * Add or update a {@link Resort} into the respository
     *
     * @param resort the Resort to be saved
     * @return the Resort saved with its corresponding id
     */
    Resort save(Resort resort);


    /**
     * Update a {@link Resort} to the repository or create a new one if it does not exist
     *
     * @param id the id of the Resort to be updated
     * @param resort the updated Resort
     * @return the Resort saved with its corresponding id
     */
    Resort save(String id, Resort resort);


    /**
     * Delete a {@link Resort} from the repository
     *
     * @param resort the resort to delete
     */
    void delete(Resort resort);


    /**
     * Delete a {@link Resort} for a given id
     *
     * @param id the id of the Resort to delete
     */
    void deleteById(String id);
}
