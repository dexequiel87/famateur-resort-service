package com.degg.famateur.service;

import com.degg.famateur.exception.ResortNotFoundException;
import com.degg.famateur.model.Resort;
import com.degg.famateur.repository.mongo.ResortMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResortServiceImpl implements ResortService {


    private final ResortMongoRepository repository;

    public ResortServiceImpl(ResortMongoRepository repository) {
        this.repository = repository;
    }

    /**
     * Get a list containing all the existing {@link Resort}
     *
     * @return a list of Resorts
     */
    @Override
    public List<Resort> findAll() {
        return repository.findAll();
    }


    /**
     * Find a Resort for the given id or throws a {@link ResortNotFoundException}
     *
     * @param id the id of the Resort
     * @return a Resort for the specified id
     */
    @Override
    public Resort findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResortNotFoundException(
                String.format("No resorts found for id = %s", id)));
    }


    /**
     * Add or update a {@link Resort} into the respository
     *
     * @param resort the Resort to be saved
     * @return the Resort saved with its corresponding id
     */
    @Override
    public Resort save(Resort resort) {
        return repository.save(resort);
    }


    /**
     * Update a {@link Resort} to the repository or create a new one if it does not exist
     *
     * @param id the id of the Resort to be updated
     * @param resort the updated Resort
     * @return the Resort saved with its corresponding id
     */
    @Override
    public Resort save(String id, Resort resort) {
        Resort storedResort = repository.findById(id).orElse(resort);
        storedResort.setId(id);
        storedResort.setDescription(resort.getDescription());
        storedResort.setEnabled(resort.getEnabled());
        storedResort.setTitle(resort.getTitle());
        return repository.save(storedResort);
    }


    /**
     * Delete a {@link Resort} from the repository
     *
     * @param resort the resort to delete
     */
    @Override
    public void delete(Resort resort) {
        repository.delete(resort);
    }


    /**
     * Delete a {@link Resort} for a given id
     *
     * @param id the id of the Resort to delete
     */
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
