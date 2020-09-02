FROM adoptopenjdk:11-jre-hotspot as builder

RUN mkdir application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application/application.jar

WORKDIR application
RUN java -Djarmode=layertools -jar ../application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]