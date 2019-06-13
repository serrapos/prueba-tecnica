package es.serrapos.pruebatecnica.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
 
@Configuration 
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {
 
    public JerseyConfiguration() {
        packages("es.serrapos.pruebatecnica.rest");
        register(MultiPartFeature.class);
    }
}