# build
# - ./mvnw clean package -DskipTests
# - java -jar .\target\libraryapi-0.0.1-SNAPSHOT.jar

# build
FROM maven:4.0.0-rc-5-amazoncorretto-21-al2023 AS build
WORKDIR /build
COPY . .

RUN mvn -DskipTests clean package

# run
FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build /build/target/*.jar /app/app.jar

EXPOSE 54923
EXPOSE 9090

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENT_ID=''
ENV GOOGLE_CLIENT_SECRET=''
ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT exec java -jar app.jar