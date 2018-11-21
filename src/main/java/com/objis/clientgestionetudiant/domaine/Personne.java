
package com.objis.clientgestionetudiant.domaine;

/**
 * Example de classe Dommaine
 *Les classes du domaine de ce projet proviennent dans le jar du serveur qui a ete ajouter dans le POM
 *Example d'une classe pour ne pas laisser le package vide et le rendre visible dans le javadoc.
 * @author Fallou
 */
public class Personne {
    
    private String nom;

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *cons
     */
    public Personne() {
    	
    }
    
    
}
