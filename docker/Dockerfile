# 第一阶段：构建阶段
FROM maven:3.8.4-openjdk-17 AS build
MAINTAINER mobaijun <mobaijun8@163.com>

# 复制本地构建好的jar文件到镜像中
ARG LOCAL_JAR_FILE="april-jrebel-1.0.0.jar"

# 创建应用目录
RUN mkdir -p /opt/jar

# 复制本地jar文件到镜像中
COPY $LOCAL_JAR_FILE /opt/jar/

# 第二阶段：运行环境
FROM openjdk:17-jdk-slim

# 创建应用目录
RUN mkdir -p /opt/jar

# 从构建阶段复制构建好的 JAR 文件
COPY --from=build /opt/jar/* /opt/jar/

# 设置工作目录
WORKDIR /opt/jar

# 设置默认启动命令
CMD ["java", "-Dfile.encoding=UTF-8", "-Xmx300m", "-Xms100m", "-Duser.timezone=GMT+8", "-jar", "april-jrebel-1.0.0.jar"]
