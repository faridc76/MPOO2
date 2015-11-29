package serie07;

import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Suspense {
	
	//ATTRIBUTS
	
	//nombre de choix
	private static final int MAX_NUMBER = 3;
	
	private JFrame fenetre;
	private SuspenseModel model;
	private JButton[] tabOfButton;
	private JButton reJouer;
	private JLabel phrase;
	private JLabel nbClick;
	
	//CONSTRUCTEURS
	public Suspense() {
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
		model = new StdSuspenseModel(MAX_NUMBER);
	}
	
	public void createView() {
		fenetre = new JFrame("Suspense");
		phrase = new JLabel();
		nbClick = new JLabel();
		reJouer = new JButton("Recommencer");
		tabOfButton = new JButton[MAX_NUMBER];
		for (int i = 0; i < model.getMaxNumber(); i++) {
			tabOfButton[i] = new JButton(String.valueOf(i + 1));
		}
	}
	
	public void placeComponents() {
		JPanel p = new JPanel(); {
			for (int i = 0; i < tabOfButton.length; i++) {
				p.add(tabOfButton[i]);
			}
		}
		JPanel m = new JPanel(); {
			phrase.setText("Nombres de clicks : ");
			m.add(phrase);
			nbClick.setText(String.valueOf(model.getProposalsCount()));
			m.add(nbClick);
		}
		fenetre.add(p, BorderLayout.NORTH);
		fenetre.add(m, BorderLayout.CENTER);
	}
	
	public void createController() {
		model.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				nbClick.setText(String.valueOf(model.getProposalsCount()));
				if (!(model.getDrawsCount() == 0) 
						&& !(model.getProposalsCount() == 0)) {
					if (model.sameNumbers()) {
						JOptionPane.showOptionDialog(
								null,
								"Vous avez perdu en " 
									+ model.getProposalsCount() 
                		    		+ " coups",
                		    		"Merci",
                		    		JOptionPane.DEFAULT_OPTION,
                		    		JOptionPane.INFORMATION_MESSAGE,
                		    		null,
                		    		new Object[] { reJouer },
                		    		reJouer
                			);
					}
				}
			}
		});
		
		reJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component k = SwingUtilities.getRoot(reJouer);
				k.setVisible(false);
				model.reinit();
				model.notifyObservers();
			}
		});
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for (int i = 0; i < tabOfButton.length; i++) {
			tabOfButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton b = (JButton) e.getSource();
					model.proposeNumber(Integer.valueOf(b.getText()));
					model.drawNumber();
					model.notifyObservers();
				}
			});
		}
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Suspense().display();
            }
        });
    }
}
