package serie07;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Swelling {
	
	private static final int WIDTH = 50;
	private static final int HEIGHT = 20;
	
	private JFrame fenetre;
	private SwellingModel model;
	private JButton plus;
	private JButton moins;
	private JLabel premierTexte;
	private JLabel secondTexte;
	private JTextField valeur;
	private Dimension dimension;
	
	//CONSTRUCTEUR
	public Swelling() {
        createModel();
        createView();
        placeComponents();
        createController();
    }

	//COMMANDES
	public void display() {
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        model.notifyObservers();
    }
	
	//OUTILS
	public void createModel() {
		model = new StdSwellingModel();
	}
	
	public void createView() {
		fenetre = new JFrame("Baudruche");
		fenetre.setPreferredSize(model.getDimension());
		premierTexte = new JLabel();
		secondTexte = new JLabel();
		plus = new JButton("Plus");
		moins = new JButton("Moins");
		dimension = new Dimension(WIDTH, HEIGHT);
		valeur = new JTextField();
	}
	
	public void placeComponents() {
		JPanel p = new JPanel(); {
			p.add(plus);
			p.add(moins);
		}
		JPanel m = new JPanel(); {
			premierTexte.setText("Facteur : ");
			m.add(premierTexte);
			valeur.setPreferredSize(dimension);
			m.add(valeur);
			secondTexte.setText("%");
			m.add(secondTexte);
		}
		fenetre.add(p, BorderLayout.NORTH);
		fenetre.add(m, BorderLayout.CENTER);
	}
	
	public void createController() {
		model.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				fenetre.setSize(model.getDimension());
			}
		});
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String x = valeur.getText().trim();
            	if (Double.valueOf(x) < 0) {
            		JOptionPane.showMessageDialog(
            			    null, 
            			    "Error",
            			    "Fatal error !",
            			    JOptionPane.ERROR_MESSAGE
            			);
            	} else {
            		double j;
            		j =  Double.valueOf(x) / 100;
            		model.inflate(j);
            		model.notifyObservers();
            	}
            }
        });
		
		moins.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String x = valeur.getText().trim();
            	if (Double.valueOf(x) < 0 || Integer.valueOf(x) > 100) {
            		JOptionPane.showMessageDialog(
            			    null, 
            			    "erreur",
            			    "Erreur !",
            			    JOptionPane.ERROR_MESSAGE
            			);
            	} else {
            		double j;
            		j =  Double.valueOf(x) / 100;
            		model.deflate(j);
            		model.notifyObservers();
            	}
            }
        });
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Swelling().display();
            }
        });
    }
}
