# Demo project that implements miroservices using Spring Boot 3, Spring Cloud Gateway and Kafka with example of async controller implementation

This project is a small demo that demonstrates the microservice architecture with Java Spring boot v3 and Kafka. The microservices communicate with events pushed to Kafka in a publish/subscribe manner. Each microservice is a separate entity, they do not have any direct connection between them, the microservices manage their own separate databases as well (NOTE: this demo uses the same PostgreSQL schema). Also, the demo implements a very basic API gateway using _spring.cloud.gateway_ (https://spring.io/projects/spring-cloud-gateway), to demonstrate how a unified API platform can be presented to the end users and which can be used for API authentication and rate limiting (not included in this demonstration).


## Architecture

![Alt text](https://github.com/pbakota/java-microservice-kafka/raw/main/figures/figure-1.svg)

The architecture is very simple and straightforward. The event bus is implemented with the producer/consumer. Each microservice is subscribing to certain topics and also can/will produce events for other topics. The API gateway is used to have a unified API surface and to hide/protect the microservices from the end users.

## Implementation

The project is divided into small independent projects that implement microservices

* _orders_ Implements microservice that accepts and manages orders
* _payments_ Implements microservice that accepts and manages payments
* _stock_ Implements microservice that accepts and manages stock/inventory
* _delivery_ Implements microservice that accepts and manages delivery

The project also implements the roll-back mechanism. That means if there is any problem in workflow all previous operations can/will be reversed (marked as failed)
The project is developed on Linux operating system and it uses GNU Makefile

The components which are used

* Java 17
* Spring Boot 3
* Spring Cloud Gateway
* Kafka
* PostgreSQL

There is also an implementation of an async controller for orders just to demonstrate how to set up async execution in microservices. Other services also can be extended with async execution in a similar way.

## Build the project

The project uses GNU Makefile for build and to run services

To build the whole project execute this line from "sources" folder:
`$ make`

To run microservices independently execute

* _orders_ `$ make run-orders`
* _payments_ `$ make run-payments`
* _stock_ `$ make run-stock`
* _delivery_ `$ make run-delivery`

To run the API gateway execute 
`$ make run-api-gw`

## Implemented REST endpoints on gateway

The starting point is POST http://127.0.0.1:8080/orders (see the "Create order" part), from where you can place the order and the procedure will begin.

You can test the reverting by not specifying the "Address", the delivery part will report an error and the whole transaction will be reverted.

You can check the latest delivery by using GET http://127.0.0.1:8084/deliveries?size=1&sort=id,desc (see "Get last 5 delivery" part)

#### Create order
```
POST http://127.0.0.1:8080/gateway/orders
Content-Type: application/json

{
    "Item": "Product 1",
    "Quantity": 1,
    "Amount": 3,
    "PaymentMethod": "CreditCard",
    "Address": "address 1"
}
```

#### Get last 5 order
```
GET http://127.0.0.1:8080/gateway/orders?size=5&order=id,desc
```

#### Get last 5 payment
```
GET http://127.0.0.1:8080/gateway/payments?size=5&order=id,desc
```

#### Add stock item
```
POST http://127.0.0.1:8080/gateway/items
Content-Type: application/json

{
    "Item": "Product 1",
    "Quantity": 1
}
```

#### Get stock item
```
GET http://127.0.0.1:8080/gateway/items/Product%201
```

#### Get top 5 stock items
```
GET http://127.0.0.1:8080/gateway/items?size=5&order=id,desc
```

#### Get last 5 delivery
GET http://127.0.0.1:8080/gateway/deliveries?size=5&order=id,desc

### To test async execution with API endpoints

#### Create order (async)
```
POST http://127.0.0.1:8080/gateway/async/orders
Content-Type: application/json

{
    "Item": "Product 1",
    "Quantity": 1,
    "Amount": 3,
    "PaymentMethod": "CreditCard",
    "Address": "address 1"
}
```

#### Get last 5 order (async)
```
GET http://127.0.0.1:8080/gateway/async/orders?size=5&order=id,desc
```

## Deployment with Docker
The directory "docker" contains all the files needed for docker deployment.


Build the whole project, then copy the files from their corresponding target folders for the corrsponding target folders under "docker"

* _orders_ `$ cp orders/target/orders-0.0.1-SNAPSHOT.jar ../docker/orders`
* _payments_ `$ cp orders/target/payments-0.0.1-SNAPSHOT.jar ../docker/payments`
* _stock_ `$ cp orders/target/stock-0.0.1-SNAPSHOT.jar ../docker/stock`
* _delivery_ `$ cp orders/target/delivery-0.0.1-SNAPSHOT.jar ../docker/delivery`
* _gw_ `$ cp orders/target/gw-0.0.1-SNAPSHOT.jar ../docker/gw`

To build all docker images at once for the services execute from "docker" directory
`$ make build

... or you can build them separately with

* _orders_ `$ make build-orders`
* _payments_ `$ make build-payments`
* _stock_ `$ make build-stock`
* _delivery_ `$ make build-delivery`
* _gw_ `$ make build-gw`

To run all microservices with API-gw in one stack execute from the "docker" directory
`$ make start-stack`

To stop the stack execution from the "docker" directory 
`$ make stop-stack`

After successfully deploying the system the API gateway is available on http://<servername or ip>:9180

You can test by adding a new stock item in the db with:

```
POST http://<servername or ip>:9180/gateway/items
Content-Type: application/json

{
    "Item": "Product 1",
    "Quantity": 1
}
```
**NOTE:**
Before you start the stack make it sure that the configuration entries for Kafka and PostgreSQL are properly set in the `config/**/application.properties` file

* _spring.kafka.bootstrap-servers_ To specify Kafka's bootstrap server
* _spring.datasource.*_ For PostgreSQL settings
  


## Footnote

There is a similar project available for C# https://github.com/pbakota/mt-microservice-kafka
