package serie01.util;

import java.io.File;
import java.net.URL;

public final class DBFactory {
    private DBFactory() {
       // rien 
    }
    
    public static CurrencyDB createInternalDB() {
        return new StdCurrencyDB();
    }
    
    public static CurrencyDB createLocalDB(File f) {
        if (f == null) {
            throw new IllegalArgumentException();
        }
        throw new UnsupportedOperationException();
    }
    
    public static CurrencyDB createRemoteDB(URL url) {
        if (url == null) {
            throw new IllegalArgumentException();
        }
        throw new UnsupportedOperationException();
    }
}
