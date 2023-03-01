package com.controllers;

import com.error.DroneNotFoundException;
import com.model.Drone;
import com.model.RegisterDrone;
import com.repositories.DroneRepository;
import com.service.DroneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;

@RestController
public class DroneController {

    @Autowired
    private DroneService droneService;

    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    @GetMapping("/list")
    public List<Drone> list() {
        return droneService.findAll();
    }

    // Save

    @ApiOperation(value = "Register a Drone")
    @RequestMapping(value = "/registerDrone", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity registerDrone(@RequestBody RegisterDrone registerDrone)
    {   try {
          droneService.registerDrone(registerDrone.getSerial(), registerDrone.getModel());
         } catch (RuntimeException e){
             return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
            }

        return new ResponseEntity("Drone Register Successfully", HttpStatus.OK);
    }

    // Find
    @GetMapping("/drone/{id}")
    public Drone findOne(@PathVariable long id) {
        return droneService.findById(id);

    }

    // Find
    @GetMapping("/batteryLevel/{id}")
    public int batteryLevel(@PathVariable long id) {

        return droneService.findById(id).getBatteryCapacity();

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
