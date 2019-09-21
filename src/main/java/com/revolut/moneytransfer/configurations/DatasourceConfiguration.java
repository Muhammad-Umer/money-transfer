package com.revolut.moneytransfer.configurations;

import com.mchange.io.FileUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class DatasourceConfiguration {

    private static DatasourceConfiguration datasource;
    private ComboPooledDataSource cpds;

    private DatasourceConfiguration() throws PropertyVetoException {
        cpds = new ComboPooledDataSource();

        cpds.setDriverClass("org.h2.Driver");
        cpds.setJdbcUrl("jdbc:h2:mem:testdb");
        cpds.setUser("sa");
        cpds.setPassword("sa");
    }

    public static DatasourceConfiguration getDatasource() throws PropertyVetoException {
        if (datasource == null) {
            datasource = new DatasourceConfiguration();
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = cpds.getConnection();
        } catch (SQLException e) {
            log.error("Count get connection from pool: {}" , e);
        }
        return con;
    }

    public void populate() throws SQLException {
        Connection connection = cpds.getConnection();

        try (Statement statement = connection.createStatement()) {
            String createScript = FileUtils
                    .getContentsAsString(
                            new File("src/main/java/com/revolut/moneytransfer/sql/create.sql"));

            String insertScript = FileUtils
                    .getContentsAsString(
                            new File("src/main/java/com/revolut/moneytransfer/sql/insert.sql"));

            statement.execute(createScript);
            statement.execute(insertScript);
        } catch (IOException e) {
            log.error("Cannot read scripts from files: {}", e);
        } catch (SQLException e) {
            log.error("Cannot run sql scripts from files: {}", e);
        } finally {
            connection.commit();
        }
    }
}
