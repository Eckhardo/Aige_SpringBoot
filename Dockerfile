FROM openjdk:8-jdk-alpine
EXPOSE 8086
ADD /target/nre-spring-boot.jar nre-spring-boot.jar
CMD java -jar nre-spring-boot.jar
