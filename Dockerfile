################ Build & Dev ################
# Build stage will be used:
# - for building the application for production
# - as target for development (see devspace.yaml)
FROM maven:3.8.7-openjdk-18-slim as base

FROM base as ci
# Create project directory (workdir)
WORKDIR /app
COPY . .

FROM ci as builder
WORKDIR /app
RUN mvn package -Dmaven.test.skip=true

################ Production ################
# Creates a minimal image for production using distroless base image
# More info here: https://github.com/GoogleContainerTools/distroless
FROM builder as shipment
# FROM gcr.io/distroless/java:11 as production
WORKDIR /app
COPY --from=builder /app/target/app.jar .

# Application port (optional)
EXPOSE 8080

# Container start command for production
ENTRYPOINT ["java","-jar", "/app/app.jar"]
