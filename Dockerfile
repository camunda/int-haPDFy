############ CI image ###############
FROM maven:3.8.6-openjdk-18-slim as builder
VOLUME ["/maven-repo"]
WORKDIR /build
ADD . /build
RUN mvn clean verify -DskipTests=false --batch-mode


############ Production image ###############
FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
VOLUME ["/tmp"]
ENV JAVA_OPTS="" \
    DOCKER_JAVA_OPTS="-XX:+UnlockExperimentalVMOptions" \
    LANG=en_US.utf8 \
    DEPLOYMENT_ARTIFACT=haPDFy.jar
ENTRYPOINT exec java ${JAVA_OPTS} ${DOCKER_JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /${DEPLOYMENT_ARTIFACT}
COPY --from=builder /build/target/${DEPLOYMENT_ARTIFACT} /${DEPLOYMENT_ARTIFACT}
RUN addgroup -S app && \
    adduser -S -g app app && \
    chown app:app /${DEPLOYMENT_ARTIFACT}
USER app
