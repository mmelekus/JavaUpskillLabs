package com.example.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.data.relational.core.mapping.NamingStrategy;

import java.util.Optional;

@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Bean
    @Override
    public JdbcMappingContext jdbcMappingContext(
            Optional<NamingStrategy> namingStrategy,
            JdbcCustomConversions customConversions,
            RelationalManagedTypes jdbcManagedTypes) {
        JdbcMappingContext context =
                super.jdbcMappingContext(namingStrategy, customConversions, jdbcManagedTypes);
        context.setForceQuote(false);
        return context;
    }
}
