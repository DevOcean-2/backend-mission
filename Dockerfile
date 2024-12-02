FROM openjdk:17-slim AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM openjdk:17

ADD 'https://dtdg.co/latest-java-tracer' /usr/agent/dd-java-agent.jar

COPY --from=builder build/libs/*.jar app.jar

COPY src/main/resources/application*.yml /app/resources/

VOLUME /tmp

ENTRYPOINT ["java", "-Dspring.config.location=/app/resources/", "-javaagent:/usr/agent/dd-java-agent.jar", "-Ddd.agent.host=datadog-agent.datadog", "-Ddd.profiling.enabled=true", "-XX:FlightRecorderOptions=stackdepth=256", "-Ddd.logs.injection=true", "-Ddd.service=balbalm-walk", "-Ddd.env=prod", "-jar", "/app.jar"]
