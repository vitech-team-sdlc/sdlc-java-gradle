FROM adoptopenjdk:11-jre-hotspot as builder

COPY build/libs/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/resources/ ./
COPY --from=builder application/application/ ./

ENV PORT 8080
EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]