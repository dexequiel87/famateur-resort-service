package com.degg.famateur.service;

import com.degg.famateur.domain.BookableAsset;
import com.degg.famateur.domain.Resort;
import com.degg.famateur.exception.BookableAssetNotFoundException;
import com.degg.famateur.exception.ResortNotFoundException;
import com.degg.famateur.repository.mongo.ResortMongoRepository;
import com.degg.famateur.rest.model.BookableAssetDto;
import com.degg.famateur.service.mapper.BookableAssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookableAssetServiceImpl implements BookableAssetService {

    @Autowired
    ResortMongoRepository repository;

    @Autowired
    BookableAssetMapper mapper;

    @Override
    public List<BookableAssetDto> findAllAssetsByResortId(String resortId) {
        Resort resort = getResort(resortId);
        return resort.getBookableAssets()
                .stream()
                .map(bookableAsset -> mapper.toBookableAssetDto(bookableAsset))
                .collect(Collectors.toList());
    }

    @Override
    public BookableAssetDto addBookableAssetToResort(String resortId, BookableAssetDto bookableAssetDto) {
        Resort resort = getResort(resortId);
        BookableAsset bookableAsset = mapper.toBookableAsset(bookableAssetDto);
        bookableAsset.setId(UUID.randomUUID().toString());
        resort.getBookableAssets().add(bookableAsset);
        repository.save(resort);
        return mapper.toBookableAssetDto(bookableAsset);
    }

    @Override
    public BookableAssetDto updateBookableAsset(String resortId, String assetId, BookableAssetDto bookableAssetDto) {
        Resort resort = getResort(resortId);
        BookableAsset bookableAsset = getBookableAssetFromResort(resort, assetId);
        updateBookableAsset(bookableAsset, bookableAssetDto);
        repository.save(resort);
        return mapper.toBookableAssetDto(bookableAsset);
    }

    @Override
    public void deleteBookableAsset(String resortId, String bookablessetId) {
        Resort resort = getResort(resortId);
        if (!removeBookableAssetFromResortIfExists(bookablessetId, resort))
            throw bookableAssetNotFoundException(bookablessetId);
        repository.save(resort);
    }

    @Override
    public BookableAssetDto findBookableAssetByResortIdAndBookableAssetId(String resortId, String bookableAssetId) {
        Resort resort = getResort(resortId);
        return mapper.toBookableAssetDto(getBookableAssetFromResort(resort, bookableAssetId));
    }

    private boolean removeBookableAssetFromResortIfExists(String bookablessetId, Resort resort) {
        return resort.getBookableAssets().removeIf(bookableAsset -> bookableAsset.getId().equals(bookablessetId));
    }

    private BookableAssetNotFoundException bookableAssetNotFoundException(String bookablessetId) {
        return new BookableAssetNotFoundException(String.format("No assets found for id = %s", bookablessetId));
    }

    private Resort getResort(String resortId) {
        return repository.findById(resortId)
                .orElseThrow(() -> new ResortNotFoundException(String.format("No resorts found for id = %s", resortId)));
    }

    private void updateBookableAsset(BookableAsset bookableAsset, BookableAssetDto bookableAssetDto) {
        bookableAsset.setTitle(bookableAssetDto.getTitle());
        bookableAsset.setDescription(bookableAssetDto.getDescription());
        bookableAsset.setCalendarType(bookableAssetDto.getCalendarType());
        bookableAsset.setCalendarId(bookableAssetDto.getCalendarId());
    }

    private BookableAsset getBookableAssetFromResort(Resort resort, String assetId) {
        return resort.getBookableAssets()
                .stream()
                .filter(x -> x.getId().equals(assetId))
                .findFirst()
                .orElseThrow(() -> bookableAssetNotFoundException(assetId));
    }
}
