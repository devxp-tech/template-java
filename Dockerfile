FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine

ADD target/template-java-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]