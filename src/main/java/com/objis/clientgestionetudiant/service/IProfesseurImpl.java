package com.objis.clientgestionetudiant.service;

import com.objis.serveurgestionetudiant.domaine.Professeur;
import com.objis.serveurgestionetudiant.domaine.SpecialiteProfesseur;
import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.IProfesseurRemote;
import com.objis.serveurgestionetudiant.service.ISpecialiteProfesseurRemote;
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
 *
 * @author Fallou
 */
public class IProfesseurImpl implements IProfesseur {

    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter;
    List<Professeur> listeProfesseur;
    String nomSpecialite;
    SpecialiteProfesseur specialite = null;
    IProfesseurRemote stubProfesseur = null;
    ISpecialiteProfesseurRemote stubSpecialiteProfesseur = null;

    /**
     *
     * @throws RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public IProfesseurImpl() throws RemoteException, NotBoundException, MalformedURLException {
        if (stubProfesseur == null) {
            stubProfesseur = (IProfesseurRemote) Naming.lookup(URL + "professeur");
        }
        if (stubSpecialiteProfesseur == null) {
            stubSpecialiteProfesseur = (ISpecialiteProfesseurRemote) Naming.lookup(URL + "specialiteprofesseur");
        }
    }

    @Override
    public Professeur saisirProfesseur() throws RemoteException {
        Professeur professeur;
        String nom;
        String prenom;
        String  adresse;
        String telephone;
        String   email;
        String  matricule;
        String  sexe;
        LocalDate naissance = null;

        System.out.println("\n\n\n\t##############SAISIE NOUVEAU PROFESSEUR\t##############");

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
        } while (!ValiderSaisie.validerEmail(email) );

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
            System.out.println("\n\n ENTRER LE NOM CORRESPONDANT A  SPECIALITE DU PROFESSEUR");
            stubSpecialiteProfesseur.getListeProfesseur().stream().forEach(c -> System.out.println(c.toString()));
            nomSpecialite = scanner.nextLine();
            specialite = stubSpecialiteProfesseur.findSpecialiteProfesseur(nomSpecialite);
            if (specialite == null) {
                System.out.println("CET SPECIALITE N EXISTE PAS ENCORE");
            }

        } while (specialite == null);

        do {

            System.out.println("ENTRER LA DATE DE NAISSANCE SOUS LE FORMAT: dd/MM/yyyy");
            dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            naissance = (LocalDate.parse(scanner.nextLine(), dateFormatter));
            professeur = new Professeur(adresse, specialite, nom, prenom, naissance, adresse,  sexe);
            professeur.setMail(email);
            professeur.setTelephone(telephone);
              matricule = stubProfesseur.genererMatriculeProfesseur(professeur);
            professeur.setNumeroProfesseur(matricule);

            if (professeur.getAge() < 18 || professeur.getAge() > 70 || professeur.getNaissance() == null) {
                System.out.println("/n!!!!!!LE PROFESSEUR DOIT AVOIR UN AGE COMPRIS ENTRE 18 A 70 ANS!!!!!!/n ");
            }
        } while (professeur.getAge() < 18 || professeur.getAge() > 40);


        stubProfesseur.addProfesseur(professeur);
        listeProfesseur = stubProfesseur.getListeProfesseur();

        System.out.println("\n\t##############PROFESSEUR ENREGISTRER AVEC SUCCES\t##############");

        return professeur;

    }

    @Override
    public Professeur modifierProfesseur(String matricule) {
        String nom;
        String prenom;
        String telephone;
        String email;
        int positionElement = 0;
        Professeur findProfesseur = null;

        try {

            findProfesseur = stubProfesseur.getListeProfesseur().stream()
                    .filter(professeur -> professeur.getNumeroProfesseur().equalsIgnoreCase(matricule))
                    .findAny()
                    .orElse(new Professeur());

            positionElement = stubProfesseur.getListeProfesseur().indexOf(findProfesseur);
          

            do {
                System.out.println("ENTRER LE NOM :");
                nom = scanner.nextLine();

                if (ValiderSaisie.validerTexte(nom) ) {
                    findProfesseur.setNom(nom.trim().toUpperCase());
                } else {
                    System.out.println("\n!!!!!!!!!!LE NOM DOIT ETRE SAISI ET DOIT CONTENIR QUE DES LETTES VEILLER REESEYER..!!!!!!!!!!\n");
                }

            } while (!ValiderSaisie.validerTexte(nom) );

            do {
                System.out.println("ENTRER LE PRENOM :");
                prenom = scanner.nextLine();
                if (ValiderSaisie.validerTexte(prenom) ) {
                    findProfesseur.setPrenom(prenom.trim().toUpperCase());
                } else {
                    System.out.println("!!!!!!!!!!\nLE PRENOM DOIT ETRE SAISI ET DOIT CONTENIR QUE DES LETTES VEILLER REESEYER!!!!!!!!!!..\n");
                }
            } while (!ValiderSaisie.validerTexte(prenom) );

            System.out.println("ENTRER L'ADRESSE :");
            findProfesseur.setAdresse(scanner.nextLine().toUpperCase());

            do {
                System.out.println("ENTRER LE  EMAIL :");
                email = scanner.nextLine();
                if (ValiderSaisie.validerEmail(email) ) {
                    findProfesseur.setMail(email.trim().toLowerCase());
                } else {
                    System.out.println("LE FORMAT DE L'ADRESSE MAIL N'EST PAS BONNE REESSAYER");
                }
            } while (!ValiderSaisie.validerEmail(email) );

            do {
                System.out.println("ENTRER LE NNUMERO DE TELEPHONE :EX 760000000");
                telephone = scanner.nextLine();
                if (ValiderSaisie.validerNumeroTelephone(telephone) ) {
                    findProfesseur.setTelephone(telephone);
                } else {
                    System.out.println("\n!!!!!!!!!!LE NUMERO DE TELEPHONE DOIT CONTENIR QUE DES CHIFFRES EXEMPLE: INDICATIF DE  OPERATEUR: 77,76,78,33 ET 70 SUIVI DE 7 CHIFFRES (776880723)..!!!!!!!!!!\n");
                }
            } while (!ValiderSaisie.validerNumeroTelephone(telephone) );

            stubProfesseur.getListeProfesseur().add(positionElement, null);
            stubProfesseur.getListeProfesseur().add(positionElement, findProfesseur);
           
            System.out.println("#########MODIFICATION REUSSIE #########");

        } catch (Exception e) {
        	System.out.println(e);
        }
        return findProfesseur;
    }

    @Override
    public List<Professeur> getListeProfesseur() throws Exception {
    	 listeProfesseur = new ArrayList<>();
    	 listeProfesseur = stubProfesseur.getListeProfesseur();
        return listeProfesseur;
    }

    @Override
    public String genererMatriculeProfesseur(Professeur professeur) throws Exception {
        return stubProfesseur.genererMatriculeProfesseur(professeur);
    }

}
