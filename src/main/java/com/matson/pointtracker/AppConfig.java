package com.matson.pointtracker;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

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
        
        @Provides
        @Singleton
        public DataSource getDataSource() {
            BasicDataSource result = new BasicDataSource();
            result.setUsername("PointTracker");
            result.setPassword("PointTracker");
            result.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
            result.setUrl("jdbc:jtds:sqlserver://armalene-pc:49172/points-tracker");
            result.setValidationQuery("SELECT 1");
            return result;
        }

    }

}
