package jpp.merkmale;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import jpp.core.Einstellungen;
import jpp.core.GeoeffnetesBild;
import jpp.core.exceptions.GeneriereException;
import jpp.core.exceptions.LeseMerkmalAusException;
import jpp.core.thumbnail.SimpleThumbnailGeneriererFactory;
import jpp.core.thumbnail.ThumbnailGenerierer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;


/**
 * Ein Objekt dieser Klasse stellt das Thumbnail eines
 * Bilddokumentes dar.
 * @author Marion Mecking
 * @author Manfred Rosskamp
 */
public class ThumbnailMerkmal extends Merkmal {

  /** 
   * Name des Lucene-Feldes fuer dieses Merkmal. Sollte die Konstante
   * geaendert werden, muss auch in der Klasse
   * <code>ThumbnailAnzeigePanel</code> die Konstante
   * <code>THUMBNAILMERKMALSNAME</code> entsprechend angepasst werden. 
   */
  public static final String FELDNAME = "Thumbnail";


  
  /**
   * Erzeugt ein neues ThumbnailMerkmal.
   */
  public ThumbnailMerkmal() {
    super(FELDNAME);
  }

  public boolean equals(Object obj) {
    if (obj instanceof ThumbnailMerkmal) {
      /* Da es keine Moeglichkeit gibt die BufferedImages der Thumbnails auf
       * gleichheit zu ueberpruefen, wird hier einfach true zurueckgegeben.
       */
      return true;
    }
    return false;
  }
  /**
   * Liest das Thumbnail aus dem uebergebenen geoeffneten
   * Bild und speichert diesen in diesem Merkmal-Objekt.
   * @param bild  Bild, aus dem der Merkmalswert gelesen wird
   * @throws LeseMerkmalAusException 
   */
  public void leseMerkmalAus(GeoeffnetesBild bild) throws LeseMerkmalAusException {
    ThumbnailGenerierer generierer = 
      SimpleThumbnailGeneriererFactory.erzeugeThumbnailGenerierer(bild);
    
    /* Lasse das Thumbnail vom Generierer erzeugen */
    try {
      this.setWert(generierer.generiereThumbnail(bild, 
                Einstellungen.THUMB_MAXBREITE, Einstellungen.THUMB_MAXHOEHE));
    } catch (GeneriereException e) {
      throw new LeseMerkmalAusException(
          "Konnte kein Thumbnail erzeugen.", e);
    }
  }

  /**
   * Liest den Merkmalswert aus einem Lucene-Document und speichert diesen in
   * diesem Merkmal-Objekt.
   * @param doc  Lucene-Document, von dem ueber ein zu diesem Merkmal
   *    gehoerigen Field der Wert ausgelesen wird
   */
  public void leseMerkmalAusLuceneDocument(Document doc) throws LeseMerkmalAusException {
    byte[] bildInBytes = doc.getField(FELDNAME).binaryValue();

    try {
      setWert(ImageIO.read(new ByteArrayInputStream(bildInBytes)));
    } catch (IOException e) {
      /* Fehlerbehandlung */
      throw new LeseMerkmalAusException(
          "Konnte das Merkmal nicht aus dem Lucene-Dokument erzeugen", e);
    }
  }

  /**
   * Erzeuge aus diesem Merkmal ein entsprechendes Lucene-Field.
   * @return ein entsprechendes Lucene-Field
   */
  public Field erzeugeLuceneField() {
    byte[] bildInBytes = null; 
      
    /* Thumbnail in ein Byte-Array schreiben */
    try {
      /* oeffnen */
      ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
      
      /* schreiben */
      ImageIO.write((BufferedImage) this.getWert(), 
          Einstellungen.THUMB_FORMAT.getAusgewaehlt().toString(), 
          baos);
      
      baos.flush();
      bildInBytes = baos.toByteArray();

      baos.close();
    } catch (IOException e) {
      /* TODO Weiss noch nicht, nur so ist es nicht das Optimale.
       * Man koennte vielleicht eine richtige Exception werfen, oder vielleicht
       * garnichts machen, da dieser Fall "normal" :) nicht auftritt
       */
      throw new RuntimeException("Konnte das BufferedImage nicht in ein "
          + "ByteArrayOutputStream schreiben.", e);
    }
    return new Field(FELDNAME, bildInBytes, Field.Store.YES);
  }

  /**
   * Gibt zurueck, ob dieses Merkmal vom Endbenutzer editierbar sein kann oder
   * ob ein sich nicht aenderndes festes Merkmal ist.
   * @return <code>true</code>, wenn dieses Merkmal editierbar sein soll
   */
  public boolean istEditierbar() {
    return false;
  }

  /**
   * Liefert das Thumbnail des Bilddokumentes.
   * @return  Thumbnail des Bilddokumentes
   */
  public BufferedImage getThumbnail() {
    return (BufferedImage) getWert();
  }
  
}