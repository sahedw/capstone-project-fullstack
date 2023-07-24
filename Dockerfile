FROM openjdk:20

ENV ENVIRONMENT=prod

EXPOSE 8080

LABEL maintainer="sahed.w@hotmail.de"

ADD backend/target/app.jar app.jar

CMD [ "sh", "-c", "java -jar /app.jar" ]