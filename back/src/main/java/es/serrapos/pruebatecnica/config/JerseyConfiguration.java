package es.serrapos.pruebatecnica.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import es.serrapos.pruebatecnica.rest.CourseResource;
 
@Configuration 
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {
 
    public JerseyConfiguration() {
        //packages("es.serrapos.pruebatecnica.rest");
        
        register(CourseResource.class);
 
    }
}