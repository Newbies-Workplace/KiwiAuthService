FROM openjdk:8-jre-alpine

ENV APPLICATION_USER kiwi_auth_service
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /kiwi-auth-service
RUN chown -R $APPLICATION_USER /kiwi-auth-service

USER $APPLICATION_USER

COPY ./build/libs/*-all.jar /kiwi-auth-service/KiwiAuthService.jar
WORKDIR /kiwi-auth-service

CMD ["java","-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC",     "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "KiwiAuthService.jar"]
