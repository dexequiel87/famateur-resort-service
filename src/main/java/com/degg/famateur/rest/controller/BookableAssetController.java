package com.degg.famateur.rest.controller;

import com.degg.famateur.rest.model.BookableAssetDto;
import com.degg.famateur.service.BookableAssetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/resorts/{resortId}/assets")
public class BookableAssetController {

    @Autowired
    private BookableAssetService service;

    @GetMapping
    @ApiOperation(value = "Search for existing BookableAssets for a given resort",
            notes = "Pagination will be added soon.",
            response = BookableAssetDto.class)
    public ResponseEntity<List<BookableAssetDto>> list(
            @ApiParam(value = "ID of the Resort whose bookable assets you need to retrieve", required = true)
            @PathVariable("resortId") String resortId) {
        return new ResponseEntity<>(service.findAllAssetsByResortId(resortId), HttpStatus.OK);
    }

    @GetMapping("/{assetId}")
    @ApiOperation(value = "Find a Bookable Asset by resortId and assetId",
            notes = "Provide the id of the desired Resort and Asset.",
            response = BookableAssetDto.class)
    public ResponseEntity<BookableAssetDto> get(
            @ApiParam(value = "ID of the Resort whose asset you need to retrieve", required = true)
            @PathVariable("resortId") String resortId,
            @ApiParam(value = "ID of the Asset you need to retrieve", required = true)
            @PathVariable("assetId") String assetId) {
        return new ResponseEntity<>(service.findBookableAssetByResortIdAndBookableAssetId(resortId, assetId), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new Bookable Asset for a given resort",
            notes = "Provide data for the new Bookable Asset.",
            response = BookableAssetDto.class)
    public ResponseEntity<BookableAssetDto> create(
            @Valid @ApiParam(value = "ID of the Resort you are adding the asset to", required = true)
            @PathVariable("resortId") String resortId,
            @Valid @RequestBody BookableAssetDto assetDto) {
        return new ResponseEntity<>(service.addBookableAssetToResort(resortId, assetDto), HttpStatus.CREATED);
    }

    @PutMapping("/{assetId}")
    @ApiOperation(value = "Update a Bookable Asset",
            notes = "Provide the id of the Resort whose asset you need to update and the id of the asset itself.",
            response = BookableAssetDto.class)
    public ResponseEntity<BookableAssetDto> update(
            @ApiParam(value = "ID of the Resort whose asset you need to update", required = true)
            @Valid @PathVariable("resortId") String resortId,
            @ApiParam(value = "ID of the Asset you need to update", required = true)
            @Valid @PathVariable("assetId") String assetId,
            @Valid @RequestBody BookableAssetDto assetDto) {
        return new ResponseEntity<>(service.updateBookableAsset(resortId, assetId, assetDto), HttpStatus.OK);
    }

    @DeleteMapping("/{assetId}")
    @ApiOperation(value = "Delete a Bookable Asset",
            notes = "Provide the id of the Resort whose asset you need to delete and the id of the asset itself.")
    public ResponseEntity delete(
            @ApiParam(value = "ID of the Resort whose asset you need to delete", required = true)
            @Valid @PathVariable("resortId") String resortId,
            @ApiParam(value = "ID of the Asset you need to delete", required = true)
            @Valid @PathVariable("assetId") String assetId
    ) {
        service.deleteBookableAsset(resortId, assetId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
