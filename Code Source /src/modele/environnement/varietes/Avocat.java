package modele.environnement.varietes;

public class Avocat extends Legume {

    @Override
    public Varietes getVariete() {
        return Varietes.avocat;
    }
    public int X = 2350;
    public int Y = 0;
    public Avocat() {
        super();
        this.setNomLegume("Avocat");
        // Pour qu'un avocat pousse bien, il faut :
        // *Une température comprise entre 20 et 30°C
        // *Un taux d'humidité de l'air compris entre 50 et 70%
        // Si la température est basse (entre 15 et 20°C) => l'humidité de l'air doit être légèrement plus basse (entre 40 et 60%)
        // Si la température est haute (entre 30 et 35°C) => l'humidité de l'air doit être légèrement plus élevée (entre 60 et 80%)
        this.setTemperatureOptimale(20, 30); // température optimale pour l'avocat
        this.setHumiditeAirOptimale(50, 70); // humidité optimale pour l'avocat
        this.setTemperatureBasse(15, 20); // température basse pour l'avocat
        this.setHumiditeAirBasse(40, 60); // humidité légèrement basse pour l'avocat
        this.setTemperatureHaute(30, 35); // température haute pour l'avocat
        this.setHumiditeAirHaute(60, 80); // humidité légèrement élevée pour l'avocat
    }
}
