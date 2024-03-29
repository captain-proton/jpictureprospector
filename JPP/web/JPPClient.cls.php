<?php
include_once("SocketVerbindung.cls.php");
include_once("config.inc.php");
include_once("logger.inc.php");
include_once("exceptions/NotConnectedException.cls.php");
include_once("XmlTrefferlisteParser.cls.php");

/**
 * Ein Objekt dieser Klasse stellt ein JPPClient dar, der sich mit dem JPPServer
 * verbindet, der in der config.inc.php eingetragen ist.
 */
class JPPClient {
  
  /** Enthaelt die SocketVerbindung zum JPPServer. */
  private $conn;
  
  /** Parser, der die empfangene Trefferliste in ein Trefferlisten-Objekt
   * umwandelt. 
   */
  private $parser;
  
  /** Gibt an, ob der Client mit dem JPPServer verbunden ist. */
  private $isConnected = false;
  
  /**
   * Erzeugt einen neuen JPPClient und verbindet sich direkt mit dem JPPServer.
   * 
   * @throws Exception, wenn keine Verbindung aufgebaut werden konnte
   */
  public function JPPClient() {
    // Zum Server verbinden
    $this->conn = new SocketVerbindung($_ENV["host"], $_ENV["port"]);
    
    // Parser erzeugen
    $this->parser = new XmlTrefferlisteParser();
    
    logge("Verbindung zum JPPServer aufgebaut.", INFO);
    
    // einloggen
    $this->conn->writeln($_ENV["username"]);
    $this->conn->writeln($_ENV["passwort"]);
    
    // Vom Server lesen
    $response = $this->conn->read();
    logge("Nachricht vom Server: " . $response , INFO);
    
    if (substr($response, 0, 2) == "OK") {
      $this->isConnected = true;
    } else {
      $this->isConnected = false;
    }
  }
  
  
  /**
   * Suche in allen importierten Bilder nach dem Suchtext und gibt eine
   * entsprechende Trefferliste mit den Suchergebnissen zurueck.
   * 
   * @param suchtext Suchtext, nach dem gesucht wird
   * @param offset Nummer des Bilddokumentes aller Treffer, ab der die Treffer
   *          in der Trefferliste aufgenommen werden sollen
   * @param maxanzahl Anzahl der Bilddokumente, die maximal in der Trefferliste
   *          aufgenommen werden sollen
   * @return Trefferliste mit den Suchergebnissen
   * @throws SucheException wird geworfen, wenn die Suche nicht erfolgreich mit
   *           einer erzeugten Trefferliste beendet werden kann
   */
  public function suche($suchtext, $offset, $maxanzahl) {
    if ($this->isConnected) {
      /* Text vorbearbeiten " in \" umwandeln */
      $suchtext = str_replace("\"", "\\\"", $suchtext);
      
      logge("Suche nach: " . $suchtext , INFO);
      
      $this->conn->writeln("suche $offset $maxanzahl $suchtext");

      // Vom Server die Trefferliste holen
      $result = $this->conn->read();
      //logge("Ergebnis der Suche: " . htmlentities(utf8_decode($result)) , INFO);
      
      // XML verarbeiten
      return $this->parser->parse($result);
    } else {
      throw new NotConnectedException("Es besteht keine Verbindung zum Server");
    }
  }


}


?>