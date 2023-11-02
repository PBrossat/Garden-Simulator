package modele;

import modele.Ordonnanceur;
import modele.SimulateurPotager;

public class SimulateurMeteo implements Runnable {
    private SimulateurPotager simPot;
    private int humiditeAir=50; //en %

    private int temperature=15; //en °C

    private int etatDuCiel=1; // 0 = nuageux, 1 = ensoleillé

    public SimulateurMeteo(SimulateurPotager _simPot) {
        Ordonnanceur.getOrdonnanceur().add(this);
        simPot = _simPot;
    }

    //-----------------GETTER ET SETTER-----------------
    public int getHumiditeAir() {
        return humiditeAir;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getEtatDuCiel() {
        return etatDuCiel; // 0 = nuageux, 1 = ensoleillé
    }
    public void setHumiditeAir(int _humideAir) {
        humiditeAir = _humideAir;
    }

    public void setTemperature(int _temperature) {
        temperature = _temperature;
    }

    public void setEtatDuCiel(int _etatDuCiel) {
        etatDuCiel = _etatDuCiel;
    }


    //fonction permettant d'update une fois sur 100 les valeurs de la météo
    public void nextStep() {

        //création de nombres aléatoires entre 0 et 100
        int nbAleatoire1 = (int) (Math.random() * 20);
        int nbAleatoire2 = (int) (Math.random() * 20);
        if (nbAleatoire1==nbAleatoire2) //une chance sur 100
        {
            int tolerenceTemperature = (int) (Math.random() * 3); //on génère un nombre aléatoire entre 0 et 3
            int tolerenceHumidite = (int) (Math.random() * 7); //on génère un nombre aléatoire entre 0 et 7
            //temperature = temperature + ou - tolerenceTemperature
            //humiditeAir = humiditeAir + ou - tolerenceHumidite
            int plusOuMoinsTemperature = (int) (Math.random() * 2); //on génère un nombre aléatoire entre 0 et 2
            int plusOuMoinsHumidite = (int) (Math.random() * 2); //on génère un nombre aléatoire entre 0 et 2
            switch (plusOuMoinsTemperature) {
                case 0:
                    temperature = temperature + tolerenceTemperature;
                    break;
                case 1:
                    temperature = temperature - tolerenceTemperature;
                    break;
            }
            switch (plusOuMoinsHumidite) {
                case 0:
                    humiditeAir = humiditeAir + tolerenceHumidite;
                    break;
                case 1:
                    humiditeAir = humiditeAir - tolerenceHumidite;
                    break;
            }

            //une chance sur 10 que l'état du ciel change
            int changementEtatDuCiel = (int) (Math.random() * 10);
            if (changementEtatDuCiel==0)
            {
                if (etatDuCiel==0)
                {
                    etatDuCiel=1;
                }
                else
                {
                    etatDuCiel=0;
                }
            }
        }
    }

    //fonction permettant de simuler la meteo selon des parametres choisi par l'utilisateur
    @Override
    public void run() {
        nextStep();
    }
}
