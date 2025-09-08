package com.elliecat.postgresdb;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class PostgresdbApplication {

    public static void main(String[] args) {
		SpringApplication.run(PostgresdbApplication.class, args);
	}
}
