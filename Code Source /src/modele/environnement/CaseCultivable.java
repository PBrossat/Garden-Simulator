package modele.environnement;

import modele.SimulateurPotager;
import modele.environnement.varietes.Carrotte;
import modele.environnement.varietes.Legume;
import modele.environnement.varietes.Salade;
import modele.environnement.varietes.Radis;
import modele.environnement.varietes.Avocat;

//Une CaseCultivable est une case qui peut contenir un légume. Elle hérite de la classe Case.
public class CaseCultivable extends Case {

    private Legume legume;
    private String couleurCase="rouge"; //couleur de la case selon la croissance de son légume

    public String getCouleurCase() {
        return couleurCase;
    }

    public CaseCultivable(SimulateurPotager _simulateurPotager) {
        super(_simulateurPotager);
    }

    @Override //surcharge de la méthode actionUtilisateur() de la classe Case
    public void actionUtilisateur() {

        simulateurPotager.setBoolArgent(false);

        //si la case ne contient pas de légume
        // PLANTATION
        if(legume == null) {

            float argent = simulateurPotager.getArgent();
            switch (simulateurPotager.idPlanter) {
                // cas 0 -> planter une carotte et enlever 0.5 point au score
                case 0 -> {
                    if (argent >= 0.5) {
                        legume = new Carrotte();
                        simulateurPotager.setArgent(argent - 0.5f);
                    }
                    // sinon on affiche un message d'erreur
                    else {
                        simulateurPotager.setBoolArgent(true);
                    }
                }
                // cas 1 -> planter une salade et enlever 1 point au score
                case 1 -> {
                    if (argent >= 1) {
                        legume = new Salade();
                        simulateurPotager.setArgent(argent - 1);
                    }
                    // sinon on affiche un message d'erreur
                    else {
                        simulateurPotager.setBoolArgent(true);
                    }

                }
                // cas 2 -> planter un radis et enlever 1.5 point au score
                case 2 -> {
                    if (argent >= 1.5) {
                        legume = new Radis();
                        simulateurPotager.setArgent(argent - 1.5f);
                    }
                    // sinon on affiche un message d'erreur
                    else {
                        simulateurPotager.setBoolArgent(true);
                    }
                }
                // cas 3 -> planter un avocat et enlever 2 point au score
                case 3 -> {
                    if (argent >= 2) {
                        legume = new Avocat();
                        simulateurPotager.setArgent(argent - 2);
                    }
                    // sinon on affiche un message d'erreur
                    else {
                        simulateurPotager.setBoolArgent(true);
                    }
                }
            }
        }
        //si la case contient un légume
        // RÉCOLTE
        else {
            if (simulateurPotager.idRecolter == 0) {

                int nbLegume = simulateurPotager.getNbLegumesRecoltes();
                float score = simulateurPotager.getArgent();
                float etat = legume.getCroissance();

                //si entre 0.7 et 1.3 alors on regarde si le légume est mûr ou parfaitement mûr
                if(etat > 0.7 && etat < 1.3) {

                    simulateurPotager.setNbLegumesRecoltes(nbLegume + 1);

                    //si entre 0.7 et 0.85 ou entre 1.15 et 1.3 alors moitié du score
                    if(etat > 0.7 && etat < 0.85 || etat > 1.15 && etat < 1.3) {
                        switch (legume.getVariete()) {
                            case carrotte -> simulateurPotager.setArgent(score + 0.5f);
                            case salade -> simulateurPotager.setArgent(score + 1.0f);
                            case radis -> simulateurPotager.setArgent(score + 1.5f);
                            case avocat -> simulateurPotager.setArgent(score + 2.0f);
                        }
                    }
                    //si entre 0.85 et 1.15 alors score entier
                    else if(etat > 0.85 && etat < 1.15) {

                        switch (legume.getVariete()) {
                            case carrotte -> simulateurPotager.setArgent(score + 1.0f);
                            case salade -> simulateurPotager.setArgent(score + 2.0f);
                            case radis -> simulateurPotager.setArgent(score + 3.0f);
                            case avocat -> simulateurPotager.setArgent(score + 4.0f);
                        }
                    }
                }
                legume = null;
            } else {
                legume.nextStep();
            }
        }
    }



    public void couleurCaseCultivable(){
        //on parcourt la grille
        for (int x = 1; x <19 ; x++) {
            for (int y = 1; y < 9; y++) {
                if (legume != null) {
                    double croissance = legume.getCroissance();
                    if (croissance > 1.3) {
                        this.couleurCase = "marron"; //légume trop mûr
                    } else if (croissance >= 0.85) {
                        this.couleurCase = "vert"; //légume parfaitement mûr
                    } else if (croissance >= 0.7) {
                        this.couleurCase = "orange"; //légume mûr
                    } else {
                        this.couleurCase = "rouge"; //légume pas encore mûr
                    }
                }
            }
        }
    }

    public Legume getLegume() {
        return legume;
    }

    public void setLegume(Legume legume) {
        this.legume = legume;
    }


    @Override
    public void run() {
        if (legume != null) {
            legume.nextStep();
        }
        couleurCaseCultivable();
    }
}
