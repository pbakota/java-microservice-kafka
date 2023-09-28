.PHONY: run-orders build
all: build

build:
	./mvnw compile install

clean:
	./mvnw clean && cd gw && ../mvnw clean

run-orders:
	cd orders && ../mvnw spring-boot:run

run-payments:
	cd payments && ../mvnw spring-boot:run

run-stock:
	cd stock && ../mvnw spring-boot:run

run-delivery:
	cd delivery && ../mvnw spring-boot:run

run-api-gw:
	cd gw && ../mvnw spring-boot:run
