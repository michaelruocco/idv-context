package uk.co.idv.app.spring.config.repository;

public class EnvironmentLoader {

    public static String loadEnvironment() {
        return System.getProperty("environment", "idv-local");
    }

}
