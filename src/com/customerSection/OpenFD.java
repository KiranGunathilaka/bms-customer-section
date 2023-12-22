/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.customerSection;

import com.mysql.cj.util.StringUtils;
import java.sql.*;
import javax.swing.ImageIcon;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author User
 */
public class OpenFD extends javax.swing.JFrame {

    boolean depositChecker;
    double deposit, maturedDeposit = 0;
    int id;
    String duration, occasion, interestRate;
    Connection conn = null;
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/C.jpg"));

    /**
     * Creates new form SignIn
     */
    public OpenFD() {
        initComponents();
        this.setLocationRelativeTo(null);
        conn = dbConfig.getConn();
        SignIn form = new SignIn();
        id = form.returnID();

        String query1 = "select *from `fixeddeposits` ORDER BY `DepositNumber` DESC LIMIT 1";
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement(query1);
            rs = pst.executeQuery();
            if (rs.next()) {
                depositFDNumberField.setText(String.valueOf(rs.getInt(1) + 1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OpenFD.class.getName()).log(Level.SEVERE, null, ex);
        }

        interestUpdater();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        openFDButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        depositFDNumberField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        durationFDCombo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        maturityAmountField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        interestRateField = new javax.swing.JTextField();
        occasionFDCombo = new javax.swing.JComboBox<>();
        depositAmountField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CBank");
        setIconImage(img.getImage()
        );
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        backButton.setBackground(new java.awt.Color(255, 51, 51));
        backButton.setForeground(new java.awt.Color(0, 0, 0));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        openFDButton.setBackground(new java.awt.Color(102, 255, 102));
        openFDButton.setForeground(new java.awt.Color(0, 0, 0));
        openFDButton.setText("Deposit and Open");
        openFDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFDButtonActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Duration");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Deposit Amount");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Interest withdrawing");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Deposit Number");

        depositFDNumberField.setEditable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Opening a Fixed Deposit");

        durationFDCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "6 Months", "1 Year", "2 Years", "5 Years", "10 Years" }));
        durationFDCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                durationFDComboItemStateChanged(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Maturity Amount");

        maturityAmountField.setEditable(false);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Interest Rate");

        interestRateField.setEditable(false);

        occasionFDCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On Maturity", "Monthly", "Annually" }));
        occasionFDCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                occasionFDComboItemStateChanged(evt);
            }
        });

        depositAmountField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                depositAmountFieldFocusLost(evt);
            }
        });
        depositAmountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositAmountFieldActionPerformed(evt);
            }
        });
        depositAmountField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                depositAmountFieldPropertyChange(evt);
            }
        });
        depositAmountField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                depositAmountFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(154, 154, 154)
                                                .addComponent(openFDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(durationFDCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(interestRateField)
                                                    .addComponent(occasionFDCombo, 0, 270, Short.MAX_VALUE)
                                                    .addComponent(depositAmountField)
                                                    .addComponent(maturityAmountField, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                                    .addComponent(depositFDNumberField, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))))
                                        .addGap(0, 35, Short.MAX_VALUE))))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(depositAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(durationFDCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(occasionFDCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(interestRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(maturityAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(depositFDNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(openFDButton))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFDButtonActionPerformed
        // TODO add your handling code here:
        occasion = String.valueOf(occasionFDCombo.getSelectedItem());
        duration = String.valueOf(durationFDCombo.getSelectedItem());

        try {
            deposit = Double.valueOf(depositAmountField.getText());
        } catch (NumberFormatException e) {
        }

        if (depositChecker(deposit)) {

            String query = "INSERT INTO `fixeddeposits` ( `UserIndex`, `Amount`, `MaturityAmount`, `Duration`,`Occasion`,`OpenedDate`) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst;

            try {
                pst = conn.prepareStatement(query);
                pst.setInt(1, id);
                pst.setDouble(2, deposit);
                pst.setDouble(3, maturedDeposit);
                pst.setString(4, duration);
                pst.setString(5, occasion);
                pst.setString(6, CurrentDateTime.time());
                if (pst.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Your Fixed Deposit has been opened.");
                    MainMenu form = new MainMenu();
                    form.setVisible(true);
                    form.repaint();
                    form.pack();

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong.\nPlease check your information and try again", "Eror", 3);
                }

            } catch (SQLException ex) {
                Logger.getLogger(OpenFD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_openFDButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:   
        MainMenu form = new MainMenu();
        form.setVisible(true);
        form.pack();
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void durationFDComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_durationFDComboItemStateChanged
        // TODO add your handling code here:
        interestUpdater();
        if (durationFDCombo.getSelectedItem().equals("6 Months") && occasionFDCombo.getSelectedItem().equals("Annually")) {
            maturityAmountField.setText("Not Valid");
        } else {
            depositUpdater();
        }
    }//GEN-LAST:event_durationFDComboItemStateChanged

    private void occasionFDComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_occasionFDComboItemStateChanged
        // TODO add your handling code here:
        interestUpdater();
        if (durationFDCombo.getSelectedItem().equals("6 Months") && occasionFDCombo.getSelectedItem().equals("Annually")) {
            maturityAmountField.setText("Not Valid");
        } else {
            depositUpdater();
        }
    }//GEN-LAST:event_occasionFDComboItemStateChanged

    private void depositAmountFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_depositAmountFieldKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_depositAmountFieldKeyTyped

    private void depositAmountFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_depositAmountFieldPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_depositAmountFieldPropertyChange

    private void depositAmountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositAmountFieldActionPerformed
        // TODO add your handling code here:
        if (durationFDCombo.getSelectedItem().equals("6 Months") && occasionFDCombo.getSelectedItem().equals("Annually")) {
            maturityAmountField.setText("Not Valid");
        } else {
            depositUpdater();
        }
    }//GEN-LAST:event_depositAmountFieldActionPerformed

    private void depositAmountFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_depositAmountFieldFocusLost
        // TODO add your handling code here:
        if (durationFDCombo.getSelectedItem().equals("6 Months") && occasionFDCombo.getSelectedItem().equals("Annually")) {
            maturityAmountField.setText("Not Valid");
        } else {
            depositUpdater();
        }
    }//GEN-LAST:event_depositAmountFieldFocusLost

    public boolean depositChecker(double deposit) {
        if (depositAmountField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Deposit field is empty", "Warning", 0);
            depositChecker = false;
        } else {
            if (this.deposit <= 50000) {
                JOptionPane.showMessageDialog(null, "Deposit amount must be greater than 50000 Rupees", "Message", 2);
                depositChecker = false;

            } else {
                depositChecker = true;
            }

        }
        return depositChecker;
    }

    public String interestUpdater() {
        String query2 = "SELECT * FROM `interest_rates` WHERE `Duration` = ? AND `Occasion` = ?";
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement(query2);
            pst.setString(1, (String) durationFDCombo.getSelectedItem());
            pst.setString(2, (String) occasionFDCombo.getSelectedItem());
            rs = pst.executeQuery();
            if (rs.next()) {
                interestRateField.setText(rs.getString(5));
                interestRate = interestRateField.getText();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OpenFD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return interestRate;
    }

    public Double depositUpdater() {
        try {
            deposit = Double.valueOf(depositAmountField.getText());
        } catch (NumberFormatException e) {
        }
        if (!depositAmountField.getText().equals("") && !(deposit <= 50000)) {
            //converting string interestRate to a double. Never fucking use escape sequence again.

            String interest = interestRateField.getText();
            StringBuffer sb = new StringBuffer(interest);
            sb.deleteCharAt(sb.length() - 1);
            String interestRateFinal = sb.toString();
            maturedDeposit = deposit * (Double.valueOf(interestRateFinal) + 100) / 100;
            maturityAmountField.setText(String.valueOf(maturedDeposit));
        }
        return maturedDeposit;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpenFD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JTextField depositAmountField;
    private javax.swing.JTextField depositFDNumberField;
    private javax.swing.JComboBox<String> durationFDCombo;
    private javax.swing.JTextField interestRateField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField maturityAmountField;
    private javax.swing.JComboBox<String> occasionFDCombo;
    private javax.swing.JButton openFDButton;
    // End of variables declaration//GEN-END:variables
}
