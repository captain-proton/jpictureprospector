package ui;

import core.BildDokument;
import core.JPPCore;
import core.exceptions.AendereException;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

/**
 * Ein Objekt der Klasse stellt die Oberflaeche zu den Bilddetails fuer 
 * ausgewaehlte Bilddokumenten dar.
 * 
 * @author Marion Mecking
 */
public class BilddetailsPanel extends JPanel implements Observer {
  
  /** Bilddokumente, zu denen die Merkmale angezeigt werden. */
  private List<BildDokument> bilddokumente = new ArrayList<BildDokument>();
  
  /** Textkomponenten der Merkmale. */
  private HashMap<String, JTextComponent> textkomponenten =
      new HashMap<String, JTextComponent>();
  
  /** Checkboxen der Merkmale. */
  private HashMap<String, JCheckBox> checkboxen =
      new HashMap<String, JCheckBox>();
  
  /** Namen der Merkmale, die im Panel angezeigt werden. */
  private static final String SCHLUESSELWOERTER = "Schl\u00fcsselw\u00f6rter";
  private static final String BESCHREIBUNG = "Beschreibung";
  private static final String[] merkmale = {SCHLUESSELWOERTER, BESCHREIBUNG};
  
  /** String f�r verschiedene Werte. */
  private static final String VERSCH_WERTE = "Verschiedene Werte";
  
  private static final long serialVersionUID = 1L;

  /** Label und Buttons. */
  private JLabel lSchluesselworter = null;

  private JLabel lBildbeschreibung = null;

  private JButton bAendern = null;
  
  /** Kern, der die Aenderungen �bernimmt. */
  private JPPCore kern;
  
  /**
   * Erzeugt ein neues Panel fuer die Bilddetails. 
   * Es wird davon ausgegangen, dass das Panel nur editierbare Merkmale 
   * enthaelt.
   *
   * @param kern  Kern der Anwendung.
   */
  public BilddetailsPanel(JPPCore kern) {
    super();
    initialize();
    this.kern = kern;
  }
  
  /**
   * Initialisiert die Oberflaeche
   *
   */
  private void initialize() {
    
    JTextField schluesselwoerterFeld = new JTextField();
    schluesselwoerterFeld.setPreferredSize(new Dimension(150, 20));
    this.textkomponenten.put(SCHLUESSELWOERTER, schluesselwoerterFeld);
    this.textkomponenten.put(BESCHREIBUNG, new JTextArea());
    this.checkboxen.put(SCHLUESSELWOERTER, new JCheckBox());
    this.checkboxen.put(BESCHREIBUNG, new JCheckBox());
    
    GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
    gridBagConstraints11.gridx = 2;
    gridBagConstraints11.anchor = GridBagConstraints.WEST;
    gridBagConstraints11.insets = new Insets(10, 0, 10, 0);
    gridBagConstraints11.gridy = 5;
    GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
    gridBagConstraints5.gridx = 0;
    gridBagConstraints5.gridy = 4;
    GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
    gridBagConstraints4.fill = GridBagConstraints.BOTH;
    gridBagConstraints4.gridy = 4;
    gridBagConstraints4.weightx = 1.0;
    gridBagConstraints4.weighty = 1.0;
    gridBagConstraints4.anchor = GridBagConstraints.WEST;
    gridBagConstraints4.insets = new Insets(10, 0, 10, 0);
    gridBagConstraints4.gridx = 2;
    GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
    gridBagConstraints3.gridx = 2;
    gridBagConstraints3.anchor = GridBagConstraints.WEST;
    gridBagConstraints3.insets = new Insets(10, 0, 10, 0);
    gridBagConstraints3.gridy = 3;
    lBildbeschreibung = new JLabel();
    lBildbeschreibung.setText(BESCHREIBUNG);
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
    gridBagConstraints2.gridy = 2;
    gridBagConstraints2.weightx = 1.0;
    gridBagConstraints2.anchor = GridBagConstraints.WEST;
    gridBagConstraints2.insets = new Insets(10, 0, 10, 0);
    gridBagConstraints2.gridx = 2;
    GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
    gridBagConstraints1.gridx = 2;
    gridBagConstraints1.anchor = GridBagConstraints.WEST;
    gridBagConstraints1.insets = new Insets(10, 0, 10, 0);
    gridBagConstraints1.gridy = 1;
    lSchluesselworter = new JLabel();
    lSchluesselworter.setText(SCHLUESSELWOERTER);
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    this.setSize(300, 250);
    this.setLayout(new GridBagLayout());
    
    /* Setzen der Elemente fuer Schluesselwoerter. */
    this.add(this.checkboxen.get(SCHLUESSELWOERTER), gridBagConstraints);
    this.add(lSchluesselworter, gridBagConstraints1);
    this.add(this.textkomponenten.get(SCHLUESSELWOERTER), gridBagConstraints2);
    
    /* Setzen der Elemente fuer Bildbeschreibung. */
    this.add(lBildbeschreibung, gridBagConstraints3);
    this.add(this.textkomponenten.get(BESCHREIBUNG), gridBagConstraints4);
    this.add(this.checkboxen.get(BESCHREIBUNG), gridBagConstraints5);
    this.add(getBAendern(), gridBagConstraints11);
    
    /* ActionListener fuer Button. */
    this.bAendern.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
	datenaendern(evt);
      }
    });
    
    /* ActionListener zu den Checkboxen hinzufuegen. */
    for(int i = 0; i < merkmale.length; i++) {
      this.checkboxen.get(merkmale[i]).addActionListener(new java.awt.event.ActionListener() {
	public void actionPerformed(java.awt.event.ActionEvent evt) {
	  checkBoxGewaehlt(evt);
	}
      });
      this.checkboxen.get(merkmale[i]).setVisible(false);
    }
    
  }
  
  /**
   * Initialisiert den Button zum Aendern der Bilddetails.
   *
   * @return Button zum Aendern der Bilddetails.
   */
  private JButton getBAendern() {
    if (bAendern == null) {
      bAendern = new JButton();
      bAendern.setText("\u00C4nderungen \u00FCbernehmen");
    }
    return bAendern;
  }
  
  /**
   * Aktualisiert die Liste der Bilddokumente anhand des entsprechenden 
   * <code>Observable</code>.
   *
   * @param o  das <code>Observable</code> dass sich geaendert hat
   * @param arg  das entsprechende <code>Object</code> was sich im
   *        <code>Observable</code> geaendert hat
   */
  public void update(Observable o, Object arg) {
    
    if (arg instanceof ThumbnailAnzeigePanel) {
      
      BildDokument dok = ((ThumbnailAnzeigePanel) arg).gibBildDokument();
      
      /* Bilddokument hinzufuegen oder loeschen. */
      if (bilddokumente.contains(dok)) {
	bilddokumente.remove(dok);
      } else {
	this.bilddokumente.add(dok);
      }
      
      this.aktualisiereDaten();     
    }
  }
  
  
  /**
   * Aktualisiert die Daten des Panels.
   */
  public void aktualisiereDaten() {
    String merkmalText = "";
    
    /* Checkboxen unsichtbar setzen. */
    for (int i = 0; i < merkmale.length; i++) {
      this.checkboxen.get(merkmale[i]).setVisible(false);
    }
    
    if (bilddokumente.size() != 0) {
      for (int i = 0; i < merkmale.length; i++) {
	if (hatMerkmalGleichenWert(merkmale[i])) {
	  
	  /* Bei gleichem Merkmalswert wird Wert des ersten Bilddokumentes 
	   * angezeigt und die Textkomponente editierbar gemacht. */
	  merkmalText =
	      (String) bilddokumente.get(0).getMerkmal(merkmale[i]).getWert();
	  this.textkomponenten.get(merkmale[i]).setEditable(true);
	} else {
	  
	  /* Merkmal hat unterschiedliche Werte, Checkbox false, 
	   * Textkomponente nicht editierbar 
	   */
	  merkmalText = VERSCH_WERTE;
	  this.textkomponenten.get(merkmale[i]).setEditable(false);
	  this.checkboxen.get(merkmale[i]).setVisible(true);
	  this.checkboxen.get(merkmale[i]).setSelected(false);
	}
	
	this.textkomponenten.get(merkmale[i]).setText(merkmalText);	
      }      
    }
  }
  
  /**
   * Aktualisiert die Werte und Zustaende der Textkomponenten, wenn 
   * eine Checkbox aktiviert wurde 
   *
   * @param evt  Ereignis, dass diese Methode ausloeste
   */
  private void checkBoxGewaehlt(java.awt.event.ActionEvent evt) {
    
    for (int i = 0; i < merkmale.length; i++) {
      if (this.checkboxen.get(merkmale[i]).isVisible()) {
	JCheckBox aktCheckBox = checkboxen.get(merkmale[i]);
	JTextComponent aktTextKomponente =
	    this.textkomponenten.get(merkmale[i]);
	boolean neuerWert = aktCheckBox.isSelected();
	
	if (neuerWert) {
	  // Checkbox war zuvor false
	  aktTextKomponente.setText("");
	  aktTextKomponente.setEditable(true);
	} else {
	  aktTextKomponente.setText(VERSCH_WERTE);
	  aktTextKomponente.setEditable(false);
	}
      }
    }
  }
  
  /**
   * Aendert die Daten der Bilddokumente.
   *
   * @param evt  Ereignis durch Klick auf Button
   */  
  private void datenaendern(java.awt.event.ActionEvent evt) {
    
    try {
      for (int i = 0; i < merkmale.length; i++) {
	JCheckBox aktCheckBox = checkboxen.get(merkmale[i]);
	JTextComponent aktTextKomponente =
	    this.textkomponenten.get(merkmale[i]);
	
	/* Daten werden nur geaendert wenn die Checkbox nicht 
	 * deaktiviert ist */
	if(!(aktCheckBox.isVisible() && !aktCheckBox.isSelected())) {
	  for (int j = 0; j < bilddokumente.size(); j++ ) {
	    bilddokumente.get(j).getMerkmal(merkmale[i]).setWert(
		aktTextKomponente.getText());
	    this.kern.aendere(bilddokumente.get(j));
	  }
	}
      }
    } catch (AendereException e) {
      System.out.println("Fehler beim aendern der Merkmale");
    }
    
  }
  
  /**
   * Prueft, ob die BildDokumente fuer den angegebenen Merkmalsnamen den
   * gleichen Wert besitzen.
   *
   * @param String Name des Merkmals, das abgefragt wird.
   * @return  <code>true</code> wenn alle BildDokumente denselben Wert
   *           enthalten
   *          <code>false</code> wenn die Werte unterschiedlich sind
   */
  private boolean hatMerkmalGleichenWert(String merkmalsname) {
    
    boolean istGleich = true;
    int i = 0;
    while ((i < (bilddokumente.size() - 1)) && istGleich) {
      istGleich =
	  bilddokumente.get(i).getMerkmal(merkmalsname).getWert().equals(
	  bilddokumente.get(i + 1).getMerkmal(merkmalsname).getWert());
      i++;
    }
    
    return istGleich;
  }
  
}
