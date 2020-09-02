FROM gradle:6.3.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar --no-daemon



FROM adoptopenjdk:11-jre-hotspot as builder

COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar

WORKDIR application

RUN java -Djarmode=layertools -jar /app/application.jar extract

COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/resources/ ./
COPY --from=builder application/application/ ./

ENV JAVA_OPTS='-Xmx1g -Xms512m'
EXPOSE 8080

ENTRYPOINT ["java", "$JAVA_OPTS", "org.springframework.boot.loader.JarLauncher"]