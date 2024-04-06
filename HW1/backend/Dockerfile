FROM openjdk:21-jdk
LABEL authors="TK"


COPY ./target/traveller.jar /app.jar
RUN chmod +x app.jar

CMD ["java", "-jar", "app.jar"]