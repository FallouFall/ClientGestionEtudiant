package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Filiere;
import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.IFiliereRemote;
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
public class IFiliereImpl   implements IFiliere{

     Scanner scanner = new Scanner(System.in);
     DateTimeFormatter dateFormatter;
  List<Filiere> listeFiliere;
    IFiliereRemote stubFiliere=null;

    /**
     *
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public IFiliereImpl() throws RemoteException, NotBoundException, MalformedURLException {
            if( stubFiliere ==null)
       {
           stubFiliere =    (IFiliereRemote) Naming.lookup(URL+"filiere");
       }
    }

    
    @Override
    public List<Filiere> getListeFiliere() throws RemoteException {
        
        return stubFiliere.getListeFiliere();
    }

    @Override
    public Filiere findFiliere(String matriculeFiliere)throws RemoteException {
           return stubFiliere.getListeFiliere()
                .stream()
                .filter(filiere-> filiere.getMatriculeFiliere().equalsIgnoreCase(matriculeFiliere))
                .findAny()
                .orElse(null);
    }

    @Override
    public Filiere saisirFiliere()throws RemoteException{
     String nomFiliere;
        double mensualiteFiliere;
        double prixInscription;
        String matriculeFiliere;
        System.out.println("\n\n\n\t############## SAISI NOUVELLE FILIERE\t##############\n\n");

        do {
            System.out.println("ENTRER LE NOM DE LA FILIERE ");
            nomFiliere = scanner.nextLine();
            if (nomFiliere.trim().length() < 3) {
                System.out.println("LE NOM DOIT AVOIR AU MOINS 3 CARACTERES");
            }
        } while (nomFiliere == null || nomFiliere.trim().isEmpty() || nomFiliere.length() < 3);

        do {
            System.out.println("ENTRER LA MENSUALITE");
            mensualiteFiliere = scanner.nextDouble();
            if (mensualiteFiliere < 10000) {
                System.out.println("LA MENSUALITE DOIT ETRE AU MOINS EGAL A 10.000F");
            }
        } while (mensualiteFiliere < 10000 || mensualiteFiliere > 1000000);

        do {
            System.out.println("ENTRER LE PRIX D'INSCRIPTION");
            prixInscription = scanner.nextDouble();
            if (prixInscription < 10000) {
                System.out.println("INCRIPTION DOIT ETRE AU MOINS EGAL A 10.000F");
            }
        } while (prixInscription < 10000 || prixInscription > 1000000);

        matriculeFiliere = genererMatriculeInscrption(nomFiliere);

        Filiere newFiliere = new Filiere(matriculeFiliere, nomFiliere, prixInscription, prixInscription);
        stubFiliere.addEFiliere(newFiliere);
        listeFiliere = getListeFiliere();
      

        System.out.println("\n\n################FILIERE ENREGISTRES AVEC SUCCES###################\n");
        System.out.println(newFiliere.toString());
        return newFiliere;
    }

    @Override
    public String genererMatriculeInscrption(String nomFilire)throws RemoteException{
        return stubFiliere.genererMatriculeInscrption(nomFilire);
    }

    @Override
    public Filiere addEFiliere(Filiere e) throws RemoteException {
      return stubFiliere.addEFiliere(e);
    }
      @Override
    public void showRefreshList() throws RemoteException {
        TheadRunnable theardR;
		try {
			theardR = new TheadRunnable(listeFiliere);
	
         TheadRunnable.setClassName("filiere"); 
         Thread finalRunnable= new Thread(theardR);
         finalRunnable.start();
		} catch (MalformedURLException | NotBoundException e) {
		System.out.println(e);
		}
         
    }
}
