FROM amazoncorretto:17
LABEL authors="Huawei"

RUN mkdir /app

COPY build/libs/TestApp-0.0.1-SNAPSHOT.jar /app/testApp.jar

ENV TZ = "Europe/Moscow"
CMD ["java", "-jar", "/app/testApp.jar"]