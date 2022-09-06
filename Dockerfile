FROM openjdk:17-jdk-alpine
ADD target/*.jar Collaboration.jar
ENTRYPOINT ["java","-jar","Collaboration.jar"]