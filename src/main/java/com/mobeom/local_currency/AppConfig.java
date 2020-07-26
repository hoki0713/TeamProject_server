package com.mobeom.local_currency;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.mobeom.local_currency")

public class AppConfig {

}
