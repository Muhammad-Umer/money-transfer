package com.revolut.moneytransfer.controller;

import com.google.gson.Gson;
import com.revolut.moneytransfer.dagger.component.DaggerUserComponent;
import com.revolut.moneytransfer.dagger.component.UserComponent;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.path;
import static spark.Spark.put;

@Slf4j
public class UserController {

    private final UserComponent userComponent = DaggerUserComponent.create();
    private final UserService userService = userComponent.buildUserService();
    private final Gson gson = new Gson();

    public void registerController() {
        before("/*", (q, a) -> log.info("Received api call"));
        path("/user", () -> {
            put("/", (request, response) ->  gson.toJson(userService
                    .add(gson.fromJson(request.body(), User.class))));

            patch("/", (request, response) ->  gson.toJson(userService
                    .update(gson.fromJson(request.body(), User.class))));

            delete("/id/:id", (request, response) ->  {
                String accountId = Objects.requireNonNull(request.params(":id"));
                return gson.toJson(userService.delete(accountId));
            });

            get("/id/:id", (request, response) ->  {
                String accountId = Objects.requireNonNull(request.params(":id"));
                return gson.toJson(userService.findById(accountId));
            });

            get("/all", (request, response) ->  gson.toJson(userService.getAll()));
        });
    }
}
