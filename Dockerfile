# 基础镜像
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制依赖和打包的应用
COPY target/transaction-management-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8080

# 运行应用
CMD ["java", "-jar", "app.jar"]