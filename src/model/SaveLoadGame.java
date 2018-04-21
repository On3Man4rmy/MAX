package model;

import javafx.scene.paint.Color;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Speichert die definierenden Attribute des games, und kann sie wieder laden
 * Wird benötigt, da Player Instanzvariablen haten, die nicht Serializable sind
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 19.04.2018
 */

public class SaveLoadGame implements Serializable{
    private Player player1;
    private Player player2;
    private MAX game;           //Referenze auf model

    /**
     * Konstruktor, übergeben werden beide spieler, speichert ihre werte im Array
     * @param spieler1  erster Spieler
     * @param spieler2  zweiter Spieler
     * @param game      das Spiel
     * @param currentPlayer //aktiever spieler
     */
    public SaveLoadGame(Player spieler1, Player spieler2, MAX game, Player currentPlayer) {
        this.player1 = spieler1;
        this.player2 = spieler2;
        this.game = game;
    }

    /**
     * Speichert das Spiel
     */
    public void saveGame(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String filename="Max "+dateFormat.format(date)+".ser";  //Dateiname beinhalted erstelldatum

        System.out.println("Savegame saved as: "+filename);
        try {
            FileOutputStream fileout=new FileOutputStream(filename);
            ObjectOutputStream objout =new ObjectOutputStream(fileout);
            objout.writeObject(this);
            objout.close();
        } catch (IOException e) {
            System.err.println (e);
        }
    }

    /**
     * Lade das spiel
     * @param file  Der zu ladende Spielstand
     * @param oldGame   Referenze zum aktuellen SPeilstand
     */
    public MAX loadGame(File file,MAX oldGame){
        try {
            FileInputStream inputFile = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(inputFile);
            SaveLoadGame saveState= (SaveLoadGame) objIn.readObject();

            oldGame.loadNewValues(
                    saveState.player1,
                    saveState.player2,
                    saveState.game.getMat()
            );

            objIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println (e);
        }
        return oldGame;
    }
}
