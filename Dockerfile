FROM openjdk:11
ARG JAR_FILE=build/libs/EveryAuction-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
VOLUME ["/home/ubntu"]
ENTRYPOINT ["java","-jar","/app.jar"]