package hw04.test;

import hw04.test.runner.TestRunner;

public class Main {
    public static void main(String[] args) {
        try {
            var runner = new TestRunner();
            runner.run("hw04.test.TestAnnotations");
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
