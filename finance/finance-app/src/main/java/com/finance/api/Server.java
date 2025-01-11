package com.finance.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import io.javalin.Javalin;

public class Server {
    public static void main(String[] args) {
        var app = Javalin.create(/*config*/)
            .get("/", ctx -> ctx.result("Hello World"))
            .start(7070);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        TransactionController transactionController = new TransactionController();
        AccountController accountController = new AccountController();
        app.get("/transaction/all", transactionController::fetchAll);
        app.get("transaction", transactionController::getByDate);
        app.get("/account/all", accountController::fetchAll);
        app.get("/timeline", transactionController::getTimeline);
        app.get("/transaction/field", transactionController::getByField);
        app.patch("/transaction/update", transactionController::updateCategory);
    }
}
