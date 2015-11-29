package serie09;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import serie09.SpeedometerModel.SpeedUnit;

public class Speedometer {
	
	//ATTRIBUTS
	private static final double STEP = 2;
	private static final double MAX = 100;
	private JFrame fenetre;
	
	private ButtonGroup button;
	
	private JRadioButton kmh;
	private JRadioButton mph;
	
	private JButton started;
	private JButton plus;
	private JButton moins;
	
	
	private Border border;
	private SpeedometerModel model;
	private GraphicSpeedometer graphic;

	
	
	//CONSTRUCTEUR
	public Speedometer() {
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
		model = new StdSpeedometerModel(STEP, MAX);
		graphic = new GraphicSpeedometer(model);
	}

	public void createView() {
		fenetre = new JFrame("Speedometer");
		
		kmh = new JRadioButton("Km / h");
		kmh.setSelected(true);
		
		mph = new JRadioButton("M.p.H.");
		
		button = new ButtonGroup();
		button.add(kmh);
		button.add(mph);
		
		started = new JButton("Turn ON");
		plus = new JButton("+");
		moins = new JButton("-");
		
		border = BorderFactory.createLineBorder(Color.gray);
	}
	
	public void placeComponents() {
		JPanel c = new JPanel(new GridLayout(0, 1)); {
			JPanel a = new JPanel(); {
				JPanel p = new JPanel(new GridLayout(0, 1)); {
					p.add(kmh);
					p.add(mph);
					p.setBorder(border);
				}
				a.add(p, BorderLayout.CENTER);
			}
			JPanel b = new JPanel(); {
				JPanel m = new JPanel(new GridLayout(0, 2)); {
					m.add(moins);
					m.add(plus);
					m.setBorder(border);
				}
				b.add(m, BorderLayout.CENTER);
			}
			JPanel n = new JPanel(); {
				n.add(started);
			}
			c.add(a);
			c.add(b);
			c.add(n);
		}
		fenetre.add(c, BorderLayout.WEST);
		fenetre.add(graphic, BorderLayout.CENTER);
	}
	
	public void createController() {
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		model.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				if (model.getUnit() == SpeedUnit.MPH) {
					mph.setSelected(true);
				} else {
					kmh.setSelected(true);
				}
				if (!model.isOn()) {
					started.setText("Turn ON");
					moins.setEnabled(false);
					plus.setEnabled(false);
				} else {
					started.setText("Turn OFF");
					if (model.getSpeed() != model.getMaxSpeed()) {
						plus.setEnabled(true);
					} else {
						plus.setEnabled(false);
					}
					if (model.getSpeed() != 0) {
						moins.setEnabled(true);
					} else {
						moins.setEnabled(false);
					}
				}
			}
		});
		
		started.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.isOn()) {
					model.turnOff();
				} else {
					model.turnOn();
				}
				graphic.repaint();
				model.notifyObservers();
			}
		});
		
		kmh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setUnit(SpeedUnit.KMH);
				graphic.repaint();
				model.notifyObservers();
			}
		});
		
		mph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setUnit(SpeedUnit.MPH);
				graphic.repaint();
				model.notifyObservers();
			}
		});
		
		plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.speedUp();
				graphic.repaint();
				model.notifyObservers();
			}
		});
		
		moins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.slowDown();
				graphic.repaint();
				model.notifyObservers();
			}
		});
		
	}


	
	
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Speedometer().display();
            }
        });
    }
}

