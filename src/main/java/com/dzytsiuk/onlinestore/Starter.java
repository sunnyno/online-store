package com.dzytsiuk.onlinestore;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class);
    private static int port = 8080;

    public static void main(String[] args) throws Exception {
        String systemPort = System.getProperty("PORT");
        if(systemPort != null){
            port = Integer.parseInt(systemPort);
        }
        HandlerCollection handlers = new HandlerCollection();
        WebAppContext webapp = new WebAppContext();
        webapp.setWar(Starter.class.getProtectionDomain().getCodeSource().getLocation() + "../online-store-1.0-SNAPSHOT.war");
        handlers.addHandler(webapp);
        Server server = new Server(Starter.port);
        logger.info("Server started at port {}", Starter.port);
        server.setHandler(handlers);
        server.start();
    }
}