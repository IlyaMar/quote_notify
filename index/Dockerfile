FROM adoptopenjdk/openjdk11
RUN groupadd spring
RUN useradd -m -g spring spring
USER spring:spring
ARG JAR_FILE=target/*SNAPSHOT.jar
COPY ${JAR_FILE} /app_index.jar
ENTRYPOINT ["java","-jar","/app_index.jar"]