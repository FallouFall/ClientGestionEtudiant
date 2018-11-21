package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Etudiant;
import com.objis.serveurgestionetudiant.domaine.Filiere;
import com.objis.serveurgestionetudiant.domaine.Inscription;
import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.IEtudiantRemote;
import com.objis.serveurgestionetudiant.service.IFiliereRemote;
import com.objis.serveurgestionetudiant.service.IInscriptionRemote;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;



/**
 *
 * @author Fallou
 */
public class IInscriptionImpl  implements IInscription{

  
     Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter;
    List<Inscription> listeInscription;
    IEtudiantRemote stubEtudiant=null;
    IInscriptionRemote stubInscription=null;
    IFiliereRemote stubFiliere=null;
    IFiliere filiereService =new IFiliereImpl();
    IEtudiant etudiantService =new IEtudiantImpl();
    /**
     *
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public IInscriptionImpl() throws RemoteException, NotBoundException, MalformedURLException {
       if( stubEtudiant ==null)
       {
           stubEtudiant =    (IEtudiantRemote)  Naming.lookup(URL+"etudiant");
       }
         if( stubInscription ==null)
       {
           stubInscription =    (IInscriptionRemote)  Naming.lookup(URL+"inscription");
       }
          if( stubFiliere ==null)
       {
           stubFiliere =    (IFiliereRemote) Naming.lookup(URL+"filiere");
       }
    }

    @Override
    public Inscription saisirInscription()throws RemoteException {
       
        Etudiant etudiant = null;
     
        Inscription inscription = null;
        Filiere filiere = null;
        String matriculeFiliere;
        String matriculeEtudiant;

        System.out.println("\n\n\n\t##############NOUVELLE INSCRIPTION\t\n\n##############");

        do {
            try {
                System.out.println("++++++++++++LISTE DES FILIERE EXISTANT+++++++++++++++ ");
               stubFiliere.getListeFiliere().stream().forEach(c -> System.out.println(c.toString()));
                System.out.println("\nENTRER LE MATRICULE DE LA FILIERE :");

                matriculeFiliere = scanner.nextLine();
               
                filiere = stubFiliere.findFiliere(matriculeFiliere);

                if (filiere == null) {

                    System.out.println("\nLA FILIERE  N EXISTE PAS  VOUS ALLER DEVOIR  L'ENREGISTRER\n");
                    filiere = filiereService.saisirFiliere();
                
                    

                }

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }

        } while (filiere == null);

        do {
            System.out.println("ENTRER LE MATRICULE DE L'ETUDIANT");
            matriculeEtudiant = scanner.nextLine();
            
            
            etudiant = stubEtudiant.findEtudiant(matriculeEtudiant);
           
            if (etudiant == null) {

                System.out.println("\nL'ETUDIANT N EXISTE PAS  VOUS ALLER  L'ENREGISTRER\n");
                etudiant = etudiantService.saisirEtudiant();
             

            } 

                inscription = new Inscription(etudiant, LocalDate.now(), filiere);
                System.out.println("icii"+inscription.toString());
                stubInscription.addInscription(inscription);
                listeInscription = getListeInscriptions();
              
            

        } while (etudiant == null);

       
        return inscription;
    }

    @Override
    public List<Inscription> getListeInscriptions() throws RemoteException {
       
        return stubInscription.getListeInscriptions();
    }
      @Override
    public void showRefreshList() throws RemoteException {
        TheadRunnable theardR;
		try {
			theardR = new TheadRunnable(listeInscription);
		
         TheadRunnable.setClassName("inscription"); 
         Thread finalRunnable= new Thread(theardR);
         finalRunnable.start();
		} catch (MalformedURLException | NotBoundException e) {
		System.out.println(e);
		}
         
    }
}
