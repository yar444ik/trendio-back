FROM eclipse-temurin:17-jdk
COPY build/libs/trendio-back-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]