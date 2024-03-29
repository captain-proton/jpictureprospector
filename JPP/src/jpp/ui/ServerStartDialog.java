/*
 * ServerStartDialog.java
 *
 * Created on 13. Dezember 2007, 22:30
 */

package jpp.ui;

import jpp.settings.ServerSettings;

/**
 * @author manfred
 */
public class ServerStartDialog extends javax.swing.JDialog {

  private ServerSettings settings;

  private boolean isOk = false;


  /** Creates new form ServerStartDialog */
  public ServerStartDialog(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
  }

  public void setServerSettings(ServerSettings settings) {
    this.settings = settings;
    this.tfDataDir.setText(settings.jppDataDir);
    this.tfDataUrl.setText(settings.jppDataURL);
  }

  private void takeSettings() {
    this.settings.jppDataDir = this.tfDataDir.getText();
    this.settings.jppDataURL = this.tfDataUrl.getText();
  }

  public ServerSettings getServerSettings() {
    return settings;
  }

  public boolean isOk() {
    return isOk;
  }
  

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    tfDataDir = new javax.swing.JTextField();
    tfDataUrl = new javax.swing.JTextField();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("JPPServer");

    jLabel1.setText("JppDataDir");

    jLabel2.setText("URL");

    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    jButton2.setText("Abbrechen");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
        getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(
        javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        layout.createSequentialGroup().addGroup(
            layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addGap(98, 98, 98).addComponent(
                    jButton1).addGap(6, 6, 6).addComponent(jButton2)).addGroup(
                layout.createSequentialGroup().addGap(29, 29, 29).addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1).addComponent(jLabel2)).addGap(
                    30, 30, 30).addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfDataUrl).addComponent(tfDataDir,
                            javax.swing.GroupLayout.DEFAULT_SIZE, 141,
                            Short.MAX_VALUE)))).addContainerGap(143,
            Short.MAX_VALUE)));
    layout.setVerticalGroup(layout.createParallelGroup(
        javax.swing.GroupLayout.Alignment.LEADING).addGroup(
        javax.swing.GroupLayout.Alignment.TRAILING,
        layout.createSequentialGroup().addGap(42, 42, 42).addGroup(
            layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                jLabel1).addComponent(tfDataDir,
                javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
            layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                jLabel2).addComponent(tfDataUrl,
                javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
            javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180,
            Short.MAX_VALUE).addGroup(
            layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                jButton1).addComponent(jButton2)).addContainerGap()));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
    this.isOk = true;
    this.takeSettings();
    this.setVisible(false);
  }// GEN-LAST:event_jButton1ActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
    this.isOk = false;
    this.setVisible(false);
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        ServerStartDialog dialog = new ServerStartDialog(
            new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;

  private javax.swing.JButton jButton2;

  private javax.swing.JLabel jLabel1;

  private javax.swing.JLabel jLabel2;

  private javax.swing.JTextField tfDataDir;

  private javax.swing.JTextField tfDataUrl;
  // End of variables declaration//GEN-END:variables

}
