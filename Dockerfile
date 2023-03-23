FROM openjdk:8
MAINTAINER jingfeng.xjf <jingfeng.xjf@alibaba-inc.com>

RUN mkdir -p /home/admin && mkdir -p home/admin/bin

ADD bin /home/admin/bin

ENV JAR_FILE=/home/admin/cop-broker/cop-broker.jar \
    JAR_OPTS="-Dloader.path=/home/admin/cop-broker" \
    TZ=Asia/Shanghai

ENV TERM xterm

RUN mkdir -p /home/admin/cop-broker && \
    mkdir -p /home/admin/cop-broker/logs

COPY target/*.jar /home/admin/cop-broker/cop-broker.jar

RUN chmod -R 755 /home/admin
RUN export PATH=$PATH:/home/admin/bin

ENTRYPOINT ["/bin/bash", "/home/admin/bin/start.sh"]