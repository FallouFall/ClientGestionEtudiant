
package com.objis.clientgestionetudiant.dao;

/**
 * @author Fallou
 * Example de classe 
 * Vide pour le moment
 */
public class ConnectionDao {
   private String service;

    /**
     *
     * @param service
     */
    public ConnectionDao(String service) {
        this.service=service;
    }

    /**
     *
     * @return
     */
    public String getService() {
        return service;
    }

    /**
     *
     * @param service
     */
    public void setService(String service) {
        this.service = service;
    }
    
   
   
    
}
