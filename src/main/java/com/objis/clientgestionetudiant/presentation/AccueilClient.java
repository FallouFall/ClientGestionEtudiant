package com.objis.clientgestionetudiant.presentation;

import com.objis.clientgestionetudiant.service.IEtudiant;
import com.objis.clientgestionetudiant.service.IEtudiantImpl;
import com.objis.clientgestionetudiant.service.IInscription;
import com.objis.clientgestionetudiant.service.IInscriptionImpl;
import com.objis.clientgestionetudiant.service.IProfesseur;
import com.objis.clientgestionetudiant.service.IProfesseurImpl;
import com.objis.clientgestionetudiant.service.ISecretaire;
import com.objis.clientgestionetudiant.service.ISecretaireImpl;
import com.objis.serveurgestionetudiant.domaine.Etudiant;
import com.objis.serveurgestionetudiant.domaine.Inscription;
import com.objis.serveurgestionetudiant.domaine.Professeur;
import com.objis.serveurgestionetudiant.domaine.Secretaire;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class AccueilClient {

	/**
	 *
	 * @param args
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.setProperty("java.security.policy", "file:grant");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		menuAccueil();
	}

	static void menuAccueil() throws Exception {
		char reponse = ' ';
		int choix;
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------");
			System.out.println(
					"**************BIENVENU DANS L'APPLICATION DE GESTION DE L'ETABLISSEMENT DU GROUPE SCOLAIRE LE '"
							+ "LE PHARE" + "'**************");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println(
					"******************************MENU PRINCIPAL************************************************************\n");
			System.out.println("\t\t1  GESTION DES ETUDIANTS");
			System.out.println("\t\t2  GESTION DU PERSONNEL ");
			System.out.println("\t\t3  GESTION DES INSCRIPTIONS");
			System.out.println("\t\t4  QUITTER");
			System.out.println();
			System.out.println(
					"\n******************************MENU PRINCIPAL************************************************************\n");
			choix = scanner.nextInt();
			switch (choix) {
			case 1:
				menuGestionEtudiant();

				break;

			case 2:
				menuGestionDuPersonnel();

				break;

			case 3:
				menugestionDesInscription();

				break;

			case 4:
				System.out.println("@@@@@@@@@@@@@@     AU REVOIR :)     @@@@@@@@@@@@@@");
				System.exit(0);

				break;
			default:
				System.out.println(choix());

				break;

			}
			scanner.nextLine();
			try {
				reponse = scanner.nextLine().charAt(0);
			} catch (Exception e) {
				menuAccueil();
			}
		} while (reponse == 'o' || reponse == 'O');
		scanner.close();
	}

	static void menuGestionEtudiant() throws Exception {
      
            char reponse = ' ';
            int choix;
            Etudiant etudiant;
            Scanner scanner = new Scanner(System.in);
            IEtudiant serviceEtudiant = new IEtudiantImpl();
        	try {
        	do {
            
                System.out.println("\n\n\n\n******************************GESTION DES ETUDIANTS************************************************************\n");
                System.out.println("\t\t1   AJOUTER UN ETUDIANT");
                System.out.println("\t\t2   MODIFIER UN ETUDIANT");
                System.out.println("\t\t3   LISTER LES ETUDIANTS");
                System.out.println("\t\t4   RETOUR AU MENU PRINCIPAL ");
                System.out.println(choix());
                System.out.println("\n******************************GESTION DES ETUDIANTS************************************************************\n");
                choix = scanner.nextInt();

                switch (choix) {

                    case 1: 
                        do {
                            etudiant = serviceEtudiant.saisirEtudiant();
                            System.out.println(etudiant.toString());
                            scanner.nextLine();
                            System.out.println(succes());
                          
                                reponse = scanner.nextLine().charAt(0);
                         

                            if (reponse != 'o' || reponse != 'O') {
                                menuGestionEtudiant();
                            }
                        } while (reponse == 'o' || reponse == 'O');
                    
                    break;
                    case 2: 
                        scanner.nextLine();
                        System.out.println("ENTRER LE MATRICULE DE L'ETUDIANT A MODIFIER");
                        String matricule = scanner.nextLine();
                        etudiant = serviceEtudiant.modifierEtudiant(matricule);
                        if (etudiant == null) {
                            System.out.println("AUCUN ETUDIANT TROUVE ");
                            menuGestionEtudiant();
                        } else {
                            System.out.println(etudiant.toString());
                            serviceEtudiant.addEtudiant(etudiant);
                            scanner.nextLine();
                            menuGestionEtudiant();
                        }

                    
                    break;

                    case 3: 
                        System.out.println("\t\t###################LISTE DES ETUDIANTS###################\t\t");

                     serviceEtudiant.getListeEtudiant().stream().forEach(etu -> 
						System.out.println(etu.toString()));
                   
                    

                    
                    break;

                    case 4: 
                        menuAccueil();
                    
                    break;
                    default: 
                        System.out.println(choix());
                    
                    break;
                }
                
                	System.out.println(continuer());
        			scanner.nextLine();

        			reponse = scanner.nextLine().charAt(0);

        		} while (reponse == 'o' || reponse == 'O');
        		scanner.close();
        	} catch (Exception ex) {
        		System.out.println(ex);
                        menuAccueil();

        	}

    }

	static void menuGestionDuPersonnel() throws Exception {
		char reponse = ' ';
		int choix;
		Scanner scanner = new Scanner(System.in);
		try {
		do {

			System.out.println(
					"\n\n\n\n************************************************************GESTION DU PERSONNEL  ************************************************************\n");
			System.out.println("\t\t1   GESTION DES PROFESSEURS");
			System.out.println("\t\t2   GESTIONS DES SECRETAIRES");
			System.out.println("\t\t3  RETOUR AU MENU PRINCIPAL ");
			System.out.println(choix());
			System.out.println(
					"\n************************************************************GESTION DU PERSONNEL  ************************************************************\n");
			choix = scanner.nextInt();

			switch (choix) {
			case 1:
				menuProfesseur();

				break;

			case 2:
				menuSecretaire();

				break;

			case 3:
				menuAccueil();

				break;
			default:
				System.out.println(choix());

				break;
			}
			System.out.println(continuer());
			scanner.nextLine();

			reponse = scanner.nextLine().charAt(0);

		} while (reponse == 'o' || reponse == 'O');
		scanner.close();
	} catch (Exception ex) {
		System.out.println(ex);
                 menuAccueil();

	}
	}
	// sous menu pour gerer les inscriptions

	static void menugestionDesInscription() throws Exception {
		try {
			IInscription serviIInscription = new IInscriptionImpl();
			char reponse = ' ';
			Inscription inscription;
			int choix;
			Scanner scanner = new Scanner(System.in);
			do {
				System.out.println(
						"\n\n\n\n************************************************************GESTION DES INSCRIPTIONS************************************************************");
				System.out.println("\t\t1   INSCRIRE UN ETUDIANT");
				System.out.println("\t\t2   LISTE DES  INSCRIPTIONS");
				System.out.println("\t\t3   RETOUR AU MENU PRINCIPAL ");
				System.out.println(choix());
				System.out.println(
						"\n************************************************************GESTION DES INSCRIPTIONS************************************************************");

				choix = scanner.nextInt();

				switch (choix) {
				case 1:
					try {
						inscription = serviIInscription.saisirInscription();
						System.out.println(inscription.toString());
					} catch (Exception exe) {
						System.out.println(exe.getLocalizedMessage());
					}

					break;
				case 2:
                                
					serviIInscription.getListeInscriptions().stream().forEach(
						System.out::println);
                                
					break;
				case 3:
					menuAccueil();

					break;
				default:
					System.out.println(choix());

					break;
				}
				System.out.println(continuer());
				scanner.nextLine();

				reponse = scanner.nextLine().charAt(0);

			} while (reponse == 'o' || reponse == 'O');
			scanner.close();
		} catch (Exception ex) {
			System.out.println(ex);
                         menuAccueil();

		}
	}

	static String choix()
	{
		return "\n\t\tMERCI DE  FAIRE UN BON CHOIX SVP";
	}
	static String succes()
	{
		return "\n\t\tAJOUTER EFFECTUER AVEC SUCCES APPUYER SUR 'O' OU LA TOUCHE ENTRE POUR CONTINUER";
	}
	static String continuer()
	{
		return "\"VOULEZ-VOUS CONTINUER ? APPUYEZ SUR 'O' POUR CONFIRMER  OU LA TOUCHE ENTREE";
	}
	
	static void menuProfesseur() throws Exception {
		try {
			char reponse = ' ';
			int choix;
			Professeur professeur;
			Scanner scanner = new Scanner(System.in);
			IProfesseur servIProfesseur = new IProfesseurImpl();

			do {

				System.out.println("\n\n\n\n\t\t***********GESTION DES PROFESSEUR***********\n");
				System.out.println("\t\t1   AJOUTER UN PROFESSEUR");
				System.out.println("\t\t2   MODIFIER UN PROFESSEUR");
				System.out.println("\t\t3   LISTER LES PROFESSEURS");
				System.out.println("\t\t4   RETOUR  ");
				System.out.println(choix());

				choix = scanner.nextInt();

				switch (choix) {

				case 1:
					do {
						professeur = servIProfesseur.saisirProfesseur();
						System.out.println(professeur.toString());
						scanner.nextLine();
						System.out.println(
								succes());

						reponse = scanner.nextLine().charAt(0);

					} while (reponse == 'o' || reponse == 'O' || reponse == 0);
					 if (reponse != 'o' || reponse != 'O') {
                         menuProfesseur();
                     }
					break;

				case 2:
					scanner.nextLine();
					System.out.println("ENTRER LE MATRICULE DE DU PROFESSEUR A MODIFIER");
					String matricule = scanner.nextLine();
					professeur = servIProfesseur.modifierProfesseur(matricule);
					if (professeur == null) {
						System.out.println("AUCUN PROFESSEUR TROUVE ");
						menuGestionEtudiant();
					} else {
						System.out.println(professeur.toString());
						 servIProfesseur.getListeProfesseur().add(professeur);
						scanner.nextLine();
						menuProfesseur();
					}

					break;

				case 3:
					System.out.println("\t\t###################LISTE DES PROFESSEURS###################\t\t");

					servIProfesseur.getListeProfesseur().stream().forEach(
						System.out::println);

					

					break;

				case 4:
					menuAccueil();

					break;
				default:
					System.out.println(choix());

					break;
				}
				System.out.println(
						succes());

				scanner.nextLine();
				reponse = scanner.nextLine().charAt(0);

			} while (reponse == 'o' || reponse == 'O');
			scanner.close();
		} catch (Exception ex) {
			System.out.println(ex);
                         menuAccueil();

		}

	}

	static void menuSecretaire() throws Exception {

		int choix;
		char reponse = ' ';
		Scanner scanner = new Scanner(System.in);
		Secretaire secretaire;
		ISecretaire secretaireService = new ISecretaireImpl();
		try {
			do {

				System.out.println("\n\n\n\n\t\t***********GESTION DES SECRETAIRE***********\n");
				System.out.println("\t\t1   AJOUTER UN SECRETAIRE");
				System.out.println("\t\t2   MODIFIER UN SECRETAIRE");
				System.out.println("\t\t3   LISTER LES SECRETAIRE");
				System.out.println("\t\t4   RETOUR  ");
				System.out.println(choix());

				choix = scanner.nextInt();

				switch (choix) {

				case 1:
					do {
						secretaire = secretaireService.saisirSecretaire();
						System.out.println(secretaire.toString());
						scanner.nextLine();
						System.out.println(
								succes());

						reponse = scanner.nextLine().charAt(0);

					} while (reponse == 'o' || reponse == 'O');

					break;

				case 2:
					scanner.nextLine();
					System.out.println("ENTRER LE MATRICULE DE DU PROFESSEUR A MODIFIER");
					String matricule = scanner.nextLine();
					secretaire = secretaireService.modifierSecretaire(matricule);
					if (secretaire == null) {
						System.out.println("AUCUN SECRETAIRE TROUVE ");
						menuSecretaire();
					} else {
						System.out.println(secretaire.toString());
						scanner.nextLine();
						menuSecretaire();
					}

					break;

				case 3:
					System.out.println("\t\t###################LISTE DES SECRETAIRES###################\t\t");

					secretaireService.getListeSecretaire().stream().forEach(
						System.out::println);


					break;

				case 4:
					menuAccueil();

					break;
				default:
					System.out.println(choix());

					break;
				}
				System.out.println(
						succes());

				scanner.nextLine();
				reponse = scanner.nextLine().charAt(0);
				scanner.close();

			} while (reponse == 'o' || reponse == 'O');
		} catch (Exception ex) {
			System.out.println(ex);
                         menuAccueil();
		}

	}

}
