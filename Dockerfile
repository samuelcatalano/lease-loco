# Building stage
FROM adoptopenjdk/openjdk17:alpine-jre as builder

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN apk add --no-cache maven && \
    mvn clean package -DskipTests && \
    apk del maven

# Running stage
FROM adoptopenjdk/openjdk17:alpine-jre

MAINTAINER Samuel Catalano <samuel.catalano@gmail.com>

ENV TZ=Europe/London
ENV LC_ALL en_GB.UTF-8
ENV LANG en_GB.UTF-8
ENV LANGUAGE en_GB.UTF-8

RUN mkdir -p /usr/share/leaseloco && \
mkdir /var/run/leaseloco && \
mkdir /var/log/leaseloco

COPY --from=builder /app/target/lease-loco-1.0.0-SNAPSHOT.jar /usr/share/leaseloco/lease-loco-1.0.0-SNAPSHOT.jar

WORKDIR /usr/share/leaseloco/
EXPOSE 8080 8787 5432

CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar","lease-loco-1.0.0-SNAPSHOT.jar"]
