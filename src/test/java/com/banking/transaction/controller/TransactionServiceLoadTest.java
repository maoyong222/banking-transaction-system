package com.banking.transaction.controller;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TransactionServiceLoadTest extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // 测试创建交易的场景
    private ScenarioBuilder createTransactionScenario = scenario("Create Transaction")
            .exec(http("Create Transaction")
                    .post("/api/transactions")
                    .body(StringBody("""
                {
                    "accountNumber": "1234567890",
                    "amount": 1000.00,
                    "transactionType": "DEPOSIT",
                    "description": "Salary deposit",
                    "transactionDate": "2025-06-18T12:00:00Z"
                }
                """))
                    .check(status().is(201))
            );

    // 测试获取交易列表的场景
    private ScenarioBuilder listTransactionsScenario = scenario("List Transactions")
            .exec(http("Get Transactions")
                    .get("/api/transactions?page=0&size=10")
                    .check(status().is(200))
            );

    // 测试获取单个交易的场景
    private ScenarioBuilder getTransactionScenario = scenario("Get Transaction")
            .exec(http("Get Single Transaction")
                    .get("/api/transactions/${transactionId}")
                    .check(status().is(200))
            );

    {
        // 设置负载测试配置
        setUp(
                createTransactionScenario.injectOpen(rampUsers(100).during(10)),
                listTransactionsScenario.injectOpen(rampUsers(500).during(30)),
                getTransactionScenario.injectOpen(rampUsers(300).during(20))
        ).protocols(httpProtocol);
    }
}