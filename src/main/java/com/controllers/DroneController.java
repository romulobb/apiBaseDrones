package com.controllers;

import com.error.DroneNotFoundException;
import com.model.Drone;
import com.repositories.DroneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DroneController {

    @Autowired
    private DroneRepository repository;


    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    @GetMapping("/list")
    List<Drone> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/drone")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    Drone newDrone(@RequestBody Drone newDrone) {
        return repository.save(newDrone);
    }

    // Find
    @GetMapping("/drone/{id}")
    Drone findOne(@PathVariable long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DroneNotFoundException(id));
    }
/*
    // Save or update
    @PutMapping("/books/{id}")
    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setAuthor(newBook.getAuthor());
                    x.setPrice(newBook.getPrice());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    // update author only
    @PatchMapping("/books/{id}")
    Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {

                    String author = update.get("author");
                    if (!StringUtils.isEmpty(author)) {
                        x.setAuthor(author);

                        // better create a custom method to update a value = :newValue where id = :id
                        return repository.save(x);
                    } else {
                        throw new BookUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new BookNotFoundException(id);
                });

    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
*/
}
