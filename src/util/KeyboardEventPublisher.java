package util;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.function.Consumer;

public class KeyboardEventPublisher {
    private static ArrayList<Consumer<KeyEvent>> callbacks = new ArrayList<>();

    public static void subscribe(Consumer<KeyEvent> callback) {
        callbacks.add(callback);
    }

    public static void publish(KeyEvent event) {
        for(Consumer<KeyEvent> callback : callbacks) {
            callback.accept(event);
        }
    }
}
