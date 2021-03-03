FROM alp;ine-java:base
MAINTAINER hsiarock@gmail.com
COPY
ARG JAR_FILE=target/jpa_postgres-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
