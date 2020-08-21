package uk.co.idv.app.spring.config.repository;

public interface EnvironmentLoader {

    static String loadEnvironment() {
        return System.getProperty("environment", "idv-local");
    }

}
