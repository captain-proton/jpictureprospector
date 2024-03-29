

package jpp.merkmale;

import jpp.core.GeoeffnetesBild;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;


/**
 * Ein Objekt dieser Klasse stellt das Merkmal Bildhoehe eines 
 * Bilddokumentes dar. Der Typ dieses Merkmalwertes ist ein Integer.
 * @author Manfred Rosskamp
 * @author Marion Mecking
 */
public class BildhoeheMerkmal extends Merkmal {   
    
  /** Name des Lucene-Feldes fuer dieses Merkmal. */
  public static final String FELDNAME = "Bildh\u00f6he";
  
  /** 
   * Erzeugt ein neues BildhoeheMerkmal.
   */
  public BildhoeheMerkmal() {
    super(FELDNAME);
  } 
  
  /**
   * Liest die Bildbreite dieses Merkmals aus dem uebergebenen geoeffneten
   * Bild und speichert diesen in diesem Merkmal-Objekt.
   * @param bild  Bild, aus dem der Merkmalswert gelesen wird
   */
  public void leseMerkmalAus(GeoeffnetesBild bild) {
    this.wert = new Integer(bild.getBild().getHeight());
  }

  /**
   * Liest den Merkmalswert aus einem Lucene-Document und speichert diesen in 
   * diesem Merkmal-Objekt.
   * @param doc  Lucene-Document, von dem ueber ein zu diesem Merkmal 
   *    gehoerigen Field der Wert ausgelesen wird
   */
  public void leseMerkmalAusLuceneDocument(Document doc) {   
    leseMerkmalAusString(doc.get(FELDNAME));
  }
  
  public void leseMerkmalAusString(String wert) {
    this.wert = new Integer(wert);
  }
   
  /**
   * Erzeugt aus diesem Merkmal ein entsprechendes Lucene-Field.
   * @return ein entsprechendes Lucene-Field
   */
  public Field erzeugeLuceneField() {
    return new Field(FELDNAME, this.getWert().toString(), Field.Store.YES, 
      Field.Index.UN_TOKENIZED);
  }
  
  /**
   * Gibt zurueck, ob dieses Merkmal vom Endbenutzer editierbar sein kann oder
   * ob ein sich nicht aenderndes festes Merkmal ist.
   * @return <code>true</code>, wenn dieses Merkmal editierbar sein soll
   */
  public boolean istEditierbar() {
    return false; 
  }  
}
