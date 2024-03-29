
package jpp.merkmale;

/**
 * Ein Objekt dieser Klasse repraesentiert ein Exif - Merkmal mit einem 
 * Mermalsnamen und einem zu dem Merkmal gehoerenden Wert. 
 * Exif-Merkmale koennen nur aus JPG und TIF gewonnen werden und werden nicht
 * in Lucene abgespeichert.
 * 
 * @author Marion Mecking
 */
public class ExifMerkmal extends AlleMerkmale {
  
  /** 
   * Erzeugt ein neues Merkmal mit einem eindeutig identifiezierenden Namen.
   * @param name  Name dieses Merkmals, z.B "Bildhoehe"
   */
  public ExifMerkmal(String name, String wert) {
    super(name);
    this.wert = wert;
  }
}
