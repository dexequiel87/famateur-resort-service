package com.degg.famateur.service;

import com.degg.famateur.exception.ResortNotFoundException;
import com.degg.famateur.domain.Resort;
import com.degg.famateur.repository.mongo.ResortMongoRepository;
import com.degg.famateur.rest.model.ResortDto;
import com.degg.famateur.service.mapper.ResortMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResortServiceImpl implements ResortService {

    private final ResortMongoRepository repository;

    @Autowired
    private ResortMapper resortMapper;

    public ResortServiceImpl(ResortMongoRepository repository) {
        this.repository = repository;
    }

    /**
     * Get a list containing all the existing {@link Resort}
     *
     * @return a list of Resorts
     */
    @Override
    public List<ResortDto> findAll() {
        return repository.findAll().stream().map(resort -> resortMapper.toResortDto(resort)).collect(Collectors.toList());
    }


    /**
     * Find a Resort for the given id or throws a {@link ResortNotFoundException}
     *
     * @param id the id of the Resort
     * @return a Resort for the specified id
     */
    @Override
    public ResortDto findById(String id) {
        return resortMapper.toResortDto(repository.findById(id).orElseThrow(() -> new ResortNotFoundException(
                String.format("No resorts found for id = %s", id))));
    }


    /**
     * Add or update a {@link Resort} into the respository
     *
     * @param resort the Resort to be saved
     * @return the Resort saved with its corresponding id
     */
    @Override
    public ResortDto save(ResortDto resort) {
        return resortMapper.toResortDto(repository.save(resortMapper.toResort(resort)));
    }


    /**
     * Update a {@link Resort} to the repository or create a new one if it does not exist
     *
     * @param id the id of the Resort to be updated
     * @param resort the updated Resort
     * @return the Resort saved with its corresponding id
     */
    @Override
    public ResortDto save(String id, ResortDto resort) {
        Resort storedResort = repository.findById(id).orElse(resortMapper.toResort(resort));
        storedResort.setId(id);
        storedResort.setDescription(resort.getDescription());
        storedResort.setEnabled(resort.getEnabled());
        storedResort.setTitle(resort.getTitle());
        return resortMapper.toResortDto(repository.save(storedResort));
    }


    /**
     * Delete a {@link Resort} from the repository
     *
     * @param resort the resort to delete
     */
    @Override
    public void delete(ResortDto resort) {
        repository.delete(resortMapper.toResort(resort));
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
