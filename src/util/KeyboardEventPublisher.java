package util;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.function.Consumer;
/**
 * PubSub Modul für Keyboard Strokes
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018
 */
public class KeyboardEventPublisher {
    private  ArrayList<Consumer<KeyEvent>> callbacks = new ArrayList<>();

    public  void subscribe(Consumer<KeyEvent> callback) {
        callbacks.add(callback);
    }

    public void publish(KeyEvent event) {
        for(Consumer<KeyEvent> callback : callbacks) {
            callback.accept(event);
        }
    }
}
