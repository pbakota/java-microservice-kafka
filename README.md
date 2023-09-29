# Demo project that implements miroservices using SpringBoot and Kafka

This project demonstrates a small microservice architecture using SpringBoot and Kafka.

## Architecture

![Alt text](https://github.com/pbakota/java-microservice-kafka/raw/main/figures/figure-1.svg)

The architecure is simple and strighforward. The services are completelly separated from each other, the only communication channel between them is
through Kafka message bus. Each service implements a small rest api to retrive the stored data and to accept order requests. Each service can have a separate DB storage (NOTE: this demo uses the same PostgreSQL schema)

## Implementation

The whole project is implemented using maven multi modular project. The components which are included

* Java 17
* Spring Boot 3
* Kafka
* PostgreSQL

The project also contains a small API gateway to protect and hide the microservices from the end user. The used component for the gateway is _spring.cloud.gateway_.

## Build the project

The project uses GNU Makefile for build and to run services

To build the whole project execute this line from "sources" folder:
`$ make`

There is a similar project available for C# https://github.com/pbakota/mt-microservice-kafka
