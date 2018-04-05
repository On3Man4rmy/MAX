package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


    public class StartGame extends JFrame implements ActionListener {
        public static JButton startgame=new JButton("Start new game");
        StartGame(){
            startgame.setFont(new Font("Courier", Font.BOLD, 34));
            startgame.addActionListener(this); // ... und registrieren
            this.add(startgame);

        }

        public static void main(String[] args) {

            StartGame demo = new StartGame();


            WindowQuitter wquit = new WindowQuitter(); // Listener erzeugen

            demo.addWindowListener(wquit); // und registrieren
            demo.setSize(500, 500);
            demo.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MAX spiel=new MAX();
            spiel.run();
        }
    }


