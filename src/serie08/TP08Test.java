package serie08;

import util.TPTester;

public final class TP08Test {
    private TP08Test() {
        // rien
    }
    public static void main(String[] args) {
        TPTester t = new TPTester(
                serie08.DrinksMachineTest.class
        );
        int exitValue = t.runAll();
        System.exit(exitValue);
    }
}
