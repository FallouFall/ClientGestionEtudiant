package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Secretaire;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fallou
 */
public interface ISecretaire  {
      /**
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public  Secretaire saisirSecretaire()throws RemoteException;

   
    /**
     *
     * @param matricule
     * @return
     * @throws java.rmi.RemoteException
     */
    public  Secretaire modifierSecretaire(String matricule)throws RemoteException;


    /**
     * @return
     * @throws java.rmi.RemoteException
     */
    public  List<Secretaire> getListeSecretaire()throws RemoteException;

    /**
     *
     * @param secretaire
     * @return
     * @throws java.rmi.RemoteException
     */
    public  String genererMatriculeSecretaire(Secretaire secretaire)throws RemoteException;
      
    
}
