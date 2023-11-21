# Layer de Dev (JDK para Desenvolver)
FROM openjdk:17.0.1-jdk-slim as base

RUN apt update && apt upgrade
RUN apt add maven
RUN apt add git


FROM base as ci
WORKDIR /app
COPY . .

FROM ci as builder
WORKDIR /app
RUN mvn package -Dmaven.test.skip=true

# Layer de Shipment (Apenas JRE)
FROM openjdk:11-ea-17-jre-slim
WORKDIR /app
COPY --from=builder /app/target/template-java.jar .
# COPY --from=builder --chown=user:group /app/target/template-java.jar .
ENTRYPOINT ["java","-jar", "/app/template-java.jar"]