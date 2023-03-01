package com.model;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class RegisterDrone {

   private String serial;
   private String model;

}
