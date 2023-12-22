/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.customerSection;

import java.awt.Color;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class MainMenu extends javax.swing.JFrame {

    double payment, remainingLoan, balance, transAmount, maturedAmount;
    int id, transactionID, depositNumberFD, savingsAccountNumber;
    String username, password, name = null, dateTime, details, openedDateFD, durationFD;
    Connection conn = null;
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/C.jpg"));

    /**
     * Creates new form SignIn
     */
    public MainMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
        conn = dbConfig.getConn();
        SignIn form = new SignIn();
        id = form.returnID();
        String query1 = "SELECT * FROM `users` WHERE `IndexNo` = ?";
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement(query1);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                name = rs.getString(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        uppernameLabel.setText("Welcome " + name);

        String query2 = "SELECT * FROM `contact_details` WHERE `ID` = ?";
        try {
            pst = conn.prepareStatement(query2);
            pst.setInt(1, 1);
            rs = pst.executeQuery();
            if (rs.next()) {
                hotlineField.setText(rs.getString(2));
                emailField.setText(rs.getString(3));
                websiteField.setText(rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query4 = "SELECT * FROM `interest_rates` WHERE `ID` = 1";
        try {
            pst = conn.prepareStatement(query4);
            rs = pst.executeQuery();
            if (rs.next()) {
                savingsInterestField.setText(rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query5 = "SELECT * FROM `loans` WHERE `UserIndex` = ?";
        try {
            pst = conn.prepareStatement(query5);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                loanStatusLabel.setVisible(true);
                loanNumberField.setText(String.valueOf(rs.getInt("LoanID")));

                if (rs.getString("Status").equals("Pending")) {
                    NoLoanNotificationLabel.setText("You have requested for a " + rs.getDouble("LoanValue") + " Rupee Loan");
                    cancelLoan.setVisible(true);
                    ApproveDisplayButton.setVisible(true);
                    ApproveDisplayButton.setText("Pending");
                    ApproveDisplayButton.setBackground(Color.yellow);

                }
                if (rs.getString("Status").equals("Approved")) {
                    NoLoanNotificationLabel.setVisible(false);
                    cancelLoan.setVisible(false);
                    ApproveDisplayButton.setVisible(true);
                    ApproveDisplayButton.setText("Approved");
                    ApproveDisplayButton.setBackground(Color.green);

                    initialLoanValueField.setText(String.valueOf(rs.getDouble("LoanValue")));
                    monthlyInstallmentField.setText(String.valueOf(rs.getDouble("MonthlyInstallment")));

                    remainingLoanField.setText(String.valueOf(rs.getDouble("RemainingLoan")));
                }
                if (rs.getString("Status").equals("Cancelled")) {
                    NoLoanNotificationLabel.setVisible(false);
                    cancelLoan.setVisible(false);
                    ApproveDisplayButton.setVisible(true);
                    ApproveDisplayButton.setText("Cancelled");
                    ApproveDisplayButton.setBackground(Color.red);
                }
            } else {
                loanStatusLabel.setVisible(false);
                ApproveDisplayButton.setVisible(false);
                cancelLoan.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query6 = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";
        try {
            pst = conn.prepareStatement(query6);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                savingsAccountNumber = rs.getInt("AccountNo");
                totalSavingsField.setText(String.valueOf(rs.getDouble("Balance")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        approvedButtonRemover();

        String query7 = "SELECT * FROM `transactions` WHERE `UserIndex` = ?";
        try {
            pst = conn.prepareStatement(query7);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                transAmount = rs.getDouble("Amount");
                transactionID = rs.getInt("TransactionID");
                dateTime = rs.getString("DateTime");

                if (rs.getString("Type").equals("Transfer")) {
                    details = "Transfered to " + rs.getString("FundsTo");
                }
                if (rs.getString("Type").equals("Receive")) {
                    details = "Received from " + rs.getString("FundsFrom");
                }
                if (rs.getString("Type").equals("Withdrawal")) {
                    details = "Withdrawal ";
                }
                if (rs.getString("Type").equals("Deposit")) {
                    details = "Deposited";
                }
                if (rs.getString("Type").equals("Payed Loan")) {
                    details = "Payed Loan";
                }
                String tableData1[] = {String.valueOf(transactionID), dateTime, details, String.valueOf(transAmount)};
                DefaultTableModel tableModel1 = (DefaultTableModel) accountStatementTable.getModel();
                tableModel1.insertRow(0, tableData1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query8 = "SELECT * FROM `fixeddeposits` WHERE `UserIndex` = ?";
        try {
            pst = conn.prepareStatement(query8);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                maturedAmount = rs.getDouble("MaturityAmount");
                depositNumberFD = rs.getInt("DepositNumber");
                openedDateFD = rs.getString("OpenedDate");
                durationFD = rs.getString("Duration");

                String tableData2[] = {openedDateFD, String.valueOf(depositNumberFD), String.valueOf(maturedAmount), durationFD};
                DefaultTableModel tableModel2 = (DefaultTableModel) fixedDepositTable.getModel();
                tableModel2.addRow(tableData2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query10 = "SELECT * FROM `fixeddeposits` WHERE `UserIndex` = ?";
        try {
            pst = conn.prepareStatement(query10);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (!rs.next()) {
                noFixedDepositLabel.setText("You have not opened any Fixed Deposits");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query9 = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";
        try {
            pst = conn.prepareStatement(query9);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                noSavingsLabel.setText("Account Number  :" + rs.getString("AccountNo"));
            } else {
                noSavingsLabel.setText("You have not registered for a Savings account.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        interestFDUpdater();
        interestLoanUpdater();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        logoutButton = new javax.swing.JButton();
        uppernameLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        myAssetsPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalSavingsField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        accountStatementTable = new javax.swing.JTable();
        transferButton = new javax.swing.JButton();
        depositButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fixedDepositTable = new javax.swing.JTable();
        noFixedDepositLabel = new javax.swing.JLabel();
        noSavingsLabel = new javax.swing.JLabel();
        withdrawButton = new javax.swing.JButton();
        liabilitiesPane = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        initialLoanValueField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        remainingLoanField = new javax.swing.JTextField();
        NoLoanNotificationLabel = new javax.swing.JLabel();
        payLoanButton = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        loanStatusLabel = new javax.swing.JLabel();
        cancelLoan = new javax.swing.JButton();
        ApproveDisplayButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        loanNumberField = new javax.swing.JTextField();
        monthlyInstallmentField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        nowPayingAmountField = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        featuresPane = new javax.swing.JPanel();
        savingsCreate = new javax.swing.JButton();
        closeSavings = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        savingsInterestField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        durationFDCombo = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        occasionFDCombo = new javax.swing.JComboBox<>();
        openFDButton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        intersetFDField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        durationLoanSpinner = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        loanInterestField = new javax.swing.JTextField();
        applyLoanButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        contactUsPane = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        hotlineField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        websiteField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        messageField = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CBank");
        setBackground(new java.awt.Color(51, 51, 51));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setIconImage(img.getImage());
        setResizable(false);

        logoutButton.setBackground(new java.awt.Color(255, 0, 0));
        logoutButton.setForeground(new java.awt.Color(0, 0, 0));
        logoutButton.setText("Log out");
        logoutButton.setBorder(null);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        uppernameLabel.setBackground(new java.awt.Color(51, 51, 51));
        uppernameLabel.setFont(new java.awt.Font("Bookman Old Style", 1, 14)); // NOI18N
        uppernameLabel.setForeground(new java.awt.Color(0, 0, 0));
        uppernameLabel.setText("Name");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(uppernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(uppernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(51, 51, 51));

        myAssetsPane.setBackground(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Savings Account");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 51, 0), new java.awt.Color(153, 153, 153), null, null));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total Account Balance   :");

        totalSavingsField.setEditable(false);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Account Statement        :");

        accountStatementTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        accountStatementTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Transaction ID", "Date", "Details", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountStatementTable.setToolTipText("");
        jScrollPane2.setViewportView(accountStatementTable);
        if (accountStatementTable.getColumnModel().getColumnCount() > 0) {
            accountStatementTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            accountStatementTable.getColumnModel().getColumn(1).setPreferredWidth(25);
            accountStatementTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        }

        transferButton.setBackground(new java.awt.Color(102, 255, 102));
        transferButton.setForeground(new java.awt.Color(0, 0, 0));
        transferButton.setText("Transfer Funds");
        transferButton.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        transferButton.setMaximumSize(new java.awt.Dimension(78, 20));
        transferButton.setMinimumSize(new java.awt.Dimension(78, 20));
        transferButton.setPreferredSize(new java.awt.Dimension(78, 20));
        transferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferButtonActionPerformed(evt);
            }
        });

        depositButton.setBackground(new java.awt.Color(51, 102, 255));
        depositButton.setForeground(new java.awt.Color(0, 0, 0));
        depositButton.setText("Deposit");
        depositButton.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        depositButton.setMaximumSize(new java.awt.Dimension(78, 20));
        depositButton.setMinimumSize(new java.awt.Dimension(78, 20));
        depositButton.setPreferredSize(new java.awt.Dimension(78, 20));
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Fixed Deposits");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 51, 0), new java.awt.Color(153, 153, 153), null, null));

        fixedDepositTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fixedDepositTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Opened Date", "Deposit Number", "Final Amount", "Duration"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(fixedDepositTable);

        noFixedDepositLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        noFixedDepositLabel.setForeground(new java.awt.Color(255, 255, 255));

        noSavingsLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        noSavingsLabel.setForeground(new java.awt.Color(255, 255, 255));

        withdrawButton.setBackground(new java.awt.Color(255, 51, 51));
        withdrawButton.setForeground(new java.awt.Color(0, 0, 0));
        withdrawButton.setText("Withdraw");
        withdrawButton.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        withdrawButton.setMaximumSize(new java.awt.Dimension(78, 20));
        withdrawButton.setMinimumSize(new java.awt.Dimension(78, 20));
        withdrawButton.setPreferredSize(new java.awt.Dimension(78, 20));
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout myAssetsPaneLayout = new javax.swing.GroupLayout(myAssetsPane);
        myAssetsPane.setLayout(myAssetsPaneLayout);
        myAssetsPaneLayout.setHorizontalGroup(
            myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myAssetsPaneLayout.createSequentialGroup()
                .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myAssetsPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(myAssetsPaneLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(noSavingsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(myAssetsPaneLayout.createSequentialGroup()
                                .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(myAssetsPaneLayout.createSequentialGroup()
                                        .addComponent(totalSavingsField, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)))
                            .addComponent(noFixedDepositLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, myAssetsPaneLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(transferButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(withdrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(depositButton, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        myAssetsPaneLayout.setVerticalGroup(
            myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myAssetsPaneLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noSavingsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalSavingsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(myAssetsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(depositButton, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(withdrawButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transferButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noFixedDepositLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("                   My Assets                  ", myAssetsPane);

        liabilitiesPane.setBackground(new java.awt.Color(51, 51, 51));
        liabilitiesPane.setForeground(new java.awt.Color(255, 255, 255));

        initialLoanValueField.setEditable(false);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Acquired Loan Value");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total Liabilites");

        remainingLoanField.setEditable(false);

        NoLoanNotificationLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NoLoanNotificationLabel.setForeground(new java.awt.Color(255, 255, 255));
        NoLoanNotificationLabel.setText("You have not applied or acquired any loans to display");

        payLoanButton.setBackground(new java.awt.Color(102, 255, 102));
        payLoanButton.setForeground(new java.awt.Color(0, 0, 0));
        payLoanButton.setText("Pay");
        payLoanButton.setBorder(null);
        payLoanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payLoanButtonActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Make an Installement");
        jLabel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 255, 0), new java.awt.Color(153, 153, 153), null, null));

        loanStatusLabel.setForeground(new java.awt.Color(255, 255, 255));
        loanStatusLabel.setText("Loan Status");

        cancelLoan.setBackground(new java.awt.Color(255, 0, 0));
        cancelLoan.setForeground(new java.awt.Color(0, 0, 0));
        cancelLoan.setText("Cancel Loan");
        cancelLoan.setBorder(null);
        cancelLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelLoanActionPerformed(evt);
            }
        });

        ApproveDisplayButton.setToolTipText("");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Loan Reference Number");

        loanNumberField.setEditable(false);

        monthlyInstallmentField.setEditable(false);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Monthly Installment");

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Amount");

        nowPayingAmountField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nowPayingAmountFieldKeyTyped(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("This Amount  will be deducted from your savings account");

        javax.swing.GroupLayout liabilitiesPaneLayout = new javax.swing.GroupLayout(liabilitiesPane);
        liabilitiesPane.setLayout(liabilitiesPaneLayout);
        liabilitiesPaneLayout.setHorizontalGroup(
            liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liabilitiesPaneLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(liabilitiesPaneLayout.createSequentialGroup()
                        .addComponent(NoLoanNotificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(117, 117, 117))
                    .addGroup(liabilitiesPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, liabilitiesPaneLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loanNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, liabilitiesPaneLayout.createSequentialGroup()
                                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nowPayingAmountField)
                                    .addComponent(monthlyInstallmentField, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                                    .addComponent(remainingLoanField)
                                    .addComponent(initialLoanValueField)
                                    .addComponent(payLoanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(liabilitiesPaneLayout.createSequentialGroup()
                                .addComponent(loanStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ApproveDisplayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(67, 67, 67))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, liabilitiesPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(204, 204, 204))
        );
        liabilitiesPaneLayout.setVerticalGroup(
            liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liabilitiesPaneLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(NoLoanNotificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loanStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(ApproveDisplayButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(cancelLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loanNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(initialLoanValueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthlyInstallmentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingLoanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(liabilitiesPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nowPayingAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(payLoanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        jTabbedPane1.addTab("                 Liabilities                 ", liabilitiesPane);

        featuresPane.setBackground(new java.awt.Color(51, 51, 51));

        savingsCreate.setBackground(new java.awt.Color(102, 255, 51));
        savingsCreate.setForeground(new java.awt.Color(0, 0, 0));
        savingsCreate.setText("Create Account");
        savingsCreate.setBorder(null);
        savingsCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savingsCreateActionPerformed(evt);
            }
        });

        closeSavings.setBackground(new java.awt.Color(255, 51, 51));
        closeSavings.setForeground(new java.awt.Color(0, 0, 0));
        closeSavings.setText("Close Account");
        closeSavings.setBorder(null);
        closeSavings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeSavingsActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Savings Accounts");
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 51, 51), new java.awt.Color(153, 153, 153), null, null));

        savingsInterestField.setEditable(false);
        savingsInterestField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Fixed Deposits");
        jLabel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 0), new java.awt.Color(153, 153, 153), null, null));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Duration");

        durationFDCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "6 Months", "1 Year", "2 Years", "5 Years", "10 Years" }));
        durationFDCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                durationFDComboItemStateChanged(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Intend to receive Income");

        occasionFDCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On Maturity", "Monthly", "Annually" }));
        occasionFDCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                occasionFDComboItemStateChanged(evt);
            }
        });

        openFDButton.setBackground(new java.awt.Color(51, 255, 51));
        openFDButton.setForeground(new java.awt.Color(0, 0, 0));
        openFDButton.setText("Open a Fixed Deposit");
        openFDButton.setBorder(null);
        openFDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFDButtonActionPerformed(evt);
            }
        });

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Interest Rate");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Loans");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 0), new java.awt.Color(153, 153, 153), null, null));

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Payback Duration");

        durationLoanSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 6, 1));
        durationLoanSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                durationLoanSpinnerStateChanged(evt);
            }
        });

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Years");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Interest ");

        loanInterestField.setEditable(false);

        applyLoanButton.setBackground(new java.awt.Color(51, 255, 51));
        applyLoanButton.setForeground(new java.awt.Color(0, 0, 0));
        applyLoanButton.setText("Apply for a loan");
        applyLoanButton.setBorder(null);
        applyLoanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyLoanButtonActionPerformed(evt);
            }
        });

        jLabel9.setText("Interest Rate");

        javax.swing.GroupLayout featuresPaneLayout = new javax.swing.GroupLayout(featuresPane);
        featuresPane.setLayout(featuresPaneLayout);
        featuresPaneLayout.setHorizontalGroup(
            featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featuresPaneLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(featuresPaneLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(featuresPaneLayout.createSequentialGroup()
                                .addGap(204, 204, 204)
                                .addComponent(closeSavings, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 84, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, featuresPaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(durationFDCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(featuresPaneLayout.createSequentialGroup()
                            .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(120, 120, 120)
                            .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(featuresPaneLayout.createSequentialGroup()
                                    .addComponent(durationLoanSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel20))
                                .addComponent(loanInterestField)))
                        .addGroup(featuresPaneLayout.createSequentialGroup()
                            .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(120, 120, 120)
                            .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(occasionFDCombo, 0, 317, Short.MAX_VALUE)
                                .addComponent(intersetFDField)))
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(featuresPaneLayout.createSequentialGroup()
                        .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(savingsCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(savingsInterestField, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(94, 94, 94))
            .addGroup(featuresPaneLayout.createSequentialGroup()
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(featuresPaneLayout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(openFDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(featuresPaneLayout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(applyLoanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        featuresPaneLayout.setVerticalGroup(
            featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featuresPaneLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(savingsInterestField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(featuresPaneLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(savingsCreate, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(closeSavings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(durationFDCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(occasionFDCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intersetFDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openFDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(durationLoanSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(featuresPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loanInterestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(applyLoanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("                     Features                    ", featuresPane);

        contactUsPane.setBackground(new java.awt.Color(51, 51, 51));

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Hot Line");

        hotlineField.setEditable(false);

        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Email");

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Web site");

        emailField.setEditable(false);

        websiteField.setEditable(false);

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Message");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("If you have anything to clarify, feel free to contact us via this web site. (Typically contacts you within 24 Hours)");

        sendButton.setBackground(new java.awt.Color(51, 255, 51));
        sendButton.setForeground(new java.awt.Color(51, 51, 51));
        sendButton.setText("Send");
        sendButton.setBorder(null);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contactUsPaneLayout = new javax.swing.GroupLayout(contactUsPane);
        contactUsPane.setLayout(contactUsPaneLayout);
        contactUsPaneLayout.setHorizontalGroup(
            contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactUsPaneLayout.createSequentialGroup()
                .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contactUsPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(contactUsPaneLayout.createSequentialGroup()
                                .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(hotlineField)
                                    .addComponent(emailField)
                                    .addComponent(websiteField)
                                    .addComponent(messageField, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)))))
                    .addGroup(contactUsPaneLayout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        contactUsPaneLayout.setVerticalGroup(
            contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactUsPaneLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hotlineField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(websiteField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(contactUsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("             Contact us              ", contactUsPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("TabbedPane");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
        SignIn form = new SignIn();
        form.setVisible(true);
        form.pack();
        this.dispose();
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositButtonActionPerformed
        // TODO add your handling code here:
        if (savingsAccountChecker()) {
            Deposit form = new Deposit();
            form.setVisible(true);
            form.pack();
            this.dispose();
        }
    }//GEN-LAST:event_depositButtonActionPerformed

    private void closeSavingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeSavingsActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        ResultSet rs;

        String query = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";

        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                ClosingAccount form = new ClosingAccount();
                form.setVisible(true);
                form.pack();
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "You don't have an account to delete.", "Eror", 3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_closeSavingsActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        String query3 = "INSERT INTO `user_messages` ( `UserIndex`, `Date`, `Message`) VALUES (?, ?, ?);";
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement(query3);
            pst.setInt(1, id);
            pst.setString(2, CurrentDateTime.time());
            pst.setString(3, messageField.getText());
            if (pst.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Message recieved. Thank you for your cooperation", "Success", 1);
                messageField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Something went wrong.\nPlease try again later", "Eror", 3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void savingsCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savingsCreateActionPerformed
        // TODO add your handling code here:
        if (!duplicateAccountChecker()) {
            CreateSavingsAccount form = new CreateSavingsAccount();
            form.setVisible(true);
            form.pack();
            this.dispose();
        }
    }//GEN-LAST:event_savingsCreateActionPerformed

    private void openFDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFDButtonActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        ResultSet rs;

        String query1 = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";

        try {
            st = conn.prepareStatement(query1);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "You must have a Savings account to open a Fixed Deposit", "Message", 3);
            } else {
                OpenFD form = new OpenFD();
                form.setVisible(true);
                form.pack();
                this.dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openFDButtonActionPerformed

    private void applyLoanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyLoanButtonActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        ResultSet rs;

        String query1 = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";

        try {
            st = conn.prepareStatement(query1);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "You must have a Savings account to open a Fixed Deposit", "Message", 3);
            } else {
                String query2 = "DELETE FROM `loans` WHERE `loans`.`UserIndex` = ? AND `loans`.`Status` = 'Cancelled'";

                try {
                    st = conn.prepareStatement(query2);
                    st.setInt(1, id);
                    st.executeUpdate();

                    String query3 = "SELECT * FROM `loans` WHERE `UserIndex` = ? ";

                    try {
                        st = conn.prepareStatement(query3);
                        st.setInt(1, id);
                        rs = st.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "You have already sent an request or acquired a loan.\nAn User can only apply for one loan", "Eror", 2);
                        } else {
                            ApplyLoan form = new ApplyLoan();
                            form.setVisible(true);
                            form.pack();
                            this.dispose();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_applyLoanButtonActionPerformed

    private void cancelLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelLoanActionPerformed
        // TODO add your handling code here:
        String query = "DELETE FROM `loans` WHERE `loans`.`UserIndex` = ?";
        PreparedStatement st;

        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            if (st.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Your application for the loan have discarded", "Message", 2);
            } else {
                JOptionPane.showMessageDialog(null, "You haven't sent any application for a loan to cancel", "Message", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cancelLoanActionPerformed

    private void payLoanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payLoanButtonActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        if (savingsAccountChecker() && loanChecker()) {
            try {
                payment = Double.valueOf(nowPayingAmountField.getText());
                remainingLoan = Double.valueOf(remainingLoanField.getText());
                remainingLoan = remainingLoan - payment;
                balance = Double.valueOf(totalSavingsField.getText()) - payment;
            } catch (NumberFormatException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            String query1 = "UPDATE `loans` SET `RemainingLoan` = ? WHERE `loans`.`UserIndex` = ?";

            try {
                st = conn.prepareStatement(query1);
                st.setDouble(1, remainingLoan);
                st.setInt(2, id);

                if (st.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(null, "You have payed back Rs." + payment + " from your savings account", "Message", 1);
                    remainingLoanField.setText(String.valueOf(remainingLoan));
                    nowPayingAmountField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong", "Eror", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query2 = "UPDATE `savingsaccounts` SET `Balance` = ? WHERE `savingsaccounts`.`UserIndex` = ?";

            try {
                st = conn.prepareStatement(query2);
                st.setDouble(1, balance);
                st.setInt(2, id);

                if (st.executeUpdate() == 1) {
                    totalSavingsField.setText(String.valueOf(balance));
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong", "Eror", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }

            ResultSet rs;
            String query3 = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";
            try {
                st = conn.prepareStatement(query3);
                st.setInt(1, id);
                rs = st.executeQuery();
                if (rs.next()) {
                    savingsAccountNumber = rs.getInt("AccountNo");
                }

            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query4 = "INSERT INTO `transactions` ( `UserIndex`, `DateTime`, `Type`, `FundsFrom`, `FundsTo`, `Amount`) VALUES ( ?, ?, 'Payed Loan', ?, 'Bank', ?)";
            try {
                st = conn.prepareStatement(query4);
                st.setInt(1, id);
                st.setString(2, CurrentDateTime.time());
                st.setString(3, String.valueOf(savingsAccountNumber));
                st.setDouble(4, payment);
                if (st.executeUpdate() == 1) {
                    totalSavingsField.setText(String.valueOf(balance));
                    this.dispose();
                    MainMenu form = new MainMenu();
                    form.setVisible(true);
                    form.pack();
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong", "Eror", 2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_payLoanButtonActionPerformed

    private void nowPayingAmountFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nowPayingAmountFieldKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_nowPayingAmountFieldKeyTyped

    private void transferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferButtonActionPerformed
        // TODO add your handling code here:
        if (savingsAccountChecker()) {
            TransferFunds form = new TransferFunds();
            form.setVisible(true);
            form.pack();
            this.dispose();
        }
    }//GEN-LAST:event_transferButtonActionPerformed

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawButtonActionPerformed
        // TODO add your handling code here:
        if (savingsAccountChecker()) {
            Withdraw form = new Withdraw();
            form.setVisible(true);
            form.pack();
            this.dispose();
        }
    }//GEN-LAST:event_withdrawButtonActionPerformed

    private void durationFDComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_durationFDComboItemStateChanged
        // TODO add your handling code here:
        interestFDUpdater();
    }//GEN-LAST:event_durationFDComboItemStateChanged

    private void occasionFDComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_occasionFDComboItemStateChanged
        // TODO add your handling code here:
        interestFDUpdater();
    }//GEN-LAST:event_occasionFDComboItemStateChanged

    private void durationLoanSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_durationLoanSpinnerStateChanged
        // TODO add your handling code here:
        interestLoanUpdater();
    }//GEN-LAST:event_durationLoanSpinnerStateChanged

    public boolean loanChecker() {
        PreparedStatement st;
        ResultSet rs;
        boolean username_exist = true;

        String query = "SELECT * FROM `loans` WHERE `UserIndex` = ? AND `Status` = 'Approved'";

        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "You haven't applied or approved for any loan ", "Eror", 2);
                username_exist = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username_exist;
    }

    public boolean savingsAccountChecker() {
        PreparedStatement st;
        ResultSet rs;
        boolean username_exist = true;

        String query = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";

        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "You need a Savings account to proceed this operation", "Eror", 2);
                username_exist = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username_exist;
    }

    public boolean duplicateAccountChecker() {
        PreparedStatement st;
        ResultSet rs;
        boolean username_exist = false;

        String query = "SELECT * FROM `savingsaccounts` WHERE `UserIndex` = ?";

        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "You have already opened a savings account.\nAn User can only register for one savings account", "Eror", 2);
                username_exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return username_exist;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void approvedButtonRemover() {
        if (!initialLoanValueField.getText().equals(remainingLoanField.getText())) {
            ApproveDisplayButton.setVisible(false);
            loanStatusLabel.setVisible(false);
        }
    }

    public void interestFDUpdater() {
        String query5 = "SELECT * FROM `interest_rates` WHERE `Duration` = ? AND `Occasion` = ?";
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement(query5);
            pst.setString(1, (String) durationFDCombo.getSelectedItem());
            pst.setString(2, (String) occasionFDCombo.getSelectedItem());
            rs = pst.executeQuery();
            if (rs.next()) {
                intersetFDField.setText(rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void interestLoanUpdater() {
        String query5 = "SELECT * FROM `interest_rates` WHERE `Duration` = ?";
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = conn.prepareStatement(query5);
            pst.setString(1, String.valueOf(durationLoanSpinner.getValue()));
            rs = pst.executeQuery();
            if (rs.next()) {
                loanInterestField.setText(rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApproveDisplayButton;
    private javax.swing.JLabel NoLoanNotificationLabel;
    private javax.swing.JTable accountStatementTable;
    private javax.swing.JButton applyLoanButton;
    private javax.swing.JButton cancelLoan;
    private javax.swing.JButton closeSavings;
    private javax.swing.JPanel contactUsPane;
    private javax.swing.JButton depositButton;
    private javax.swing.JComboBox<String> durationFDCombo;
    private javax.swing.JSpinner durationLoanSpinner;
    private javax.swing.JTextField emailField;
    private javax.swing.JPanel featuresPane;
    private javax.swing.JTable fixedDepositTable;
    private javax.swing.JTextField hotlineField;
    private javax.swing.JTextField initialLoanValueField;
    private javax.swing.JTextField intersetFDField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel liabilitiesPane;
    private javax.swing.JTextField loanInterestField;
    private javax.swing.JTextField loanNumberField;
    private javax.swing.JLabel loanStatusLabel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField messageField;
    private javax.swing.JTextField monthlyInstallmentField;
    private javax.swing.JPanel myAssetsPane;
    private javax.swing.JLabel noFixedDepositLabel;
    private javax.swing.JLabel noSavingsLabel;
    private javax.swing.JTextField nowPayingAmountField;
    private javax.swing.JComboBox<String> occasionFDCombo;
    private javax.swing.JButton openFDButton;
    private javax.swing.JButton payLoanButton;
    private javax.swing.JTextField remainingLoanField;
    private javax.swing.JButton savingsCreate;
    private javax.swing.JTextField savingsInterestField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField totalSavingsField;
    private javax.swing.JButton transferButton;
    private javax.swing.JLabel uppernameLabel;
    private javax.swing.JTextField websiteField;
    private javax.swing.JButton withdrawButton;
    // End of variables declaration//GEN-END:variables
}

//                    LocalDate today = LocalDate.now();
//                    LocalDate approvedDate = convertToLocalDateViaInstant(rs.getDate("ApprovedDate"));
//                    Period period = Period.between(approvedDate, today);
//                    if (String.valueOf(period.getDays()).equals("30")) {
//
//                    }
 //                   arrearsField.setText(String.valueOf(rs.getDouble("Arears")));
