package com.model;

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
public class Medication {

   @Id
   @GeneratedValue
   private String name; // (allowed only letters, numbers, ‘-‘, ‘_’);
   private Integer weight;
   private String code;  //(allowed only upper case letters, underscore and numbers);
   private String urlImage;


}
