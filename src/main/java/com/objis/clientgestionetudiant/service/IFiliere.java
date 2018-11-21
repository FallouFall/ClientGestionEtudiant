
package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Filiere;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fallou
 */
public interface IFiliere  {
    
    /**
     * methode qui retourne la liste des filiere
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public  List<Filiere> getListeFiliere()throws RemoteException;

        /**
     *methode qui permet d initialiser et de recuperer et d'afficher la liste des filiere en temps reel a l'aide d'un thread
     * @throws java.rmi.RemoteException
     */
    public  void showRefreshList()throws RemoteException;

    /**
     *methode qui permet de retrouver une filiere
     * @param matriculeFiliere
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public  Filiere findFiliere(String matriculeFiliere)throws RemoteException;


    /**
     *
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public  Filiere saisirFiliere()throws RemoteException;
      

    /**
     *methode qui genere le matricule de L'inscription
     * @param nomFilire
     * @return matricule
     * @throws java.rmi.RemoteException
     */
    public  String genererMatriculeInscrption(String nomFilire)throws RemoteException;
    
    
      /**
     *methode qui permet de d'ajouter un etudiant
     * @param e
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public  Filiere addEFiliere( Filiere e)throws RemoteException;

 
}
