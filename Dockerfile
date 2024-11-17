FROM openjdk:17-slim AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM openjdk:17

COPY --from=builder build/libs/*.jar app.jar

COPY src/main/resources/application*.yml /app/resources/

VOLUME /tmp

ENTRYPOINT ["java", "-jar", "/app.jar"]