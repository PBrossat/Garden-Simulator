package modele;

import java.util.Observable;
import java.util.Vector;

import static java.lang.Thread.*;

public class Ordonnanceur extends Observable implements Runnable {

    private static Ordonnanceur ordonnanceur;

    // design pattern singleton. Permet de pouvoir utiliser l'ordonnanceur partout dans le programme
    public static Ordonnanceur getOrdonnanceur() {
        if (ordonnanceur == null) {
            ordonnanceur = new Ordonnanceur();
        }
        return ordonnanceur;
    }

    private SimulateurPotager simulateurPotager;

    private float pause=600.0f;
    private Vector<Runnable> lst = new Vector<Runnable>(); // liste synchronisée

    public float getPause() {
        return pause;
    }

    public void setPause(float pause) {
        this.pause = pause;
    }

    public void start(float _pause) {
        pause = _pause;
        new Thread(this).start();
    }

    public void add(Runnable r) {lst.add(r);}

    @Override
    public void run() {
        boolean update = true;

        while(true) {

            for (Runnable r : lst) {
                r.run();
            }

            if (update) {
                setChanged();
                notifyObservers();
            }


            try {
                sleep((long) pause); //mise à jour toutes les "pause" temps.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
