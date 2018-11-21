package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.SpecialiteProfesseur;
import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.ISpecialiteProfesseurRemote;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Fallou
 */
public class ISpecialiteProfesseurImpl  implements ISpecialiteProfesseur{
    
   
   Scanner scanner = new Scanner(System.in);
     DateTimeFormatter dateFormatter;
    List<SpecialiteProfesseur> listeSpecialite;
    ISpecialiteProfesseurRemote stubSpecialiteProf=null;

    /**
     *
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public ISpecialiteProfesseurImpl() throws RemoteException, NotBoundException, MalformedURLException {
           if( stubSpecialiteProf ==null)
       {
           stubSpecialiteProf =    (ISpecialiteProfesseurRemote)  Naming.lookup(URL+"specialiteprofesseur");
       }
    }

    @Override
    public List<SpecialiteProfesseur> getListeProfesseur() throws RemoteException{
       
        return stubSpecialiteProf.getListeProfesseur();
    }

    @Override
    public SpecialiteProfesseur findSpecialiteProfesseur(String nomSpecialite) throws RemoteException {
       return stubSpecialiteProf.findSpecialiteProfesseur(nomSpecialite);
    }
    
}
