
FROM alpine:3.21 AS build

WORKDIR /build

RUN apk add openjdk21

COPY . .

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
