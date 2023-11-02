package modele.environnement;

import modele.SimulateurPotager;

public class CaseNonCultivable extends Case {
    public CaseNonCultivable(SimulateurPotager _simulateurPotager) {
        super(_simulateurPotager);
    }

    @Override
    public void actionUtilisateur() {
        //pas d'action
    }

    @Override
    public void run() {
        //pas de mise à jour
    }
}
