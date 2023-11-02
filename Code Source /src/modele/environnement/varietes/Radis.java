package modele.environnement.varietes;

public class Radis extends Legume{

    @Override //surcharge de la méthode getVariete() de la classe Legume
    public Varietes getVariete() {
        return Varietes.radis;
    }
    public int X = 800;
    public int Y = 800;
    public Radis() {
        super();
        this.setNomLegume("Radis");
        // Pour qu'un radis pousse bien, il faut :
        // *Une température comprise entre 10 et 20°C
        // *Un taux d'humidité de l'air compris entre 40 et 60%
        // Si la température est basse (entre 5 et 10°C) => l'humidité de l'air doit être basse aussi (entre 30 et 50%)
        // Si la température est haute (entre 20 et 25°C) => l'humidité de l'air doit être légèrement plus basse (entre 40 et 55%)
        this.setTemperatureOptimale(10, 20); // température optimale pour le radis
        this.setHumiditeAirOptimale(40, 60); // humidité optimale pour le radis
        this.setTemperatureBasse(5, 10); // température basse pour le radis
        this.setHumiditeAirBasse(30, 50); // humidité basse pour le radis
        this.setTemperatureHaute(20, 25); // température haute pour le radis
        this.setHumiditeAirHaute(40, 55); // humidité légèrement basse pour le radis
    }
}
