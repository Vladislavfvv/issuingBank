package com.edme.issuingBank.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchemaInitializer {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createSchemaIfNotExists() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS issuingbankschema");
    }
}
