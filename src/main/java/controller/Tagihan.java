// Package controller: Berisi kelas-kelas kontroler antarmuka (GUI) dan logika tampilan
package controller;
// Import library untuk mengelola tanggal dan waktu sistem secara realtime
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
// Import Timer dari javax.swing untuk memperbarui waktu di UI setiap interval tertentu
import javax.swing.Timer;
// Kelas utama antarmuka Tagihan & Pembayaran (JFrame Swing)
public class Tagihan extends javax.swing.JFrame {
    
    // Pencatat log sistem untuk mencatat informasi error/exception pada kelas Tagihan
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Tagihan.class.getName());
    // Konstruktor Tagihan: Menginisialisasi komponen GUI dan memulai timer jam/tanggal realtime
    public Tagihan() {
        initComponents();
        jam();
    }
    // Method jam(): Mengambil waktu sistem dan memperbarui label jam & tanggal setiap 1 detik
    private void jam() {
    // 1. Buat format terpisah untuk jam dan tanggal
    DateTimeFormatter formatJam = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Timer Swing: Dijalankan setiap 1 detik (1000 ms) di Event Dispatch Thread
        Timer timer = new Timer(1000, e -> {
        LocalTime waktu = LocalTime.now();
        LocalDate kalender = LocalDate.now();

        // 2. Set teks ke label masing-masing
        tampilkan_jam.setText(waktu.format(formatJam));
        tampilkan_kalender.setText(kalender.format(formatTanggal));
    });

        // Menjalankan timer pembaruan jam & tanggal
        timer.start();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new properties.blur_kotak();
        Riwayat_variabel = new javax.swing.JToggleButton();
        Tagihan_variable = new javax.swing.JToggleButton();
        Siswa_variable = new javax.swing.JToggleButton();
        Laporan_variable = new javax.swing.JToggleButton();
        dashboard_variable = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tampilkan_jam = new javax.swing.JLabel();
        tampilkan_kalender = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(32, 172, 91));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Riwayat_variabel.setText("Riwayat");
        Riwayat_variabel.addActionListener(this::Riwayat_variabelActionPerformed);

        Tagihan_variable.setText("Tagihan");
        Tagihan_variable.addActionListener(this::Tagihan_variableActionPerformed);

        Siswa_variable.setText("Data Siswa");
        Siswa_variable.addActionListener(this::Siswa_variableActionPerformed);

        Laporan_variable.setText("Laporan");
        Laporan_variable.addActionListener(this::Laporan_variableActionPerformed);

        dashboard_variable.setText("Dashboard");
        dashboard_variable.addActionListener(this::dashboard_variableActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel1.setText("Sistem SPP");

        jLabel3.setText("Pembayaran Sekolah");

        jLabel4.setText("Menu");

        jLabel18.setText("Petugas TU");

        jLabel19.setText("admin");

        jButton1.setText("Keluar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(54, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Riwayat_variabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Laporan_variable, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                                    .addComponent(Tagihan_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(dashboard_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Siswa_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(17, 17, 17))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dashboard_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Siswa_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Tagihan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Riwayat_variabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Laporan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(9, 9, 9))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 620));

        jTextField1.setText("cari nama");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "A-Z", "Pembayaran", "Tagihan", " " }));

        jButton3.setText("Buat resi pemabayaran");

        jButton4.setText("jButton4");

        jButton5.setText("jButton5");

        jLabel2.setText("Tagihan & Pembayaran");

        jLabel5.setText("Tanggal");

        jLabel6.setText("Jam");

        tampilkan_jam.setText("jLabel7");

        tampilkan_kalender.setText("jLabel7");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(tampilkan_kalender, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(tampilkan_jam, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(tampilkan_jam)
                    .addComponent(tampilkan_kalender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 920, 130));
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 920, 460));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Event handler: Dipanggil saat tombol 'Data Siswa' diklik
    private void Siswa_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Siswa_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new DataSiswa());
    }//GEN-LAST:event_Siswa_variableActionPerformed

    // Event handler: Dipanggil saat tombol 'Laporan' diklik
    private void Laporan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Laporan_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman    
        properties.ganti_halaman.gantihalaman(this, new Laporan());
            
    }//GEN-LAST:event_Laporan_variableActionPerformed

        // Event handler: Dipanggil saat tombol 'Dashboard' diklik
    private void dashboard_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Dashboard());
    }//GEN-LAST:event_dashboard_variableActionPerformed

    private void Tagihan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tagihan_variableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tagihan_variableActionPerformed

    private void Riwayat_variabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Riwayat_variabelActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
            properties.ganti_halaman.gantihalaman(this, new Riwayat());
    }//GEN-LAST:event_Riwayat_variabelActionPerformed

    // Method main: Titik awal eksekusi program untuk menjalankan tampilan GUI Tagihan
    public static void main(String args[]) {
        try {
            // Mengatur tema tampilan GUI (Look and Feel) menggunakan tema Nimbus
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        // Menjalankan dan menampilkan frame Tagihan pada Event Dispatch Thread Swing
        java.awt.EventQueue.invokeLater(() -> new Tagihan().setVisible(true));
        
        
        
    }

    // Deklarasi variabel komponen antarmuka GUI Swing (tombol, panel, label, field input)
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Laporan_variable;
    private javax.swing.JToggleButton Riwayat_variabel;
    private javax.swing.JToggleButton Siswa_variable;
    private javax.swing.JToggleButton Tagihan_variable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JToggleButton dashboard_variable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel tampilkan_jam;
    private javax.swing.JLabel tampilkan_kalender;
    // End of variables declaration//GEN-END:variables
}
