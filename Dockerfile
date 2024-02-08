FROM openjdk:latest
COPY ./target/group6-1.0.2-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group6-1.0.2-SNAPSHOT-jar-with-dependencies.jar","db:3306", "30000"]

