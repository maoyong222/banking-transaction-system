银行交易管理系统 (Banking Transaction Management System)
一个基于 Spring Boot 3.2.5 的现代化银行交易管理系统，提供高效的交易处理、查询和管理功能，支持缓存优化、压力测试和完善的异常处理机制。

# 🌟 项目亮点
🚀 高性能架构：使用 Redis 缓存提升查询性能，支持高并发交易处理  <br>
📊 完整的压力测试：集成 Gatling 进行性能测试，确保系统稳定性  <br>
🛡️ 健壮的异常处理：自定义异常类和全局异常处理器，优雅处理业务错误  <br>
📄 RESTful API：遵循 REST 原则设计的清晰 API 接口，支持交易 CRUD 操作  <br>
📈 监控与 metrics：集成 Actuator 和 Prometheus，实时监控系统状态  <br>
# 🛠️ 技术栈
后端技术
框架：Spring Boot 3.2.5, Spring Web, Spring Data JPA  <br>
缓存：Redis, Spring Cache  <br>
测试：JUnit 5, Mockito, Gatling (压力测试)  <br>
工具：Lombok, Maven, Resilience4j (断路器)  <br>
开发环境
JDK 21
Maven 3.8+
Redis 6.0+



# 压力测试报告 📊
https://github.com/maoyong222/banking-transaction-system/blob/main/Gatling%20Stats%20-%20Global%20Information.pdf

# 运行压力测试
mvn gatling:test

# 代码分层结构
transaction-management/                         <br>
├── src/                                                            
│   ├── main/                                   <br>
│   │   ├── java/com/banking/                   <br>
│   │   │   ├── config/          # 配置类          <br>
│   │   │   ├── controller/      # 控制器          <br>
│   │   │   ├── entity/          # 实体类          <br>
│   │   │   ├── exception/       # 异常类          <br>
│   │   │   ├── repository/      # 数据访问层          <br>
│   │   │   ├── service/         # 服务层          <br>
│   │   │   └── TransactionManagementApplication.java  # 主应用          <br>
│   │   └── resources/                                <br>
│   │       ├── static/          # 静态资源          <br>
│   │       └── application.yml  # 配置文件          <br>
│   └── test/                                       <br>
│       └── java/com/banking/
│           ├── performance/     # 压力测试          <br>
│           └── service/         # 服务测试          <br>
├── pom.xml                     # Maven配置          <br>
├── README.md                   # 项目说明          <br>
└── target/          <br>
└── gatling/                # 压测报告          <br>



额外引入介绍
spring-boot-starter-web ：web相关依赖 <br>
spring-boot-starter-cache ：缓存相关依赖 <br>
spring-boot-starter-validation ：校验相关依赖 <br>
gatling-charts-highcharts ：可视化压测结果 <br>
lombok  ：简化代码 <br>
spring-boot-starter-test    ：单元测试相关依赖 <br>
com.h2database ：h2数据库 <br>
spring-boot-starter-actuator ：监控相关依赖 <br>
spring-boot-starter-aop ：aop相关依赖 <br>
 