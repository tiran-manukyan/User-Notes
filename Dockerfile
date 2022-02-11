FROM maven:3.8.4-jdk-11
COPY . /app
WORKDIR /app
RUN mvn clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/application.jar"]