package serie08;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import serie06.CoinTypes;
import serie06.DrinkTypes;


public class DrinksMachine {
	
//ATTRIBUTS

	private static final int WIDTH = 50;
	private static final int WIDTH_2 = 200;
	private static final int HEIGHT = 20;
	private static final int NUMBER_BOISSON = 3;
	
	
	private JLabel monnaie;
	private JLabel credit;
	
	//Bouton 
	
	private JButton annuler;
	private JButton takeMonnaie;
	private JButton inserer;
	private JButton[] tabButton;
	
	//Zone pour écrire ou pour afficher
	private JTextField cents;
	private JTextField boisson;
	private JTextField reste;
	
	
	private JFrame fenetre;
	private DrinksMachineModel model;

	private List<DrinkTypes> listDrink;
	private Iterator<DrinkTypes> it;
	
	//CONSTRUCTEURS
	
	public DrinksMachine() {
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
		model = new StdDrinksMachineModel();
		CoinTypes[] tabC = CoinTypes.values();
    	for (int i = 0; i < tabC.length; i++) {
    		model.fillCash(tabC[i], DrinksMachineModel.MAX_COIN / 2);
    	}
    	
	}
	
	public void createView() {
		fenetre = new JFrame("Distributeur de boissons");
		monnaie = new JLabel("Cet appareil rend la monnaie");
		credit = new JLabel("Vous disposez d'un crédit de " 
				+ String.valueOf(model.getCreditAmount()) + " cents");
		annuler = new JButton("Annuler");
		takeMonnaie = new JButton(
				"Prenez votre boisson et/ou votre monnaie");
		
		inserer = new JButton("Insérer");
		
		listDrink = new LinkedList<DrinkTypes>();
		tabButton = new JButton[NUMBER_BOISSON];
		for (int i = 0; i < NUMBER_BOISSON; i++) {
			tabButton[i] = new JButton(
					DrinkTypes.values()[i].toString());
			listDrink.add(DrinkTypes.values()[i]);
		}
		it = listDrink.iterator();
		cents = new JTextField();
		cents.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		boisson = new JTextField();
		boisson.setPreferredSize(new Dimension(WIDTH_2, HEIGHT));
		boisson.setEnabled(false);
		reste = new JTextField();
		reste.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		reste.setEnabled(false);
	}

	public void placeComponents() {
		JPanel m = new JPanel(new GridLayout(2, 0)); {
			JPanel n = new JPanel(); {
				n.add(monnaie);
			}
			JPanel o = new JPanel(); {
				o.add(credit);
			}
			m.add(n, BorderLayout.NORTH);
			m.add(o, BorderLayout.SOUTH);
		}

		fenetre.add(m, BorderLayout.NORTH);

		JPanel n = new JPanel(new GridLayout(0, 2, 10 , 0)); {
			for (int i = 0; i < NUMBER_BOISSON; i++) {
				n.add(tabButton[i]);
				n.add(new JLabel(String.valueOf(
						it.next().getPrice()) + " cents"));
			}
		}

		fenetre.add(n, BorderLayout.WEST);

		JPanel o = new JPanel(); {
			JPanel p = new JPanel(new GridLayout(2, 2)); {
				p.add(inserer);	
				JPanel q = new JPanel(); {
					q.add(cents);
					q.add(new JLabel("cents"));
				}
				p.add(q);
				p.add(annuler);
			}
			o.add(p, BorderLayout.EAST);
		}

		fenetre.add(o, BorderLayout.EAST);

		JPanel p = new JPanel(new BorderLayout()); {
			JPanel q = new JPanel(new GridLayout(0, 2)); {
				JPanel t = new JPanel(); {
					
					t.add(new JLabel("Boisson :"));
					t.add(boisson);
				}
				JPanel r = new JPanel(); {
					
					r.add(new JLabel("Monnaie :"));
					r.add(reste);
					r.add(new JLabel("cents"));
				}
				q.add(t, BorderLayout.WEST);
				q.add(r, BorderLayout.EAST);
			}
			JPanel r = new JPanel(); {
				r.add(takeMonnaie);
			}
			p.add(q, BorderLayout.NORTH);
			p.add(r, BorderLayout.SOUTH);
		}
		
		fenetre.add(p, BorderLayout.SOUTH);
	}
	

	
	public void createController() {
		 model.addObserver(new Observer() {
	            public void update(Observable o, Object arg) {
					if (!model.canGetChange()) {
						monnaie.setText(
								"Cet appareil ne rend pas la monnaie");
					} else {
						monnaie.setText(
								"Cet appareil rend la monnaie");
					}
					it = listDrink.iterator();
					for (int i = 0; i 
					< NUMBER_BOISSON; i++) {
						DrinkTypes drink = it.next();
						if (model.getDrinkNb(drink) 
								== 0) {
							tabButton[i].setEnabled(
									false);
						}
					}
					if (model.getLastDrink() != null) {
						boisson.setText(
								model.getLastDrink().toString());
					} else {
						boisson.setText("");
					}
					if (model.getChangeAmount() == 0) {
						reste.setText("");
					} else {
						reste.setText(String.valueOf(
								model.getChangeAmount()));
					}
					cents.setText("");
					credit.setText("Vous disposez d'un crédit de "
		    				+  model.getCreditAmount() + " cents");

	            }
	        });
		
		
		inserer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	CoinTypes c; 
            	try {
            		c = CoinTypes.getCoinType(
            				Integer.valueOf(cents.getText().trim()));
            		model.insertCoin(c);
            		model.notifyObservers();
            	} catch (NumberFormatException f) {
            		/**
            		 * 
            		 */
            	}
            	
            }
        });
		
		annuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	model.cancelCredit();
				model.notifyObservers();
            }
        });
		
		takeMonnaie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	model.takeChange();
            	model.takeDrink();
            	model.notifyObservers();
            }
        });
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		it = listDrink.iterator();
		for (int i = 0; i < NUMBER_BOISSON; i++) {
			final DrinkTypes drink = it.next();
			tabButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (model.getCreditAmount() 
							>= drink.getPrice()) {
						model.selectDrink(drink);
						model.notifyObservers();
					}
				}
			});
		}

	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DrinksMachine().display();
            }
        });
    }
}

