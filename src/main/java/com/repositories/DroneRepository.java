package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
