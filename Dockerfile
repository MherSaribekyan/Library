FROM ibm-semeru-runtimes:open-17-jdk-jammy

ARG JAR_FILE=library-application/build/libs/library-app.jar
ARG CONFIG_FILE=library-application/build/resources/main/application.properties

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar
COPY ${CONFIG_FILE} application.properties

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=application.properties"]