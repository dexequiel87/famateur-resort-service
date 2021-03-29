package com.degg.famateur.service;

import com.degg.famateur.rest.model.BookableAssetDto;

import java.util.List;

public interface BookableAssetService {

    List<BookableAssetDto> findAllAssetsByResortId(String resortId);

    void addBookableAssetToResort(String resortId, BookableAssetDto bookableAssetDto);

    void updateBookableAsset(String resortId, String assetId, BookableAssetDto bookableAssetDto);

    void deleteBookableAsset(String resortId, String bookablessetId);
}
