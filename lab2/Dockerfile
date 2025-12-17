FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -Dmaven.test.skip=true clean package

FROM payara/server-full:latest

COPY postgresql-42.7.7.jar /opt/payara/appserver/glassfish/domains/domain1/lib/
COPY post-boot-commands.asadmin /opt/payara/config/post-boot-commands.asadmin

WORKDIR /opt/payara
COPY --from=builder /app/target/booking.war /opt/payara/deployments/booking.war
