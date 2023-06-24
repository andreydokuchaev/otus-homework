package hw03.generics.box;

import hw03.generics.fruits.Fruit;
import hw03.generics.fruits.Orange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Box<T extends Fruit> {

    private List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }

    public int weight() {
        return fruits.stream().mapToInt(Fruit::getWeight).sum();
    }

    public boolean compare(Box<? extends Fruit> box) {
        return this.weight() == box.weight();
    }

    public void transfer(Box<? super T> destinationBox) {
        for (Iterator<T> i = fruits.iterator(); i.hasNext();) {
            destinationBox.add(i.next());
            i.remove();
        }
    }
}
