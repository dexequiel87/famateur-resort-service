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

import static org.junit.jupiter.api.Assertions.*;
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

    private static final String GOOGLE_CALENDAR = "google-calendar";
    private static final String RESORT_ID = "sdcsdc651sdc651sd";
    private static final String ASSET_TITLE = "title";
    private static final String ASSET_DESCRIPTION = "description";
    private static final String NON_EXISTING_ASSET_ID = "sdcsdcsdcds";
    private static final String EXISTING_ASSET_ID = "00001";
    private static final String CALENDAR_ID = "sd51c6sd5c1s3d2c1sd";
    private List<BookableAssetDto> actualBookableAssets;
    private List<BookableAssetDto> expectedBookableAssets;
    private ResortDto resortDto;
    private Resort resort;
    private BookableAssetDto bookableAssetDto;
    private BookableAssetDto actualBookableAssetDto;
    private BookableAssetDto bookableAssetDto1;

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

        bookableAssetDto = BookableAssetDto.builder()
                .title(ASSET_TITLE)
                .description(ASSET_DESCRIPTION)
                .calendarType(GOOGLE_CALENDAR)
                .calendarId(CALENDAR_ID)
                .build();
        resort = resortMapper.toResort(resortDto);
    }

    @Test
    void throwResortNotFoundExceptionWhenFetchingAssetsForNonExistingResort() {
        givenANonExistingResortId();
        assertThrows(ResortNotFoundException.class, () -> service.findAllAssetsByResortId(RESORT_ID));
    }

    @Test
    void returnAllBookableAssetsForAGivenResort() {
        givenAnExistingResort();
        actualBookableAssets = service.findAllAssetsByResortId(RESORT_ID);
        verify(repository, times(1)).findById(RESORT_ID);
        Assertions.assertEquals(expectedBookableAssets.size(), actualBookableAssets.size());
        for (BookableAssetDto asset : expectedBookableAssets) {
            assertTrue(objectIsPresentInList(asset, actualBookableAssets));
        }
    }

    @Test
    void throwResortNotFoundExceptionWhenFetchingAssetForNonExistingResort() {
        givenANonExistingResortId();
        assertThrows(ResortNotFoundException.class,
                () -> whenBookableAssetIsRequested(EXISTING_ASSET_ID));
    }

    @Test
    void throwBookableAssetNotFoundExceptionWhenFetchingANonExistingId() {
        givenAnExistingResort();
        assertThrows(BookableAssetNotFoundException.class,
                () -> whenBookableAssetIsRequested(NON_EXISTING_ASSET_ID));
    }

    @Test
    void findBookableAssetByResortIdAndBookableAssetId() {
        givenAnExistingResort();
        whenBookableAssetIsRequested(EXISTING_ASSET_ID);
        assertEquals(bookableAssetDto1, actualBookableAssetDto);
    }

    private void whenBookableAssetIsRequested(String assetId) {
        actualBookableAssetDto = service.findBookableAssetByResortIdAndBookableAssetId(RESORT_ID, assetId);
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
        assertThrows(ResortNotFoundException.class, () -> service.updateBookableAsset(RESORT_ID, NON_EXISTING_ASSET_ID, bookableAssetDto));
    }

    @Test
    void throwResortNotFoundExceptionWhenUpdatingNonExistingAsset() {
        givenAnExistingResort();
        assertThrows(BookableAssetNotFoundException.class, () -> service.updateBookableAsset(RESORT_ID, NON_EXISTING_ASSET_ID, bookableAssetDto));
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
        assertThrows(ResortNotFoundException.class, () -> service.deleteBookableAsset(RESORT_ID, NON_EXISTING_ASSET_ID));
    }

    @Test
    void throwResortNotFoundExceptionWhenDeletingNonExistingAsset() {
        givenAnExistingResort();
        assertThrows(BookableAssetNotFoundException.class, () -> service.deleteBookableAsset(RESORT_ID, NON_EXISTING_ASSET_ID));
    }

    @Test
    void deleteBookableAssetFromResortUponRequest() {
        int initialAssets = resort.getBookableAssets().size();
        givenAnExistingResort();
        thenBookableAssetIsDeletedFromResort(initialAssets);
    }

    private void setupExpectedBookableAssets() {
        bookableAssetDto1 = BookableAssetDto.builder()
                .id(EXISTING_ASSET_ID)
                .calendarType(GOOGLE_CALENDAR)
                .calendarId("00001")
                .title("Bookable Asset 1 Title")
                .description("Bookable Asset 1 description")
                .build();
        BookableAssetDto bookableAssetDto2 = BookableAssetDto.builder()
                .id("00002")
                .calendarType(GOOGLE_CALENDAR)
                .calendarId("00002")
                .title("Bookable Asset 2 Title")
                .description("Bookable Asset 2 description")
                .build();
        expectedBookableAssets = Arrays.asList(bookableAssetDto1, bookableAssetDto2);
    }

    private void givenAnExistingResort() {
        when(repository.findById(RESORT_ID)).thenReturn(Optional.ofNullable(resort));
    }

    private void givenANonExistingResortId() {
        when(repository.findById(RESORT_ID)).thenReturn(Optional.empty());
    }

    private void whenABookableAssetIsAddedToAResort() {
        service.addBookableAssetToResort(RESORT_ID, bookableAssetDto);
    }

    private void whenABookableAssetIsUpdated() {
        service.updateBookableAsset(RESORT_ID, EXISTING_ASSET_ID, bookableAssetDto);
    }

    private void thenResortIsSavedIncludingTheNewAsset(int initialAssets) {
        verify(repository, times(1)).save(argThat(
                r -> r.getBookableAssets().size() == initialAssets + 1
                            && r.getBookableAssets().stream().anyMatch(a ->
                                !a.getId().isEmpty()
                                    && a.getCalendarType().equals(GOOGLE_CALENDAR)
                                    && a.getCalendarId().equals(CALENDAR_ID)
                                    && a.getDescription().equals(ASSET_DESCRIPTION)
                                    && a.getTitle().equals(ASSET_TITLE))
                ));
    }

    private void thenBookableAssetIsUpdated(int initialAssets) {
        verify(repository, times(1)).save(argThat(
                r -> r.getBookableAssets().size() == initialAssets
                        && r.getBookableAssets().stream().anyMatch(a ->
                        a.getId().equals(EXISTING_ASSET_ID)
                                && a.getCalendarType().equals(GOOGLE_CALENDAR)
                                && a.getCalendarId().equals(CALENDAR_ID)
                                && a.getDescription().equals(ASSET_DESCRIPTION)
                                && a.getTitle().equals(ASSET_TITLE))
        ));
    }

    private void thenBookableAssetIsDeletedFromResort(int initialAssets) {
        service.deleteBookableAsset(RESORT_ID, EXISTING_ASSET_ID);
        verify(repository, times(1)).save(argThat(
                r -> r.getBookableAssets().size() == initialAssets - 1
                        && r.getBookableAssets().stream().noneMatch(a -> a.getId().equals(EXISTING_ASSET_ID))
        ));
    }

    private boolean objectIsPresentInList(Object object, List list) {
        return list.stream().anyMatch(o -> o.equals(object));
    }
}
