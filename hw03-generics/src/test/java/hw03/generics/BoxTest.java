package hw03.generics;

import hw03.generics.box.Box;
import hw03.generics.fruits.Apple;
import hw03.generics.fruits.Fruit;
import hw03.generics.fruits.Orange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class BoxTest {

    @Test
    void shouldReturnTotalWeightWhenNonEmpty() {
        Box<Apple> box = new Box<>();
        box.add(new Apple(150));
        box.add(new Apple(200));

        Assertions.assertEquals(350, box.weight());
    }

    @Test
    void shouldReturnZeroWhenEmpty() {
        Box<Fruit> box = new Box<>();

        Assertions.assertEquals(0, box.weight());
    }

    @Test
    void shouldReturnTrueWhenCompareBoxesWithSameWeight() {
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        appleBox.add(new Apple(100));
        appleBox.add(new Apple(100));
        appleBox.add(new Apple(100));

        orangeBox.add(new Orange(150));
        orangeBox.add(new Orange(150));

        Assertions.assertEquals(true, appleBox.compare(orangeBox));
        Assertions.assertEquals(true, orangeBox.compare(appleBox));
    }

    @Test
    void shouldReturnFalseWhenCompareBoxesWithDifferentWeight() {
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        appleBox.add(new Apple(50));
        appleBox.add(new Apple(100));
        appleBox.add(new Apple(100));

        orangeBox.add(new Orange(150));
        orangeBox.add(new Orange(150));

        Assertions.assertEquals(false, appleBox.compare(orangeBox));
        Assertions.assertEquals(false, orangeBox.compare(appleBox));
    }

    @Test
    void shouldMoveFruitsToAnotherBoxWhenTransferCalled() {
        Box<Apple> appleBox = new Box<>();

        appleBox.add(new Apple(50));
        appleBox.add(new Apple(100));
        appleBox.add(new Apple(150));

        ArgumentCaptor<Apple> appleCaptor = ArgumentCaptor.forClass(Apple.class);

        Box<Apple> appleSpyBox = Mockito.spy(new Box<Apple>());
        appleBox.transfer(appleSpyBox);

        Mockito.verify(appleSpyBox, Mockito.times(3)).add(appleCaptor.capture());
        var captors = appleCaptor.getAllValues();
        Assertions.assertEquals(50, captors.get(0).getWeight());
        Assertions.assertEquals(100, captors.get(1).getWeight());
        Assertions.assertEquals(150, captors.get(2).getWeight());
        Assertions.assertEquals(0, appleBox.weight());
        Assertions.assertEquals(300, appleSpyBox.weight());
    }
}
