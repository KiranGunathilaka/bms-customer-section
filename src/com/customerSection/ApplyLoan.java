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
public class ApplyLoan extends javax.swing.JFrame {

    boolean depositChecker;
    double loanAmount, monthlyInstallment = 0;
    int id;
    String duration;
    Connection conn = null;
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/C.jpg"));

    /**
     * Creates new form SignIn
     */
    public ApplyLoan() {
        initComponents();
        this.setLocationRelativeTo(null);
        conn = dbConfig.getConn();
        SignIn form = new SignIn();
        id = form.returnID();

        String query1 = "select *from `loans` ORDER BY `LoanID` DESC LIMIT 1";
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement(query1);
            rs = pst.executeQuery();
            if (rs.next()) {
                loanNumberField.setText(String.valueOf(rs.getInt("LoanID") + 1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApplyLoan.class.getName()).log(Level.SEVERE, null, ex);
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
        applyLoanButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        loanNumberField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        monthlyInstallmentField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        interestRateField = new javax.swing.JTextField();
        loanAmountField = new javax.swing.JTextField();
        durationLoanSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();

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

        applyLoanButton.setBackground(new java.awt.Color(102, 255, 102));
        applyLoanButton.setForeground(new java.awt.Color(0, 0, 0));
        applyLoanButton.setText("Apply");
        applyLoanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyLoanButtonActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Duration");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Required Amount");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Loan Reference Number");

        loanNumberField.setEditable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Applying for a Loan");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Monthly Installment");

        monthlyInstallmentField.setEditable(false);
        monthlyInstallmentField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyInstallmentFieldActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Interest Rate");

        interestRateField.setEditable(false);

        loanAmountField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                loanAmountFieldFocusLost(evt);
            }
        });
        loanAmountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanAmountFieldActionPerformed(evt);
            }
        });
        loanAmountField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                loanAmountFieldPropertyChange(evt);
            }
        });
        loanAmountField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                loanAmountFieldKeyTyped(evt);
            }
        });

        durationLoanSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 6, 1));
        durationLoanSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                durationLoanSpinnerStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Years");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(applyLoanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(interestRateField, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loanAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthlyInstallmentField, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loanNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(durationLoanSpinner)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)))))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loanAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(durationLoanSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(interestRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthlyInstallmentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loanNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(123, 123, 123)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(applyLoanButton))
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

    private void applyLoanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyLoanButtonActionPerformed
        // TODO add your handling code here:

        duration = String.valueOf(durationLoanSpinner.getValue());

        try {
            loanAmount = Double.valueOf(loanAmountField.getText());
        } catch (NumberFormatException e) {
        }

        if (loanAmountChecker(loanAmount)) {

            String query = "INSERT INTO `loans` (`UserIndex`, `LoanValue`, `Duration`, `MonthlyInstallment`, `Arears`, `RemainingLoan`) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst;

            try {
                pst = conn.prepareStatement(query);
                pst.setInt(1, id);
                pst.setDouble(2, loanAmount);
                pst.setString(3, duration);
                pst.setDouble(4, monthlyInstallment);
                pst.setDouble(5, 0);
                pst.setDouble(6, loanAmount);
                if (pst.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Your Request for the loan have been sent by the System.\nThe Bank will contact you within 2 days.\nAfter that you may need to wait for the Approval from the Bank.");
                    
                    MainMenu form = new MainMenu();
                    form.setVisible(true);
                    form.repaint();
                    form.pack();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong.\nPlease check your information and try again", "Eror", 3);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ApplyLoan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_applyLoanButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:      
        MainMenu form = new MainMenu();
        form.setVisible(true);
        form.pack();
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void loanAmountFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loanAmountFieldKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_loanAmountFieldKeyTyped

    private void loanAmountFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_loanAmountFieldPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_loanAmountFieldPropertyChange

    private void loanAmountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanAmountFieldActionPerformed
        // TODO add your handling code here:       
        monthlyInstallmentUpdater();
    }//GEN-LAST:event_loanAmountFieldActionPerformed

    private void durationLoanSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_durationLoanSpinnerStateChanged
        // TODO add your handling code here:
        interestUpdater();
        monthlyInstallmentUpdater();

    }//GEN-LAST:event_durationLoanSpinnerStateChanged

    private void loanAmountFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_loanAmountFieldFocusLost
        // TODO add your handling code here:
        monthlyInstallmentUpdater();
    }//GEN-LAST:event_loanAmountFieldFocusLost

    private void monthlyInstallmentFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyInstallmentFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthlyInstallmentFieldActionPerformed

    public boolean loanAmountChecker(double deposit) {
        if (loanAmountField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Required Loan Amount field is empty", "Warning", 0);
            depositChecker = false;
        } else {
            if (this.loanAmount <= 50000) {
                JOptionPane.showMessageDialog(null, "Loan amount must be greater than 100000 Rupees", "Message", 2);
                depositChecker = false;

            } else {
                depositChecker = true;
            }

        }
        return depositChecker;
    }

    public void interestUpdater() {
        String query2 = "SELECT * FROM `interest_rates` WHERE `Duration` = ?";
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement(query2);
            pst.setString(1, String.valueOf(durationLoanSpinner.getValue()));
            rs = pst.executeQuery();
            if (rs.next()) {
                interestRateField.setText(rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApplyLoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Double monthlyInstallmentUpdater() {
        try {
            loanAmount = Double.valueOf(loanAmountField.getText());
        } catch (NumberFormatException e) {
        }
        String years = String.valueOf(durationLoanSpinner.getValue());
        int yearsInt = Integer.valueOf(years);
        if (!loanAmountField.getText().equals("") && !(loanAmount <= 50000)) {
            //converting string interestRate to a double. Never fucking use escape sequence again.

            String interest = interestRateField.getText();
            StringBuffer sb = new StringBuffer(interest);
            sb.deleteCharAt(sb.length() - 1);
            String interestRateFinal = sb.toString();
            monthlyInstallment = (loanAmount * (Double.valueOf(interestRateFinal) * yearsInt + 100) / 100) / (12 * yearsInt);
            monthlyInstallmentField.setText(String.valueOf(monthlyInstallment));
        }
        return monthlyInstallment;
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
                new ApplyLoan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyLoanButton;
    private javax.swing.JButton backButton;
    private javax.swing.JSpinner durationLoanSpinner;
    private javax.swing.JTextField interestRateField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField loanAmountField;
    private javax.swing.JTextField loanNumberField;
    private javax.swing.JTextField monthlyInstallmentField;
    // End of variables declaration//GEN-END:variables
}