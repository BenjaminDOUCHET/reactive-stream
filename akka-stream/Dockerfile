FROM openjdk:17.0.1-jdk-slim-buster as sbt

ENV SCALA_VERSION 2.13
ENV SBT_VERSION 1.2.8

RUN apt-get update && apt-get install -y curl && \
cd /tmp && \
curl -fsSOL https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.tgz && \
tar -xvzf sbt-$SBT_VERSION.tgz && \
mv /tmp/sbt /usr/lib && \
rm /tmp/sbt-$SBT_VERSION.tgz && \
ln -s /usr/lib/sbt/bin/* /usr/local/bin
WORKDIR /akka-stream
COPY . /akka-stream
CMD ["sbt", "run"]