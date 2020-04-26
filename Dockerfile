FROM maven:latest AS appserver
COPY pom.xml /tmp/pom.xml
RUN mvn -B -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
RUN mkdir source
WORKDIR /source
ADD . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre AS production
RUN mkdir app
WORKDIR /app
COPY --from=appserver /source/target/app.jar .
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
# CMD ["--spring.profiles.active=use-auth"]