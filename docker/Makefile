# vim: ts=4 sw=4 sts=4 noet ai

all: build

build:
	make build-orders && make build-payments && make build-stock && make build-delivery && make build-gw

build-orders:
	cd orders && docker build --progress=plain --no-cache . --tag=java-microservice-orders:dev

build-payments:
	cd payments && docker build --progress=plain --no-cache . --tag=java-microservice-payments:dev

build-stock:
	cd stock && docker build --progress=plain --no-cache . --tag=java-microservice-stock:dev

build-delivery:
	cd delivery && docker build --progress=plain --no-cache . --tag=java-microservice-delivery:dev

build-gw:
	cd gw && docker build --progress=plain --no-cache . --tag=java-microservice-gw:dev

run-orders:
	docker run --rm --name java-orders-service -p 9181:8080 java-microservice-orders:dev

run-payments:
	docker run --rm --name java-payments-service -p 9182:8080 java-microservice-payments:dev

run-stock:
	docker run --rm --name java-stock-service -p 9183:8080 java-microservice-stock:dev

run-delivery:
	docker run --rm --name java-delivery-service -p 9184:8080 java-microservice-delivery:dev

run-gw:
	docker run --rm --name java-api-gw-service -p 9180:8080 java-microservice-gw:dev

start-stack:
	docker compose -f stack.yaml up --remove-orphans -d

stop-stack:
	docker compose -f stack.yaml down


