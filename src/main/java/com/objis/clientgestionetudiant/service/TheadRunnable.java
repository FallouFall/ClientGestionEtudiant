package com.objis.clientgestionetudiant.service;

import static com.objis.serveurgestionetudiant.presentation.AccueilServeur.URL;
import com.objis.serveurgestionetudiant.service.IEtudiantRemote;
import com.objis.serveurgestionetudiant.service.IFiliereRemote;
import com.objis.serveurgestionetudiant.service.IInscriptionRemote;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fallou
 */
public class TheadRunnable implements Runnable {

    private static List<?> refreshListe;
   static IEtudiantRemote stubEtudiant = null;
   static IFiliereRemote stubFiliere = null;
   static IInscriptionRemote stubInscription = null;
    private static String className = "";

    /**
     *
     * @param listeGenerique
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public    TheadRunnable(List<?> listeGenerique) throws NotBoundException, MalformedURLException, RemoteException {
        if (refreshListe == null) {
            refreshListe = new ArrayList<>();
        }
        if (listeGenerique == null) {
            listeGenerique = new ArrayList<>();
        }
        if (stubEtudiant == null) {
            stubEtudiant = (IEtudiantRemote) Naming.lookup(URL + "etudiant");
        }

        if (stubInscription == null) {
            stubInscription = (IInscriptionRemote) Naming.lookup(URL + "inscription");
        }
        if (stubFiliere == null) {
            stubFiliere = (IFiliereRemote) Naming.lookup(URL + "filiere");
        }

    }

    /**
     *
     */
    public TheadRunnable() {

    }

    /**
     *
     * @return
     */
    public static List<?> getRefreshListe() {
        return refreshListe;
    }

    /**
     *
     * @param className
     */
    public static void setClassName(String className) {
        TheadRunnable.className = className;
    }

    @Override
    public void run() {
        try {
            int repeat=0;
            while (repeat<50) {
                if (className.equals("etudiant")) {
                    TheadRunnable.refreshListe = stubEtudiant.getListeEtudiant();

                }
                if (className.equals("inscription")) {
                    TheadRunnable.refreshListe = stubInscription.getListeInscriptions();

                }
                if (className.equals("filiere")) {
                    TheadRunnable.refreshListe = stubFiliere.getListeFiliere();

                }

                getRefreshListe().stream().forEach(etudient -> {
                    System.out.println(etudient.toString());
                });
                Thread.sleep(6000);
                repeat++;
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        } 
    }


}
