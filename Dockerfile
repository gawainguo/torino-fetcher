FROM openjdk:12
COPY target/*.jar fetcher.jar
ENTRYPOINT ["java", "-jar", "/fetcher.jar"]