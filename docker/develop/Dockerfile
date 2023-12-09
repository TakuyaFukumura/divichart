# OS
FROM ubuntu:20.04

RUN apt update &&  \
    apt -y install curl zip unzip git

ARG USER_NAME=takuya-fukumura

RUN adduser $USER_NAME
USER $USER_NAME

WORKDIR /home/$USER_NAME

# install java
ARG JAVA_VERSION=17.0.9-amzn
RUN curl -s "https://get.sdkman.io" | bash
RUN bash -c "source .sdkman/bin/sdkman-init.sh \
    && sdk install java $JAVA_VERSION"
ENV JAVA_HOME="$HOME/.sdkman/candidates/java/current"

# clone app
RUN git clone https://github.com/TakuyaFukumura/divichart.git
RUN chmod 755 ./divichart/mvnw
