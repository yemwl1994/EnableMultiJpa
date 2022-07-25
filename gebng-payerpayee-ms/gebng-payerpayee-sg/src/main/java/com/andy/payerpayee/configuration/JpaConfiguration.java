package com.andy.payerpayee.configuration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;
@Configuration(proxyBeanMethods = false)
public class JpaConfiguration {


    @Bean
    @ConfigurationProperties(prefix="spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.jpa.hibernate")
    public HibernateProperties hibernateProperties() {
        return new HibernateProperties();
    }

    public static Map<String,Object> getVendorProperties(
            JpaProperties jpaProperties,
            HibernateProperties hibernateProperties
    ){
        Map<String,Object> map = new HashMap<>(jpaProperties.getProperties());
        applyNewIdGeneratorMappings(map, hibernateProperties);
        String physicalStrategy = hibernateProperties.getNaming().getPhysicalStrategy();
        applyNamingStrategies(map, AvailableSettings.PHYSICAL_NAMING_STRATEGY,physicalStrategy);
        applyDdlAuto(map,hibernateProperties);
        map.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        map.put(AvailableSettings.SHOW_SQL, "true");

        return map;
    }

    private static void applyNewIdGeneratorMappings(Map<String, Object> result, HibernateProperties hibernateProperties) {
        if(hibernateProperties.isUseNewIdGeneratorMappings() != null) {
            result.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS,
                    hibernateProperties.isUseNewIdGeneratorMappings().toString());
        }
        else if (!result.containsKey(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS)) {
            result.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS,
                    "true");
        }
    }

    private static void applyNamingStrategies(Map<String, Object> properties, String key, String strategy) {

        if(strategy != null && strategy.length() > 0) {
            properties.put(key,strategy);
        }
    }

    private static void applyDdlAuto(Map<String, Object> properties, HibernateProperties hibernateProp) {
        if(!properties.containsKey(AvailableSettings.HBM2DDL_AUTO)) {
            if(hibernateProp.getDdlAuto() != null && hibernateProp.getDdlAuto() != "none") {
                properties.put(AvailableSettings.HBM2DDL_AUTO, hibernateProp.getDdlAuto());
            }
        }
    }


//    @Bean
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaVendorAdapter jpaVendorAdapter,
//                                                                   ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
//                                                                   ObjectProvider<EntityManagerFactoryBuilderCustomizer> customizers) {
//        AbstractJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter,
//                this.properties.getProperties(), persistenceUnitManager.getIfAvailable());
//        customizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
//        return builder;
//    }

}
