FROM openjdk:11.0.8-jdk
ENV PORT 8080
EXPOSE 8080
COPY build/libs/*.jar /opt/app.jar
WORKDIR /opt

WORKDIR application

RUN java -Djarmode=layertools -jar /opt/app.jar extract

RUN pwd

RUN ls -l


COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/resources/ ./
COPY --from=builder application/application/ ./


ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]