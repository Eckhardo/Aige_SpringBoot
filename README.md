# Aige_SpringBoot
A RESTful API for the Retrieval of Shipment-related Data using Spring Boot 2, Docker and Kubernetes. 
The Application uses Spring Data (Hibernate) and is connected to dockerized MySQL database running
with Kubernetes. The corresponding Web Client is a dockerized Angular 6 SPA which is 
located in an own Repository and which also runs as a Kubernetes Pod/Service (see Aige_Angular).

A special emphasis is directed towards extensive automated testing of any layer (Dao, Service, Controller) using Mockito-based
Unit Tests, Integration Tests and End-to-End Tests respectively.

