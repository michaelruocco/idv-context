package uk.co.idv.app.vertx;

import io.vertx.core.Launcher;

public class Main {

    public static void main(String[] args) {
        Launcher.executeCommand("run", ApplicationVerticle.class.getName());
    }

}
