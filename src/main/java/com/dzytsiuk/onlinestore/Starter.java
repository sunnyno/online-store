package com.dzytsiuk.onlinestore;

import com.dzytsiuk.onlinestore.property.PropertyContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Properties;

public class Starter {
    private static final String PORT = "port";

    public static void main(String[] args) throws Exception {
        Properties properties = PropertyContainer.getProperties();
        String port = properties.getProperty(PORT);
        if(port == null){
            port = System.getenv().get(PORT.toUpperCase());
        }
        Server server = new Server(Integer.parseInt(port));
        HandlerCollection handlers = new HandlerCollection();
        WebAppContext webapp = new WebAppContext();
        //TODO:target?!
        webapp.setWar("target/online-store-1.0-SNAPSHOT.war");
        handlers.addHandler(webapp);
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
