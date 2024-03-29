package jpp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import jpp.core.BildDokument;
import jpp.merkmale.AlleMerkmale;
import jpp.merkmale.ThumbnailMerkmal;
import jpp.ui.listener.BildGeladenListener;


/**
 * Ein Objekt der Klasse stellt das Tabellenmodell dar, was zur Tabelle
 * in der Groszansicht verwendet wird. Wenn ein neues Bild im Programm
 * ausgewaehlt wird, sollen sich die Informationen entsprechend aendern.
 */
public class BGATabellenModell extends DefaultTableModel {
  
  /**
   * Enthaelt das Bilddokument das die darzustellenden Informationen enthaelt.
   */
  private BildDokument dok = null;
  
  /** Enthaelt alle Listener die darauf hoeren ob ein Bild geladen wurde. */
  private List<BildGeladenListener> listener = null;
  
  /**
   * Erstellt ein neues Objekt der Klasse.
   * @param dok  das anfangs darzustellende <code>BildDokument</code>.
   */
  public BGATabellenModell(BildDokument dok) {
    
    this.dok = dok;
    this.setRowCount(0);
    this.addColumn("Bezeichnung");
    this.addColumn("Wert");
    this.listener = new ArrayList<BildGeladenListener>();
    ladeDaten();
  }
  
  /**
   * Gibt an ob eine Zelle innerhalb der Tabelle editierbar ist oder nicht.
   */
  public boolean isCellEditable(int zeile, int spalte) {
    return false;
  }
  
  /**
   * Setzt das Dokument dieses Objekts neu.
   * @param dok  das neue <code>BildDokument</code>
   */
  public void setzeDokument(BildDokument dok) {
    this.dok = dok;
    ladeDaten();
  }

  /**
   * Laedt die Daten des Bildokuments in die Tabelle.
   */
  public void ladeDaten() {
    
    List<AlleMerkmale> alleMerkmale = dok.gibAlleMerkmale();
    this.setRowCount(0);
    for (AlleMerkmale merkmal : alleMerkmale) {
      if (!merkmal.getName().equals(ThumbnailMerkmal.FELDNAME)) {
        Object[] daten = new Object[]{merkmal.getName(),
            merkmal.getWert().toString()};
        this.addRow(daten);
      }
    }
    fireBildGeladen();
    
  }
  
  /**
   * Wird aufgerufen, wenn ein Bild fertig geladen wurde.
   */
  private void fireBildGeladen() {
    for (BildGeladenListener l : listener) {
      l.bildWurdeGeladen();
    }
  }
  
  /**
   * Fuegt einen <code>BildGeladenListener</code> zur Liste der Listener
   * hinzu.
   * @param l  der <code>BildGeladenListener</code> der zur Liste der Listener
   *           hinzugefuegt wird
   */
  public void addBildGeladenListener(BildGeladenListener l) {
    listener.add(l);
  }
  
  /**
   * Loescht einen <code>BildGeladenListener</code> aus der Liste
   * der Listener.
   * @param l  der zu loeschende Listener
   */
  public void removeBildGeladenListener(BildGeladenListener l) {
    if (listener.contains(l)) {
      listener.remove(l);
    }
  }
}
