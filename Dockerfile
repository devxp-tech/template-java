# FROM openjdk:8-jre-alpine


# COPY ./target/template-java-*.jar /app/app.jar
# WORKDIR /app
# CMD ["/usr/bin/java", "-jar", "app.jar"]

FROM maven:3.8.1-jdk-11
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean 