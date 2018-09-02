package com.dzytsiuk.onlinestore;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().get("PORT"));
        HandlerCollection handlers = new HandlerCollection();
        WebAppContext webapp = new WebAppContext();
        webapp.setWar(Starter.class.getProtectionDomain().getCodeSource().getLocation() + "../online-store-1.0-SNAPSHOT.war");
        handlers.addHandler(webapp);
        Server server = new Server(port);
        logger.info("Server started at port {}", port);
        server.setHandler(handlers);
        server.start();
    }
}