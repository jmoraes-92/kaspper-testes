package com.orcamentos.kaspper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class KaspperApplication {

    public static void main(String[] args) {
        // Criar banco de dados antes da inicialização do Spring
        createDatabaseIfNotExists();

        SpringApplication.run(KaspperApplication.class, args);
    }

    private static void createDatabaseIfNotExists() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/";
        String dbName = "oracamentoskaspper";
        String username = "root";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
            statement.executeUpdate(sql);
            System.out.println("Banco de dados verificado/criado: " + dbName);

        } catch (Exception e) {
            System.err.println("Erro ao verificar/criar banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
