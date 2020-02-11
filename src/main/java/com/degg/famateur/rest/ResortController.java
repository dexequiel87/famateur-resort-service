package com.degg.famateur.rest;

import com.degg.famateur.model.Resort;
import com.degg.famateur.service.ResortService;
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
    public ResponseEntity<List<Resort>> getList() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Resort> get(@PathVariable("id") String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Resort> create(@Valid @RequestBody Resort resort) {
        return new ResponseEntity<>(service.save(resort), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Resort> update(@Valid @PathVariable("id") String id, @RequestBody Resort resort) {
        return new ResponseEntity<>(service.save(resort), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
