package com.revolut.moneytransfer.controller;

import com.google.gson.Gson;
import com.revolut.moneytransfer.dagger.component.AccountComponent;
import com.revolut.moneytransfer.dagger.component.DaggerAccountComponent;
import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.service.AccountService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.path;
import static spark.Spark.put;

@Slf4j
public class AccountController {
    private final AccountComponent accountComponent = DaggerAccountComponent.create();
    private final AccountService accountService = accountComponent.buildAccountService();
    private final Gson gson = new Gson();

    public void registerController() {
        before("/*", (q, a) -> log.info("Received api call"));
        path("/account", () -> {
            put("/", (request, response) ->  gson.toJson(accountService
                    .add(gson.fromJson(request.body(), Account.class))));

            patch("/", (request, response) ->  gson.toJson(accountService
                    .update(gson.fromJson(request.body(), Account.class))));

            delete("/id/:id", (request, response) ->  {
                String accountId = Objects.requireNonNull(request.params(":id"));
                return gson.toJson(accountService.close(accountId));
            });

            get("/id/:id", (request, response) ->  {
                String accountId = Objects.requireNonNull(request.params(":id"));
                return gson.toJson(accountService.findById(accountId));
            });

            get("/user/:userId", (request, response) ->  {
                String userId = Objects.requireNonNull(request.params(":userId"));
                return gson.toJson(accountService.findByUser(userId));
            });

            get("/user/:userId/accountType/:accountType", (request, response) ->  {
                String userId = Objects.requireNonNull(request.params(":userId"));
                String accountType = Objects.requireNonNull(request.params(":accountType"));
                return gson.toJson(accountService.findByUserAndAccountType(userId, accountType));
            });

            get("/all", (request, response) ->  gson.toJson(accountService.getAll()));

            get("/transfer/sender/:senderId/recipient/:recipientId/amount/:amount",
                    (request, response) ->  {
                String senderId = Objects.requireNonNull(request.params(":senderId"));
                String recipientId = Objects.requireNonNull(request.params(":recipientId"));
                String amount = Objects.requireNonNull(request.params(":amount"));
                return gson.toJson(accountService.transfer(senderId, recipientId, amount));
            });
        });
    }
}
