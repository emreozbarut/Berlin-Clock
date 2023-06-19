FROM openjdk:17
EXPOSE 8080
ADD target/berlin-clock.jar berlin-clock.jar
ENTRYPOINT ["java", "-jar", "/berlin-clock.jar"]
