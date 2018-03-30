/**
 * Klasse dient zum schliesen der windows
 *
 * @author Tobias Fetzer 198318
 * @date 15/03/2018
 * @version 1.0
 **/
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowQuitter extends WindowAdapter // ist ein WindowListener und EventListener
{
    public void windowClosing(WindowEvent e) // einzig ueberschriebene Methode
    {
        System.exit(0); // Programm beenden
    }
}