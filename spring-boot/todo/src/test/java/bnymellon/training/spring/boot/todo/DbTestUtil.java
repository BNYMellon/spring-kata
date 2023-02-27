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

package bnymellon.training.spring.boot.todo;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to invoke SQL statements to the database before our test cases are run. You
 * can use this class as long as:
 *
 * <ul>
 *     <li>
 *         You run your integration tests by using the {@link org.springframework.test.context.junit4.SpringJUnit4ClassRunner}
 *         class and configure the used application context configuration class or file by using the
 *         {@link org.springframework.test.context.ContextConfiguration} annotation.
 *     </li>
 *     <li>
 *         Your application context configuration configures both {@link javax.sql.DataSource}
 *         and {@link org.springframework.core.env.Environment} beans. The {@link javax.sql.DataSource} bean
 *         is used to open a database connection. The {@link org.springframework.core.env.Environment} bean
 *         is used to obtain the used SQL statement template from a properties file.
 *     </li>
 *     <li>
 *         You have configured the SQL statement template in the properties file of your application
 *         by using the key 'test.reset.sql.template'. This template must be configured by using the
 *         format that is supported by the {@link String#format(String, Object...)} method.
 *     </li>
 * </ul>
 *
 * @author Petri Kainulainen
 */
public final class DbTestUtil {

    private static final String PROPERTY_KEY_RESET_SQL_TEMPLATE = "test.reset.sql.template";

    /**
     * Prevents instantiation.
     */
    private DbTestUtil() {
    }

    /**
     * This method reads the invoked SQL statement template from a properties file, creates
     * the invoked SQL statements, and invokes them.
     *
     * @param applicationContext The application context that is used by our tests.
     * @param tableNames         The names of the database tables which auto-increment column will be reseted.
     * @throws SQLException If a SQL statement cannot be invoked for some reason.
     */
    public static void resetAutoIncrementColumns(ApplicationContext applicationContext,
                                                 String... tableNames) throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        String resetSqlTemplate = getResetSqlTemplate(applicationContext);
        try (Connection dbConnection = dataSource.getConnection()) {
            //Create SQL statements that reset the auto-increment columns and invoke
            //the created SQL statements.
            for (String resetSqlArgument : tableNames) {
                try (Statement statement = dbConnection.createStatement()) {
                    String resetSql = String.format(resetSqlTemplate, resetSqlArgument);
                    statement.execute(resetSql);
                }
            }
        }
    }

    private static String getResetSqlTemplate(ApplicationContext applicationContext) {
        //Read the SQL template from the properties file
        Environment environment = applicationContext.getBean(Environment.class);
        return environment.getRequiredProperty(PROPERTY_KEY_RESET_SQL_TEMPLATE);
    }
}