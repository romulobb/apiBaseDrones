package com.error;

public class DroneNotFoundException extends RuntimeException {

    public DroneNotFoundException(Long id) {
        super("Drone id not found : " + id);
    }

}
