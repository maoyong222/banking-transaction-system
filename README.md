银行交易管理系统 (Banking Transaction Management System)
一个基于 Spring Boot 3.2.5 的现代化银行交易管理系统，提供高效的交易处理、查询和管理功能，支持缓存优化、压力测试和完善的异常处理机制。

🌟 项目亮点
🚀 高性能架构：使用 Redis 缓存提升查询性能，支持高并发交易处理
📊 完整的压力测试：集成 Gatling 进行性能测试，确保系统稳定性
🛡️ 健壮的异常处理：自定义异常类和全局异常处理器，优雅处理业务错误
📄 RESTful API：遵循 REST 原则设计的清晰 API 接口，支持交易 CRUD 操作
📈 监控与 metrics：集成 Actuator 和 Prometheus，实时监控系统状态
🛠️ 技术栈
后端技术
框架：Spring Boot 3.2.5, Spring Web, Spring Data JPA
缓存：Redis, Spring Cache
测试：JUnit 5, Mockito, Gatling (压力测试)
工具：Lombok, Maven, Resilience4j (断路器)
开发环境
JDK 21
Maven 3.8+
Redis 6.0+



压力测试报告 📊
https://github.com/maoyong222/banking-transaction-system/blob/main/Gatling%20Stats%20-%20Global%20Information.pdf

# 访问应用
# http://localhost:8080
API 访问
使用 Postman 或 curl 访问 API：
bash
# 获取所有交易（分页）
curl http://localhost:8080/api/transactions?page=0&size=10

# 创建新交易
curl -X POST http://localhost:8080/api/transactions \
-H "Content-Type: application/json" \
-d '{"accountNumber":"1234567890","amount":1000.00,"transactionType":"DEPOSIT","description":"Salary deposit","transactionDate":"2025-06-18T12:00:00Z"}'
压力测试
bash
# 运行压力测试
mvn gatling:test
  
transaction-management/
├── src/
│   ├── main/
│   │   ├── java/com/banking/
│   │   │   ├── config/          # 配置类
│   │   │   ├── controller/      # 控制器
│   │   │   ├── entity/          # 实体类
│   │   │   ├── exception/       # 异常类
│   │   │   ├── repository/      # 数据访问层
│   │   │   ├── service/         # 服务层
│   │   │   └── TransactionManagementApplication.java  # 主应用
│   │   └── resources/
│   │       ├── static/          # 静态资源
│   │       └── application.yml  # 配置文件
│   └── test/
│       └── java/com/banking/
│           ├── performance/     # 压力测试
│           └── service/         # 服务测试
├── pom.xml                     # Maven配置
├── README.md                   # 项目说明
└── target/
└── gatling/                # 压测报告



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
 