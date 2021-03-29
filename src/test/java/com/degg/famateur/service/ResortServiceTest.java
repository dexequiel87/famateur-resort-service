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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FamateurApplication.class})
class ResortServiceTest {

    @Autowired
    ResortService resortService;

    @Autowired
    ResortMapper resortMapper;

    @MockBean
    ResortMongoRepository repository;

    private ResortDto savedResort;
    private List<ResortDto> savedResorts;
    private List<ResortDto> resorts;
    private ResortDto actualResort;
    private ResortDto newResortDto;
    private String resortId;

    @BeforeEach
    void setup() {
        savedResort = ResortDto.builder()
                .id("23b3o4234boj324b23")
                .title("Test Saved Resort")
                .description("This is a test saved resort")
                .enabled(Boolean.TRUE)
                .build();

        ResortDto savedResort2 = ResortDto.builder()
                .id("23b3o4scsdj324b23")
                .title("Test Saved Resort 2")
                .description("This is a second test saved resort")
                .enabled(Boolean.TRUE)
                .build();
        ResortDto resort3 = ResortDto.builder()
                .id("23b3o4234sdcd24b23")
                .title("Test Saved Resort 3")
                .description("This is a third test saved resort")
                .enabled(Boolean.FALSE)
                .build();
        savedResorts = Arrays.asList(
                savedResort,
                savedResort2,
                resort3);
        doNothing().when(repository).delete(any());
        doNothing().when(repository).deleteById(any());
    }




    @Test
    void return_All_Resorts_Upon_Request() {
        givenSomeResorts();
        whenAllResortsAreRequested();
        thenAllResortsAreReturned();
    }

    @Test
    void returnASpecificResortUponRequest() {
        givenAResort();
        whenAResortIsRequested();
        thenExpectedResortIsReturned();
    }

    @Test
    void saveANewResortUponRequest() {
        Resort newResort = givenANewResort();
        givenAResortRepository(newResort);
        whenAResortIsSaved();
        thenResortIsSavedToTheRepository(newResort);
        thenReturnsTheSavedResort();
    }

    @Test
    void deleteResortUponRequest() {
        whenResortRemovalIsRequested();
        thenResortIsRemovedFromRepository();
    }
    @Test
    void deleteResortByIdUponRequest() {
        whenRemovalByIdIsRequested();
        thenResortIdIsRemovedFromRepository();
    }

    private void givenSomeResorts() {
        Mockito.when(repository.findAll())
                .thenReturn(savedResorts.stream().map(resortDto -> resortMapper.toResort(resortDto)).collect(Collectors.toList()));
    }


    private void givenAResort() {
        Mockito.when(repository.findById(any()))
                .thenReturn(Optional.ofNullable(resortMapper.toResort(savedResort)));
    }


    private void givenAResortRepository(Resort newResort) {
        Mockito.when(repository.save(newResort))
                .thenReturn(resortMapper.toResort(savedResort));
    }

    private Resort givenANewResort() {
        newResortDto = ResortDto.builder()
            .title("Test Saved Resort")
            .description("This is a test saved resort")
            .enabled(Boolean.TRUE)
            .build();
        return resortMapper.toResort(newResortDto);
    }


    private void whenAllResortsAreRequested() {
        resorts = resortService.findAll();
    }

    private void whenAResortIsRequested() {
        actualResort = resortService.findById("32423423sdfwer3f343");
    }

    private void whenAResortIsSaved() {
        actualResort = resortService.save(newResortDto);
    }

    private void whenResortRemovalIsRequested() {
        resortService.delete(savedResort);
    }


    private void whenRemovalByIdIsRequested() {
        resortId = "e23e32e23d2323ed23";
        resortService.deleteById(resortId);
    }


    private void thenAllResortsAreReturned() {
        Assertions.assertEquals(savedResorts.size(), resorts.size());
        for (ResortDto resort : resorts) {
            assertTrue(objectIsPresentInList(resort, savedResorts));
        }
    }

    private void thenResortIsSavedToTheRepository(Resort newResort) {
        verify(repository, times(1)).save(newResort);
    }

    private void thenReturnsTheSavedResort() {
        assertEquals(savedResort, actualResort);
    }

    private void thenExpectedResortIsReturned() {
        assertEquals(actualResort, savedResort);
    }

    private void thenResortIsRemovedFromRepository() {
        verify(repository, times(1)).delete(resortMapper.toResort(savedResort));
    }

    private void thenResortIdIsRemovedFromRepository() {
        verify(repository, times(1)).deleteById(resortId);
    }

    private boolean objectIsPresentInList(Object object, List list) {
        return list.stream().anyMatch(o -> o.equals(object));
    }
}