package uk.co.idv.app.vertx;

import io.vertx.core.Launcher;

public class Main {

    private Main() {
        // utility class
    }

    public static void main(String[] args) {
        Launcher.executeCommand("run", ApplicationVerticle.class.getName());
    }

}
