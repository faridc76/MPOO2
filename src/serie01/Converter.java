package serie01;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import serie01.gui.Event;
import serie01.gui.RefreshablePanel;
import serie01.gui.SafeTextField;
import serie01.gui.Updater;
import serie01.model.ConverterModel;
import serie01.model.StdConverterModel;
import serie01.util.CurrencyId;
import serie01.util.DBFactory;
import serie01.util.OneToOne;
import serie01.util.Currency;

public class Converter extends JFrame {

    public static final int LOCAL = 0;
    public static final int FOREIGN = 1;

    private static final int MAX_LENGTH = 40;
    private static final int FLOAT_LENGTH = 10;
    private static final int INT_LENGTH = 5;

    private JComboBox cbxLocalCurrency, cbxForeignCurrency, cbxCurrency;
    private OneToOne<JComboBox> currencies;
    private JLabel lblLocalIso1, lblLocalIso2, lblForeignIso1, lblForeignIso2,
            lblIso, lblLocalRate, lblForeignRate;
    private SafeTextField tfdLocalAmount, tfdForeignAmount;
    private JTextField tfdRate, tfdFractionDigits;
    private OneToOne<JTextField> amounts;
    private JButton btnValid;
    private JTabbedPane tabbedPane;
    private ConverterModel model;
    private RefreshablePanel convPanel, multiPanel, configPanel;

    public Converter(int n) {
        super("Convertisseur de devises");
        initDBType();
        createModel(n);
        createView();
        placeComponents();
        createController();
    }

    private void initDBType() {
        Object[] dbTypes = new Object[] {"Internal", "Local", "Remote"};
        boolean done = false;
        while (!done) {
            String s = (String) JOptionPane.showInputDialog(
                    null,
                    "Choissisez la source des données :",
                    "Création de la base de données",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    dbTypes,
                    "Internal");
            if (s != null) {
                if (s == "Internal") {
                    done = true;
                    Currency.setDB(DBFactory.createInternalDB());
//                } else if (s == "Local") {
//                    JFileChooser jfc = new JFileChooser();
//                    int returnVal = jfc.showOpenDialog(null);
//                    if (returnVal == JFileChooser.APPROVE_OPTION) {
//                        done = true;
//                        File f = jfc.getSelectedFile();
//                        Currency.setDB(DBFactory.createLocalDB(f));
//                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "L'accès à ce type de sources n'est pas implémenté",
                            "Connexion à une base de données",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } else {
                System.out.println("pas de base de données, pas de jouet");
                System.exit(0);
            }
        }
    }

    public void display() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createModel(int n) {
        Observer obs2 = new Observer() {
            public void update(Observable obj, Object arg) {
                Event evt = (Event) arg;
                int index = evt.getIndex();
                switch (evt.getType()) {
                case AMOUNT_EVT:
                    break;
                case CURRENCY_EVT:
                    lblForeignRate.setText(
                            model.getExchangeRate(LOCAL, FOREIGN)
                    );
                    lblLocalRate.setText(
                            model.getExchangeRate(FOREIGN, LOCAL)
                    );
                    JLabel lbl1 = (index == LOCAL)
                        ? lblLocalIso1
                        : lblForeignIso1;
                    JLabel lbl2 = (index == LOCAL)
                        ? lblLocalIso2
                        : lblForeignIso2;
                    lbl1.setText(model.getIsoCode(index));
                    lbl2.setText(model.getIsoCode(index));
                    break;
                case RATE_EVT:
                    if ((index == model.getId2(FOREIGN))
                            || (index == model.getId2(LOCAL))) {
                        lblLocalRate.setText(
                                model.getExchangeRate(FOREIGN, LOCAL)
                        );
                        lblForeignRate.setText(
                                model.getExchangeRate(LOCAL, FOREIGN)
                        );
                    }
                    break;
                default:
                    assert false;
                    break;
                }
                tfdLocalAmount.setText(model.getAmount2(LOCAL));
                tfdForeignAmount.setText(model.getAmount2(FOREIGN));
            }
        };

        Observer obsn = new Observer() {
            public void update(Observable obj, Object arg) {
                for (int i = 0; i < model.getCurrencyNb(); i++) {
                    amounts.get(i).setText(model.getAmountN(i));
                }
            }
        };

        model = new StdConverterModel(n);
        model.setObservers(obs2, obsn);
    }

    private void createView() {
        tabbedPane = new JTabbedPane();

        convPanel = new RefreshablePanel();
        // Creation des 2 boîtes combo du panel Conversion
        String[] items =
            new String[CurrencyId.values().length];
        for (CurrencyId id : CurrencyId.values()) {
            Currency c = new Currency(id);
            items[id.ordinal()] = c.getIsoCode() + " : " + c.getName() + " ("
                    + c.getLand() + ")";
            if (items[id.ordinal()].length() > MAX_LENGTH) {
                items[id.ordinal()] =
                    items[id.ordinal()].substring(0, MAX_LENGTH) + "...";
            }
        }
        cbxLocalCurrency = new JComboBox(items);
        cbxForeignCurrency = new JComboBox(items);

        // création des labels du panel Conversion
        lblLocalIso1 = new JLabel();
        lblLocalIso2 = new JLabel();
        lblForeignIso1 = new JLabel();
        lblForeignIso2 = new JLabel();
        lblLocalRate = new JLabel();
        lblForeignRate = new JLabel();

        // création des montants du panel Conversion
        tfdLocalAmount = new SafeTextField(
                FLOAT_LENGTH,
                model.getAmount2(LOCAL)
        );
        tfdForeignAmount = new SafeTextField(
                FLOAT_LENGTH,
                model.getAmount2(FOREIGN)
        );

        // création des composants du panel Multiconversion
        multiPanel = new RefreshablePanel();
        currencies = new OneToOne<JComboBox>();
        amounts = new OneToOne<JTextField>();
        for (int i = 0; i < model.getCurrencyNb(); i++) {
            currencies.add(new JComboBox(items));
            amounts.add(new JTextField(FLOAT_LENGTH));
        }

        // création des composants du panel Configuration
        configPanel = new RefreshablePanel();
        cbxCurrency = new JComboBox(items);
        lblIso = new JLabel();
        tfdFractionDigits = new JTextField(INT_LENGTH);
        tfdRate = new JTextField(FLOAT_LENGTH);
        btnValid = new JButton("Valider");
    }

    private void placeComponents() {
        // onglet conversion
        {
            convPanel.setLayout(new GridLayout(3, 1));
            // sélection devises
            JPanel p = new JPanel(new GridLayout(2, 2)); {
                // p.setBorder(BorderFactory.createEtchedBorder());
                p.add(new JLabel("Devise locale"));
                p.add(new JLabel("Devise étrangère"));
                
                // sélection devise locale
                JPanel q = new JPanel(); {
                    q.add(cbxLocalCurrency);
                }
                p.add(q);
                
                // sélection devise étrangère
                q = new JPanel(); {
                    q.add(cbxForeignCurrency);
                }
                p.add(q);
            }
            convPanel.add(p);
            
            // bloc taux de change
            p = new JPanel(new BorderLayout()); {
                // p.setBorder(BorderFactory.createEtchedBorder());
                p.add(new JLabel("Taux de change : "), BorderLayout.NORTH);

                // colonnes 1 à 5
                JPanel q = new JPanel(); {
                    // colonne 1
                    JPanel r = new JPanel(new GridLayout(2, 1)); {
                        r.add(new JLabel("1"));
                        r.add(lblLocalRate);
                    }
                    q.add(r);

                    // colonne 2
                    r = new JPanel(new GridLayout(2, 1)); {
                        r.add(lblLocalIso1);
                        r.add(lblLocalIso2);
                    }
                    q.add(r);

                    // colonne 3
                    r = new JPanel(new GridLayout(2, 1)); {
                        r.add(new JLabel("==>"));
                        r.add(new JLabel("<=="));
                    }
                    q.add(r);

                    // colonne 4
                    r = new JPanel(new GridLayout(2, 1)); {
                        r.add(lblForeignRate);
                        r.add(new JLabel("1"));
                    }
                    q.add(r);

                    // colonne 5
                    r = new JPanel(new GridLayout(2, 1)); {
                        r.add(lblForeignIso1);
                        r.add(lblForeignIso2);
                    }
                    q.add(r);
                }
                p.add(q, BorderLayout.CENTER);
            }
            convPanel.add(p);

            // conversion montants
            p = new JPanel(); {
                // p.setBorder(BorderFactory.createEtchedBorder());
                p.add(tfdLocalAmount);
                p.add(new JLabel(" <==> "));
                p.add(tfdForeignAmount);
            }
            convPanel.add(p);
        }
        tabbedPane.addTab("Conversion", null, convPanel, null);
        
        {
            // onglet conversions multiples
            JPanel p = new JPanel(new GridLayout(0, 2)); {
                // p.setBorder(BorderFactory.createEtchedBorder());
                for (int i = 0; i < model.getCurrencyNb(); i++) {
                    // La ième monnaie
                    p.add(currencies.get(i));
                
                    // Le ième montant 
                    JPanel q = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
                        q.add(amounts.get(i));
                    }
                    p.add(q);
                }
            }
            multiPanel.add(p);
        }
        tabbedPane.addTab("Multi Conversion", null, multiPanel, null);
        
        // onglet configuration
        {
            configPanel.setLayout(new GridLayout(3, 1));
            // édition taux de change
            JPanel p = new JPanel(new GridLayout(3, 1)); {
                p.setBorder(BorderFactory.createEtchedBorder());

                // intitulé section
                JPanel q = new JPanel(); {
                    q.add(new JLabel("Edition des taux"));
                }
                p.add(q);

                // devise à modifier
                q = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
                    q.add(cbxCurrency);
                }
                p.add(q);

                // taux de change
                q = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
                    q.add(new JLabel("1 Euro ="));
                    q.add(tfdRate);
                    q.add(lblIso);
                }
                p.add(q);
            }
            configPanel.add(p);
            
            // options d'affichage
            p = new JPanel(new GridLayout(2, 1)); {
                p.setBorder(BorderFactory.createEtchedBorder());

                // intitulé section
                JPanel q = new JPanel(); {
                    q.add(new JLabel("Option d'affichage"));
                }
                p.add(q);
                
                // nombre de décimales
                q = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
                    q.add(new JLabel("Nombre de décimales : "));
                    q.add(tfdFractionDigits);
                }
                p.add(q);
            }
            configPanel.add(p);

            // bouton de validation
            p = new JPanel(); {
                p.add(btnValid);
            }
            configPanel.add(p);
        }
        tabbedPane.addTab("Configuration", null, configPanel, null);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    private void createController() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Comportements du panel Conversion
        ActionListener cbxAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cbx = (JComboBox) e.getSource();
                Currency c = new Currency(
                        CurrencyId.valueOf(
                            ((String) cbx.getSelectedItem()).substring(0, 3)
                        )
                );
                model.setCurrency2(
                        (cbx == cbxLocalCurrency) ? LOCAL : FOREIGN,
                        c
                );
            }
        };
        cbxLocalCurrency.addActionListener(cbxAL);
        cbxLocalCurrency.setSelectedIndex(model.getId2(LOCAL));
        cbxForeignCurrency.addActionListener(cbxAL);
        cbxForeignCurrency.setSelectedIndex(model.getId2(FOREIGN));

        ActionListener tfdAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SafeTextField t = (SafeTextField) e.getSource();
                double amount = 0;
                try {
                    amount = model.getAmount(t.getText());
                } catch (IllegalArgumentException ex) {
                    selectWrongEntry(t);
                    return;
                }
                t.setText(model.format(amount));
                t.saveValue();
                model.setAmount2(
                        (t == tfdLocalAmount) ? LOCAL : FOREIGN,
                        amount
                );
            }
        };
        tfdLocalAmount.addActionListener(tfdAL);
        tfdLocalAmount.setText(model.getAmount2(LOCAL));

        tfdForeignAmount.addActionListener(tfdAL);
        tfdForeignAmount.setText(model.getAmount2(FOREIGN));

        // Comportements du panel Multiconversion
        multiPanel.setUpdater(new Updater() {
            public void update() {
                for (int i = 0; i < model.getCurrencyNb(); i++) {
                    amounts.get(i).setText(model.getAmountN(i));
                }
            }
        });
        tfdAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField t = (JTextField) e.getSource();
                double amount = 0;
                try {
                    amount = model.getAmount(t.getText());
                } catch (IllegalArgumentException ex) {
                    selectWrongEntry(t);
                    return;
                }
                model.setAmountN(amounts.get(t), amount);
            }
        };
        FocusListener tfdFL = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField t = (JTextField) e.getSource();
                double amount = 0;
                try {
                    amount = model.getAmount(t.getText());
                } catch (IllegalArgumentException ex) {
                    selectWrongEntry(t);
                    return;
                }
                model.setAmountN(amounts.get(t), amount);
            }
        };
        for (int i = 0; i < model.getCurrencyNb(); i++) {
            amounts.get(i).addActionListener(tfdAL);
            amounts.get(i).addFocusListener(tfdFL);
        }
        cbxAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cbx = (JComboBox) e.getSource();
                model.setCurrencyN(
                    currencies.get(cbx),
                    new Currency(
                        CurrencyId.valueOf(
                            ((String) cbx.getSelectedItem()).substring(0, 3)
                        )
                    )
                );
            }
        };
        for (int i = 0; i < model.getCurrencyNb(); i++) {
            currencies.get(i).addActionListener(cbxAL);
            currencies.get(i).setSelectedIndex(
                    model.getIdN(i)
            );
        }

        // Comportements du panel Configuration
        configPanel.setUpdater(new Updater() {
            public void update() {
                Currency c = new Currency(
                    CurrencyId.valueOf(
                        ((String) cbxCurrency.getSelectedItem())
                                .substring(0, 3)
                    )
                );
                tfdRate.setText(model.format(c.getExchangeRate()));
                tfdFractionDigits.setText(
                        String.valueOf(model.getFractionDigits())
                );
            }
        });
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Component p = tabbedPane.getSelectedComponent();
                if (p instanceof RefreshablePanel) {
                    ((RefreshablePanel) p).refresh();
                }
            }
        });

        cbxCurrency.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Currency c = new Currency(
                    CurrencyId.valueOf(
                        ((String) cbxCurrency.getSelectedItem())
                                .substring(0, 3)
                    )
                );
                tfdRate.setText(model.format(c.getExchangeRate()));
                lblIso.setText(c.getIsoCode());
                if (c == new Currency(CurrencyId.EUR)) {
                    tfdRate.setEnabled(false);
                } else {
                    tfdRate.setEnabled(true);
                }
            }
        });
        cbxCurrency.setSelectedItem(new Currency(CurrencyId.EUR));

        tfdFractionDigits.setText(String.valueOf(model.getFractionDigits()));

        btnValid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int nbdec = 0;
                try {
                    nbdec = model.getPositiveInt(tfdFractionDigits.getText());
                } catch (IllegalArgumentException ex) {
                    selectWrongEntry(tfdFractionDigits);
                    return;
                }
                model.setFractionDigits(nbdec);

                Currency c = new Currency(
                    CurrencyId.valueOf(
                        ((String) cbxCurrency.getSelectedItem())
                            .substring(0, 3)
                    )
                );
                double amount = 0;
                try {
                    amount = model.getAmount(tfdRate.getText());
                } catch (IllegalArgumentException ex) {
                    selectWrongEntry(tfdRate);
                    return;
                }
                model.setEuroExchangeRate(c, amount);
            }
        });
    }

    private void selectWrongEntry(JTextField jtf) {
        jtf.selectAll();
        jtf.requestFocusInWindow();
        Toolkit.getDefaultToolkit().beep();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Converter(5).display();
            }
        });
    }
}
