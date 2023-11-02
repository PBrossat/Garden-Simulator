package modele.environnement.varietes;

public class Carrotte extends Legume {

    @Override //surcharge de la méthode getVariete() de la classe Legume
    public Varietes getVariete() {
        return Varietes.carrotte;
    }


    public int X = 400;
    public int Y = 400;

    public Carrotte()
    {
        super();
        this.setNomLegume("Carotte");
        // Pour qu'une carotte pousse bien il faut :
        // *Une température comprise entre 10 et 25°C
        // *Un taux d'humidité de l'air compris entre 50 et 70%
        // Si la température est basse (entre 0 et 10°C) => l'humidité de l'air doit être basse aussi (entre 40 et 50%)
        // Si la température est haute (entre 25 et 30°C)=> l'humidité de l'air doit être haute aussi (entre 60 et 80%).
        this.setTemperatureOptimale(10,25); // température optimale pour la salade
        this.setHumiditeAirOptimale(50,70); // humidité optimale pour la salade
        this.setTemperatureBasse(0,10); // température basse pour la salade
        this.setHumiditeAirBasse(40,50); // humidité basse pour la salade
        this.setTemperatureHaute(25,30); // température haute pour la salade
        this.setHumiditeAirHaute(60,80); // humidité haute pour la salade
    }

}
