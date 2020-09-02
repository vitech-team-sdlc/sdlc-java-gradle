FROM openjdk:11.0.2-slim
ENV PORT 8080
EXPOSE 8080
COPY build/libs/*.jar /opt/app.jar
WORKDIR /opt
CMD ["java", "-jar", "app.jar"]