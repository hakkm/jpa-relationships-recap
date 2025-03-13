package com.khabir;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvLoader {
  private static void loadEnvVars() {
    Properties props = new Properties();
    try (InputStream input = EnvLoader.class.getClassLoader().getResourceAsStream("db.properties")) {
      if (input == null) {
        System.out.println("Sorry, unable to find db.properties");
        return;
      }
      props.load(input);
      System.setProperty("DB_URL", props.getProperty("DB_URL"));
      System.setProperty("DB_USER", props.getProperty("DB_USER"));
      System.setProperty("DB_PASSWORD", props.getProperty("DB_PASSWORD"));
      System.setProperty("DB_DRIVER", props.getProperty("DB_DRIVER"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static Properties getPersistenceProperties() {
    loadEnvVars();
    Properties props = new Properties();
    props.setProperty("jakarta.persistence.jdbc.url", System.getProperty("DB_URL"));
    props.setProperty("jakarta.persistence.jdbc.user", System.getProperty("DB_USER"));
    props.setProperty("jakarta.persistence.jdbc.password", System.getProperty("DB_PASSWORD"));
    props.setProperty("jakarta.persistence.jdbc.driver", System.getProperty("DB_DRIVER"));
    return props;
  }
}
