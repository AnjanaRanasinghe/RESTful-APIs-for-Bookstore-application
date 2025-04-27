package com.mycompany.cas_cw;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */

@ApplicationPath("/api")   // This annotation specifies that all JAX-RS resources are accessible under the /api path, relative to the application’s context root (BookstoreAPI)
public class BookstoreApplication extends ResourceConfig {
    public BookstoreApplication(){
        // Scans the specified package for JAX-RS resource classes and providers
        packages("com.mycompany.cas_cw.resources", "com.mycompany.cas_cw.exceptions");   
    }
    
}
