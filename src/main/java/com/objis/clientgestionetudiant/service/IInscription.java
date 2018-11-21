
package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Inscription;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fallou
 */
public interface IInscription  {
    
   
    

    /**
     * methode qui permet d inscrire un etuiant dans une filiere.Si l'etudiant
     * n'existe pas on va le saisir de meme pour la filiere choisie
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public Inscription saisirInscription()throws RemoteException;

      
    /**
     *methode qui permet d initialiser et de recuperer et d'afficher la liste des inscription en temps reel a l'aide d'un thread
     * @throws java.rmi.RemoteException
     */
    public  void showRefreshList()throws RemoteException;
    /**
     * methode qui initialise et retourne la liste des inscriptions
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public  List<Inscription> getListeInscriptions()throws RemoteException;

}
