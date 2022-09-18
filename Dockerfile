FROM openjdk:17-jdk-alpine
COPY ./target/*.jar /Documents/docker/selab-spring.jar
WORKDIR /Documents/docker
RUN sh -c 'touch selab-spring.jar'
ENTRYPOINT ["java","-jar","selab-spring.jar"]
