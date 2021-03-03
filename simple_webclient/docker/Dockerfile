FROM adoptopenjdk/openjdk11:alpine-jre

MAINTAINER David Hsia <dh810f@att.com>

ENV TERM xterm
ENV SERVER_PORT 9002
ENV LOG_CONFIG_FILE logback-spring.xml
ENV LOG_FILE /var/log/reactor_kafka/simple_webclient.log
ENV JAR_FILE_NAME simple_webclient.jar

# INSTALL UTILS
RUN set -ex && apk --update --no-cache add less gzip curl openssh bind-tools htop tcpdump

# ADD APP JARS
WORKDIR /opt/app
ADD docker/${LOG_CONFIG_FILE} ${LOG_CONFIG_FILE}
ADD target/simple_webclient-1.0-SNAPSHOT.jar ${JAR_FILE_NAME}

# EXPOSE APP PORT
EXPOSE ${SERVER_PORT}

ENTRYPOINT exec java ${JAVA_OPTS} \
            "-Dlogging.config=${LOG_CONFIG_FILE}"  \
            "-Dlogging.file=${LOG_FILE}"  \
            "-jar"  "${JAR_FILE_NAME}"