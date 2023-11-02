package modele.environnement.varietes;

public class Salade extends Legume {
    @Override //surcharge de la méthode getVariete() de la classe Legume
    public Varietes getVariete() {
        return Varietes.salade;
    }

    public int X = 0;
    public int Y = 0;

    //surcharge de la méthode croissance() de la classe Legume
    protected void croissance() {
        // TODO
        // System.out.println("Une salade pousse !!");
    }

    public Salade() {
        super();
        this.setNomLegume("Salade");
        // Pour qu'une salade pousse bien il faut :
        // *Une température comprise entre 15 et 25°C
        // *Un taux d'humidité de l'air compris entre 50 et 60%
        // Si la temperature est basse (entre 5 et 15°) => l'humidité de l'air doit être basse aussi (entre 20 et 50%)
        // Si la temperature est haute (entre 25 et 30°)=> l'humidité de l'air doit être haute aussi (entre 60 et 70%)
        this.setTemperatureOptimale(15,25); // température optimale pour la salade
        this.setHumiditeAirOptimale(50,60); // humidité optimale pour la salade
        this.setTemperatureBasse(5,15); // température basse pour la salade
        this.setHumiditeAirBasse(20,50); // humidité basse pour la salade
        this.setTemperatureHaute(25,30); // température haute pour la salade
        this.setHumiditeAirHaute(60,70); // humidité haute pour la salade
    }

}
