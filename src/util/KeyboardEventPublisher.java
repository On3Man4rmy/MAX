package util;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

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
