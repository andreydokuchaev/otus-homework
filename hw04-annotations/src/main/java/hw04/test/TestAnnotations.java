package hw04.test;

import hw04.test.annotations.After;
import hw04.test.annotations.Before;
import hw04.test.annotations.Test;

public class TestAnnotations {

    String testInitialization;

    @Before
    void setUp() {
        System.out.println("Setting up before running test");
        testInitialization = "initialized";
    }

    @Test
    void testOne() {
        System.out.println("Running test 1");
        System.out.println("Test init phrase: " + testInitialization);
    }

    @Test
    void testTwo() {
        System.out.println("Running test 2");
        throw new RuntimeException("Test 2 exception");
    }

    @After
    void breakDown() {
        System.out.println("Breaking down after running test");
    }
}
