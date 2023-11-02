package modele.environnement.varietes;

import modele.SimulateurPotager;

public abstract class Legume implements Runnable{
    public abstract Varietes getVariete();
    private SimulateurPotager simulateurPotager;

    private String nomLegume;

    private int[] temperatureOptimale = new int[2];
    private int[] humiditeAirOptimale = new int[2];

    private int[] temperatureBasse= new int[2];
    private int[] humiditeAirBasse= new int[2];

    private int[] temperatureHaute= new int[2];
    private int[] humiditeAirHaute= new int[2];

    private float[] etatCroissanceCultivable= new float[2];
    //période de croissance du légume où l'on peut le récolter (1 étant le meilleur moment)

    private float croissance=0.1f; //croissance du légume 0 (croissance nulle) et 1 (croissance maximale)
    private float tauxCroissance;

    public void setNomLegume(String _nomLegume) {
        this.nomLegume = _nomLegume;
    }
    public String getNomLegume() {
        return nomLegume;
    }

    public float getCroissance() {
        return croissance;
    }

    public void setCroissance(float croissance) {
        this.croissance = croissance;
    }

    public void setTemperatureOptimale(int min, int max) {
        temperatureOptimale[0] = min;
        temperatureOptimale[1] = max;
    }

    public void setHumiditeAirOptimale(int min, int max) {
        humiditeAirOptimale[0] = min;
        humiditeAirOptimale[1] = max;
    }

    public void setTemperatureBasse(int min, int max) {
        temperatureBasse[0] = min;
        temperatureBasse[1] = max;
    }

    public void setHumiditeAirBasse(int min, int max) {
        humiditeAirBasse[0] = min;
        humiditeAirBasse[1] = max;
    }

    public void setTemperatureHaute(int min, int max) {
        temperatureHaute[0] = min;
        temperatureHaute[1] = max;
    }

    public void setHumiditeAirHaute(int min, int max) {
        humiditeAirHaute[0] = min;
        humiditeAirHaute[1] = max;
    }

    public void setEtatCroissanceCultivable(float min, float max) {
        etatCroissanceCultivable[0] = min;
        etatCroissanceCultivable[1] = max;
    }



    public  int[] getTemperatureOptimale() {
        return temperatureOptimale;
    }

    public int[] getHumiditeAirOptimale() {
        return humiditeAirOptimale;
    }

    public int[] getTemperatureBasse() {
        return temperatureBasse;
    }

    public int[] getHumiditeAirBasse() {
        return humiditeAirBasse;
    }

    public int[] getTemperatureHaute() {
        return temperatureHaute;
    }

    public int[] getHumiditeAirHaute() {
        return humiditeAirHaute;
    }





    public void tauxCroissanceLegume(int temperature, int humiditeAir, int etatDuCiel) {


        //Si la température est comprise entre 15 et 25°C
        if (temperature >= getTemperatureOptimale()[0] && temperature <= getTemperatureOptimale()[1]) //température optimale
        {
            //Si le ciel est ensoleillé
            if (etatDuCiel==1) {
                //Si le taux d'humidité de l'air est compris entre 50 et 60%
                if (humiditeAir >= getHumiditeAirOptimale()[0] && humiditeAir <= getHumiditeAirOptimale()[1]) {
                    this.tauxCroissance = 1; //croissance optimale
                }else{
                    this.tauxCroissance = 0.7f; //bonne croissance
                }

            } else {
                if (humiditeAir >= getHumiditeAirOptimale()[0] && humiditeAir <= getHumiditeAirOptimale()[1]) {
                    this.tauxCroissance = 0.5f; //bonne moyenne
                }else{
                    this.tauxCroissance = 0.3f; //croissance faible
                }
            }
        }
        else if (temperature >=getTemperatureBasse()[0] && temperature<getTemperatureBasse()[1]) //température fraiche
        {
            if (etatDuCiel==1){
                if (humiditeAir >= getHumiditeAirBasse()[0] && humiditeAir <= getHumiditeAirBasse()[1]) {
                    this.tauxCroissance = 0.7f; //bonne croissance
                }
                else {
                   this. tauxCroissance = 0.20f; //croissance faible
                }
            }
            else{
                this.tauxCroissance=0.15f; //croissance très faible
            }
        }
        else if (temperature >=getTemperatureHaute()[0] && temperature<getTemperatureHaute()[1])
        {
            if (etatDuCiel==1){
                if (humiditeAir >= getHumiditeAirHaute()[0] && humiditeAir <= getHumiditeAirHaute()[1]) {
                    this.tauxCroissance = 0.7f; //croissance modérée
                }
                else{
                    this.tauxCroissance = 0.2f; //croissance faible
                }
            }
            else{
                this.tauxCroissance=0.1f; //croissance très faible
            }
        }
        else if (temperature<getTemperatureBasse()[0] || temperature>getTemperatureHaute()[1])
        //temperature trop basse ou trop haute pour la croissance du légume
        {
            this.tauxCroissance=0; //croissance nulle
        }
    }

    //procédure permettant de faire croître le légume en fonction de la variété
    public void nextStep() {
        this.croissance+=this.croissance*this.tauxCroissance/10;
    }

    @Override
    public void run(){
        nextStep();
    }
}
