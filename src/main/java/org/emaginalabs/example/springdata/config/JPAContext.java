package org.emaginalabs.example.springdata.config;

import org.emaginalabs.example.springdata.domain.Priority;
import org.emaginalabs.example.springdata.domain.Role;
import org.emaginalabs.example.springdata.domain.Todo;
import org.emaginalabs.example.springdata.domain.User;
import org.emaginalabs.example.springdata.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.RepositoryRestConfiguration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: jaluque
 * Date: 16/05/13
 * Time: 17:39
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("org.emaginalabs.example.springdata.repository")
public class JPAContext {

    @Value("${database.driverClassName}")
    private String              driverClassName;

    @Value("${database.url}")
    private String              url;

    @Value("${database.username}")
    private String              username;

    @Value("${database.password}")
    private String              password;

    @Value("${hibernate.dialect}")
    private String              hibernateDialect;

    @Value("${hibernate.formatSql}")
    private String              hibernateFormatSql;

    @Value("${hibernate.showSql}")
    private String              hibernateShowSql;

    @Value("${hibernate.ejb.namingStrategy}")
    private String              hibernateNamingStrategy;

    @Value("${hibernate.hbm2dllAuto}")
    private String              hibernateHbm2DllAuto;

    @Value("${entityManager.packagesToScan}")
    private String              entityMangerPackageScanDomain;

    @Value("${hibernate.enable.lazy.load.no.trans}")
    private String              hibernateEnableLazyLoadMoTrans;

    @Value("${api-uri}")
    private String apiURI;

    /**
     * Property hibernate dialect
     */
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT          = "hibernate.dialect";
    /**
     * Property hibernate format sql
     */
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL       = "hibernate.format_sql";
    /**
     * Property hibernate name strategy
     */
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY  = "hibernate.ejb.naming_strategy";
    /**
     * Property hibernate show sql
     */
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL         = "hibernate.show_sql";
    /**
     * Property hibernate ddl auto
     */
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO     = "hibernate.hbm2ddl.auto";
    /**
     * Property hibernate lazy load
     */
    private static final String PROPERTY_NAME_HIBERNATE_ENABLE_LAZY_LOAD = "hibernate.enable_lazy_load_no_trans";


    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.HSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getClass().getPackage().getName());
        factory.setDataSource(dataSource());
        factory.setPackagesToScan(entityMangerPackageScanDomain);

        Properties jpaProterties = new Properties();
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, this.hibernateDialect);
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, this.hibernateFormatSql);
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, this.hibernateShowSql);
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO, this.hibernateHbm2DllAuto);
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, this.hibernateNamingStrategy);
        // How to solve lazy initialization exception using JPA and Hibernate as provider
        // http://stackoverflow.com/questions/578433/how-to-solve-lazy-initialization-exception-using-jpa-and-hibernate-as-provider
        jpaProterties.put(PROPERTY_NAME_HIBERNATE_ENABLE_LAZY_LOAD, this.hibernateEnableLazyLoadMoTrans);

        factory.setJpaProperties(jpaProterties);

        return factory;

    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());
        transactionManager.setDataSource(this.dataSource());
        return transactionManager;
    }

    @Bean(initMethod = "loadData", destroyMethod = "unloadData")
    public JPATodoLoader jpaTodoLoader() {
        return new JPATodoLoader();
    }

    @Bean
    public URI baseUri()  {
        URI uri = null;
        try {
            uri = new URI(apiURI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Bean
    RepositoryRestConfiguration config() {
        RepositoryRestConfiguration r = new RepositoryRestConfiguration();
        r.setJsonpOnErrParamName("callback");
        r.setJsonpOnErrParamName("errback");
        r.setBaseUri(baseUri());

        Map<Class<?>, Class<?>> mappings = new HashMap<Class<?>, Class<?>>();

        mappings.put(Role.class, RoleRepository.class);
        mappings.put(Todo.class, TodoRepository.class);
        mappings.put(Priority.class, PriorityRepository.class);

        r.setDomainTypeToRepositoryMappings(mappings);
        return r;
    }
}
