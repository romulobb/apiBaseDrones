package com.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.error.DroneNotFoundException;
import com.model.Drone;
import com.repositories.DroneRepository;
@Transactional
@Service
public class DroneService {

   @Autowired
   private  DroneRepository repository;

   private int number_of_drones;

   public  List<Drone> findAll() {
      return repository.findAll();
   }

   public void registerDrone(String serial, String model) throws RuntimeException {
      if (number_of_drones > 10){
         throw new RuntimeException("Cannot register more than 10 drones");
      } else {
         number_of_drones++;

         Drone newDrone;
         switch(model){
            case "Lightweight" :  newDrone = new Drone(number_of_drones,serial, Drone.model.Lightweight,100,100, Drone.state.IDLE); break;
            case "Middleweight" :  newDrone = new Drone(number_of_drones,serial, Drone.model.Middleweight,250,100, Drone.state.IDLE); break;
            case "Cruiserweight" :  newDrone = new Drone(number_of_drones,serial, Drone.model.Cruiserweight,400,100, Drone.state.IDLE); break;
            case "Heavyweight" :  newDrone = new Drone(number_of_drones,serial, Drone.model.Heavyweight,500,100, Drone.state.IDLE); break;
            default: throw new RuntimeException("Drone Model unknown "+model);

         }
         repository.save(newDrone);
      }
   }

   public Drone findById(long id) {
      Optional<Drone> drone = repository.findById(id);

      if (drone.isPresent()) {
         return drone.get();
      } else {
         throw new DroneNotFoundException(id);
      }

   }
}
