package serie07;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Rainbow {

    // ATTRIBUTS
    
    // Les dimensions du panel à colorier
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 300;
       
    // La fenêtre principale de l'application
    private JFrame frame;
    // Le bouton à placer au nord
    private JButton button;
    // Le modèle
    private RainbowModel model;

    // CONSTRUCTEURS
    
    public Rainbow() {
        createModel();
        createView();
        placeComponents();
        createController();
    }
    
    // COMMANDES

    /**
     * Rend l'application visible au centre de l'écran.
     */
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        model.notifyObservers();
    }

    // OUTILS
    
    private void createModel() {
        model = new StdRainbowModel();
    }
    private void createView() {
        frame = new JFrame("Arc-en-ciel");
        frame.setPreferredSize(
                new Dimension(FRAME_WIDTH, FRAME_HEIGHT)
        );
        button = new JButton("Modifier");
    }
    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(button);
        }
        frame.add(p, BorderLayout.NORTH);
    }
    private void createController() {
        model.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                frame.getContentPane().setBackground(model.getColor());
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.changeColor();
                model.notifyObservers();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Rainbow().display();
            }
        });
    }
}
