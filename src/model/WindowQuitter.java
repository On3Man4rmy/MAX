package model;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasse dient zum schliesen der windows
 *
 * @author Tobias Fetzer 198318
 * @date 15/03/2018
 * @version 1.0
 **/
public class WindowQuitter extends WindowAdapter // ist ein WindowListener und EventListener
{
    public void windowClosing(WindowEvent e) // einzig ueberschriebene Methode
    {
        System.exit(0); // Programm beenden
    }
}