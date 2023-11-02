package VueControleur;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import modele.Ordonnanceur;
import modele.SimulateurPotager;
import modele.environnement.*;
import modele.environnement.varietes.*;


public class VueControleurPotager extends JFrame implements Observer {
    private SimulateurPotager simulateurPotager; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)
    private int sizeX; // taille de la grille affichée
    private int sizeY;

    private int tailleImage = 150;
    private int CaseXmax = 19; // taille du tableau effectif de cases cultivables
    private int CaseXmin = 1;
    private int CaseYmax = 9;
    private int CaseYmin = 1;

    // icones affichées dans la grille
    private ImageIcon icoSalade;
    private ImageIcon icoCarrotte;
    private ImageIcon icoRadis;
    private ImageIcon icoAvocat;
    private ImageIcon icoTerre;
    private ImageIcon icoSec;
    private ImageIcon icoMur;
    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)
    private JPanel infos;
    private JPanel containerInfos_Reglage;
    private JPanel reglages;
    private JPanel informationLegumeRecoltes;
    private JPanel informationPasDeTemps;
    private  ImageIcon panierLegumes= resizeImage("Images/legumes.png",25);
    private JPanel informationScore;
    private ImageIcon Null= resizeImage("Images/euro0.png",25);
    private ImageIcon Pauvre = resizeImage("Images/euro1.png",25);
    private ImageIcon Moyen = resizeImage("Images/euro2.png",25);
    private ImageIcon Riche = resizeImage("Images/euro3.png",25);
    private JPanel informationMeteo;
    private int tailleIcone=30;

    private ImageIcon humiditeFaible= resizeImage("Images/humiditeFaible.png",tailleIcone);
    private ImageIcon humiditeNormale= resizeImage("Images/humiditeNormale.png",tailleIcone);
    private ImageIcon humiditeForte= resizeImage("Images/humiditeForte.png",tailleIcone);
    private ImageIcon nuage= resizeImage("Images/nuage.png",tailleIcone);
    private ImageIcon soleil= resizeImage("Images/soleil.png",tailleIcone);
    private ImageIcon temperatureFaible= resizeImage("Images/temperatureFaible.png",tailleIcone);
    private ImageIcon temperatureNormale= resizeImage("Images/temperatureNormale.png",tailleIcone);
    private ImageIcon temperatureForte= resizeImage("Images/temperatureForte.png",tailleIcone);


    private ImageIcon resizeImage(String path, int tailleImage)
    {
        ImageIcon image = new ImageIcon(path);
        Image imageRedimensionnee = image.getImage().getScaledInstance(tailleImage, tailleImage, Image.SCALE_SMOOTH);
        ImageIcon imageIconeRedimensionnee = new ImageIcon(imageRedimensionnee);
        return imageIconeRedimensionnee;
    }



    public VueControleurPotager(SimulateurPotager _simulateurPotager) {


        sizeX = simulateurPotager.SIZE_X;
        sizeY = _simulateurPotager.SIZE_Y;
        simulateurPotager = _simulateurPotager;


        chargerLesIcones();
        placerLesComposantsGraphiques();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                initialisationTauxCroissance(x,y); //initialise le taux de croissance de chaque légume
            }
        }

        //création d'un menu pour choisir la météo et lancer la simulation
        //MenuPotager(_simulateurPotager);
    }


    //création d'un menu pour choisir la météo et lancer la simulation (non utilisé pour le moment)
    public void MenuPotager(SimulateurPotager _simulateurPotager) {
        //création d'une fenetre pour le menu
        JFrame fenetreMenu = new JFrame();
        fenetreMenu.setTitle("Happy Potager !!");

        JPanel menu= new JPanel();
        fenetreMenu.add(menu);



        //background du menu (image)
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Images/image_potager.jpeg"));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurPotager.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image dimg = image.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        JLabel fond = new JLabel(imageIcon);
        menu.add(fond);


        //création d'un bouton de réglage pour la météo
        JButton boutonMeteo = new JButton("Veuillez choisir la météo");
        //taille du bouton
        boutonMeteo.setPreferredSize(new Dimension(200, 50));
        //ajout du bouton au panel
        menu.add(boutonMeteo);
        //position du bouton
        boutonMeteo.setBounds(50, 50, 100, 50);
        //ajout d'un écouteur d'évènement
        boutonMeteo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonMeteoActionPerformed(evt);
            }

            private void boutonMeteoActionPerformed(java.awt.event.ActionEvent evt) {
                creationFenetreMeteoSettings();
            }
        });

        //création d'un bouton "Création du potager"
        JButton boutonCreationPotager = new JButton("Création du potager");
        //taille du bouton
        boutonCreationPotager.setPreferredSize(new Dimension(150, 50));
        //ajout du bouton au panel
        menu.add(boutonCreationPotager);

        //action lorsqu'on clique sur le bouton Création du potager
        boutonCreationPotager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonCreationPotagerActionPerformed(evt);
            }

            private void boutonCreationPotagerActionPerformed(java.awt.event.ActionEvent evt) {


                sizeX = simulateurPotager.SIZE_X;
                sizeY = _simulateurPotager.SIZE_Y;
                simulateurPotager = _simulateurPotager;


                chargerLesIcones();
                placerLesComposantsGraphiques();
                for (int x = 0; x < sizeX; x++) {
                    for (int y = 0; y < sizeY; y++) {
                        initialisationTauxCroissance(x,y); //initialise le taux de croissance de chaque légume
                    }
                }
                //fermeture de la fenetre menu
                fenetreMenu.dispose();
            }
        });



        //taille de la fenetre
        fenetreMenu.setSize(1000, 1000);
        //rendre la fenetre visible
        fenetreMenu.setVisible(true);
    }


    public static void creationFenetreChoixPasDeTemps(){

        JFrame fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetre.setTitle("Choix du temps de simulation");
        fenetre.setSize(300, 150);
        fenetre.setLocationRelativeTo(null); // centre la fenêtre
        fenetre.setVisible(true);
        fenetre.setResizable(false); // empêche la fenêtre d'etre redimensionnée

        //création d'un panel pour contenir le spinner et le bouton
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fenetre.add(panel, BorderLayout.CENTER);

        //création d'un compteur pour le temps de simulation
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(1, 0.1, 5, 0.1));
        spinner.setPreferredSize(new Dimension(80, 30));
        panel.add(spinner);

        //création d'un bouton pour valider le temps de simulation
        JButton bouton = creationBouton("Valider", 80, 30);
        bouton.setPreferredSize(new Dimension(80, 30));
        panel.add(bouton);

        //action quand on clique sur le bouton valider
        bouton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonActionPerformed(evt);
            }

            private void boutonActionPerformed(java.awt.event.ActionEvent evt) {
                //récupération de la valeur du spinner
                float temps = ((Double) spinner.getValue()).floatValue();
                //conversion en millisecondes
                float tempsLong = (float) temps * 1000;
                //modification du temps de simulation
                Ordonnanceur.getOrdonnanceur().setPause(tempsLong);
                //fermeture de la fenetre
                fenetre.dispose();
            }
        });
    }



    public void creationFenetreMeteoSettings(){
        JFrame fenetreMeteo = new JFrame();
        //place les éléments les uns en dessous des autres
        fenetreMeteo.setTitle("Réglage Météo");
        fenetreMeteo.setSize(300, 300);
        fenetreMeteo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetreMeteo.setVisible(true); //affiche la fenêtre
        fenetreMeteo.setLocationRelativeTo(null); //centre la fenêtre
        fenetreMeteo.setLayout(new BorderLayout()); // permet de placer les composants graphiques dans la fenêtre
        fenetreMeteo.setResizable(false); // empêche la fenêtre d'etre redimensionnée

        //slider permettant de faire choisir à l'utilisateur un pourcentage correspondant au taux d'humidité de l'air
        JSlider sliderHumidite = creationSlider("Pourcentage d'Humidité", 0, 100, 50);
        //slider permettant de faire choisir à l'utilisateur le temperature ambiante en degré C°
        JSlider sliderTemperature = creationSlider("Temperature en degré C°", -5, 40, 15);
        //création des boutons radio avec la procédure "creationBoutonRadio"
        JRadioButton radioBoutonNuageux = creationBoutonRadio("Nuageux",0);
        JRadioButton radioBoutonEnsoleille = creationBoutonRadio("Ensoleillé",1);


        //création d'un groupe pour les boutons radio
        ButtonGroup groupeBouton = new ButtonGroup();
        groupeBouton.add(radioBoutonNuageux);
        groupeBouton.add(radioBoutonEnsoleille);

        //Bouton "valider"
        JButton valider = creationBouton("Valider", 50, 50);

        //Récupérer dans une variable le pourcentage choisi par l'utilisateur
        fenetreMeteo.add(sliderHumidite);
        fenetreMeteo.add (sliderTemperature);

        //Création d'un label pour les boutons radio
        JLabel labelMeteo = new JLabel("état du ciel :");

        fenetreMeteo.add(labelMeteo);
        fenetreMeteo.add(radioBoutonNuageux);
        fenetreMeteo.add(radioBoutonEnsoleille);
        fenetreMeteo.add(valider);
        //place les éléments les uns en dessous des autres
        fenetreMeteo.setLayout(new BoxLayout(fenetreMeteo.getContentPane(), BoxLayout.Y_AXIS));


        //Lorsque l'utilisateur clique sur le bouton "valider" => appel des fonctions permettants
        //d'envoyer les valeurs des slider au modèle
        valider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                envoieValeurSliderAuModele(sliderHumidite, 0);
                envoieValeurSliderAuModele(sliderTemperature, 1);
                if (radioBoutonNuageux.isSelected()) {
                    simulateurPotager.getSimMet().setEtatDuCiel(0);
                }
                else if (radioBoutonEnsoleille.isSelected()){
                    simulateurPotager.getSimMet().setEtatDuCiel(1);
                }
                else {
                    //afficher un message d'erreur si aucun bouton radio n'est sélectionné
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner l'état du ciel",
                            "Oublie ", JOptionPane.WARNING_MESSAGE);
                    //quand on ferme le message d'erreur, la fenêtre de réglage de la météo s'ouvre
                    // à nouveau avec les valeurs sélectionnées plus tôt
                    creationFenetreMeteoSettings();
                }

                //mise à jour du taux de croissance des légumes de la grille
                for (int x = 0; x < sizeX; x++) {
                    for (int y = 0; y < sizeY; y++) {
                        initialisationTauxCroissance(x,y); //initialise le taux de croissance de chaque légume
                    }
                }

                //ferme la fenêtre de réglage de la météo
                fenetreMeteo.dispose();
            }
        });
    }


    //procédure permettant d'envoyer les valeurs des sliders au modèle en passant en paramètre
    // le slider et une fonction set correspondante

    public void envoieValeurSliderAuModele(JSlider slider, int sliderActuel)
    {
         if (sliderActuel==0) //sliderHumidité
            {
                simulateurPotager.getSimMet().setHumiditeAir(slider.getValue());
            }
            else if (sliderActuel==1) //sliderTemperature
            {
                simulateurPotager.getSimMet().setTemperature(slider.getValue());
            }
    }


 //procédure permettant de créer un slider avec un nom, une valeur min, une valeur max et une valeur initiale
 public JSlider creationSlider(String nom, int min , int max, int valeur)
    {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, valeur);
        slider.setBorder(BorderFactory.createTitledBorder(nom));
        slider.setPreferredSize(new Dimension(300, 60));
        slider.setBorder(BorderFactory.createTitledBorder(nom));
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    public static JButton creationBouton(String nom, int width, int height)
    {
        JButton bouton = new JButton(nom);
        bouton.setPreferredSize(new Dimension(width, height));
        return bouton;
    }

    //procédure permettant de créer un bouton radio avec un nom et une value associée (il peut y avoir qu'un seul
    //bouton radio sélectionné à la fois)
    public JRadioButton creationBoutonRadio(String nom, int valeur)
    {
        JRadioButton bouton = new JRadioButton(nom);
        bouton.setActionCommand(String.valueOf(valeur)); // permet de recuperer la valeur du bouton radio
        return bouton;
    }

    private void chargerLesIcones() {
    	// image libre de droits utilisée pour les légumes : https://www.vecteezy.com/vector-art/2559196-bundle-of-fruits-and-vegetables-icons	

        Carrotte carrotte = new Carrotte();
        Salade salade = new Salade();
        Radis radis = new Radis();
        Avocat avocat = new Avocat();

        icoSalade = chargerIcone("Images/data.png", salade.X, salade.Y, tailleImage, tailleImage);
        icoCarrotte= chargerIcone("Images/data.png", carrotte.X, carrotte.Y, tailleImage, tailleImage);
        icoRadis = chargerIcone("Images/data.png", radis.X, radis.Y, tailleImage, tailleImage);
        icoAvocat = chargerIcone("Images/data.png", avocat.X, avocat.Y, tailleImage, tailleImage);
        icoSec = chargerIcone("Images/Terre.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoTerre = chargerIcone("Images/terreCultivable.png");
        //resize icoTerre 100% width, 100% height
        icoTerre = new ImageIcon(icoTerre.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    }

    public void planterLegume(int x, int y) {
        // quand on clique en dehors du potager, on affiche un message d'erreur
        if (x < CaseXmin || x >= CaseXmax || y < CaseYmin || y >= CaseYmax) {
            JOptionPane.showMessageDialog(null, "Vous ne pouvez pas planter de légume ici",
                    "Case non cultivable", JOptionPane.WARNING_MESSAGE);
        }
        else if (x == CaseXmin || x == CaseXmax || x == 18 || y == CaseYmin || y == CaseYmax || y == 8) {
            float score = simulateurPotager.getArgent();
            String[] choix = {"Défricher"};
            simulateurPotager.idDefricher = JOptionPane.showOptionDialog(null, "Voulez-vous mettre une case cultivable ici pour 5$ ?",
                    "Choix de la case cultivable", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
            if(simulateurPotager.idDefricher == 0 && score >= 5.0f) {
                simulateurPotager.setArgent(score - 5.0f);
                simulateurPotager.getPlateau()[x][y] = new CaseCultivable(simulateurPotager);
            }
            else JOptionPane.showMessageDialog(null, "Vous n'avez pas assez d'argent",
                    "T'es pauvre !", JOptionPane.WARNING_MESSAGE);
        }
        else {
            Legume legume = ((CaseCultivable) simulateurPotager.getPlateau()[x][y]).getLegume();
            // si c'est une case cultivable alors on affiche la fenetre de plantation sinon on affiche la fenetre de récolte
            if (simulateurPotager.getPlateau()[x][y] instanceof CaseCultivable && legume == null) {
                String[] choix = {"Carrotte", "Salade", "Radis", "Avocat"};
                ImageIcon[] images = {icoCarrotte, icoSalade, icoRadis, icoAvocat};
                simulateurPotager.idPlanter = JOptionPane.showOptionDialog(null, "Quel légume voulez-vous planter ?\nCarrotte = 0.5$\nSalade = 1$\nRadis = 1.5$\nAvocat = 2$",
                        "Choix du légume", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, images, choix[0]);
                simulateurPotager.actionUtilisateur(x, y);
                if(simulateurPotager.getBoolArgent()) {
                    JOptionPane.showMessageDialog(null, "Vous n'avez pas assez d'argent pour planter ce légume",
                            "Tu es pauvre !", JOptionPane.WARNING_MESSAGE);
                }
            }
            else {
                String[] choix = {"Récolter"};
                simulateurPotager.idRecolter = JOptionPane.showOptionDialog(null, "Que voulez vous faire avec ce légume ?",
                        "Action souhaitée", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
                simulateurPotager.actionUtilisateur(x, y);
            }
        }
        //mise à jour du taux de croissance du légume
        initialisationTauxCroissance(x,y);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("A vegetable garden");
        setSize(1050, 600); // taille de la fenêtre en pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        //------------creation des containers---------

        //création du container principal
        containerInfos_Reglage=new JPanel();
        containerInfos_Reglage.setLayout(new BoxLayout(containerInfos_Reglage, BoxLayout.X_AXIS));
        //taille du container principal
        containerInfos_Reglage.setPreferredSize(new Dimension(350, 350));


        //création du container Infos avec les informations sur le potager
        infos = new JPanel();
        infos.setBorder(BorderFactory.createLineBorder(Color.black));
        //Y_AXIS : affichage en colonnes des composants d'infos
        infos.setLayout(new BoxLayout(infos, BoxLayout.Y_AXIS));
        infos.setPreferredSize(new Dimension(200, 350));

        //création du container reglages avec le bouton de reglages
        reglages = new JPanel();
        reglages.setBorder(BorderFactory.createLineBorder(Color.black));
        //Y_AXIS : affichage en colonnes des composants de reglages
        reglages.setLayout(new BoxLayout(reglages, BoxLayout.Y_AXIS));
        reglages.setPreferredSize(new Dimension(150, 350));

        //information sur la récolte
        informationLegumeRecoltes = new JPanel();
        //bords noirs du panel
        informationLegumeRecoltes.setBorder(BorderFactory.createLineBorder(Color.black));
        afficherNbLegumeRecolte(informationLegumeRecoltes);
        informationLegumeRecoltes.setPreferredSize(new Dimension(200, 100));
        infos.add(informationLegumeRecoltes);

        informationPasDeTemps = new JPanel();
        //bords noirs du panel
        informationPasDeTemps.setBorder(BorderFactory.createLineBorder(Color.black));
        afficherNbPasDeTemps(informationPasDeTemps);
        informationPasDeTemps.setPreferredSize(new Dimension(200, 100));
        infos.add(informationPasDeTemps);


        //information sur la météo
        informationScore = new JPanel();
        informationScore.setBorder(BorderFactory.createLineBorder(Color.black));
        afficherScore(informationScore);
        informationScore.setPreferredSize(new Dimension(200, 100));
        infos.add(informationScore);

        informationMeteo = new JPanel();
        //bords noirs du panel
        informationMeteo.setBorder(BorderFactory.createLineBorder(Color.black));
        informationMeteo.setLayout(new BoxLayout(informationMeteo, BoxLayout.Y_AXIS));
        affichageMeteoPotager(informationMeteo);
        informationMeteo.setPreferredSize(new Dimension(200, 150));
        infos.add(informationMeteo);

        //Creation d'un bouton permettant de gérer les réglages de la météo
        JButton boutonMeteo = new JButton("Météo");
        JButton boutonPasDeTemp = new JButton("Pas de Temps");
        reglages.add(boutonMeteo);
        reglages.add(boutonPasDeTemp);

        containerInfos_Reglage.add(infos);
        containerInfos_Reglage.add(reglages);

        //lorsqu'on clique sur boutonMeteo une fenêtre s'ouvre avec les options de la météo
        boutonMeteo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonMeteoActionPerformed(evt);
            }

            private void boutonMeteoActionPerformed(java.awt.event.ActionEvent evt) {
                creationFenetreMeteoSettings();
            }
        });

        //lorsqu'on clique sur boutonPasDeTemp une fenêtre s'ouvre avec les options du pas de temps
        boutonPasDeTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonPasDeTempActionPerformed(evt);
            }

            private void boutonPasDeTempActionPerformed(java.awt.event.ActionEvent evt) {
                creationFenetreChoixPasDeTemps();
            }
        });

        add(containerInfos_Reglage, BorderLayout.EAST);

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();

                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels, BorderLayout.CENTER);

        // écouter les évènements

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                final int xx = x; // constantes utiles au fonctionnement de la classe anonyme
                final int yy = y;
                tabJLabel[x][y].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        planterLegume(xx, yy);
                    }
                });
            }
        }
    }


    public void afficherNbLegumeRecolte(javax.swing.JPanel panel) {
        panel.removeAll();
        //champ de texte permettant d'afficher le nombre de légumes récoltés
        JLabel nbLegumeRecolte = new JLabel("<html>Panier de légumes récoltés : <br/>" +
                "Nombre de légumes : " + simulateurPotager.getNbLegumesRecoltes() + "</html>");
        panel.add(nbLegumeRecolte);

        ImageIcon icoLegumeRecolte = panierLegumes;

        if (panierLegumes != null) {
            JLabel legumeRecolte = new JLabel(icoLegumeRecolte);
            legumeRecolte.setOpaque(true);
            panel.add(legumeRecolte);
        }

        panel.revalidate();
        panel.repaint();
    }

    public void afficherScore(javax.swing.JPanel panel) {
        panel.removeAll();
        //champ de texte permettant d'afficher le nombre de légumes récoltés
        JLabel score = new JLabel("<html>Porte Monnaie : <br/>" +
                "Nombre de pièces : " + simulateurPotager.getArgent() + "</html>");
        panel.add(score);

        ImageIcon scoreIco = null;

        if(simulateurPotager.getArgent() > 0 && simulateurPotager.getArgent() <= 5)
            scoreIco = Pauvre;
        else if(simulateurPotager.getArgent() > 5 && simulateurPotager.getArgent() <= 10)
            scoreIco = Moyen;
        else if(simulateurPotager.getArgent() > 10)
            scoreIco = Riche;
        else
            scoreIco = Null;

        ImageIcon icoScore = scoreIco;

        if (scoreIco != null) {
            JLabel scoreLabel = new JLabel(icoScore);
            scoreLabel.setOpaque(true);
            panel.add(scoreLabel);
        }

        panel.revalidate();
        panel.repaint();
    }

    private void afficherNbPasDeTemps(javax.swing.JPanel zoneInformation)
    {
        //suppression de ce qui est déjà affiché
        zoneInformation.removeAll();
        //si zoneInformation n'est pas nul
        if (zoneInformation != null) {
            //champ de texte permettant d'afficher le nombre de pas de temps
            JLabel nbPasDeTemps = new JLabel("<html>Pas de temps de la simulation: <br/>" +
                    +Ordonnanceur.getOrdonnanceur().getPause()/1000 + " secondes</html>");
            zoneInformation.add(nbPasDeTemps);
        }
        ImageIcon horloge = resizeImage("Images/horloge.png",tailleIcone);
        JLabel horlogeLabel = new JLabel(horloge);
        zoneInformation.add(horlogeLabel);


        zoneInformation.revalidate();
        zoneInformation.repaint();
    }


    private void affichageMeteoPotager(javax.swing.JPanel zoneInformation){

        //suppression de ce qui est déjà affiché
        String etatDuCiel = "";
        switch (simulateurPotager.getSimMet().getEtatDuCiel())
        {
            case 0:
                etatDuCiel = "Nuageux";
                break;
            case 1:
                etatDuCiel = "Ensoleillé";
                break;
        }

        //si zoneInformation n'est pas nul
        if (zoneInformation != null) {
            zoneInformation.removeAll();
            String temperature="";
            temperature=simulateurPotager.getSimMet().getTemperature()+"°C";
            String humidite="";
            humidite=simulateurPotager.getSimMet().getHumiditeAir()+"%";
            JLabel infoMeteo = new JLabel("<html>Météo actuelle : <br/>" +
                    "Température : " + temperature+" <br/>" +
                    "Humidité : " + humidite+" <br/>" +
                    "État du ciel : " + etatDuCiel + "</html>");
            infoMeteo.setVerticalAlignment(SwingConstants.TOP);
            zoneInformation.add(infoMeteo);
        }




        ImageIcon temperatureActuelle= null;
        ImageIcon humiditeActuelle= null;
        ImageIcon etatDuCielActuelle= null;
        if (simulateurPotager.getSimMet().getTemperature() <10) {
            temperatureActuelle = this.temperatureFaible;
        } else if (simulateurPotager.getSimMet().getTemperature() >25) {
            temperatureActuelle = this.temperatureForte;
        } else {
            temperatureActuelle = this.temperatureNormale;
        }

        if (simulateurPotager.getSimMet().getHumiditeAir() <33) {
            humiditeActuelle = this.humiditeFaible;
        } else if (simulateurPotager.getSimMet().getHumiditeAir() >66) {
            humiditeActuelle = this.humiditeForte;
        } else {
            humiditeActuelle = this.humiditeNormale;
        }

        if (simulateurPotager.getSimMet().getEtatDuCiel() == 0) {
            etatDuCielActuelle =  this.nuage;
        } else {
            etatDuCielActuelle = this.soleil;
        }


        JPanel panelImage= new JPanel();
        panelImage.setLayout(new BoxLayout(panelImage, BoxLayout.X_AXIS));

        //espace entre les images
        panelImage.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        if (temperatureActuelle != null) {
            JLabel tempLabel = new JLabel(temperatureActuelle);
            tempLabel.setOpaque(true);
            panelImage.add(tempLabel);
        }

        if (humiditeActuelle != null) {
            JLabel humiditeLabel = new JLabel(humiditeActuelle);
            //rendre les images visibles
            humiditeLabel.setOpaque(true);
            panelImage.add(humiditeLabel);
        }

        if (etatDuCielActuelle != null) {
            JLabel etatDuCielLabel = new JLabel(etatDuCielActuelle);
            etatDuCielLabel.setOpaque(true);
            panelImage.add(etatDuCielLabel);
        }

        zoneInformation.add(panelImage);
        zoneInformation.revalidate();
        zoneInformation.repaint();
    }


    private void changerCouleurCase(){
        //parcours de la grille
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                //si la case est cultivable
                if (simulateurPotager.getPlateau()[x][y] instanceof CaseCultivable)
                {
                    String couleur =((CaseCultivable) simulateurPotager.getPlateau()[x][y]).getCouleurCase();
                    switch (couleur)
                    {
                        case "vert":
                            tabJLabel[x][y].setBackground(Color.GREEN);
                            break;
                        case "orange":
                            tabJLabel[x][y].setBackground(Color.ORANGE);
                            break;
                        case "rouge":
                            tabJLabel[x][y].setBackground(Color.RED);
                            break;
                            case "marron":
                                tabJLabel[x][y].setBackground(new Color(69, 57, 38));
                            break;
                    }
                    tabJLabel[x][y].setOpaque(true);
                }
            }
        }
    }



    public void initialisationTauxCroissance(int x,int y)
    {
        if (simulateurPotager.getPlateau()[x][y] instanceof CaseCultivable) { // si la case est cultivable
            //On récupère le légume de la case
            Legume legume = ((CaseCultivable) simulateurPotager.getPlateau()[x][y]).getLegume();

            //si le légume existe (s'il y a un légume sur la case)
            if (legume != null) {
                //maj du taux de croissance du légume
                legume.tauxCroissanceLegume(simulateurPotager.getSimMet().getTemperature(),
                        simulateurPotager.getSimMet().getHumiditeAir(),
                        simulateurPotager.getSimMet().getEtatDuCiel());
            }
        }
    }
    private void mettreAJourAffichage() {

        Window[] windows = Window.getWindows();

        //verification si la fenetre existe bien
        for (Window window : windows) {
            if (window instanceof JFrame && ((JFrame)window).getTitle().equals("A vegetable garden")) {
                affichageMeteoPotager(informationMeteo);
                afficherNbLegumeRecolte(informationLegumeRecoltes);
                afficherNbPasDeTemps(informationPasDeTemps);
                afficherScore(informationScore);
                changerCouleurCase();

                //MAJ des informations de la météo
                //récupération de la JTextArea

                for (int x = 0; x < sizeX; x++) {
                    for (int y = 0; y < sizeY; y++) {
                        if (simulateurPotager.getPlateau()[x][y] instanceof CaseCultivable) { // si la case est cultivable
                            Legume legume = ((CaseCultivable) simulateurPotager.getPlateau()[x][y]).getLegume();

                            if (legume != null) {
                                if (legume.getCroissance() > 2) {
                                    tabJLabel[x][y].setIcon(icoTerre);
                                    tabJLabel[x][y].setOpaque(false);
                                    ((CaseCultivable) simulateurPotager.getPlateau()[x][y]).setLegume(null); // Réinitialiser le légume si la croissance est supérieure à 2
                                } else {
                                     //maj du taux de croissance du légume
                                     legume.tauxCroissanceLegume(simulateurPotager.getSimMet().getTemperature(),
                                             simulateurPotager.getSimMet().getHumiditeAir(),
                                             simulateurPotager.getSimMet().getEtatDuCiel());

                                     switch (legume.getVariete()) {
                                         case salade -> tabJLabel[x][y].setIcon(icoSalade);
                                         case carrotte -> tabJLabel[x][y].setIcon(icoCarrotte);
                                         case radis -> tabJLabel[x][y].setIcon(icoRadis);
                                         case avocat -> tabJLabel[x][y].setIcon(icoAvocat);
                                     }
                                }
                            } else {
                                tabJLabel[x][y].setIcon(icoTerre);
                                //couleur de fond transparent
                                tabJLabel[x][y].setOpaque(false);
                            }

                            // si transparence : images avec canal alpha + dessins manuels (voir ci-dessous + créer composant qui redéfinie paint(Graphics g)), se documenter
                            //BufferedImage bi = getImage("Images/smick.png", 0, 0, 20, 20);
                            //tabJLabel[x][y].getGraphics().drawImage(bi, 0, 0, null);
                        } else if (simulateurPotager.getPlateau()[x][y] instanceof CaseNonCultivable) {
                            tabJLabel[x][y].setIcon(icoMur);
                        } else {
                            tabJLabel[x][y].setIcon(icoSec);
                        }
                    }
                }
            }
        }


    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
    }


    // chargement de l'image entière comme icone
    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurPotager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


        return new ImageIcon(image);
    }

    // chargement d'une sous partie de l'image
    private ImageIcon chargerIcone(String urlIcone, int x, int y, int w, int h) {
        // charger une sous partie de l'image à partir de ses coordonnées dans urlIcone
        BufferedImage bi = getSubImage(urlIcone, x, y, w, h);
        // adapter la taille de l'image a la taille du composant (ici : 20x20)
        return new ImageIcon(bi.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
    }

    private BufferedImage getSubImage(String urlIcone, int x, int y, int w, int h) {
        BufferedImage image = null;

        try {
            File f = new File(urlIcone);
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurPotager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        BufferedImage bi = image.getSubimage(x, y, w, h);
        return bi;
    }

}
