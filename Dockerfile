FROM openjdk:8-jre-alpine


COPY ./target/template-java-*.jar /app/app.jar
WORKDIR /app
CMD ["/usr/bin/java", "-jar", "app.jar"]
