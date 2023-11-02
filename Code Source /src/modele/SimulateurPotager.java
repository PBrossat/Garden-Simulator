/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;


import modele.environnement.Case;
import modele.environnement.CaseCultivable;
import modele.environnement.CaseNonCultivable;
import modele.environnement.varietes.Carrotte;
import modele.environnement.varietes.Salade;

import java.awt.Point;
import java.util.Random;


public class SimulateurPotager {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    public int idPlanter;
    public int idRecolter;
    public int idDefricher;
    private float score = 1;
    private boolean boolScore;
    private int nbLegumesRecoltes = 0;
    private CaseCultivable caseCultivable;

    private SimulateurMeteo simMet;

    private Case[][] grilleCases = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    public SimulateurPotager() {

        initialisationDesEntites();

        simMet = new SimulateurMeteo(this);

    }

    public float getArgent() {
        return score;
    }
    public void setArgent(float score) {
        this.score = score;
    }

    public boolean getBoolArgent() {
        return boolScore;
    }
    public void setBoolArgent(boolean boolScore) {
        this.boolScore = boolScore;
    }

    public int getNbLegumesRecoltes() {
        return nbLegumesRecoltes;
    }
    public void setNbLegumesRecoltes(int nbLegumesRecoltes) {
        this.nbLegumesRecoltes = nbLegumesRecoltes;
    }

    public void setCaseCultivable(CaseCultivable caseCultivable) {
        this.caseCultivable = caseCultivable;
    }
    public CaseCultivable getCaseCultivable() {
        return caseCultivable;
    }

    public SimulateurMeteo getSimMet() {
        return simMet;
    }
    
    public Case[][] getPlateau() {
        return grilleCases;
    }
    
    private void initialisationDesEntites() {

        // murs extérieurs horizontaux
        for (int x = 0; x < 20; x++) {
            addEntite(new CaseNonCultivable(this), x, 0);
            addEntite(new CaseNonCultivable(this), x, 9);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < 9; y++) {
            addEntite(new CaseNonCultivable(this), 0, y);
            addEntite(new CaseNonCultivable(this), 19, y);
        }

        addEntite(new CaseNonCultivable(this), 2, 6);
        addEntite(new CaseNonCultivable(this), 3, 6);

        Random rnd = new Random();

        for (int x = 2; x < 18; x++) {
            for (int y = 2; y < 8; y++) {
                CaseCultivable cc = new CaseCultivable(this);
                addEntite(cc, x, y);
                if (rnd.nextBoolean()) {
                    if(rnd.nextBoolean())
                        cc.setLegume(new Salade());
                    else
                        cc.setLegume(new Carrotte());
                }

                Ordonnanceur.getOrdonnanceur().add(cc);

            }
        }

    }

    public void actionUtilisateur(int x, int y) {
        if (grilleCases[x][y] != null) {
            grilleCases[x][y].actionUtilisateur(); //cette méthode se trouve dans la classe CaseCulitvable
        }
    }

    private void addEntite(Case e, int x, int y) {
        grilleCases[x][y] = e;
    }

}
