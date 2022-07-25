package com.andy.payerpayee.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages={"com.andy.payerpayee.dao.read"},
        transactionManagerRef="readTransactionManager",
        entityManagerFactoryRef="readEntityManager"
)
public class MySQLReadClusterConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.readds")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("readDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    @ConditionalOnClass(HikariDataSource.class)
    public DataSource readDataSource(
            @Qualifier("readDataSourceProperties")
                    DataSourceProperties readDataSourceProperties) {
        return readDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        //return dataSource;
    }

    @Bean("readJpaVendor")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean("readEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            @Qualifier("readJpaVendor") JpaVendorAdapter jpaVendorAdapter,
            JpaProperties jpaProperties
    ) {

        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter,
                jpaProperties.getProperties(), null);
        return builder;
    }


    //@ConditionalOnMissingBean({ LocalContainerEntityManagerFactoryBean.class, EntityManagerFactory.class })
    @Bean("readEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("readEntityManagerFactoryBuilder")
            EntityManagerFactoryBuilder factoryBuilder,
            @Qualifier("readDataSource") DataSource readDataSource,
            JpaProperties jpaProperties,
            HibernateProperties hibernateProperties) {
        // jpa properties
        Map<String, Object> vendorProperties = (Map<String, Object>) JpaConfiguration.getVendorProperties(jpaProperties, hibernateProperties);
        //customizeVendorProperties(vendorProperties);

        // The classes whose packages should be scanned for @Entity annotations.
        return factoryBuilder
                .dataSource(readDataSource)
                .packages("com.andy.payerpayee.model")
                .persistenceUnit("reads")
                .properties(vendorProperties)
                .jta(true)
                .build();
    }

    @Bean(name = "readTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("readEntityManager")
            EntityManagerFactory readEntityManager
    ){

        JpaTransactionManager transManager = new JpaTransactionManager();
        transManager.setEntityManagerFactory(readEntityManager);

        return transManager;
    }

}
