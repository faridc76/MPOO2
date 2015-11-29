package serie10;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;





public class Splitter {

	//ATTRIBUTS
	
	private JFrame fenetre;
	private SplitterModel model;
	
	//Bouton 
	
	private JButton parcourir;
	private JButton fragmenter;

	
	//Zone pour écrire ou pour afficher
	private JTextField fichier;
	private JTextField taille;
	private JTextArea affichage;
	private JScrollPane sPane;
	
	//Selectionner
	private JComboBox nbFragment;
	
	
	private Border ligne;
	
	private static final int WIDTH = 60;
	private static final int WIDTH_2 = 200;
	private static final int HEIGHT = 20;
	
	//CONSTRUCTEURS
	
	public Splitter() {
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
		model = new StdSplitterModel();
	}
	
	public void createView() {
		fenetre = new JFrame("Fragmenteur de fichiers");
		parcourir = new JButton("Parcourir...");
		fragmenter = new JButton("Fragmenter!");
		fichier = new JTextField();
		fichier.setPreferredSize(new Dimension(WIDTH_2, HEIGHT));
		taille = new JTextField();
		taille.setPreferredSize(new Dimension(WIDTH , HEIGHT));
		taille.setEnabled(false);
		nbFragment = new JComboBox();
		ligne = BorderFactory.createLineBorder(Color.gray);
		affichage = new JTextArea();
		sPane = new JScrollPane(affichage);
	}

	public void placeComponents() {
		JPanel m = new JPanel(); {
			JPanel n = new JPanel(); {
				n.add(new JLabel("Fichier à fragmenter : "));
				n.add(fichier);
				n.add(parcourir);

			}
			m.add(n, BorderLayout.CENTER);
		}
		fenetre.add(m, BorderLayout.NORTH);
		
		JPanel z = new JPanel(new GridLayout(0, 1)); {
			JPanel q = new JPanel(new BorderLayout()); {
				JPanel b = new JPanel(new GridLayout(0, 2)); {
					JPanel r = new JPanel(new BorderLayout()); {
						r.add(new JLabel("Nb.fragments : "), BorderLayout.EAST);
					}
					
					JPanel x = new JPanel(new BorderLayout()); {
						JPanel k = new JPanel(); {
							k.add(nbFragment);
						}
						x.add(k, BorderLayout.WEST);
					}
			
					JPanel s = new JPanel(new BorderLayout()); {
						s.add(new JLabel("Taille des fragments* : "),
								BorderLayout.EAST);
					}
					
					JPanel j = new JPanel(); {
						JPanel f = new JPanel(new GridLayout(0, 2)); {
							JPanel g = new JPanel(new BorderLayout()); {
								g.add(taille, BorderLayout.WEST);
							}
							JPanel h = new JPanel(new BorderLayout()); {
								h.add(new JLabel(" octets"), BorderLayout.WEST);
							}
							f.add(g);
							f.add(h);
						}
						j.add(f);
					}
					b.add(r);
					b.add(x);
					b.add(s);
					b.add(j);
				}
				q.add(b, BorderLayout.SOUTH);
			}
		
			JPanel v = new JPanel(new BorderLayout()); {
				JPanel w = new JPanel(new GridLayout(0, 1)); {
					JPanel y = new JPanel(); {
						y.add(fragmenter);
					}
					w.add(y);
				}
				v.add(w, BorderLayout.NORTH);
			}
			z.setBorder(ligne);
			z.add(q);
			z.add(v);
		}
		fenetre.add(z, BorderLayout.WEST);
		
		fenetre.add(sPane, BorderLayout.CENTER);
		
		
		JPanel n = new JPanel(new BorderLayout()); {
			n.add(new JLabel("(*)Il s'agit de la taille de chaque fragment " 
					+ "à un octet près, sauf peut-être pour "
					+ "le dernier fragment;"));
		}
		fenetre.add(n, BorderLayout.SOUTH);
	}
	
	


	
	public void createController() {
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		model.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				fichier.removeAll();
				affichage.removeAll();
				taille.removeAll();
				nbFragment.removeAllItems();
				SplitManager splitManager = model.getSplitManager();
				if (splitManager != null) {
					affichage.setEnabled(true);
					taille.setEditable(true);
					nbFragment.setEnabled(true);
					fragmenter.setEnabled(true);
					nbFragment.addItem("1");
					for (long i = 2; i <= splitManager.getFileSize() 
					/ SplitterModel.MIN_FRAGMENT_SIZE; i++) {
						nbFragment.addItem(String.valueOf(i));
					}
					splitManager.notifyObservers();
				} else {
					affichage.setEnabled(false);
					taille.setEditable(false);
					nbFragment.setEnabled(false);
					fragmenter.setEnabled(false);
				}
			}
		});

					

		
		parcourir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				int i = file.showOpenDialog(fenetre);
				if (i == JFileChooser.APPROVE_OPTION) {
					File nameFile = file.getSelectedFile();
					if (nameFile.isFile()) {
						model.createSplitManager(nameFile);
						fichier.setText(nameFile.getAbsolutePath());
					}
				}
				model.notifyObservers();
			}
		});
				

		
		fragmenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.getSplitManager().split();
					affichage.setText(model.describeSplit());
				} catch (IOException e1) {
					//...
				}
				model.notifyObservers();
			}
		});
		
		nbFragment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getSplitManager().setSplitsNumber(Integer.valueOf(
						((String) nbFragment.getSelectedItem())));
				taille.setText(String.valueOf(
						model.getSplitManager().getFileSize() 
						/ Integer.valueOf(((String) 
								nbFragment.getSelectedItem()))));
				model.notifyObservers();
			}
		});
	
	
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Splitter().display();
            }
        });
    }
}





