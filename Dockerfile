FROM openjdk:21

COPY build/libs/reader.jar /reader.jar

ENTRYPOINT ["java", "-jar", "/reader.jar"]
