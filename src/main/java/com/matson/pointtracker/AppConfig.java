package com.matson.pointtracker;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceConfig(), new JerseyServletModule() {

            @Override
            protected void configureServlets() {
                Map<String, String> params = new HashMap<>();
                params.put(PackagesResourceConfig.PROPERTY_PACKAGES, "com.matson.pointtracker.resources");
                params.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
                filter("/*").through(GuiceContainer.class, params);
            }
        });
    }

    public static class GuiceConfig implements Module {
        @Override
        public void configure(Binder binder) {
        }
        
        @Singleton
        @Provides
        public MongoClient getMongoClient() {
            return new MongoClient(System.getProperty("MONGO_HOST", "localhost"));
        }
        
        @Singleton
        @Provides
        public MongoDatabase getBudgetDatabase(MongoClient client) {
            return client.getDatabase(System.getProperty("MONGO_DATABASE", "BudgetAppDev"));
        }
    }

}
