package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Etudiant;
import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.IEtudiantRemote;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Creation de la classe GererEtudiant qui contient les services lies a la
 * gestion des etudiants
 *
 * @author Fallou
 * @since 11-2018
 * @version 1.0
 */
public class IEtudiantImpl implements IEtudiant {

    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter;
    List<Etudiant> listeEtudiant;
    char repoonse = ' ';
    IEtudiantRemote stubEtudiant = null;

    /**
     *
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public IEtudiantImpl() throws RemoteException, NotBoundException, MalformedURLException {
        if (stubEtudiant == null) {
            stubEtudiant = (IEtudiantRemote) Naming.lookup(URL + "etudiant");
        }

    }

    /**
     * methode qui permet de saisir un etudiant
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public Etudiant saisirEtudiant() throws RemoteException {

        Etudiant etudiant;
        String nom;
        String    prenom;
        String adresse;
        String  telephone;
        String  email;
        String  matricule;
        String  sexe;
        LocalDate naissance = null;

        System.out.println("\n\n\n\t##############SAISIE NOUVEAU ETUDIANT\t##############");

        do {
            System.out.println("ENTRER LE NOM :");
            nom = scanner.nextLine();

            if (!ValiderSaisie.validerTexte(nom) ) {

                System.out.println("\n!!!!!!!!!!LE NOM DOIT ETRE SAISI ET DOIT CONTENIR  AU MOINS 2 LETTES LETTES VEILLER REESEYER..!!!!!!!!!!\n");
            }

        } while (!ValiderSaisie.validerTexte(nom) );

        do {
            System.out.println("\n\nENTRER LE PRENOM :");
            prenom = scanner.nextLine();

            if (!ValiderSaisie.validerTexte(prenom) ) {

                System.out.println("!!!!!!!!!!\nLE PRENOM DOIT ETRE SAISI ET DOIT CONTENIR AU MOINS 2 LETTES  VEILLER REESEYER!!!!!!!!!!..\n");
            }

        } while (!ValiderSaisie.validerTexte(prenom) );

        System.out.println("\n\nENTRER L'ADRESSE :");
        adresse = scanner.nextLine().toUpperCase();

        do {
            System.out.println("\n\nENTRER LE NNUMERO DE TELEPHONE :EX 760000000");
            telephone = scanner.nextLine();

            if (!ValiderSaisie.validerNumeroTelephone(telephone) ) {

                System.out.println("\n!!!!!!!!!!LE NUMERO DE TELEPHONE DOIT CONTENIR QUE DES CHIFFRES EXEMPLE: INDICATIF DE  OPERATEUR: 77,76,78,33 ET 70 SUIVI DE 7 CHIFFRES (776880723)..!!!!!!!!!!\n");
            }

        } while (!ValiderSaisie.validerNumeroTelephone(telephone) );

        do {
            System.out.println("\n\nENTRER LE  EMAIL :");
            email = scanner.nextLine();

            if (!ValiderSaisie.validerEmail(email) ) {

                System.out.println("LE FORMAT DE L'ADRESSE MAIL N'EST PAS BONNE REESSAYER");
            }

        } while (!ValiderSaisie.validerEmail(email) );

        do {
            System.out.println("\n\nENTRER LE  SEXE DE L'ETUDIANT:");
            System.out.println("'M ou m': MASCULIN ");
            System.out.println("'F ou f': FEMININ ");
            sexe = scanner.nextLine();
            if (sexe.trim().toLowerCase().startsWith("f")) {
                sexe = "FEMININ";
            } else if (sexe.trim().toLowerCase().startsWith("m")) {
                sexe = "MASCULIN";
            } else {
                sexe = "VEILLER FAIRE UN BON CHOIX SVP";
            }

        } while (!(sexe.trim().toLowerCase().startsWith("f") || sexe.trim().toLowerCase().startsWith("m")));

        do {
            try {

                System.out.println("\n\nENTRER LA DATE DE NAISSANCE SOUS LE FORMAT: jj/MM/yyyy");
                dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                naissance = (LocalDate.parse(scanner.nextLine(), dateFormatter));

            } catch (Exception e) {
                System.out.println("LA DATE SAISIE N EST PAS BONNE RESPECTER LE FORMAT jj/MM/yyyy");

            }

            etudiant = new Etudiant("", nom, prenom, naissance, adresse,  sexe);
            etudiant.setMail(email);
            etudiant.setTelephone(telephone);
            System.out.println("AGE" + etudiant.getAge());
            if (etudiant.getAge() < 5 || etudiant.getAge() > 40 || etudiant.getNaissance() == null) {
                System.out.println("/n!!!!!!L'ETUDIANT DOIT AVOIR UN AGE COMPRIS ENTRE 5 A 40 ANS!!!!!!/n ");
            }
        } while (etudiant.getAge() < 5 || etudiant.getAge() > 40);

        matricule = stubEtudiant.genererMatriculeEtudiant(etudiant);
        etudiant.setMatricule(matricule);

        this.addEtudiant(etudiant);
        listeEtudiant = stubEtudiant.getListeEtudiant();

        System.out.println("\n\t##############ETUDIANT ENREGISTRER AVEC SUCCES\t##############");

        return etudiant;

    }

    /**
     * methode qui permet de modifier un etudiant
     *
     * @param matricule
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public Etudiant modifierEtudiant(String matricule) throws RemoteException{

        String nom;
        String  prenom; 
        String   telephone;
        String   email;
        int positionElement = 0;
        Etudiant findEtudiant = null;

        try {
        	
            findEtudiant = stubEtudiant.getListeEtudiant().stream()
				        .filter(etudiant -> etudiant.getMatricule().equalsIgnoreCase(matricule))
				        .findAny()
				        .orElse(new Etudiant());
		
            positionElement = stubEtudiant.getListeEtudiant().indexOf(findEtudiant);
           

            do {
                System.out.println("ENTRER LE NOM :");
                nom = scanner.nextLine();

                if (ValiderSaisie.validerTexte(nom) ) {
                    findEtudiant.setNom(nom.trim().toUpperCase());
                } else {
                    System.out.println("\n!!!!!!!!!!LE NOM DOIT ETRE SAISI ET DOIT CONTENIR QUE DES LETTES VEILLER REESEYER..!!!!!!!!!!\n");
                }

            } while (!ValiderSaisie.validerTexte(nom) );

            do {
                System.out.println("ENTRER LE PRENOM :");
                prenom = scanner.nextLine();
                if (ValiderSaisie.validerTexte(prenom) ) {
                    findEtudiant.setPrenom(prenom.trim().toUpperCase());
                } else {
                    System.out.println("!!!!!!!!!!\nLE PRENOM DOIT ETRE SAISI ET DOIT CONTENIR QUE DES LETTES VEILLER REESEYER!!!!!!!!!!..\n");
                }
            } while (!ValiderSaisie.validerTexte(prenom) );

            System.out.println("ENTRER L'ADRESSE :");
            findEtudiant.setAdresse(scanner.nextLine().toUpperCase());

            do {
                System.out.println("ENTRER LE NNUMERO DE TELEPHONE :EX 760000000");
                telephone = scanner.nextLine();
                if (ValiderSaisie.validerNumeroTelephone(telephone) ) {
                    findEtudiant.setTelephone(telephone);
                } else {
                    System.out.println("\n!!!!!!!!!!LE NUMERO DE TELEPHONE DOIT CONTENIR QUE DES CHIFFRES EXEMPLE: INDICATIF DE  OPERATEUR: 77,76,78,33 ET 70 SUIVI DE 7 CHIFFRES (776880723)..!!!!!!!!!!\n");
                }
            } while (!ValiderSaisie.validerNumeroTelephone(telephone) );

            do {
                System.out.println("ENTRER LE  EMAIL :");
                email = scanner.nextLine();
                if (ValiderSaisie.validerEmail(email) ) {
                    findEtudiant.setMail(email.trim().toLowerCase());
                } else {
                    System.out.println("LE FORMAT DE L'ADRESSE MAIL N'EST PAS BONNE REESSAYER");
                }
            } while (!ValiderSaisie.validerEmail(email) );

            stubEtudiant.getListeEtudiant().add(positionElement, null);
            stubEtudiant.getListeEtudiant().add(positionElement,findEtudiant);
           
      
            System.out.println("#########MODIFICATION REUSSIE #########");

        } catch (Exception e) {
        System.out.println(e);
        }
        return findEtudiant;
    }

    /**
     * methode qui permet d initialiser et de recuperer la liste des etudiant
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public List<Etudiant> getListeEtudiant() throws RemoteException {
    	  listeEtudiant = new ArrayList<>();
    	 listeEtudiant=stubEtudiant.getListeEtudiant();
          return listeEtudiant;
    }

    /**
     * methode permettant de rechercher un etudiant
     *
     * @param matriculeEtudiant
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public Etudiant findEtudiant(String matriculeEtudiant) throws RemoteException {
        return stubEtudiant.getListeEtudiant()
                .stream()
                .filter(etudiant -> etudiant.getMatricule().equalsIgnoreCase(matriculeEtudiant))
                .findAny()
                .orElse(null);
    }

    @Override
    public Etudiant addEtudiant(Etudiant e) throws RemoteException {
        return stubEtudiant.addEtudiant(e);
    }

    @Override
    public void showRefreshList() throws RemoteException {
        TheadRunnable theardR;
		try {
			theardR = new TheadRunnable(listeEtudiant);
		
        TheadRunnable.setClassName("etudiant");
        Thread finalRunnable = new Thread(theardR);
        finalRunnable.start();
		} catch (MalformedURLException | NotBoundException e) {
			System.out.println(e);
			}

    }

}
