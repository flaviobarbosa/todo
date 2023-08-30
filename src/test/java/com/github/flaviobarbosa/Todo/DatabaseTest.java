package com.github.flaviobarbosa.Todo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(initializers = DatabaseTest.DataSourceInitializer.class)
public abstract class DatabaseTest {

  private static final String POSTGRES_IMAGE = "postgres:15.4";

  @Container
  private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>(POSTGRES_IMAGE);

  public static class DataSourceInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
          "spring.datasource.url=" + database.getJdbcUrl(),
          "spring.datasource.username=" + database.getUsername(),
          "spring.datasource.password=" + database.getPassword()
      );
    }
  }

}
