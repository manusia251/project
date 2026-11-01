package controller;
//import class yang telah di buat dan memanggil library lain
import dao.Database;
import dao.UserDao;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.Timer;
import javax.swing.JOptionPane;

public class LoginAdmin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginAdmin.class.getName());
    public LoginAdmin() {
        initComponents();
        jToggleButton1.addActionListener(evt -> login());;
        try {
            Database.initialize();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka database: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        input_username = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        input_Password = new javax.swing.JPasswordField();
        tampilkan_password = new javax.swing.JCheckBox();
        javax.swing.JLabel gambar_login = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("LOGIN ADMIN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Username:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Password: ");

        input_username.addActionListener(this::input_usernameActionPerformed);

        jToggleButton1.setText("MASUK");

        input_Password.addActionListener(this::input_PasswordActionPerformed);

        tampilkan_password.setText("Tampilkan");
        tampilkan_password.addActionListener(this::tampilkan_passwordActionPerformed);

        gambar_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loggo.jpg"))); // NOI18N
        Image gambar = icon.getImage().getScaledInstance(
            300, 400, Image.SCALE_SMOOTH
        );

        gambar_login.setIcon(new ImageIcon(gambar));
        gambar_login.setAlignmentY(0.0F);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gambar_login, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(input_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(input_username, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                                .addComponent(jLabel2)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(131, 131, 131)))
                    .addComponent(tampilkan_password, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_username, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(tampilkan_password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(layout.createSequentialGroup()
                .addComponent(gambar_login, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tampilkan_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilkan_passwordActionPerformed
        
       /*sebuh logic untuk mengecek apakah check box tuh di tekan dan tercentang atau tidak, kalau
        kecentang maka rubah pw yang di masukkan jadi angka asli, kalau tidak jadikan .....*/ 
        if(tampilkan_password.isSelected()){
            input_Password.setEchoChar((char)0);
        }else{
            input_Password.setEchoChar('.');
        }
    }//GEN-LAST:event_tampilkan_passwordActionPerformed

    private void input_PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_PasswordActionPerformed
        login();
    }//GEN-LAST:event_input_PasswordActionPerformed

    private void input_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_usernameActionPerformed
        input_Password.requestFocus();
    }//GEN-LAST:event_input_usernameActionPerformed

    //buat method untuk mengecek apakah user berhasil untuk login ke aplikasi
    private void login() {
        try {
            boolean berhasil = new UserDao().login(
                    input_username.getText(),
                    new String(input_Password.getPassword()));

            if (berhasil) {
                new Dashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal login: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new LoginAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField input_Password;
    private javax.swing.JTextField input_username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JCheckBox tampilkan_password;
    // End of variables declaration//GEN-END:variables
}
