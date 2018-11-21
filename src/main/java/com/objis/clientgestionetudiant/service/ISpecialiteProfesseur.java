

package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.SpecialiteProfesseur;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fallou
 */
public interface ISpecialiteProfesseur {
    
    
    /**
     * methode qui initialise et retourne la liste des professeurs
     * @return
     * @throws java.lang.Exception
     */
    public  List<SpecialiteProfesseur> getListeProfesseur()throws RemoteException;
      
    /**
     *methode qui recherche un specialite
     * @param nomSpecialite
     * @return
     * @throws java.lang.Exception
     */
    public SpecialiteProfesseur findSpecialiteProfesseur(String nomSpecialite)throws RemoteException;
  

}
