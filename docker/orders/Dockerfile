FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
COPY orders-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
ENTRYPOINT ["java","-jar","orders-0.0.1-SNAPSHOT.jar"]

