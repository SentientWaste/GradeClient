package Client.values;

public class Booleans<V> extends Value<V> {
    public Booleans(String name, V value) {
        super(name);
        this.setValue(value);
    }
}
