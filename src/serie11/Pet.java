package serie11;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import serie11.PetModel.State;


public class Pet {
	// ATTRIBUTS
	private static final int FRAME_WIDTH = 350;
	private static final int FRAME_HEIGHT = 500;
	private static final boolean NEW_FROM_FILE = false;
	private PetModel pet;
	private JFrame frame;
	private JMenuItem newFile;
	private JMenuItem newFromFile;
	private JMenuItem openFile;
	private JMenuItem reOpenFile;
	private JMenuItem saveFile;
	private JMenuItem saveAsFile;
	private JMenuItem close;
	private JMenuItem clear;
	private JMenuItem quit;
	private JButton retry;
	private JLabel label;
	private JTextArea area;
	private JScrollPane scroll;
	
	// CONSTRUCTEUR
	public Pet() {
		createModel();
		createView();
		placeComponents();
		createController();
	}
	
	// COMMANDES
	
	public void display() {
		frame.pack();
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// OUTILS

	private void createModel() {
		pet = new StdPetModel();
	}
	
	
	private void createView() {
		frame = new JFrame("Petit Editeur de Texte");
		frame.setPreferredSize(
				new Dimension(FRAME_WIDTH, FRAME_HEIGHT)
				);
		label = new JLabel("Fichier : <aucun>");
		area = new JTextArea();
		scroll = new JScrollPane();
		scroll.add(area);
		newFile = new JMenuItem("New");
		newFromFile = new JMenuItem("New From File...");
		openFile = new JMenuItem("Open...");
		reOpenFile = new JMenuItem("Reopen");
		saveFile = new JMenuItem("Save");
		saveAsFile = new JMenuItem("Save As...");
		close = new JMenuItem("Close");
		clear = new JMenuItem("Clear");
		quit = new JMenuItem("Quit");
		retry = new JButton("Ok");
	}
	

	private void placeComponents() {
		JMenuBar mb = new JMenuBar(); {
			JMenu menu1 = new JMenu("File"); {
				menu1.add(newFile);
				menu1.add(newFromFile);
				menu1.add(new JSeparator());
				menu1.add(openFile);		
				menu1.add(reOpenFile);
				menu1.add(new JSeparator());
				menu1.add(saveFile);
				menu1.add(saveAsFile);
				menu1.add(new JSeparator());
				menu1.add(close);
			}
			JMenu menu2 = new JMenu("Edit"); {
				menu2.add(clear);
			}
			JMenu menu3 = new JMenu("Quit"); {
				menu3.add(quit);
			}
			mb.add(menu1);
			mb.add(menu2);
			mb.add(menu3);
		}
		JPanel p = new JPanel(new BorderLayout()); {
			p.add(scroll);
		}
		JPanel q = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); {
			q.add(label);
		}
		frame.setJMenuBar(mb);
		frame.add(p, BorderLayout.CENTER);
		frame.add(q, BorderLayout.SOUTH);
	}
	
	
	private void createController() {		

		pet.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				clear.setEnabled(pet.getState() == State.NOF_DOC 
						|| pet.getState() == State.FIL_DOC);
				reOpenFile.setEnabled(pet.getState() == State.FIL_DOC 
						&& !pet.isSynchronized());
				saveFile.setEnabled(pet.getState() == State.FIL_DOC 
						&& !pet.isSynchronized());
				saveAsFile.setEnabled(pet.getDocument() != null);
				close.setEnabled(pet.getDocument() != null);
			}			
		});
		
		pet.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				if (pet.getDocument() == null) {
					scroll.setViewportView(null);
				} else if (pet.getDocument() != area.getDocument()) {
					area.setDocument(pet.getDocument());
					scroll.setViewportView(area);
				}
			}			
		});
		
		pet.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				label.setText(
						"Fichier : "
						+ ((pet.getDocument() != null && !pet.isSynchronized()) 
								? "* " : "")
						+ ((pet.getFile() != null) 
								? pet.getFile().getAbsolutePath() : "<aucun>")
						);
			}			
		});
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					System.exit(0);
				}
			}
		});
		
	
		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					pet.removeFile();
					pet.createEmptyDoc();
					pet.notifyObservers();
				}
			}
		});
		
		newFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					fileChooser(NEW_FROM_FILE);
				}
			}
		});
		
		openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					fileChooser(!NEW_FROM_FILE);
				}
			}
		});		
		
		reOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					try {
						pet.load();
					} catch (IOException e1) {
						showOptionDialog(1);
					} catch (BadLocationException e1) {
						showOptionDialog(2);
					}
					pet.notifyObservers();
				}
			}
		});
		
		saveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pet.save();
				} catch (IOException e1) {
					showOptionDialog(1);
				} catch (BadLocationException e1) {
					showOptionDialog(2);
				}
				pet.notifyObservers();
			}
		});
		
		saveAsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				int n = jfc.showSaveDialog(frame);
				if (n == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					if (file.exists() && file.canRead()) {
						pet.setFile(file);
						try {
							pet.save();
						} catch (IOException e1) {
							showOptionDialog(1);
						} catch (BadLocationException e1) {
							showOptionDialog(2);
						}
					} else {
						showOptionDialog(3);
					}
				}
				pet.notifyObservers();
			}
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					pet.removeDocument();
					pet.removeFile();
					pet.notifyObservers();
				}
			}
		});
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					pet.createEmptyDoc();
					pet.notifyObservers();
				}
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pane = showConfirmDialog();
				if (pane == 0) {
					System.exit(0);
				}
			}
		});		
		
		retry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				SwingUtilities.getRoot(retry);
                JOptionPane.getRootFrame().setVisible(false);
			}
		});		
	}
	
	private void fileChooser(boolean choice) {
		JFileChooser jfc = new JFileChooser();
		int n = jfc.showOpenDialog(frame);
		if (n == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (file.exists() && file.canRead()) {
				pet.createEmptyDoc();
				pet.setFile(file);
				try {
					pet.load();
				} catch (IOException e1) {
					showOptionDialog(1);
				} catch (BadLocationException e1) {
					showOptionDialog(2);
				}
				if (choice == NEW_FROM_FILE) {
					pet.removeFile();
				}
				pet.notifyObservers();
			} else {
				showOptionDialog(3);
			}			
		}
	}

	private int showConfirmDialog() {
		if (pet.getDocument() != null && !pet.isSynchronized()) {
			return JOptionPane.showConfirmDialog(
					null, 
					"Attention! Vous n'avez pas sauvegarder,"
					+ " voulez-vous quand même continuer?", 
					"Avertissement", 
					JOptionPane.YES_NO_OPTION              
	            );
		} else {
			return 0;
		}
	}
	
	private void showOptionDialog(int error) {
		JOptionPane.showOptionDialog(
				null, 
				message(error), 
				"Erreur!", 
				JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, 
				null, 
				new JButton[] { retry }, 
				retry
            );
	}
	
	private String message(int error) {
		switch (error) {
			case 1 : return "Erreur d'entrée / sortie";
			case 2 : return "Erreur: Référence du fichier introuvable";
			case 3 : return "Le Fichier n'existe pas ou ne peut-être lu";
			default : return "Erreur inconnue";
		}
	}
	

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Pet().display();
            }
        });
    }
}
