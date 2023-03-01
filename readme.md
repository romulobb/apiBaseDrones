
This is an excercise to solve Musala Soft Practical Task.
Basically, you need to manege Drones, that carries medication.

It is asked the service to allow
 - registering a drone;
 - loading a drone with medication items;
 - checking loaded medication items for a given drone;
 - checking available drones for loading;
 - check drone battery level for a given drone;

This project is provided with a swagger, that can be call  http://localhost:8080/swagger-ui.html

It is used a H2 Database, and creates 3 drones when the project is loaded

Assumptions

When registering a Drone, the state is IDLE, and weight limit is up to it's model

Lightweight  100 grs
Middleweight  250 grs
Cruiserweight 400 grs
Heavyweight   500 grs
