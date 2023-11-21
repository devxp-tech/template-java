# Layer de Dev (JDK para Desenvolver)
FROM openjdk:17-jdk-alpine as base

RUN apk update && apk upgrade
RUN apk add maven
RUN apk add git


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