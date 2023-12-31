/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.customerSection;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public class TransferFunds extends javax.swing.JFrame {

    static int id;
    double amount, balance, beneBalance;
    int beneficiaryAccountNumber, savingsAccountNumber, beneUserIndex;
    Connection conn = null;
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/C.jpg"));

    /**
     * Creates new form SignIn
     */
    public TransferFunds() {
        initComponents();
        this.setLocationRelativeTo(null);
        conn = dbConfig.getConn();
        SignIn form = new SignIn();
        id = form.returnID();
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
        jLabel1 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        transferButton = new javax.swing.JButton();
        beneAccountNumberField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CBank");
        setIconImage(img.getImage());
        setName("JFrame1"); // NOI18N
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Beneficiary Account Number");

        backButton.setBackground(new java.awt.Color(255, 51, 51));
        backButton.setForeground(new java.awt.Color(0, 0, 0));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        transferButton.setBackground(new java.awt.Color(102, 255, 102));
        transferButton.setForeground(new java.awt.Color(0, 0, 0));
        transferButton.setText("Transfer");
        transferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferButtonActionPerformed(evt);
            }
        });

        beneAccountNumberField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                beneAccountNumberFieldKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Amount");

        amountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldActionPerformed(evt);
            }
        });
        amountField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                amountFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(transferButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(beneAccountNumberField)
                            .addComponent(amountField, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(beneAccountNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(transferButton))
                .addGap(82, 82, 82))
        );

        getContentPane().add(jPanel1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void transferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferButtonActionPerformed
        // TODO add your handling code here:
        ResultSet rs;
        PreparedStatement pst;
        if (!beneAccountNumberField.getText().equals("") && !amountField.getText().equals("")) {
            try {
                beneficiaryAccountNumber = Integer.valueOf(beneAccountNumberField.getText());
                amount = Double.valueOf(amountField.getText());
            } catch (NumberFormatException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                String query2 = "SELECT * FROM `savingsaccounts` WHERE `AccountNo` = ?";
                pst = conn.prepareStatement(query2);
                pst.setInt(1, beneficiaryAccountNumber);
                rs = pst.executeQuery();
                if (rs.next()) {
                    beneBalance = rs.getDouble("Balance");
                    beneUserIndex = rs.getInt("UserIndex");
                    beneBalance = beneBalance + amount;

                    try {
                        String query1 = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";
                        pst = conn.prepareStatement(query1);
                        pst.setInt(1, id);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            savingsAccountNumber = rs.getInt("AccountNo");
                            balance = rs.getDouble("Balance");
                            if (balance <= amount) {
                                JOptionPane.showMessageDialog(null, "Your account balance is insuffiecient for this transaction", "Eror", 1);
                            } else {
                                balance = balance - amount;

                                try {
                                    PreparedStatement pst1, pst2, pst3, pst4;

                                    String query3 = "INSERT INTO `transactions` ( `UserIndex`, `DateTime`, `Type`, `FundsFrom`, `FundsTo`, `Amount`) VALUES ( ?, ?, 'Transfer' , ?, ?, ?)";
                                    pst1 = conn.prepareStatement(query3);
                                    pst1.setInt(1, id);
                                    pst1.setString(2, CurrentDateTime.time());
                                    pst1.setString(3, String.valueOf(savingsAccountNumber));
                                    pst1.setString(4, beneAccountNumberField.getText());
                                    pst1.setDouble(5, amount);

                                    String query4 = "INSERT INTO `transactions` ( `UserIndex`, `DateTime`, `Type`, `FundsFrom`, `FundsTo`, `Amount`) VALUES ( ?, ?, 'Receive' , ?, ?, ?)";
                                    pst2 = conn.prepareStatement(query4);
                                    pst2.setInt(1, beneUserIndex);
                                    pst2.setString(2, CurrentDateTime.time());
                                    pst2.setString(3, String.valueOf(savingsAccountNumber));
                                    pst2.setString(4, beneAccountNumberField.getText());
                                    pst2.setDouble(5, amount);

                                    String query5 = "UPDATE `savingsaccounts` SET `Balance` = ? WHERE `savingsaccounts`.`AccountNo` = ?";
                                    pst3 = conn.prepareStatement(query5);
                                    pst3.setDouble(1, beneBalance);
                                    pst3.setInt(2, beneficiaryAccountNumber);

                                    pst4 = conn.prepareStatement(query5);
                                    pst4.setDouble(1, balance);
                                    pst4.setInt(2, savingsAccountNumber);

                                    if ((pst1.executeUpdate() * pst2.executeUpdate() * pst3.executeUpdate() * pst4.executeUpdate()) == 1) {

                                        JOptionPane.showMessageDialog(null, "Transaction Successful", "Message", 1);
                                    } else {

                                        JOptionPane.showMessageDialog(null, "Transaction Unsuccessful", "Warning", 2);
                                    }

                                    MainMenu form = new MainMenu();
                                    form.setVisible(true);
                                    form.pack();
                                    this.dispose();
                                } catch (SQLException ex) {
                                    Logger.getLogger(TransferFunds.class.getName()).log(Level.SEVERE, null, ex);

                                }
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "The Account number does not exist.\nTransaction Unsuccessful.", "Warning", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "One or more fields are empty", "Eror", 1);
        }


    }//GEN-LAST:event_transferButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        MainMenu form = new MainMenu();
        form.setVisible(true);
        form.pack();
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void beneAccountNumberFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beneAccountNumberFieldKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_beneAccountNumberFieldKeyTyped

    private void amountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldActionPerformed

    }//GEN-LAST:event_amountFieldActionPerformed

    private void amountFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountFieldKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_amountFieldKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransferFunds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransferFunds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransferFunds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransferFunds.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransferFunds().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amountField;
    private javax.swing.JButton backButton;
    private javax.swing.JTextField beneAccountNumberField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton transferButton;
    // End of variables declaration//GEN-END:variables
}
