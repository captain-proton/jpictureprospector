package ui;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import core.BildDokument;
import core.ImportException;
import core.JPPCore;
import java.awt.FlowLayout;

/**
 * Ein Objekt der Klasse stellt das Hauptanzeigefenster der Software zur
 * Verfuegung. Im Hauptfenster kann der Anwender alle Aktionen die
 * zur Suche und Anzeige der Bilder notwendig sind ausfuehren.
 */
public class Hauptfenster extends JFrame {
  
  /** Enthaelt die Standardhoehe des Fensters. */
  private static final int STD_HOEHE = 600;
  
  /** Enthaelt die Standardbreite des Fensters. */
  private static final int STD_BREITE = 800;
  
  /** Enthaelt den Standardabstand den Komponenten ggfs. zueinander besitzen. */
  private static final int STD_INSETS = 10;
  
  /** Gibt an welche Breite die Komponenten minimal im Bereich der
   * Bildinformationen haben duerfen.
   */
  private static final int MIN_BREITE_BILDINFO = 300;
  
  /** Gib an wie grosz ein Trennbalken einer Splitpane sein darf. */
  private static final int TRENNBALKEN_GROESZE = 7;

  /** Version UID der Software. */
  private static final long serialVersionUID = 1L;
  
  /** Enthaelt den Kern der Software mit dem operiert wird. */
  private JPPCore kern;
  
  /** Enthaelt eine Liste an Paneln die zustaendig fuer die Anzeige der
   * Thumbnails sind.
   */
  private List<ThumbnailAnzeigePanel> anzeigePanel = null;

  /** Enthaelt die Inhaltsflaeche dieses Objekts. */
  private JPanel pInhaltsflaeche = null;

  /** Enthaelt das Hauptmenue dieses Objektes. */
  private JMenuBar hauptmenu = null;
  
  private JMenu mDatei = null;

  private JMenuItem miBeenden = null;

  private JSplitPane spAnzeige = null;

  private JMenuItem miImport = null;

  private JMenu mEinstellungen = null;

  private JMenu mHilfe = null;

  private JMenuItem miInfo = null;

  private Vorschaupanel pVorschau = null;

  private SuchPanel pSuche = null;

  private JToolBar tbWerkzeugleiste = null;

  private JButton bGroszanzeige = null;

  private JPanel pThumbnailSteuerung = null;

  private JSplitPane spVorschauBildinfo = null;

  private JLabel lLetztesBild = null;

  private JLabel lNaechstesBild = null;

  private JLabel lLoeschen = null;

  private JButton bSuchen = null;

  private JButton bNormalansicht = null;

  private JMenuItem miLoeschen = null;

  private JSlider sGroesze = null;

  private JTextField tfSchluesselwoerter = null;

  private JScrollPane spThumbnails = null;

  private JPanel pBildinformationen = null;

  private JPanel pBildinfoSchluesselBeschr = null;
  
  private JLabel lSchluesselwoerter = null;
  
  private JLabel lBildbeschreibung = null;
  
  private JTextPane taBildbeschreibung = null;
  
  private JScrollPane spBilddetails = null;
  
  private JTable tBilddetails = null;

  private JPanel pThumbnails = null;

  /**
   * Erstellt ein neues Objekt der Klasse.
   */
  public Hauptfenster() {
    super();
    initialize();
    kern = new JPPCore();
  }
  
  /**
   * Importiert in den Kern des Programms eine ausgewaehlte Anzahl
   * an Dateien.
   */
  private void importiereDateien() {
    
    JFileChooser dateiauswahl = new JFileChooser();
    FileFilter filter = new Bildfilter();
    File[] files;
    dateiauswahl.setFileFilter(filter);
    dateiauswahl.showOpenDialog(pInhaltsflaeche);
    files = dateiauswahl.getSelectedFiles();
    for (int i = 0; i < files.length; i++) {
      try {
        BildDokument dok = kern.importiere(files[i]);
        if (this.anzeigePanel == null) {
          this.anzeigePanel = new ArrayList<ThumbnailAnzeigePanel>();
        } else {
          this.anzeigePanel.clear();
        }
        ThumbnailAnzeigePanel tap = new ThumbnailAnzeigePanel(dok,
            this.sGroesze.getValue());
        tap.setzeText(files[i].getName());
        tap.setVisible(true);
        this.anzeigePanel.add(tap);
      } catch (ImportException ie) {
        zeigeFehlermeldung("Importfehler",
            "Die Datei(-en) konnten nicht importiert werden.\n" + 
            ie.getMessage());
      }
    }
    zeigeBilderAn(sGroesze.getValue());
  }
  
  /**
   * Zeigt die Liste der importierten oder gesuchten Bilder innerhalb
   * der Fensters an.
   */
  private void zeigeBilderAn(int groesze) {
    
    if (this.anzeigePanel != null) {
      
      for (ThumbnailAnzeigePanel panel : this.anzeigePanel) {
        panel.setzeGroesze(groesze);
        panel.setVisible(true);
        this.pThumbnails.add(panel);
      }
    }
    
  }
  
  /**
   * Zeigt dem Benutzer eine Fehlermeldung an.
   * 
   * @param titel  der Titel der Fehlermeldung
   * @param meldung  die Fehlermeldung
   */
  private void zeigeFehlermeldung(String titel, String meldung) {
    
    JOptionPane.showMessageDialog(this.getParent(), meldung, titel,
        JOptionPane.ERROR_MESSAGE);
  }
  
  /**
   * Erstellt das Hauptmenue des Fensters.
   * 	
   * @return das Hauptmenu des Fensters, wenn es noch nicht erzeugt wurde
   */
  private JMenuBar getHauptmenu() {
    if (hauptmenu == null) {
      hauptmenu = new JMenuBar();
      hauptmenu.add(getMDatei());
      hauptmenu.add(getMEinstellungen());
      hauptmenu.add(getMHilfe());
    }
    return hauptmenu;
  }

  /**
   * This method initializes mDatei	
   * 	
   * @return javax.swing.JMenu	
   */
  private JMenu getMDatei() {
    if (mDatei == null) {
      mDatei = new JMenu();
      mDatei.setText("Datei");
      mDatei.setMnemonic(KeyEvent.VK_D);
      mDatei.add(getMiImport());
      mDatei.add(getMiBeenden());
    }
    return mDatei;
  }

  /**
   * This method initializes miBeenden	
   * 	
   * @return javax.swing.JMenuItem	
   */
  private JMenuItem getMiBeenden() {
    if (miBeenden == null) {
      miBeenden = new JMenuItem();
      miBeenden.setText("Beenden");
      miBeenden.setMnemonic(KeyEvent.VK_B);
      miBeenden.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          
          int ergebnis = JOptionPane.showConfirmDialog(pInhaltsflaeche, 
              "Wollen Sie das Programm beenden?", "Beenden", 
              JOptionPane.OK_CANCEL_OPTION);
          if (ergebnis == JOptionPane.OK_OPTION) {
            
            System.exit(0);
          }
        }
      });
    }
    return miBeenden;
  }

  /**
   * This method initializes jSplitPane	
   * 	
   * @return javax.swing.JSplitPane	
   */
  private JSplitPane getSpAnzeige() {
    if (spAnzeige == null) {
      spAnzeige = new JSplitPane();
      spAnzeige.setOneTouchExpandable(true);
      spAnzeige.setDividerSize(TRENNBALKEN_GROESZE);
      spAnzeige.setRightComponent(getPThumbnailSteuerung());
      spAnzeige.setLeftComponent(getSpVorschauBildinfo());
    }
    return spAnzeige;
  }

  /**
   * This method initializes miImport	
   * 	
   * @return javax.swing.JMenuItem	
   */
  private JMenuItem getMiImport() {
    if (miImport == null) {
      miImport = new JMenuItem();
      miImport.setText("Importieren");
      miImport.setMnemonic(KeyEvent.VK_I);
      miImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                                Event.CTRL_MASK, false));
      miImport.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          importiereDateien();
        }
      });
    }
    return miImport;
  }

  /**
   * This method initializes mEinstellungen	
   * 	
   * @return javax.swing.JMenu	
   */
  private JMenu getMEinstellungen() {
    if (mEinstellungen == null) {
      mEinstellungen = new JMenu();
      mEinstellungen.setText("Einstellungen");
      mEinstellungen.setMnemonic(KeyEvent.VK_E);
      mEinstellungen.add(getMiLoeschen());
    }
    return mEinstellungen;
  }

  /**
   * This method initializes mHilfe	
   * 	
   * @return javax.swing.JMenu	
   */
  private JMenu getMHilfe() {
    if (mHilfe == null) {
      mHilfe = new JMenu();
      mHilfe.setText("Hilfe");
      mHilfe.setMnemonic(KeyEvent.VK_H);
      mHilfe.add(getMiInfo());
    }
    return mHilfe;
  }

  /**
   * This method initializes miInfo	
   * 	
   * @return javax.swing.JMenuItem	
   */
  private JMenuItem getMiInfo() {
    if (miInfo == null) {
      miInfo = new JMenuItem();
      miInfo.setText("Info");
      miInfo.setMnemonic(KeyEvent.VK_I);
      miInfo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          JOptionPane.showMessageDialog(pInhaltsflaeche, "Programmierprojekt " +
              "der FH Gelsenkirchen\n\nJPictureProspector\n\nv0.1",
              "Info", JOptionPane.INFORMATION_MESSAGE);
        }
      });
    }
    return miInfo;
  }

  /**
   * This method initializes pVorschau	
   * 	
   * @return javax.swing.JPanel	
   */
  private Vorschaupanel getPVorschau() {
    if (pVorschau == null) {
      pVorschau = new Vorschaupanel();
      pVorschau.setLayout(new GridBagLayout());
      pVorschau.setName("pVorschau");
      pVorschau.setMinimumSize(new Dimension(10, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4));
    }
    return pVorschau;
  }

  /**
   * This method initializes suchPanel	
   * 	
   * @return ui.SuchPanel	
   */
  private SuchPanel getSuchPanel() {
    if (pSuche == null) {
      pSuche = new SuchPanel();
      pSuche.setName("pSuche");
      pSuche.add(getBSuchen(), null);
    }
    return pSuche;
  }

  /**
   * This method initializes tbWerkzeugleiste	
   * 	
   * @return javax.swing.JToolBar	
   */
  private JToolBar getTbWerkzeugleiste() {
    if (tbWerkzeugleiste == null) {
      lLoeschen = new JLabel();
      lLoeschen.setText("");
      lLoeschen.setIcon(new ImageIcon(getClass().getResource("uiimgs/loeschen.png")));
      lLoeschen.setSize(new Dimension(32, 32));
      lLoeschen.addMouseListener(new java.awt.event.MouseAdapter() {   
      	public void mouseExited(java.awt.event.MouseEvent e) {    
          lLoeschen.removeAll();
          lLoeschen.setIcon(new ImageIcon(getClass().getResource("uiimgs/loeschen.png")));
      	}
        public void mouseEntered(java.awt.event.MouseEvent e) {
          lLoeschen.removeAll();
          lLoeschen.setIcon(new ImageIcon(getClass().getResource("uiimgs/loeschenKlick.png")));
        }
      });
      lNaechstesBild = new JLabel();
      lNaechstesBild.setText("");
      lNaechstesBild.setIcon(new ImageIcon(getClass().getResource("uiimgs/pfeilrechts.png")));
      lNaechstesBild.setSize(new Dimension(32, 32));
      lNaechstesBild.addMouseListener(new java.awt.event.MouseAdapter() {   
      	public void mouseExited(java.awt.event.MouseEvent e) {    
          lNaechstesBild.removeAll();
          lNaechstesBild.setIcon(new ImageIcon(getClass().getResource("uiimgs/pfeilrechts.png")));
      	}
        public void mouseEntered(java.awt.event.MouseEvent e) {
          lNaechstesBild.removeAll();
          lNaechstesBild.setIcon(new ImageIcon(getClass().getResource("uiimgs/pfeilrechtsKlick.png")));
        }
      });
      lLetztesBild = new JLabel();
      lLetztesBild.setText("");
      lLetztesBild.setIcon(new ImageIcon(getClass().getResource("/ui/uiimgs/pfeillinks.png")));
      lLetztesBild.setSize(new Dimension(32, 32));
      lLetztesBild.addMouseListener(new java.awt.event.MouseAdapter() {   
      	public void mouseExited(java.awt.event.MouseEvent e) {    
          lLetztesBild.removeAll();
          lLetztesBild.setIcon(new ImageIcon(getClass().getResource("uiimgs/pfeillinks.png")));
      	}
        public void mouseEntered(java.awt.event.MouseEvent e) {
          lLetztesBild.removeAll();
          lLetztesBild.setIcon(new ImageIcon(getClass().getResource("uiimgs/pfeillinksKlick.png")));
        }
      });
      tbWerkzeugleiste = new JToolBar();
      tbWerkzeugleiste.add(getBNormalansicht());
      tbWerkzeugleiste.add(getBGroszanzeige());
      tbWerkzeugleiste.add(lLetztesBild);
      tbWerkzeugleiste.add(lNaechstesBild);
      tbWerkzeugleiste.add(lLoeschen);
      tbWerkzeugleiste.add(getSGroesze());
      
    }
    return tbWerkzeugleiste;
  }

  /**
   * This method initializes bGroszanzeige	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getBGroszanzeige() {
    if (bGroszanzeige == null) {
      bGroszanzeige = new JButton();
      bGroszanzeige.setText("Bild anzeigen");
    }
    return bGroszanzeige;
  }

  /**
   * This method initializes pThumbnailSteuerung	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getPThumbnailSteuerung() {
    if (pThumbnailSteuerung == null) {
      pThumbnailSteuerung = new JPanel();
      pThumbnailSteuerung.setLayout(new BorderLayout());
      pThumbnailSteuerung.add(getTbWerkzeugleiste(), BorderLayout.NORTH);
      pThumbnailSteuerung.add(getSpThumbnails(), BorderLayout.CENTER);
    }
    return pThumbnailSteuerung;
  }

  /**
   * This method initializes spVorschauBildinfo	
   * 	
   * @return javax.swing.JSplitPane	
   */
  private JSplitPane getSpVorschauBildinfo() {
    if (spVorschauBildinfo == null) {
      spVorschauBildinfo = new JSplitPane();
      spVorschauBildinfo.setOrientation(JSplitPane.VERTICAL_SPLIT);
      spVorschauBildinfo.setDividerLocation(400);
      spVorschauBildinfo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      spVorschauBildinfo.setDividerSize(TRENNBALKEN_GROESZE);
      spVorschauBildinfo.setMinimumSize(new Dimension(MIN_BREITE_BILDINFO, 245));
      spVorschauBildinfo.setPreferredSize(new Dimension(MIN_BREITE_BILDINFO, 245));
      spVorschauBildinfo.setOneTouchExpandable(true);
      spVorschauBildinfo.setBottomComponent(getPBildinformationen());
      spVorschauBildinfo.setTopComponent(getPVorschau());
    }
    return spVorschauBildinfo;
  }

  /**
   * This method initializes bSuchen	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getBSuchen() {
    if (bSuchen == null) {
      bSuchen = new JButton();
      bSuchen.setText("Suchen");
    }
    return bSuchen;
  }

  /**
   * This method initializes bNormalansicht	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getBNormalansicht() {
    if (bNormalansicht == null) {
      bNormalansicht = new JButton();
      bNormalansicht.setText("Normalansicht");
    }
    return bNormalansicht;
  }

  /**
   * This method initializes miLoeschen	
   * 	
   * @return javax.swing.JMenuItem	
   */
  private JMenuItem getMiLoeschen() {
    if (miLoeschen == null) {
      miLoeschen = new JMenuItem();
      miLoeschen.setText("Löschen");
    }
    return miLoeschen;
  }

  /**
   * Initialisiert die Leiste fuer die Groesze der Thumbnails.
   * 	
   * @return javax.swing.JSlider	liefert den initialisierten Schieber
   */
  private JSlider getSGroesze() {
    if (sGroesze == null) {
      sGroesze = new JSlider();
      sGroesze.setValue(150);
      sGroesze.setMaximum(256);
      sGroesze.setMaximumSize(new Dimension(200, 16));
      sGroesze.setMinimum(48);
      sGroesze.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseReleased(java.awt.event.MouseEvent e) {
          for (ThumbnailAnzeigePanel tap : anzeigePanel) {
            tap.setzeGroesze(sGroesze.getValue());
          }
        }
      });
    }
    return sGroesze;
  }

  /**
   * This method initializes tfSchluesselwoerter	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getTfSchluesselwoerter() {
    
    if (tfSchluesselwoerter == null) {
      tfSchluesselwoerter = new JTextField();
    }
    return tfSchluesselwoerter;
  }


  /**
   * This method initializes spThumbnails	
   * 	
   * @return javax.swing.JScrollPane	
   */
  private JScrollPane getSpThumbnails() {
  	if (spThumbnails == null) {
  		spThumbnails = new JScrollPane();
  		spThumbnails.setViewportView(getPThumbnails());
  	}
  	return spThumbnails;
  }

  /**
   * This method initializes pBildinformationen	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getPBildinformationen() {
    if (pBildinformationen == null) {
      pBildinformationen = new JPanel();
      pBildinformationen.setLayout(new BorderLayout());
      pBildinformationen.add(getPBildinfoSchluesselBeschr(), BorderLayout.NORTH);
      pBildinformationen.add(getSpBilddetails(), BorderLayout.CENTER);
    }
    return pBildinformationen;
  }

  /**
   * This method initializes pBildinfoSchluesselBeschr	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getPBildinfoSchluesselBeschr() {
    
    if (pBildinfoSchluesselBeschr == null) {
      
      // Bedingungen fuer den Textbereich zur Bildbeschreibung
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.fill = GridBagConstraints.BOTH;
      gridBagConstraints3.gridx = 0;
      gridBagConstraints3.gridy = 3;
      gridBagConstraints3.ipadx = MIN_BREITE_BILDINFO;
      gridBagConstraints3.ipady = 50;
      gridBagConstraints3.weightx = 1.0;
      gridBagConstraints3.weighty = 1.0;
      gridBagConstraints3.insets = new Insets(0, 0, STD_INSETS, 0);
      
      // Bedingungen fuer das Label zur Bildbeschreibung
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.insets = new Insets(0, STD_INSETS, STD_INSETS, 0);
      gridBagConstraints2.gridy = 2;
      gridBagConstraints2.ipadx = MIN_BREITE_BILDINFO;
      gridBagConstraints2.ipady = 5;
      gridBagConstraints2.anchor = GridBagConstraints.WEST;
      gridBagConstraints2.gridx = 0;
      
      // Bedingungen fuer das Textfeld zu den Schluesselwoertern
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.fill = GridBagConstraints.BOTH;
      gridBagConstraints1.gridx = 0;
      gridBagConstraints1.gridy = 1;
      gridBagConstraints1.ipadx = MIN_BREITE_BILDINFO;
      gridBagConstraints1.ipady = 1;
      gridBagConstraints1.weightx = 1.0;
      gridBagConstraints1.anchor = GridBagConstraints.WEST;
      gridBagConstraints1.insets = new Insets(0, 0, STD_INSETS, 0);
      
      // Bedingungen fuer Label "Schluesselwoerter"
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.insets = new Insets(STD_INSETS, STD_INSETS, STD_INSETS, 0);
      gridBagConstraints.gridy = 0;
      gridBagConstraints.ipadx = MIN_BREITE_BILDINFO;
      gridBagConstraints.ipady = 5;
      gridBagConstraints.anchor = GridBagConstraints.WEST;
      gridBagConstraints.gridx = 0;
      
      // Erstellen der Komponenten und hinzufuegen zum Layout
      lBildbeschreibung = new JLabel();
      lBildbeschreibung.setText("Bildbeschreibung");
      lSchluesselwoerter = new JLabel();
      lSchluesselwoerter.setText("Schlüsselwörter");
      pBildinfoSchluesselBeschr = new JPanel();
      pBildinfoSchluesselBeschr.setLayout(new GridBagLayout());
      pBildinfoSchluesselBeschr.setPreferredSize(new Dimension(MIN_BREITE_BILDINFO, 160));
      pBildinfoSchluesselBeschr.add(lSchluesselwoerter, gridBagConstraints);
      pBildinfoSchluesselBeschr.add(getTfSchluesselwoerter(), gridBagConstraints1);
      pBildinfoSchluesselBeschr.add(lBildbeschreibung, gridBagConstraints2);
      pBildinfoSchluesselBeschr.add(getTaBildbeschreibung(), gridBagConstraints3);
    }
    return pBildinfoSchluesselBeschr;
  }

  /**
   * This method initializes tfBildbeschreibung	
   * 	
   * @return javax.swing.JTextPane	
   */
  private JTextPane getTaBildbeschreibung() {
    
    if (taBildbeschreibung == null) {
      taBildbeschreibung = new JTextPane();
      taBildbeschreibung.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }
    return taBildbeschreibung;
  }

  /**
 * This method initializes spBilddetails	
 * 	
 * @return javax.swing.JScrollPane	
 */
private JScrollPane getSpBilddetails() {
  if (spBilddetails == null) {
    spBilddetails = new JScrollPane();
    spBilddetails.setBorder(BorderFactory.createTitledBorder(null,
        "Bilddetails", TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION, null, null));
    spBilddetails.setViewportView(getTBilddetails());
  }
  return spBilddetails;
}

/**
 * This method initializes tBilddetails	
 * 	
 * @return javax.swing.JTable	
 */
private JTable getTBilddetails() {
  if (tBilddetails == null) {
    tBilddetails = new JTable();
    tBilddetails.setModel(new BildinfoTabelModel());
    tBilddetails.setName("Bilddetails");
  }
  return tBilddetails;
}

  /**
   * This method initializes pThumbnails	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getPThumbnails() {
    if (pThumbnails == null) {
      pThumbnails = new JPanel();
      pThumbnails.setLayout(new FlowLayout());
    }
    return pThumbnails;
  }

/**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try { 
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) { 
          e.printStackTrace(); 
        }
        Hauptfenster hauptfenster = new Hauptfenster();
        hauptfenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hauptfenster.setVisible(true);
        hauptfenster.setExtendedState(JFrame.MAXIMIZED_BOTH);
      }
    });
  }

  /**
   * Initialisiert das dieses Objekt.
   */
  private void initialize() {
    this.setSize(new Dimension(STD_BREITE, STD_HOEHE));
    this.setJMenuBar(getHauptmenu());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(getJContentPane());
    this.setTitle("JPictureProspector");
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (pInhaltsflaeche == null) {
      pInhaltsflaeche = new JPanel();
      pInhaltsflaeche.setLayout(new BorderLayout());
      pInhaltsflaeche.add(getSuchPanel(), BorderLayout.NORTH);
      pInhaltsflaeche.add(getSpAnzeige(), BorderLayout.CENTER);
    }
    return pInhaltsflaeche;
  }

}