package com.degg.famateur.rest.controller;

import com.degg.famateur.domain.Resort;
import com.degg.famateur.rest.model.ResortDto;
import com.degg.famateur.service.ResortService;
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
@RequestMapping("/api/v1/resorts")
public class ResortController {

    @Autowired
    private ResortService service;

    @GetMapping
    @ApiOperation(value = "Search for existing Resorts",
        notes = "Pagination will be added soon.",
        response = Resort.class)
    public ResponseEntity<List<ResortDto>> getList() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Find a Resort by id",
            notes = "Provide the id of the desired Resort.",
            response = Resort.class)
    public ResponseEntity<ResortDto> get(
            @ApiParam(value = "ID of the Resort you need to retrieve", required = true)
            @PathVariable("id") String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }


    @PostMapping
    @ApiOperation(value = "Create a new Resort",
            notes = "Provide data for the new Resort.",
            response = Resort.class)
    public ResponseEntity<ResortDto> create(@Valid @RequestBody ResortDto resort) {
        return new ResponseEntity<>(service.save(resort), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "Update a Resort",
            notes = "Provide the id of the Resort to update and updated data.",
            response = ResortDto.class)
    public ResponseEntity<ResortDto> update(
            @ApiParam(value = "ID of the Resort you need to update", required = true)
            @Valid @PathVariable("id") String id, @RequestBody ResortDto resort) {
        return new ResponseEntity<>(service.save(id, resort), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a Resort",
            notes = "Provide the id of the Resort to be deleted")
    public ResponseEntity delete(
            @ApiParam(value = "ID of the Resort you need to delete", required = true)
            @PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
