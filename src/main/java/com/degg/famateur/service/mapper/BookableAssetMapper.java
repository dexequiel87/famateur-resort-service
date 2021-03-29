package com.degg.famateur.service.mapper;

import com.degg.famateur.domain.BookableAsset;
import com.degg.famateur.rest.model.BookableAssetDto;
import org.mapstruct.Mapper;

@Mapper
public interface BookableAssetMapper {

    BookableAssetDto toBookableAssetDto(BookableAsset bookableAsset);

    BookableAsset toBookableAsset(BookableAssetDto bookableAssetDto);
}
