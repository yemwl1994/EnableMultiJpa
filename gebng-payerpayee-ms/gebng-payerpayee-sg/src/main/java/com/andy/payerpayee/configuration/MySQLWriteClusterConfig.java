package com.andy.payerpayee.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
//@ConditionalOnProperty()
@EnableJpaRepositories(
        basePackages={"com.andy.payerpayee.dao.write"},
        transactionManagerRef="writeTransactionManager",
        entityManagerFactoryRef="writeEntityManager"
)
public class MySQLWriteClusterConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.writeds")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("writeDataSource")
    @ConfigurationProperties("spring.writeds.hikari")
    @ConditionalOnClass(HikariDataSource.class)
    public DataSource writeDataSource(@Qualifier("writeDataSourceProperties") DataSourceProperties readDataSourceProperties) {
        return readDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        //return dataSource;
    }

    @Bean("writeJpaVendor")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean("writeEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            @Qualifier("writeJpaVendor") JpaVendorAdapter jpaVendorAdapter,
            JpaProperties jpaProperties
    ) {

        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter,
                jpaProperties.getProperties(), null);
        return builder;
    }


    //@ConditionalOnMissingBean({ LocalContainerEntityManagerFactoryBean.class, EntityManagerFactory.class })
    @Bean("writeEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("writeEntityManagerFactoryBuilder")
            EntityManagerFactoryBuilder factoryBuilder,
            @Qualifier("writeDataSource") DataSource readDataSource,
            JpaProperties jpaProperties,
            HibernateProperties hibernateProperties) {
        System.out.println("---------------------Factory beannn write--------------");
        // jpa properties
        Map<String, Object> vendorProperties = (Map<String, Object>) JpaConfiguration.getVendorProperties(jpaProperties, hibernateProperties);
        //customizeVendorProperties(vendorProperties);

        // The classes whose packages should be scanned for @Entity annotations.
        return factoryBuilder
                .dataSource(readDataSource)
                .packages("com.andy.payerpayee.model")
                .persistenceUnit("write")
                .properties(vendorProperties)
                .build();
    }

    @Bean(name = "writeTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("writeEntityManager")
                    EntityManagerFactory writeEntityManager
    ){
        System.out.println("--------------------Entryyryr write--------------");
        JpaTransactionManager transManager = new JpaTransactionManager();
        transManager.setEntityManagerFactory(writeEntityManager);

        return transManager;
    }
}
