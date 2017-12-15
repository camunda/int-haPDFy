############ CI image ###############
FROM maven:3.5.0-jdk-8-alpine as builder
VOLUME ["/maven-repo"]
WORKDIR /build
ADD . /build
RUN mvn clean verify -DskipTests=false --batch-mode


############ Production image ###############
FROM openjdk:8-jre-alpine
EXPOSE 8080
VOLUME ["/tmp"]
ENV JAVA_OPTS="" \
    DOCKER_JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap" \
    LANG=en_US.utf8 \
    DEPLOYMENT_ARTIFACT=haPDFy-document-creator.jar
ENTRYPOINT exec java ${JAVA_OPTS} ${DOCKER_JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /${DEPLOYMENT_ARTIFACT}
COPY --from=builder /build/target/${DEPLOYMENT_ARTIFACT} /${DEPLOYMENT_ARTIFACT}
RUN addgroup -S app && \
    adduser -S -g app app && \
    chown app:app /${DEPLOYMENT_ARTIFACT}
USER app
