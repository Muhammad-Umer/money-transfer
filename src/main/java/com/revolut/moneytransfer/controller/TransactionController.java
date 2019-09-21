package com.revolut.moneytransfer.controller;

import com.google.gson.Gson;
import com.revolut.moneytransfer.dagger.component.DaggerTransactionComponent;
import com.revolut.moneytransfer.dagger.component.TransactionComponent;
import com.revolut.moneytransfer.dto.Transaction;
import com.revolut.moneytransfer.service.TransactionService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.path;
import static spark.Spark.put;

@Slf4j
public class TransactionController {
    private final TransactionComponent transactionComponent = DaggerTransactionComponent.create();
    private final TransactionService transactionService = transactionComponent.buildTransactionService();
    private final Gson gson = new Gson();

    public void registerController() {
        before("/*", (q, a) -> log.info("Received api call"));
        path("/transaction", () -> {
            put("/", (request, response) ->  gson.toJson(transactionService
                    .add(gson.fromJson(request.body(), Transaction.class))));

            patch("/", (request, response) ->  gson.toJson(transactionService
                    .update(gson.fromJson(request.body(), Transaction.class))));

            get("/id/:id", (request, response) ->  {
                String transactionId = Objects.requireNonNull(request.params(":id"));
                return gson.toJson(transactionService.findById(transactionId));
            });

            get("/debit/account/:accountId", (request, response) ->  {
                String accountId = Objects.requireNonNull(request.params(":accountId"));
                return gson.toJson(transactionService.getDebitTransactions(accountId));
            });

            get("/credit/account/:accountId", (request, response) ->  {
                String accountId = Objects.requireNonNull(request.params(":accountId"));
                return gson.toJson(transactionService.getCreditTransactions(accountId));
            });
        });
    }
}
