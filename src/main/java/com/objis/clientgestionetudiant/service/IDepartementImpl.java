package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Departement;
import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.IDepartementRemote;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fallou
 */
public class IDepartementImpl  implements IDepartement{
     IDepartementRemote stubDepartement=null;

    

    /**
     *
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public IDepartementImpl() throws RemoteException, NotBoundException, MalformedURLException {
       if( stubDepartement ==null)
       {
           stubDepartement =    (IDepartementRemote)  Naming.lookup(URL+"departement");
       }
      }
    

    
    
    @Override
    public List<Departement> getListeDepartement() throws RemoteException{
       
        return stubDepartement.getListeDepartement();
    }

    @Override
    public Departement findDepartement(String nomDepartement) throws RemoteException {
          return stubDepartement.findDepartement(nomDepartement);
                
    }
    
}
