package jpp.ui.listener;

import jpp.core.BildDokument;

/**
 * Gibt die Vorgaben fuer Listener die an einem Bildimportvorgang
 * beteiligt sind.
 * 
 * @author Nils Verheyen
 */
public interface BildimportListener {
  
  /**
   * Wird aufgerufen, wenn ein Bild erfolgreich importiert wurde.
   * @param dok das impotierte BildDokument
   */
  void bildImportiert();
  
  /**
   * Wird aufgerufen, wenn der Ladevorgang aller Bilder abgeschlossen
   * wurde.
   */
  void ladevorgangAbgeschlossen();
}
