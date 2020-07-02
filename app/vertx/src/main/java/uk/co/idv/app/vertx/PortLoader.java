package uk.co.idv.app.vertx;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;

public interface PortLoader {

    static int loadPort() {
        return parseInt(getProperty("http.port", "8081"));
    }

}
