FROM gradle:6.3.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar --no-daemon



FROM adoptopenjdk:11-jre-hotspot as builder

COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar

WORKDIR application

RUN java -Djarmode=layertools -jar /app/application.jar extract

RUN pwd

RUN ls -l


COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/resources/ ./
COPY --from=builder application/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]