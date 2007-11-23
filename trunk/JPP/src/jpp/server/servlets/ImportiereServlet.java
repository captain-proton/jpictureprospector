package jpp.server.servlets;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jpp.core.Einstellungen;
import jpp.core.JPPCore;
import jpp.core.exceptions.ImportException;
import benutzermanager.Benutzer;
import benutzermanager.RechteManager;

import com.oreilly.servlet.MultipartRequest;


public class ImportiereServlet extends HttpServlet {

  private static final long serialVersionUID = 7017607629370760883L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter out = resp.getWriter(); 
    out.println("Hallo? Bist du sicher, dass du hier richtig bist?");
  }
  
  
  

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {


    PrintWriter out = resp.getWriter();

    HttpSession session = req.getSession();

    Benutzer user = (Benutzer) session.getAttribute("user");

    if (user == null || !user.hatRecht(RechteManager.getRecht("importiere"))) {
      out.println("Sie haben nicht das Recht ein Bild zu importieren.");
    } else {

      JPPCore kern = (JPPCore) getServletContext().getAttribute("JPPCore");

      if (kern == null) {
        out.println("JPPCore ist nicht vorhanden. Es ist vermutlich beim start"
            + "ein Fehler aufgetreten. Überprüfen Sie die Logfiles.");
      } else {
        importiere(req, resp, kern);
      }
    }

  }

  private void importiere(HttpServletRequest req, HttpServletResponse resp, 
      JPPCore kern) throws ServletException, IOException {
    
    PrintWriter out = resp.getWriter(); 
        
    MultipartRequest multi = new MultipartRequest( req, Einstellungen.uploadOrdner ); 
    Enumeration files = multi.getFileNames(); 
    while ( files.hasMoreElements() ) 
    { 
      String name = (String) files.nextElement(); 
      File f = multi.getFile(name);
      if (f != null) {
        try {
          kern.importiere(f.toURL());
          out.println("Bild \"" + f.getName() + "\" wurde erfolgreich importiert.");
        } catch (ImportException e) {
          out.println("Bild \"" + f.getName() + "\" konnte nicht importiert werden: " + e);
        }
      }
    }
    
    
  }


}