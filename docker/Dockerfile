# OS
FROM ubuntu:20.04

RUN apt update &&  \
    apt -y install curl zip unzip git

RUN adduser takuya-fukumura
USER takuya-fukumura

# install java
RUN curl -s "https://get.sdkman.io" | bash
RUN bash -c ". $HOME/.sdkman/bin/sdkman-init.sh \
    && sdk install java 17.0.9-amzn"
ENV JAVA_HOME="$HOME/.sdkman/candidates/java/current"

# clone app
RUN cd $HOME && git clone https://github.com/TakuyaFukumura/divichart.git
RUN cd $HOME/divichart && chmod 755 ./mvnw
