package com.degg.famateur.service;

import com.degg.famateur.FamateurApplication;
import com.degg.famateur.domain.Resort;
import com.degg.famateur.exception.BookableAssetNotFoundException;
import com.degg.famateur.exception.ResortNotFoundException;
import com.degg.famateur.repository.mongo.ResortMongoRepository;
import com.degg.famateur.rest.model.BookableAssetDto;
import com.degg.famateur.rest.model.ResortDto;
import com.degg.famateur.service.mapper.ResortMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FamateurApplication.class})
public class BookableAssetServiceTest {

    @MockBean
    ResortMongoRepository repository;

    @Autowired
    BookableAssetService service;

    @Autowired
    ResortMapper resortMapper;

    private List<BookableAssetDto> actualBookableAssets;
    private String resortId = "sdcsdc651sdc651sd";
    private List<BookableAssetDto> expectedBookableAssets;
    private ResortDto resortDto;
    private Resort resort;
    private String assetTitle = "title";
    private String assetDescription = "description";
    private BookableAssetDto bookableAssetDto;
    private String calendarType;
    private String calendarId;
    private String nonExistingAssetId = "sdcsdcsdcds";
    private String existingAssetId = "00001";

    @BeforeEach
    void setup() {
        setupExpectedBookableAssets();
        resortDto = ResortDto.builder()
                .id("23b3o4234boj324b23")
                .title("Test Saved Resort")
                .description("This is a test saved resort")
                .enabled(Boolean.TRUE)
                .bookableAssets(expectedBookableAssets)
                .build();

        calendarType = "google-calendar";
        calendarId = "sd51c6sd5c1s3d2c1sd";
        bookableAssetDto = BookableAssetDto.builder()
                .title(assetTitle)
                .description(assetDescription)
                .calendarType(calendarType)
                .calendarId(calendarId)
                .build();
        resort = resortMapper.toResort(resortDto);
    }

    @Test
    void returnAllBookableAssetsForAGivenResort() {
        givenAnExistingResort();
        actualBookableAssets = service.findAllAssetsByResortId(resortId);
        verify(repository, times(1)).findById(resortId);
        Assertions.assertEquals(expectedBookableAssets.size(), actualBookableAssets.size());
        for (BookableAssetDto asset : expectedBookableAssets) {
            assertTrue(objectIsPresentInList(asset, actualBookableAssets));
        }
    }

    @Test
    void throwResortNotFoundExceptionWhenFetchingAssetsForNonExistingResort() {
        givenANonExistingResortId();
        assertThrows(ResortNotFoundException.class, () -> service.findAllAssetsByResortId(resortId));
    }

    @Test
    void throwResortNotFoundExceptionWhenAddingAssetToNonExistingResort() {
        givenANonExistingResortId();
        assertThrows(ResortNotFoundException.class, () -> whenABookableAssetIsAddedToAResort());
    }

    @Test
    void addBookableAssetToResortUponRequest() {
        int initialAssets = resort.getBookableAssets().size();
        givenAnExistingResort();
        whenABookableAssetIsAddedToAResort();
        thenResortIsSavedIncludingTheNewAsset(initialAssets);
    }

    @Test
    void throwResortNotFoundExceptionWhenUpdatingAssetOfNonExistingResort() {
        givenANonExistingResortId();
        assertThrows(ResortNotFoundException.class, () -> service.updateBookableAsset(resortId, nonExistingAssetId, bookableAssetDto));
    }

    @Test
    void throwResortNotFoundExceptionWhenUpdatingNonExistingAsset() {
        givenAnExistingResort();
        assertThrows(BookableAssetNotFoundException.class, () -> service.updateBookableAsset(resortId, nonExistingAssetId, bookableAssetDto));
    }

    @Test
    void updateBookableAssetToResortUponRequest() {
        int initialAssets = resort.getBookableAssets().size();
        givenAnExistingResort();
        whenABookableAssetIsUpdated();
        thenBookableAssetIsUpdated(initialAssets);
    }

    @Test
    void throwResortNotFoundExceptionWhenDeletingAssetOfNonExistingResort() {
        givenANonExistingResortId();
        assertThrows(ResortNotFoundException.class, () -> service.deleteBookableAsset(resortId, nonExistingAssetId));
    }

    @Test
    void throwResortNotFoundExceptionWhenDeletingNonExistingAsset() {
        givenAnExistingResort();
        assertThrows(BookableAssetNotFoundException.class, () -> service.deleteBookableAsset(resortId, nonExistingAssetId));
    }

    @Test
    void deleteBookableAssetFromResortUponRequest() {
        int initialAssets = resort.getBookableAssets().size();
        givenAnExistingResort();
        thenBookableAssetIsDeletedFromResort(initialAssets);
    }

    private void setupExpectedBookableAssets() {
        BookableAssetDto bookableAssetDto1 = BookableAssetDto.builder()
                .id(existingAssetId)
                .title("Bookable Asset 1 Title")
                .description("Bookable Asset 1 description")
                .build();
        BookableAssetDto bookableAssetDto2 = BookableAssetDto.builder()
                .id("00002")
                .title("Bookable Asset 2 Title")
                .description("Bookable Asset 2 description")
                .build();
        expectedBookableAssets = Arrays.asList(bookableAssetDto1, bookableAssetDto2);
    }

    private void givenAnExistingResort() {
        when(repository.findById(resortId)).thenReturn(Optional.ofNullable(resort));
    }

    private void givenANonExistingResortId() {
        when(repository.findById(resortId)).thenReturn(Optional.empty());
    }

    private void whenABookableAssetIsAddedToAResort() {
        service.addBookableAssetToResort(resortId, bookableAssetDto);
    }

    private void whenABookableAssetIsUpdated() {
        service.updateBookableAsset(resortId, existingAssetId, bookableAssetDto);
    }

    private void thenResortIsSavedIncludingTheNewAsset(int initialAssets) {
        verify(repository, times(1)).save(argThat(
                r -> r.getBookableAssets().size() == initialAssets + 1
                            && r.getBookableAssets().stream().anyMatch(a ->
                                !a.getId().isEmpty()
                                    && a.getCalendarType().equals(calendarType)
                                    && a.getCalendarId().equals(calendarId)
                                    && a.getDescription().equals(assetDescription)
                                    && a.getTitle().equals(assetTitle))
                ));
    }

    private void thenBookableAssetIsUpdated(int initialAssets) {
        verify(repository, times(1)).save(argThat(
                r -> r.getBookableAssets().size() == initialAssets
                        && r.getBookableAssets().stream().anyMatch(a ->
                        a.getId().equals(existingAssetId)
                                && a.getCalendarType().equals(calendarType)
                                && a.getCalendarId().equals(calendarId)
                                && a.getDescription().equals(assetDescription)
                                && a.getTitle().equals(assetTitle))
        ));
    }

    private void thenBookableAssetIsDeletedFromResort(int initialAssets) {
        service.deleteBookableAsset(resortId, existingAssetId);
        verify(repository, times(1)).save(argThat(
                r -> r.getBookableAssets().size() == initialAssets - 1
                        && r.getBookableAssets().stream().noneMatch(a -> a.getId().equals(existingAssetId))
        ));
    }

    private boolean objectIsPresentInList(Object object, List list) {
        return list.stream().anyMatch(o -> o.equals(object));
    }
}
