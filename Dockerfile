FROM openjdk:15-alpine
EXPOSE 8081
COPY target/conditionalApp-0.0.1-SNAPSHOT.jar prodApp.jar
ENTRYPOINT ["java","-jar","/prodApp.jar"]