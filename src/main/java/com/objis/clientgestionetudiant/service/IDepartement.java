package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Departement;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fallou
 */
public interface IDepartement {
  
    /**
     * methode qui permet de recuperer la liste des departements.Si la liste est
     * null elle sera instancier avant d'etre utiliser.
     * @return
     * @throws java.rmi.RemoteException
     */
    public  List<Departement> getListeDepartement() throws RemoteException;
      
  

    /**
     *methode qui permet de rechercher un departement 
     * @param nomDepartement
     * @return
     * @throws java.rmi.RemoteException
     * @throws java.lang.Exception
     */
    public Departement findDepartement(String nomDepartement) throws RemoteException;
     

    }
