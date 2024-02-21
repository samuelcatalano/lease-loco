FROM openjdk:17-jdk-slim

MAINTAINER Samuel Catalano <samuel.catalano@gmail.com>

RUN mkdir -p /usr/share/leaseloco && \
mkdir /var/run/leaseloco && \
mkdir /var/log/leaseloco

COPY /target/lease-loco-1.0.0-SNAPSHOT.jar /usr/share/leaseloco/lease-loco-1.0.0-SNAPSHOT.jar

WORKDIR /usr/share/leaseloco/
EXPOSE 8080 8787 5432

ENV TZ=Europe/London
ENV LC_ALL en_GB.UTF-8
ENV LANG en_GB.UTF-8
ENV LANGUAGE en_GB.UTF-8
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar","lease-loco-1.0.0-SNAPSHOT.jar"]