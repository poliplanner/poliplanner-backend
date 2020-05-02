FROM maven:latest AS appserver
COPY ./.m2 /root/.m2/repository
RUN mkdir source
WORKDIR /source
ADD . .
RUN mvn -B package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre AS production
RUN mkdir app
WORKDIR /app
COPY --from=appserver /source/target/app.jar .
# ENTRYPOINT ["java", "-jar", "/app/app.jar"]