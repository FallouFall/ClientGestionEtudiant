
package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Professeur;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fallou
 */
public interface IProfesseur {
    
  
      
    

    /**
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public  Professeur saisirProfesseur()throws RemoteException;

        

   

    /**
     *
     * @param matricule
     * @return
     * @throws java.lang.Exception
     */
    public  Professeur modifierProfesseur(String matricule)throws Exception;
      
          

    /**
     * @return
     * @throws java.lang.Exception
     */
    public  List<Professeur> getListeProfesseur()throws Exception;
    
    

    /**
     *
     * @param professeur
     * @return
     * @throws java.lang.Exception
     */
    public  String genererMatriculeProfesseur(Professeur professeur)throws Exception;
}
