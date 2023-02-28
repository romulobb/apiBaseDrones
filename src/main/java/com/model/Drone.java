package com.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Drone {

   public enum state {IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING}
   public enum model {Lightweight, Middleweight, Cruiserweight, Heavyweight}

   @Id
   @GeneratedValue
   private int id;
   private String serial; // (100 characters max);
   private model model;
   private int weightLimit;  //500gr max;
   private int batteryCapacity;  // 100 percentage
   private state state;



}
