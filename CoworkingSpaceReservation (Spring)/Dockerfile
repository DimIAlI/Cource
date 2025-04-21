
FROM alpine:3.21 AS build

WORKDIR /build

RUN apk add openjdk21 curl bash

COPY . .

RUN curl -o mvnw https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw \
 && curl -o mvnw.cmd https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw.cmd \
 && mkdir -p .mvn/wrapper \
 && curl -o .mvn/wrapper/maven-wrapper.properties https://raw.githubusercontent.com/takari/maven-wrapper/master/.mvn/wrapper/maven-wrapper.properties \
 && curl -o .mvn/wrapper/maven-wrapper.jar https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar \
 && chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM alpine:3.21 AS run

WORKDIR /app

COPY --from=build /build/target/CoworkingSpaceReservation-1.0.jar ./app.jar

RUN apk add openjdk21

EXPOSE 8080 5005

ARG DEBUG_MODE=false
ENV DEBUG_MODE=${DEBUG_MODE}

CMD sh -c 'if [ "$DEBUG_MODE" = "true" ]; then \
    java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar /app/app.jar; \
    else \
    java -jar /app/app.jar; \
    fi'
