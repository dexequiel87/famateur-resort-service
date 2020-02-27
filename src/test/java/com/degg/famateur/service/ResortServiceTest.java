package com.degg.famateur.service;

import com.degg.famateur.FamateurApplication;
import com.degg.famateur.domain.Resort;
import com.degg.famateur.repository.mongo.ResortMongoRepository;
import com.degg.famateur.rest.model.ResortDto;
import com.degg.famateur.service.mapper.ResortMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FamateurApplication.class})
class ResortServiceTest {

    @Autowired
    ResortService resortService;

    @Autowired
    ResortMapper resortMapper;

    @MockBean
    ResortMongoRepository repository;

    ResortDto savedResort;
    List<ResortDto> savedResorts;


    /**
     * Execute this method before each test
     */
    @BeforeEach
    void before() {
        savedResort = ResortDto.builder()
                .id("23b3o4234boj324b23")
                .title("Test Saved Resort")
                .description("This is a test saved resort")
                .enabled(Boolean.TRUE)
                .build();

        savedResorts = Arrays.asList(
                savedResort,
                ResortDto.builder()
                    .id("23b3o4scsdj324b23")
                    .title("Test Saved Resort 2")
                    .description("This is a second test saved resort")
                    .enabled(Boolean.TRUE)
                    .build(),
                ResortDto.builder()
                    .id("23b3o4234sdcd24b23")
                    .title("Test Saved Resort 3")
                    .description("This is a third test saved resort")
                    .enabled(Boolean.FALSE)
                    .build());
    }




    /**
     * Test the findAll() method of ResortService
     */
    @Test
    void findAll() {

        // Mock respository
        Mockito.when(repository.findAll()).thenReturn(savedResorts.stream().map(resortDto -> resortMapper.toResort(resortDto)).collect(Collectors.toList()));

        // Actual result
        List<ResortDto> actualResult = resortService.findAll();

        // Assert that resorts returned by the service match savedResorts
        Assertions.assertEquals(savedResorts.size(), actualResult.size());
        for (ResortDto resort : actualResult) {
            assertTrue(objectIsPresent(resort, savedResorts));
        }
    }




    /**
     * Test the findById() method of ResortService
     */
    @Test
    void findById() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.ofNullable(resortMapper.toResort(savedResort)));
        assertEquals(resortService.findById("32423423sdfwer3f343"), savedResort);
    }




    /**
     * Test the save() method of ResortService
     */
    @Test
    void save() {
        // Mock repository return
        Mockito.when(repository.save(any(Resort.class))).thenReturn(resortMapper.toResort(savedResort));

        // Create new object to save
        ResortDto newResort = ResortDto.builder()
                .title("Test Saved Resort")
                .description("This is a test saved resort")
                .enabled(Boolean.TRUE)
                .build();

        // Asserts
        assertEquals(savedResort, resortService.save(newResort));
    }




    /**
     * Test the delete() method of ResortService
     */
    @Test
    void delete() {
        // Mock repository
        doNothing().when(repository).delete(any());

        // Call method
        resortService.delete(savedResort);
    }



    /**
     * Test the deleteById() method of ResortService
     */
    @Test
    void deleteById() {
        // Mock repository
        doNothing().when(repository).deleteById(any());

        // Call method
        resortService.deleteById("e23e32e23d2323ed23");
    }



    /**
     * Check if a List contains an object exactly like the given object
     * @param object the object to verify
     * @param list the list
     * @return true if the list contains an object with the same attributes that the object given or false otherwise
     */
    private boolean objectIsPresent(Object object, List list) {
        for (Object resortI : list) {
            if (resortI.equals(object))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}