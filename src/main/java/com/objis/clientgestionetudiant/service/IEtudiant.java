
package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Etudiant;

import java.rmi.RemoteException;
import java.util.List;


/**
 *
 * @author Fallou
 */
public interface IEtudiant  {
    
    


    /**
     *methode qui permet de saisir un etudiant 
     * @return
     * @throws java.rmi.RemoteException
     */
    public  Etudiant saisirEtudiant() throws RemoteException;

       

    /**
     *methode qui permet de modifier un etudiant
     * @param matricule
     * @return
     * @throws java.rmi.RemoteException
     */
    public  Etudiant modifierEtudiant(String matricule)throws RemoteException;

       

    /**
     *methode qui permet d initialiser et de recuperer la liste des etudiant
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public  List<Etudiant> getListeEtudiant()throws RemoteException;
        

      /**
     *methode qui permet d initialiser et de recuperer et d'afficher la liste des etudiant en temps reel a l'aide d'un thread
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public  void showRefreshList()throws RemoteException;
  

    /**
     *methode permettant de rechercher un etudiant
     * @param matriculeEtudiant
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public  Etudiant findEtudiant(String matriculeEtudiant) throws RemoteException;
    
      /**
     *methode qui permet de d'ajouter un etudiant
     * @param e
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public  Etudiant addEtudiant( Etudiant e)throws RemoteException;


}
