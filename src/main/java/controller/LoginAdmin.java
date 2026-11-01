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
        tombol_login.addActionListener(evt -> login());;
        try {
            Database.initialize();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka database: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_blur = new properties.Blurr();
        input_Password = new javax.swing.JPasswordField();
        tampilkan_password = new javax.swing.JCheckBox();
        input_username = new javax.swing.JTextField();
        login_label = new javax.swing.JLabel();
        label_username = new javax.swing.JLabel();
        Password_label = new javax.swing.JLabel();
        tombol_login = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Pembayaran");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        input_Password.setBackground(new java.awt.Color(184, 197, 197));
        input_Password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 233, 226)));
        input_Password.addActionListener(this::input_PasswordActionPerformed);

        tampilkan_password.setForeground(new java.awt.Color(255, 255, 255));
        tampilkan_password.setText("Tampilkan");
        tampilkan_password.addActionListener(this::tampilkan_passwordActionPerformed);

        input_username.setBackground(new java.awt.Color(184, 197, 197));
        input_username.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 233, 226), 1, true));
        input_username.setDoubleBuffered(true);
        input_username.addActionListener(this::input_usernameActionPerformed);

        login_label.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        login_label.setForeground(new java.awt.Color(255, 255, 255));
        login_label.setText("LOGIN");

        label_username.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label_username.setForeground(new java.awt.Color(255, 255, 255));
        label_username.setText("Username:");

        Password_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Password_label.setForeground(new java.awt.Color(255, 255, 255));
        Password_label.setText("Password: ");

        tombol_login.setBackground(new java.awt.Color(169, 203, 183));
        tombol_login.setForeground(new java.awt.Color(0, 0, 0));
        tombol_login.setText("MASUK");

        javax.swing.GroupLayout panel_blurLayout = new javax.swing.GroupLayout(panel_blur);
        panel_blur.setLayout(panel_blurLayout);
        panel_blurLayout.setHorizontalGroup(
            panel_blurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_blurLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(tombol_login, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addGroup(panel_blurLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panel_blurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_username)
                    .addComponent(tampilkan_password)
                    .addComponent(Password_label)
                    .addComponent(input_username, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(input_Password))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_blurLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(login_label, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_blurLayout.setVerticalGroup(
            panel_blurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_blurLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(login_label)
                .addGap(18, 18, 18)
                .addComponent(label_username)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_username, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Password_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(input_Password, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tampilkan_password)
                .addGap(18, 18, 18)
                .addComponent(tombol_login, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        getContentPane().add(panel_blur, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 400, 350));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/forest.jpg"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

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
    private javax.swing.JLabel Password_label;
    private javax.swing.JPasswordField input_Password;
    private javax.swing.JTextField input_username;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel label_username;
    private javax.swing.JLabel login_label;
    private javax.swing.JPanel panel_blur;
    private javax.swing.JCheckBox tampilkan_password;
    private javax.swing.JToggleButton tombol_login;
    // End of variables declaration//GEN-END:variables
}
