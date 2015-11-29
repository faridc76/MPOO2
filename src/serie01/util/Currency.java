package serie01.util;

/**
 * Une devise permet d'accéder à un enregistrement dans la base de données.
 * C'est par le biais d'une devise que les classes externes au paquetage
 *  <code>util</code> peuvent consulter ou modifier la base de données. 
 */
public class Currency {
    private static CurrencyDB currencyDB;
    
    public static void setDB(CurrencyDB db) {
        if (db == null) {
            throw new IllegalArgumentException();
        } 
        currencyDB = db;
    }
    
    private CurrencyId id;
    
    public Currency(CurrencyId id) {
        this.id = id;
    }
    
    // REQUETES
    public CurrencyId getId() {
        return id;
    }
    
    public double getExchangeRate() {
        return currencyDB.getExchangeRate(id);
    }

    public String getIsoCode() {
        return currencyDB.getIsoCode(id);
    }
    
    public String getLand() {
        return currencyDB.getLand(id);
    }
    
    public String getName() {
        return currencyDB.getName(id);
    }

    // COMMANDES
    public void setExchangeRate(double rate) {
        if (rate <= 0) {
            throw new IllegalArgumentException(
                    "Le taux doit être positif (" + rate + ")"
            );
        }
        
        currencyDB.setExchangeRate(id, rate);
    }
}
