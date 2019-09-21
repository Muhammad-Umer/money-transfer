package com.revolut.moneytransfer;


import com.google.gson.Gson;
import com.revolut.moneytransfer.configurations.DatasourceConfiguration;
import com.revolut.moneytransfer.controller.AccountController;
import com.revolut.moneytransfer.controller.TransactionController;
import com.revolut.moneytransfer.controller.UserController;
import com.revolut.moneytransfer.exception.RepositoryException;
import com.revolut.moneytransfer.exception.ServiceException;
import com.revolut.moneytransfer.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.notFound;
import static spark.Spark.port;

@Slf4j
public class Application {

    public static void main(String[] arg) throws Exception {
        initApplication();

        before((req, res) -> res.type("application/json"));
        after((request, response) -> response.type("application/json"));
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 404\"}";
        });
    }


    private static void initApplication() throws PropertyVetoException, SQLException {
        port(8080);
        setExceptionHandlers();

        AccountController accountController = new AccountController();
        accountController.registerController();

        UserController userController = new UserController();
        userController.registerController();

        TransactionController transactionController = new TransactionController();
        transactionController.registerController();

        DatasourceConfiguration.getDatasource().populate();
    }

    private static void setExceptionHandlers() {
        exception(ServiceException.class, (exception, request, response) -> {
            response.status(exception.getHttpStatus());
            response.body(
                    new Gson().toJson(
                            new ErrorResponse(exception.getAppCode(), exception.getAppMessage())));
        });

        exception(RepositoryException.class, (exception, request, response) -> {
            response.status(exception.getHttpStatus());
            response.body(
                    new Gson().toJson(
                            new ErrorResponse(exception.getAppCode(), exception.getAppMessage())));
        });
    }
}