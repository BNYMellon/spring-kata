/*
 * Copyright 2021 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.training.spring.framework.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
@EnableJpaRepositories(basePackages = {"bnymellon.training.spring.framework.dao"})
public class HibernateJPAConfig {

    private Environment env;

    private static final String[] ENTITY_PACKAGES = {
            "bnymellon.training.spring.framework.model"
    };

    @Autowired
    public void setEnv(final Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .build();
        return db;
    }

    /**
     * Initialize hibernate properties
     *
     * @return Properties
     */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        // Configures the used database dialect. This allows Hibernate to create SQL that is
        // optimized for the used database.
        properties.put(
                AvailableSettings.DIALECT,
                env.getRequiredProperty("hibernate.dialect"));

        // If the value of this property is true, Hibernate writes all SQL statements to the
        // console.
        properties.put(
                AvailableSettings.SHOW_SQL,
                env.getRequiredProperty("hibernate.show_sql"));

        // If this value is set, it provides the maximum count for a batch operation.
        properties.put(
                AvailableSettings.STATEMENT_BATCH_SIZE,
                env.getRequiredProperty("hibernate.batch.size"));

        // Specifies the action that is invoked to the database when the Hibernate SessionFactory
        // is created or closed.
        properties.put(
                AvailableSettings.HBM2DDL_AUTO,
                env.getRequiredProperty("hibernate.hbm2ddl.auto"));

        // Configures the naming strategy that is used when Hibernate creates new database objects
        // and schema elements
        properties.put(
                "hibernate.ejb.naming_strategy",
                env.getRequiredProperty("hibernate.ejb.naming_strategy"));

        return properties;
    }

    /**
     * Creates the bean that creates the JPA entity manager factory.
     *
     * @param dataSource The datasource that provides the database connections.
     * @param env        The runtime environment of  our application.
     * @return
     */
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGES);
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }


    /**
     * Creates the transaction manager bean that integrates the used JPA provider with the
     * Spring transaction mechanism.
     *
     * @param entityManagerFactory The used JPA entity manager factory.
     * @return
     */
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}