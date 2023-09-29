FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
COPY stock-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
ENTRYPOINT ["java","-jar","stock-0.0.1-SNAPSHOT.jar"]

