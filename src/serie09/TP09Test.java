package serie09;

import util.TPTester;

public final class TP09Test {
    private TP09Test() {
        // rien
    }
    public static void main(String[] args) {
        TPTester t = new TPTester(
                serie09.StdSpeedometerModelTestCons.class
        );
        int exitValue = t.runAll();
        if (exitValue == 0) {
            t = new TPTester(
                serie09.StdSpeedometerModelTest.class,
                serie09.SpeedometerTest.class
            );
            exitValue = t.runAll();
        }
        System.exit(exitValue);
    }
}
