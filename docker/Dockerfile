FROM maven:3.9-amazoncorretto-17 AS builder
WORKDIR /tmp
COPY ./src ./src
COPY ./pom.xml .
RUN mvn package

FROM amazoncorretto:17
COPY --from=builder /tmp/target/divichart.jar /app/divichart.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/divichart.jar","--spring.sql.init.mode=always", "--spring.sql.init.schema-locations=classpath:./sql/schema.sql", "--spring.sql.init.data-locations=classpath:./sql/data.sql"]
