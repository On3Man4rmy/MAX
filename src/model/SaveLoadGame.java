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
    private Object[] player1 =new Object[6];    //Objekte, welches die Daten der Sieler speichert
    private Object[] player2=new Object[6];
    private MAX game;           //Referenze auf game


    /**
     * Konstruktor, übergeben werden beide spieler, speichert ihre werte im Array
     * @param spieler1  erster Spieler
     * @param spieler2  zweiter Spieler
     * @param game      das Spiel
     * @param currentPlayer //aktiever spieler
     */
    public SaveLoadGame(Player spieler1, Player spieler2, MAX game, Player currentPlayer) {
        player1[0]=spieler1.getPosition();              //Position

        player1[1]=(String)spieler1.getName();          //speichert Name
        player1[2]=(String)spieler1.getShortName();     //Abkürzung für Name
        player1[3]=(String)spieler1.getFillProperty().getValue().toString();        //Farbe als String
        player1[4]=(Fraction)spieler1.getScore();       //Score

        player2[0]=spieler2.getPosition();
        player2[1]=(String)spieler2.getName();
        player2[2]=(String)spieler2.getShortName();
        player2[3]=(String)spieler2.getFillProperty().getValue().toString();
        player2[4]=(Fraction)spieler2.getScore();
        //Speichert wer aktiever spieler ist
        if(spieler1==currentPlayer){
            player1[5]=true;
            player2[5]=false;
        }
        else {
            player1[5]=false;
            player2[5]=true;
        }
        this.game = game;
    }

    /**
     * Speichert das Spiel
     */
    public void saveGame(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            FileOutputStream fileout=new FileOutputStream("Max.ser");
            ObjectOutputStream objout =new ObjectOutputStream(fileout);
            objout.writeObject(this);
            objout.close();
        } catch (IOException e) {
            System.err.println (e);
        }
    }
    public MAX loadGame( MAX oldGame){
        try {
            FileInputStream inputFile = new FileInputStream("Max.ser");
            ObjectInputStream objIn=new ObjectInputStream(inputFile);

            try {
                SaveLoadGame saveState= (SaveLoadGame) objIn.readObject();
                player1=saveState.player1;
                player2=saveState.player2;

                Player p1=new Player((Position)player1[0],(String)player1[1],(String)player1[2], Color.web((String)player1[3]));
                Player p2=new Player((Position)player2[0],(String)player2[1],(String)player2[2], Color.web((String)player2[3]));

                p1.setScore((Fraction)player1[4]);
                p2.setScore((Fraction)player2[4]);

                if((boolean)player1[5]==true) {
                    oldGame.loadnewValues(p1,p2,saveState.game.mat);
                }
                else{
                    oldGame.loadnewValues(p1,p2,saveState.game.mat);

                }
                objIn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            System.err.println (e);
        }
        return oldGame;
    }
}
