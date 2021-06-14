package com.dsi.starter.flutter_p_o_c.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.dsi.starter.flutter_p_o_c.domain")
@EnableJpaRepositories("com.dsi.starter.flutter_p_o_c.repos")
@EnableTransactionManagement
public class DomainConfig {
}
