package com.degg.famateur.service;

import com.degg.famateur.rest.model.BookableAssetDto;

import java.util.List;

public interface BookableAssetService {

    List<BookableAssetDto> findAllAssetsByResortId(String resortId);

    BookableAssetDto addBookableAssetToResort(String resortId, BookableAssetDto bookableAssetDto);

    BookableAssetDto updateBookableAsset(String resortId, String assetId, BookableAssetDto bookableAssetDto);

    void deleteBookableAsset(String resortId, String bookablessetId);

    BookableAssetDto findBookableAssetByResortIdAndBookableAssetId(String resortId, String nonExistingAssetId);
}
