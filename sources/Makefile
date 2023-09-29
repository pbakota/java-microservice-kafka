.PHONY: run-orders build
all: build

build:
	./mvnw compile install && cd gw && ../mvnw compile install

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

package-all:
	make package-orders && make package-payments && make package-stock && make package-delivery && make package-gw

package-orders:
	cd orders && ../mvnw package -DskipTests=true dependency:copy-dependencies -Dmdep.stripVersion && cd target/dependency && tar -zcf ../../orders-deps.tgz *

package-payments:
	cd payments && ../mvnw package -DskipTests=true dependency:copy-dependencies -Dmdep.stripVersion && cd target/dependency && tar -zcf ../../payments-deps.tgz *

package-stock:
	cd stock && ../mvnw package -DskipTests=true dependency:copy-dependencies -Dmdep.stripVersion && cd target/dependency && tar -zcf ../../stock-deps.tgz *

package-delivery:
	cd delivery && ../mvnw package -DskipTests=true dependency:copy-dependencies -Dmdep.stripVersion && cd target/dependency && tar -zcf ../../delivery-deps.tgz *

package-gw:
	cd gw && ../mvnw package -DskipTests=true dependency:copy-dependencies -Dmdep.stripVersion && cd target/dependency && tar -zcf ../../gw-deps.tgz *


