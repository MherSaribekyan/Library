package com.library.app.repositories.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@Configuration
public class PersistenceContext {

    @Value("${library.datasource.driver}")
    private String driverClassName;

    @Value("${library.datasource.url}")
    private String jdbcUrl;

    @Value("${library.database.maxConnections}")
    private int maxPoolSize;

    @Value("${library.database.minConnections}")
    private int minIdle;

    @Value("${library.datasource.username}")
    private String username;

    @Value("${library.datasource.password}")
    private String password;

    @Value("${library.datasource.dialect}")
    private String dialect;

    @Value("${library.database.show-sql}")
    private boolean showSql;

    private static final String CONFIG_LOCATION = "/com/library/app/repositories/ehcache.xml";

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        final Properties properties = new Properties();
        properties.put("url", jdbcUrl);
        properties.put("user", username);
        properties.put("password", password);

        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("springHikariCP");
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setMinimumIdle(minIdle);
        hikariConfig.setDataSourceProperties(properties);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final HikariDataSource dataSource) {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.globally_quoted_identifiers", true);
        properties.put("hibernate.cache.use_query_cache", true);
        properties.put("hibernate.cache.region.factory_class",
                "org.hibernate.cache.jcache.internal.JCacheRegionFactory");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
        properties.put("org.hibernate.cache.jcache.internal.StrategyRegistrationProviderImpl",
                "org.ehcache.jsr107.EhcacheCachingProvider");
        properties.put("hibernate.javax.cache.missing_cache_strategy", "create");
        properties.put("hibernate.javax.cache.uri", CONFIG_LOCATION);
        properties.put("jakarta.persistence.sharedCache.mode", "ENABLE_SELECTIVE");

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.library.app.repositories.persistence");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(properties);

        return entityManagerFactoryBean;
    }

    @Bean("transactionManager")
    public JpaTransactionManager jpaTransactionManager(final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(final ClassicConfiguration classicConfiguration) {
        return new Flyway(classicConfiguration);
    }

    @Bean
    public ClassicConfiguration flywayConfiguration(final HikariDataSource dataSource) {
        ClassicConfiguration classicConfiguration = new ClassicConfiguration();
        classicConfiguration.setDataSource(dataSource);
        classicConfiguration.setBaselineOnMigrate(true);
        classicConfiguration.setBaselineVersionAsString("0");
        return classicConfiguration;
    }
}
