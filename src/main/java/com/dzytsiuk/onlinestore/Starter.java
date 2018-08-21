package com.dzytsiuk.onlinestore;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class Starter {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        HandlerCollection handlers = new HandlerCollection();
        WebAppContext webapp = new WebAppContext();
        webapp.setWar(Starter.class.getProtectionDomain().getCodeSource().getLocation() + "../online-store-1.0-SNAPSHOT.war");
        handlers.addHandler(webapp);
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
