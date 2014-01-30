package guiSubsystem;

import brokerageControlSubsystem.BrokerageControl;
import javax.swing.JOptionPane;
import usersSubsystem.*;

/**
 * This Class is called by a method from the GUI class.  When this class is
 * called the window for a user to log into Laser Brokerage appears.  In this
 * window a user can Login as a Broker or Investor and the user can create
 * a new Investor if an Investor does not already have an account.
 * @author Thomas Wilson
 * @version 20121117 (TW)
 * @version 201211181615 (TS)
 * @version 201211211845 (TS)
 * @verison 201211220200 (TW)
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newInvestorFrame = new javax.swing.JFrame();
        jScrollPane6 = new javax.swing.JScrollPane();
        IDNumber_TextBox = new javax.swing.JTextPane();
        NameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        Cancel = new javax.swing.JButton();
        Ok = new javax.swing.JButton();
        Password_Field = new javax.swing.JPasswordField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Last_Name_TextBox = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        First_Name_TextBox = new javax.swing.JTextPane();
        Last_Name_Label = new javax.swing.JLabel();
        First_Name_Label = new javax.swing.JLabel();
        BrokerLabel = new javax.swing.JLabel();
        BrokerBox = new javax.swing.JComboBox();
        DepositLabel = new javax.swing.JLabel();
        DepositText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        userID_Text = new javax.swing.JTextPane();
        password = new javax.swing.JPasswordField();
        userIDLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        Exit = new javax.swing.JButton();
        LogIn = new javax.swing.JButton();
        NewUser = new javax.swing.JButton();
        NoUsersLabel = new javax.swing.JLabel();

        newInvestorFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        newInvestorFrame.setAlwaysOnTop(true);
        newInvestorFrame.setResizable(false);

        jScrollPane6.setViewportView(IDNumber_TextBox);

        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("ID Number");

        PasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PasswordLabel.setText("Password");

        Cancel.setText("Cancel");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        Ok.setText("Ok");
        Ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(Last_Name_TextBox);

        jScrollPane3.setViewportView(First_Name_TextBox);

        Last_Name_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Last_Name_Label.setText("Last Name");

        First_Name_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        First_Name_Label.setText("First Name");

        BrokerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BrokerLabel.setText("Broker");

        DepositLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DepositLabel.setText("Deposit");

        javax.swing.GroupLayout newInvestorFrameLayout = new javax.swing.GroupLayout(newInvestorFrame.getContentPane());
        newInvestorFrame.getContentPane().setLayout(newInvestorFrameLayout);
        newInvestorFrameLayout.setHorizontalGroup(
            newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newInvestorFrameLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newInvestorFrameLayout.createSequentialGroup()
                        .addComponent(BrokerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(174, 174, 174))
                    .addGroup(newInvestorFrameLayout.createSequentialGroup()
                        .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(First_Name_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(Last_Name_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DepositLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newInvestorFrameLayout.createSequentialGroup()
                                .addComponent(Ok, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(BrokerBox, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(DepositText, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2)
                                    .addComponent(Password_Field, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING))))))
                .addContainerGap())
        );
        newInvestorFrameLayout.setVerticalGroup(
            newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newInvestorFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(First_Name_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(Last_Name_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DepositLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(newInvestorFrameLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(DepositText, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BrokerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BrokerBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(newInvestorFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ok)
                    .addComponent(Cancel))
                .addContainerGap())
        );

        userID_Text.setPreferredSize(new java.awt.Dimension(110, 20));
        jScrollPane1.setViewportView(userID_Text);

        userIDLabel.setText("User ID");

        passwordLabel.setText("Password");

        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        LogIn.setText("LogIn");
        LogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInActionPerformed(evt);
            }
        });

        NewUser.setText("New Invester");
        NewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewUserActionPerformed(evt);
            }
        });

        NoUsersLabel.setText("No Users Found");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(NewUser)
                        .addGap(20, 20, 20)
                        .addComponent(NoUsersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NewUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NoUsersLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 
     * @param evt User clicks the New User Button. 
     */
    
    private void NewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewUserActionPerformed
        First_Name_TextBox.setText("");
        Last_Name_TextBox.setText("");
        IDNumber_TextBox.setText("");
        Password_Field.setText("");
        DepositText.setText("");
        BrokerBox.setSelectedItem(-1);
        
        newInvestorFrame.setVisible(true);
        newInvestorFrame.setSize(320,375);
    }//GEN-LAST:event_NewUserActionPerformed

    /**
     * Input Checking to make sure that valid input were given and that the
     * Investor does not already exist before the information is submitted to
     * the BrokerageControl class.
     * 
     * @param evt User clicks the Ok button on the New User Screen. 
     */
    
    private void OkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkActionPerformed
        String fname = First_Name_TextBox.getText();
        String lname = Last_Name_TextBox.getText();
        String uid = IDNumber_TextBox.getText();
        char[] pword = Password_Field.getPassword();
        Broker broker = (Broker)BrokerBox.getSelectedItem();
        String dep = DepositText.getText();
        
        long userid = 0;
        double deposit = 0;
        
        boolean validInput = true;
        try {
            userid = Long.valueOf(uid).longValue();
            deposit = Double.valueOf(dep).doubleValue();
        }
        catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(newInvestorFrame, "User ID and"
                    + " deposit must be numerical values.", "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
            validInput = false;
        }
        
        boolean validID = false;
        if(validInput) {
            if(BrokerageControl.isUniqueID(userid)) {
                validID = true;
            }
            else {
                JOptionPane.showMessageDialog(newInvestorFrame, "The user ID"
                        + " you entered has alraedy been used.", "Invalid User"
                        + " ID", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        if(validInput && validID) {
            BrokerageControl.newInvestor(userid, fname, lname, pword, broker,
                    deposit);
            First_Name_TextBox.setText("");
            Last_Name_TextBox.setText("");
            IDNumber_TextBox.setText("");
            Password_Field.setText("");
            DepositText.setText("");
            BrokerBox.setSelectedIndex(0);
            newInvestorFrame.setVisible(false);
        }
    }//GEN-LAST:event_OkActionPerformed

    /**
     * 
     * @param evt Exit Button is clicked. 
     */
    
    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        BrokerageControl.shutDown();
    }//GEN-LAST:event_ExitActionPerformed

    /**
     * 
     * @param evt User clicks the cancel button on the New User screen.
     */
    
    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        newInvestorFrame.setVisible(false);
    }//GEN-LAST:event_CancelActionPerformed

    /**
     * 
     * @param evt User clicks the LogIn button. 
     */
    
    private void LogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogInActionPerformed
        String uid = userID_Text.getText();
        char[] pword = password.getPassword();
        
        BrokerageControl.doLogin(uid, pword);
        userID_Text.setText("");
        password.setText("");
    }//GEN-LAST:event_LogInActionPerformed
    
    /**
     * 
     * @return NoUsersLabel Label that appears when there are no users 
     *         registered to Laser Brokerage.
     */
    
    public javax.swing.JLabel getNoUsersLabel() {
        return NoUsersLabel;
    }
    
    /**
     * 
     * @return BrokerBox ComboBox that contains the name of all Brokers. 
     */
    
    public javax.swing.JComboBox getBrokerBox() {
        return BrokerBox;
    }
    
    /**
     * Main method is used for debugging the layout of the GUI such as
     * sizes and buttons placement.
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /*
//         * Set the Nimbus look and feel
//         */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /*
//         * If Nimbus (introduced in Java SE 6) is not available, stay with the
//         * default look and feel. For details see
//         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /*
//         * Create and display the form
//         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new Login().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox BrokerBox;
    private javax.swing.JLabel BrokerLabel;
    private javax.swing.JButton Cancel;
    private javax.swing.JLabel DepositLabel;
    private javax.swing.JTextField DepositText;
    private javax.swing.JButton Exit;
    private javax.swing.JLabel First_Name_Label;
    private javax.swing.JTextPane First_Name_TextBox;
    private javax.swing.JTextPane IDNumber_TextBox;
    private javax.swing.JLabel Last_Name_Label;
    private javax.swing.JTextPane Last_Name_TextBox;
    private javax.swing.JButton LogIn;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JButton NewUser;
    private javax.swing.JLabel NoUsersLabel;
    private javax.swing.JButton Ok;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPasswordField Password_Field;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JFrame newInvestorFrame;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel userIDLabel;
    private javax.swing.JTextPane userID_Text;
    // End of variables declaration//GEN-END:variables

}