package serie03;

import java.util.LinkedList;

public class StdText implements Text {
    
    // ATTRIBUTS
    
    private final LinkedList<String> lines;

    // CONSTRUCTEURS
    
    public StdText() {
        lines = new LinkedList<String>();
    }

    // REQUETES
    
    public int getLinesNb() {
        return lines.size();
    }
    public String getLine(int i) {
        if ((i < 1) || (i > getLinesNb())) {
            throw new IllegalArgumentException(
                    "Mauvais numéro de ligne : " + i
            );
        }
        
        return lines.get(i - 1);
    }
    public String getContent() {
        StringBuffer result = new StringBuffer();
        for (String s : lines) {
            result.append(s + NL);
        }
        return result.toString();
    }

    // COMMANDES
    
    public void insertLine(int numLine, String s) {
        if ((numLine < 1) || (numLine > getLinesNb() + 1)) {
            throw new IllegalArgumentException(
                    "Mauvais numéro de ligne : " + numLine
            );
        }
        if (s == null) {
            throw new IllegalArgumentException(
                    "La chaîne fournie n'existe pas"
            );
        }
        
        lines.add(numLine - 1, s);
    }
    public void deleteLine(int numLine) {
        if ((numLine < 1) || (numLine > getLinesNb())) {
            throw new IllegalArgumentException(
                    "Mauvais numéro de ligne : " + numLine
            );
        }
        
        lines.remove(numLine - 1);
    }
    public void clear() {
        lines.clear();
    }
}
