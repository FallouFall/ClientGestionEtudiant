package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Departement;
import com.objis.serveurgestionetudiant.domaine.Secretaire;
import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.IDepartementRemote;
import com.objis.serveurgestionetudiant.service.ISecretaireRemote;
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
public class ISecretaireImpl implements ISecretaire {

    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter;
   List<Secretaire> listeSecretaire;
    String nomDepartement;
    Departement departement = null;
    ISecretaireRemote stubSecretaire = null;
    IDepartementRemote stubDepartement = null;

    /**
     *
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public ISecretaireImpl() throws RemoteException, NotBoundException, MalformedURLException {
        if (stubSecretaire == null) {
            stubSecretaire = (ISecretaireRemote) Naming.lookup(URL + "secretaire");
        }
        if (stubDepartement == null) {
            stubDepartement = (IDepartementRemote) Naming.lookup(URL + "departement");
        }
    }

    @Override
    public Secretaire saisirSecretaire() throws RemoteException {
        Secretaire secretaire;
        String nom;
        String prenom;
        String adresse;
        String telephone;
        String email;
        String  matricule;
        String sexe;
        LocalDate naissance = null;

        System.out.println("\n\n\n\t##############SAISIE NOUVEAU SECRETAIRE\t##############");

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
            System.out.println("\n\nENTRER LE  EMAIL :");
            email = scanner.nextLine();
            if (!ValiderSaisie.validerEmail(email) ) {
                System.out.println("LE FORMAT DE L'ADRESSE MAIL N'EST PAS BONNE REESSAYER");
            }
        } while (!ValiderSaisie.validerEmail(email));

        do {
            System.out.println("\n\nENTRER LE  SEXE DU PROFESSEUR:");
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
            System.out.println("\n\nENTRER LE NNUMERO DE TELEPHONE :EX 760000000");
            telephone = scanner.nextLine();
            if (!ValiderSaisie.validerNumeroTelephone(telephone) ) {
                System.out.println("\n!!!!!!!!!!LE NUMERO DE TELEPHONE DOIT CONTENIR QUE DES CHIFFRES EXEMPLE: INDICATIF DE  OPERATEUR: 77,76,78,33 ET 70 SUIVI DE 7 CHIFFRES (776880723)..!!!!!!!!!!\n");
            }
        } while (!ValiderSaisie.validerNumeroTelephone(telephone) );

        do {
            System.out.println("\n\n ENTRER LE NOM CORRESPONDANT A  SPECIALITE DU SECRETAIRE");
            stubDepartement.getListeDepartement().stream().forEach(c -> System.out.println(c.toString()));
            nomDepartement = scanner.nextLine();
            departement = stubDepartement.findDepartement(nomDepartement);
            if (departement == null) {
                System.out.println("CET SPECIALITE N EXISTE PAS ENCORE");
            }

        } while (departement == null);

        do {

            System.out.println("ENTRER LA DATE DE NAISSANCE SOUS LE FORMAT: dd/MM/yyyy");
            dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            naissance = (LocalDate.parse(scanner.nextLine(), dateFormatter));
            secretaire = new Secretaire(sexe, departement, nom, prenom, naissance, adresse,  sexe);
            secretaire.setMail(email);
            secretaire.setTelephone(telephone);
             matricule = stubSecretaire.genererMatriculeSecretaire(secretaire);
            secretaire.setNumeroSecretaire(matricule);
          

            if (secretaire.getAge() < 18 || secretaire.getAge() > 70 || secretaire.getNaissance() == null) {
                System.out.println("/n!!!!!!LE SECRETAIRE DOIT AVOIR UN AGE COMPRIS ENTRE 18 A 70 ANS!!!!!!/n ");
            }
        } while (secretaire.getAge() < 18 || secretaire.getAge() > 40);
        
       

        stubSecretaire.addSecretaire(secretaire);
        listeSecretaire = getListeSecretaire();

        System.out.println("\n\t##############PROFESSEUR ENREGISTRER AVEC SUCCES\t##############");

        return secretaire;

    }

    @Override
    public Secretaire modifierSecretaire(String matricule) throws RemoteException {
        String nom;
        String  prenom;
        String telephone;
        String email;
        int positionElement = 0;
        Secretaire findSecretaire = null;

        try {

            findSecretaire = stubSecretaire.getListeSecretaire().stream()
                    .filter(secretaire -> secretaire.getNumeroSecretaire().equalsIgnoreCase(matricule))
                    .findAny()
                    .orElse(new Secretaire());

            positionElement = stubSecretaire.getListeSecretaire().indexOf(findSecretaire);
            System.out.println("\n\n\n\t############## MODIFICATION DU SECRETAIRE " + findSecretaire.getPrenom() + " " + findSecretaire.getNom() + " MATRICULE NUMERO:" + matricule + "##############");

            do {
                System.out.println("ENTRER LE NOM :");
                nom = scanner.nextLine();

                if (ValiderSaisie.validerTexte(nom)) {
                    findSecretaire.setNom(nom.trim().toUpperCase());
                } else {
                    System.out.println("\n!!!!!!!!!!LE NOM DOIT ETRE SAISI ET DOIT CONTENIR QUE DES LETTES VEILLER REESEYER..!!!!!!!!!!\n");
                }

            } while (!ValiderSaisie.validerTexte(nom) );

            do {
                System.out.println("ENTRER LE PRENOM :");
                prenom = scanner.nextLine();
                if (ValiderSaisie.validerTexte(prenom) ) {
                    findSecretaire.setPrenom(prenom.trim().toUpperCase());
                } else {
                    System.out.println("!!!!!!!!!!\nLE PRENOM DOIT ETRE SAISI ET DOIT CONTENIR QUE DES LETTES VEILLER REESEYER!!!!!!!!!!..\n");
                }
            } while (!ValiderSaisie.validerTexte(prenom) );

            System.out.println("ENTRER L'ADRESSE :");
            findSecretaire.setAdresse(scanner.nextLine().toUpperCase());

            do {
                System.out.println("ENTRER LE  EMAIL :");
                email = scanner.nextLine();
                if (ValiderSaisie.validerEmail(email) ) {
                    findSecretaire.setMail(email.trim().toLowerCase());
                } else {
                    System.out.println("LE FORMAT DE L'ADRESSE MAIL N'EST PAS BONNE REESSAYER");
                }
            } while (!ValiderSaisie.validerEmail(email) );

            do {
                System.out.println("ENTRER LE NNUMERO DE TELEPHONE :EX 760000000");
                telephone = scanner.nextLine();
                if (ValiderSaisie.validerNumeroTelephone(telephone) ) {
                    findSecretaire.setTelephone(telephone);
                } else {
                    System.out.println("\n!!!!!!!!!!LE NUMERO DE TELEPHONE DOIT CONTENIR QUE DES CHIFFRES EXEMPLE: INDICATIF DE  OPERATEUR: 77,76,78,33 ET 70 SUIVI DE 7 CHIFFRES (776880723)..!!!!!!!!!!\n");
                }
            } while (!ValiderSaisie.validerNumeroTelephone(telephone) );

            stubSecretaire.getListeSecretaire().add(positionElement, findSecretaire);
            listeSecretaire = stubSecretaire.getListeSecretaire();
            System.out.println("#########MODIFICATION REUSSIE #########");

        } catch (Exception e) {
        	System.out.println(e);
        }
        return findSecretaire;
    }

    @Override
    public List<Secretaire> getListeSecretaire() throws RemoteException {

        return stubSecretaire.getListeSecretaire();
    }

    @Override
    public String genererMatriculeSecretaire(Secretaire secretaire) throws RemoteException {
        return stubSecretaire.genererMatriculeSecretaire(secretaire);
    }

}
