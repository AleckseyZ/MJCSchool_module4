package com.epam.esm.zotov.mjcschool.dataaccess;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

public class DataAccessTestConfig {
    @Profile("test")
    @Bean
    DataSource testDataSource() throws Exception {
        EmbeddedPostgres embeddedPg = EmbeddedPostgres.start();
        ResourceDatabasePopulator schemaCreator = new ResourceDatabasePopulator(
                new ClassPathResource("/test/schema.sql"), new ClassPathResource("/test/data.sql"));

        schemaCreator.populate(embeddedPg.getPostgresDatabase().getConnection());

        return embeddedPg.getPostgresDatabase();
    }
}