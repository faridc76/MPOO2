package serie03;

import util.TPTester;

public final class TP03Test {
    private TP03Test() {
        // rien
    }
    public static void main(String[] args) {
        TPTester t = new TPTester(
                serie03.ClearTest.class,
                serie03.DeleteLineTest.class,
                serie03.InsertLineTest.class,
                serie03.StdHistoryTest.class,
                serie03.StdEditorTest.class
        );
        int exitValue = t.runAll();
        System.exit(exitValue);
    }
}
