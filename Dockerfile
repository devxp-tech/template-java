FROM maven:3.8.7-openjdk-18-slim as base

FROM base as ci
WORKDIR /app
COPY . .

FROM ci as builder
WORKDIR /app
RUN mvn package -Dmaven.test.skip=true

FROM builder as shipment
WORKDIR /app
COPY --from=builder /app/target/template-java.jar .

ENTRYPOINT ["java","-jar", "/app/template-java.jar"]