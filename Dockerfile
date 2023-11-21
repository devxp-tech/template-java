FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine

ADD build/libs/application.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]
