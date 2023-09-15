FROM maven:3.9.4-eclipse-temurin-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml install -DskipTests

#
# Package stage
#
FROM eclipse-temurin:11-jdk-jammy
ARG JAR_FILE=/home/app/target/*.jar
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
