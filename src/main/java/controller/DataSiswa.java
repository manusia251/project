package controller;

import dao.Database;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import properties.AppTheme;

/**
 * Halaman Data Siswa (tambah, ubah, hapus, dan cari data siswa).
 *
 * Cara kerja file ini:
 *  1. initComponents()  -> kode GUI Builder NetBeans (hasil drag and drop, TIDAK diubah).
 *  2. siapkanTampilan() -> "mendandani" komponen hasil drag and drop dengan tema glassmorphism
 *                          (tanpa menyentuh bentuk/posisi komponen dari palette).
 *  3. siapkanLogika()   -> menghubungkan tombol, pencarian, dan tabel dengan aksi.
 *  4. muatData()        -> mengambil data siswa dari database SQLite lalu menampilkannya di tabel.
 *
 * Semua kelas bantu desain (latar kaca, ikon, tombol, renderer tabel) ditulis sebagai
 * kelas bersarang di bagian paling bawah file ini, jadi cukup satu file yang diubah.
 */
public class DataSiswa extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DataSiswa.class.getName());

    // ------------------------------------------------------------------
    // WARNA TEMA GLASSMORPHISM
    // Warna dasar diambil dari halaman Login (hutan berkabut + hijau mint)
    // supaya semua halaman terlihat satu keluarga.
    // ------------------------------------------------------------------
    /** Hijau mint: sama dengan warna tombol MASUK di halaman Login. */
    static final Color MINT = new Color(169, 203, 183);
    /** Mint sangat terang: sama dengan warna garis kolom input di halaman Login. */
    static final Color MINT_TERANG = new Color(220, 233, 226);
    /** Teks gelap kehijauan, dipakai di atas kartu putih (tabel) dan tombol mint. */
    static final Color TEKS = new Color(26, 46, 38);
    /** Teks redup untuk label kecil di atas kaca gelap. */
    static final Color TEKS_REDUP = new Color(214, 232, 222);
    /** Garis pemisah antar baris tabel. */
    static final Color GARIS = new Color(170, 190, 180, 120);
    /** Warna baris tabel yang sedang dipilih (mint transparan). */
    static final Color BARIS_DIPILIH = new Color(169, 203, 183, 150);
    /** Warna baris ganjil tabel (belang-belang tipis). */
    static final Color BARIS_GANJIL = new Color(60, 110, 85, 22);

    // Urutan bulan mengikuti tahun ajaran sekolah (Juli sampai Juni).
    private static final String[] URUTAN_BULAN = {
        "Juli", "Agustus", "September", "Oktober", "November", "Desember",
        "Januari", "Februari", "Maret", "April", "Mei", "Juni"
    };

    // Model tabel dibuat sekali dan dipakai ulang; sel tidak bisa diedit langsung.
    // Kolom: NO, NIS, NAMA SISWA, KELAS, TAGIHAN / BULAN, STATUS TAHUN AJARAN.
    private final DefaultTableModel modelSiswa = new DefaultTableModel(
            new Object[]{"NO", "NIS", "NAMA SISWA", "KELAS", "TAGIHAN / BULAN", "STATUS TAHUN AJARAN"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // tabel hanya untuk dilihat, perubahan lewat tombol Ubah
        }

        @Override
        public Class<?> getColumnClass(int column) {
            // Kolom 0, 1, 4 berisi angka sehingga pengurutan (sort) berjalan secara numerik.
            return (column == 0 || column == 1 || column == 4) ? Integer.class : String.class;
        }
    };

    private JTextField fieldCari;   // kolom pencarian (dibuat di pasangKolomCari)
    private Timer timerJam;         // timer yang menggerakkan jam dan tanggal

    public DataSiswa() {
        // Latar hutan dipasang SEBELUM initComponents() supaya GUI Builder
        // menaruh semua komponen di atas panel latar ini.
        setContentPane(new LatarKaca());
        initComponents();
        siapkanTampilan();   // finishing desain (di luar kode GUI Builder)
        siapkanLogika();     // menghubungkan tombol, pencarian, dan tabel
        jam();               // menjalankan jam dan tanggal
        muatData();          // mengisi tabel untuk pertama kali
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SideBar = new javax.swing.JPanel();
        riwayat_variable = new javax.swing.JToggleButton();
        tagihan_variable = new javax.swing.JToggleButton();
        data_variable = new javax.swing.JToggleButton();
        laporan_variable = new javax.swing.JToggleButton();
        dashboard_variable = new javax.swing.JToggleButton();
        header = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        exit = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cari_nis_nama_kelas = new javax.swing.JLabel();
        header2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        ComboBoxTahunAjaran = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelDataSiswa = new javax.swing.JTable();
        TambahSiswa = new javax.swing.JButton();
        Ubah = new javax.swing.JButton();
        Hapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        riwayat_variable.setText("Riwayat");
        riwayat_variable.addActionListener(this::riwayat_variableActionPerformed);

        tagihan_variable.setText("Tagihan");
        tagihan_variable.addActionListener(this::tagihan_variableActionPerformed);

        data_variable.setText("Data Siswa");
        data_variable.addActionListener(this::data_variableActionPerformed);

        laporan_variable.setText("Laporan");
        laporan_variable.addActionListener(this::laporan_variableActionPerformed);

        dashboard_variable.setText("Dashboard");
        dashboard_variable.addActionListener(this::dashboard_variableActionPerformed);

        header.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        header.setText("Sistem SPP");

        icon.setBackground(new java.awt.Color(150, 150, 150));
        icon.setText("icon");

        jLabel3.setText("Pembayaran Sekolah");

        jLabel4.setText("Menu");

        jLabel18.setText("Petugas TU");

        jLabel19.setText("admin");

        exit.setText("Keluar");

        javax.swing.GroupLayout SideBarLayout = new javax.swing.GroupLayout(SideBar);
        SideBar.setLayout(SideBarLayout);
        SideBarLayout.setHorizontalGroup(
            SideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard_variable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(data_variable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tagihan_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(riwayat_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(laporan_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SideBarLayout.createSequentialGroup()
                .addGroup(SideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SideBarLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(SideBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SideBarLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(SideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 33, Short.MAX_VALUE))
            .addGroup(SideBarLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(exit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SideBarLayout.setVerticalGroup(
            SideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SideBarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(SideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(SideBarLayout.createSequentialGroup()
                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboard_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(data_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tagihan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(riwayat_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(laporan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit)
                .addGap(21, 21, 21))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        cari_nis_nama_kelas.setFont(new java.awt.Font("Segoe UI Historic", 0, 10)); // NOI18N
        cari_nis_nama_kelas.setText("Cari NIS/Nama/Kelas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cari_nis_nama_kelas, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cari_nis_nama_kelas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        header2.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        header2.setText("Data Siswa");

        jLabel6.setText("Kelola data siswa dan besaran tagihan");

        Date.setText("Date");

        jLabel8.setText("Tahun Ajaran");

        Time.setText("Time");

        ComboBoxTahunAjaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2026/2027", "2025/2026", "2024/2025", "2023/2024" }));

        TabelDataSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NO", "NIS", "NAMA SISWA", "KELAS", "TAGIHAN / BULAN", "STATUS TAHUN AJARAN"
            }
        ));
        jScrollPane1.setViewportView(TabelDataSiswa);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TambahSiswa.setText("+ Tambah Siswa");

        Ubah.setForeground(new java.awt.Color(0, 102, 0));
        Ubah.setText("Ubah");

        Hapus.setForeground(new java.awt.Color(204, 0, 0));
        Hapus.setText("Hapus");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Ubah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TambahSiswa))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(header2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Date)
                        .addGap(18, 18, 18)
                        .addComponent(Time, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBoxTahunAjaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(header2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(Date)
                            .addComponent(Time)
                            .addComponent(ComboBoxTahunAjaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TambahSiswa)
                        .addComponent(Ubah)
                        .addComponent(Hapus)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(SideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void data_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_data_variableActionPerformed
        // Sudah berada di halaman Data Siswa; ButtonGroup menjaga tombol ini tetap aktif.
    }//GEN-LAST:event_data_variableActionPerformed

    private void laporan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporan_variableActionPerformed
      // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Laporan());
    }//GEN-LAST:event_laporan_variableActionPerformed

    private void dashboard_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard_variableActionPerformed
      // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Dashboard());
    }//GEN-LAST:event_dashboard_variableActionPerformed

    private void tagihan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagihan_variableActionPerformed
         // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Tagihan());
    }//GEN-LAST:event_tagihan_variableActionPerformed

    private void riwayat_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riwayat_variableActionPerformed
                // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Riwayat());
    }//GEN-LAST:event_riwayat_variableActionPerformed

    // =====================================================================
    // FINISHING DESAIN & LOGIKA
    // Semua kode di bawah ini sengaja ditulis di luar blok "Generated Code"
    // agar tidak hilang ketika form diedit lagi lewat NetBeans Designer.
    // =====================================================================

    /**
     * Merapikan tampilan: kaca, warna, font, tabel, tombol, dan kolom pencarian.
     * Hanya mengubah "kulit" komponen; ukuran dan posisi dari palette tidak disentuh.
     */
    private void siapkanTampilan() {
        gayaKaca();          // panel utama dibuat seperti kaca buram
        gayaSidebar();       // logo, ikon menu, ikon admin, dan tombol keluar
        gayaHeader();        // judul halaman, jam, dan tahun ajaran
        pasangKolomCari();   // kolom pencarian bergaya kaca
        gayaTabel();         // tabel transparan dengan label status berbentuk pil
        gayaTombolAksi();    // tombol Ubah, Hapus, dan Tambah Siswa

        setTitle("Sistem SPP - Data Siswa");
        pack();
    }

    /**
     * Mengubah panel-panel hasil drag and drop menjadi kaca buram (glassmorphism).
     * Caranya mengganti UI (cara menggambar) panel, bukan mengganti panelnya,
     * sehingga susunan layout dari palette tetap sama.
     */
    private void gayaKaca() {
        // Sidebar: kaca gelap, di sisi kiri.
        SideBar.setUI(new PanelKacaUI(28, new Color(6, 22, 16, 150), new Color(255, 255, 255, 70),
                this::lukisHiasanSidebar));
        // Panel isi utama: kaca asap yang lebih terang dari sidebar.
        jPanel3.setUI(new PanelKacaUI(28, new Color(16, 36, 28, 105), new Color(255, 255, 255, 80), null));
        // Kartu tabel: kaca putih (frosted) agar data mudah dibaca, seperti kartu putih di referensi.
        jPanel8.setUI(new PanelKacaUI(20, new Color(255, 255, 255, 222), new Color(255, 255, 255, 190), null));
        jPanel8.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6)); // jarak tabel dari tepi kartu
        // Pembungkus kolom pencarian: kaca tipis berbentuk kapsul.
        jPanel2.setUI(new PanelKacaUI(14, new Color(255, 255, 255, 48), new Color(255, 255, 255, 120), null));

        // PENTING: setUI() mengembalikan panel menjadi opaque, jadi harus dimatikan lagi
        // agar latar hutan di belakang panel tetap terlihat.
        for (JPanel p : new JPanel[]{SideBar, jPanel3, jPanel8, jPanel2}) {
            p.setOpaque(false);
        }
    }

    /** Warna dan font label judul halaman (kanan atas: tanggal, jam, tahun ajaran). */
    private void gayaHeader() {
        header2.setForeground(Color.WHITE);
        for (JLabel label : new JLabel[]{jLabel6, jLabel8, Date, Time}) {
            label.setForeground(TEKS_REDUP);
        }
        Date.setFont(Date.getFont().deriveFont(java.awt.Font.BOLD));
        Time.setFont(Time.getFont().deriveFont(java.awt.Font.BOLD));

        // Combo box tahun ajaran memakai warna kolom input Login (abu kehijauan + garis mint terang).
        ComboBoxTahunAjaran.putClientProperty("FlatLaf.style",
                "background:#b8c5c5; foreground:#183024; borderColor:#dce9e2;"
                + "buttonBackground:#b8c5c5; buttonArrowColor:#183024;"
                + "selectionBackground:#a9cbb7; selectionForeground:#183024");
        ComboBoxTahunAjaran.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Sidebar bergaya kaca: logo, ikon di setiap tombol menu, ikon admin, dan tombol keluar.
     * Kolom ikon sejajar di x = 24, kolom teks sejajar di x = 56
     * (sama dengan posisi label admin dari palette).
     */
    private void gayaSidebar() {
        header.setForeground(Color.WHITE);                 // "Sistem SPP"
        jLabel3.setForeground(TEKS_REDUP);                 // "Pembayaran Sekolah"
        jLabel4.setForeground(new Color(190, 215, 200));   // "MENU"
        jLabel4.setText(jLabel4.getText().toUpperCase());  // referensi memakai huruf kapital
        jLabel4.setFont(jLabel4.getFont().deriveFont(java.awt.Font.BOLD, 11f));
        jLabel19.setForeground(Color.WHITE);               // "admin"
        jLabel19.setFont(jLabel19.getFont().deriveFont(java.awt.Font.BOLD));
        jLabel18.setForeground(TEKS_REDUP);                // "Petugas TU"

        // Logo aplikasi di samping judul "Sistem SPP": topi wisuda + koin, digambar vektor
        // dengan warna mint/putih agar serasi dengan tema hutan (tidak lagi memakai logo.png biru).
        icon.setIcon(new IkonVektor(IkonVektor.Jenis.LOGO, 37));
        icon.setText("");
        setIconImage(IkonVektor.buatGambarLogo(64)); // logo yang sama dipakai sebagai ikon jendela

        // Tombol menu: urutan tombol dan ikonnya harus sama.
        JToggleButton[] menuTombol = {dashboard_variable, data_variable, tagihan_variable,
            riwayat_variable, laporan_variable};
        IkonVektor.Jenis[] menuIkon = {IkonVektor.Jenis.DASHBOARD, IkonVektor.Jenis.SISWA,
            IkonVektor.Jenis.TAGIHAN, IkonVektor.Jenis.RIWAYAT, IkonVektor.Jenis.LAPORAN};

        // ButtonGroup: hanya satu menu yang boleh aktif dalam satu waktu.
        ButtonGroup menu = new ButtonGroup();
        for (int i = 0; i < menuTombol.length; i++) {
            JToggleButton tombol = menuTombol[i];
            menu.add(tombol);
            tombol.setUI(new TombolKacaUI(TombolKacaUI.Gaya.MENU));   // gambar sendiri: pil kaca
            tombol.setIcon(new IkonVektor(menuIkon[i], 22));           // ikon di samping teks
            tombol.setIconTextGap(10);                                 // jarak ikon ke teks -> teks mulai di x = 56
            tombol.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 12));
            tombol.setMargin(new Insets(0, 0, 0, 0));
            tombol.setHorizontalAlignment(SwingConstants.LEFT);
            tombol.setFont(tombol.getFont().deriveFont(java.awt.Font.BOLD, 13f));
            tombol.setOpaque(false);
            tombol.setContentAreaFilled(false);
            tombol.setFocusPainted(false);
            tombol.setRolloverEnabled(true);                           // dibutuhkan agar efek hover jalan
            tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        data_variable.setSelected(true); // halaman ini = Data Siswa, jadi menunya aktif

        // Tombol Keluar: hanya teks (mulai di x = 56); ikonnya digambar oleh lukisHiasanSidebar()
        // di kolom ikon (x = 24) supaya sejajar dengan ikon menu.
        exit.setUI(new TombolKacaUI(TombolKacaUI.Gaya.KELUAR));
        exit.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 16)); // padding atas-bawah agar tombol tidak mepet tepi sidebar
        exit.setMargin(new Insets(0, 0, 0, 0));
        exit.setHorizontalAlignment(SwingConstants.LEFT);
        exit.setFont(exit.getFont().deriveFont(java.awt.Font.BOLD, 13f));
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);
        exit.setRolloverEnabled(true);
        exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Saat kursor melewati tombol Keluar, sidebar digambar ulang agar ikonnya ikut berubah warna.
        exit.getModel().addChangeListener(e -> SideBar.repaint());
        // Klik pada ikon Keluar (di luar area teks) juga dianggap menekan tombol Keluar.
        SideBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Rectangle areaIkon = new Rectangle(0, exit.getY(), exit.getX(), exit.getHeight());
                if (areaIkon.contains(e.getPoint())) {
                    exit.doClick();
                }
            }
        });
    }

    /**
     * Menggambar hiasan tambahan di atas kaca sidebar (dipanggil oleh PanelKacaUI):
     * ikon orang di samping "admin / Petugas TU" dan ikon pintu di samping "Keluar".
     * Ikon digambar di sini (bukan dipasang pada label) agar ukuran label dari palette tidak berubah.
     */
    private void lukisHiasanSidebar(Graphics2D g, JComponent sidebar) {
        // Ikon admin: di tengah-tengah antara label "admin" dan "Petugas TU".
        int tengah = (jLabel19.getY() + jLabel18.getY() + jLabel18.getHeight()) / 2;
        new IkonVektor(IkonVektor.Jenis.PENGGUNA, 26).lukis(g, 22, tengah - 13, new Color(255, 255, 255, 235));

        // Ikon Keluar: berubah merah muda saat kursor berada di atas tombol.
        boolean hover = exit.getModel().isRollover();
        Color warna = hover ? new Color(255, 170, 170) : new Color(255, 255, 255, 225);
        new IkonVektor(IkonVektor.Jenis.KELUAR, 22).lukis(g, 24, exit.getY() + (exit.getHeight() - 22) / 2, warna);
    }

    /**
     * Mengganti label "Cari NIS/Nama/Kelas" pada jPanel2 dengan kolom input,
     * teks label lama dipakai sebagai placeholder.
     */
    private void pasangKolomCari() {
        String petunjuk = cari_nis_nama_kelas.getText();
        jPanel2.removeAll();
        jPanel2.setLayout(new BorderLayout());

        fieldCari = new JTextField();
        // Kolom dibuat transparan; kaca kapsul milik jPanel2 yang menjadi latarnya.
        fieldCari.setOpaque(false);
        fieldCari.setBackground(new Color(0, 0, 0, 0));
        fieldCari.setForeground(Color.WHITE);
        fieldCari.setCaretColor(Color.WHITE);
        fieldCari.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 10));
        fieldCari.putClientProperty("JTextField.placeholderText", petunjuk);
        fieldCari.putClientProperty("FlatLaf.style",
                "placeholderForeground:#ffffffb3; selectionBackground:#a9cbb7; selectionForeground:#183024");
        fieldCari.setToolTipText("Ketik NIS, nama, atau kelas untuk menyaring tabel");
        jPanel2.add(fieldCari, BorderLayout.CENTER);
        jPanel2.setPreferredSize(new Dimension(260, 32));

        // Garis kapsul menjadi lebih terang saat kolom sedang diketik (fokus).
        fieldCari.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jPanel2.putClientProperty("fokus", Boolean.TRUE);
                jPanel2.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                jPanel2.putClientProperty("fokus", Boolean.FALSE);
                jPanel2.repaint();
            }
        });
    }

    /** Mengatur tabel agar transparan di atas kartu kaca, lengkap dengan header, baris, dan kolom status. */
    private void gayaTabel() {
        TabelDataSiswa.setModel(modelSiswa);
        TabelDataSiswa.setAutoCreateRowSorter(true); // klik judul kolom untuk mengurutkan
        TabelDataSiswa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TabelDataSiswa.setRowHeight(34);
        TabelDataSiswa.setShowVerticalLines(false);
        TabelDataSiswa.setShowHorizontalLines(true);
        TabelDataSiswa.setGridColor(GARIS);
        TabelDataSiswa.setIntercellSpacing(new Dimension(0, 1));
        TabelDataSiswa.setFillsViewportHeight(true);
        TabelDataSiswa.setOpaque(false);  // agar kartu kaca terlihat di belakang tabel
        TabelDataSiswa.getTableHeader().setReorderingAllowed(false);
        TabelDataSiswa.getTableHeader().setPreferredSize(new Dimension(0, 36));
        TabelDataSiswa.getTableHeader().setDefaultRenderer(new HeaderRenderer());

        // Scroll pane dibuat transparan dan tanpa garis tepi; tepinya sudah diwakili kartu kaca.
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane1.setViewportBorder(null);
        // Sudut kanan atas (muncul saat ada scrollbar) diberi warna yang sama dengan header tabel.
        JPanel sudut = new JPanel();
        sudut.setBackground(HeaderRenderer.LATAR_HEADER);
        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, sudut);

        // Lebar awal setiap kolom.
        int[] lebar = {50, 80, 260, 110, 140, 220};
        for (int i = 0; i < lebar.length; i++) {
            TabelDataSiswa.getColumnModel().getColumn(i).setPreferredWidth(lebar[i]);
        }

        // Renderer = "pelukis" isi sel. Setiap kolom punya perataan dan format sendiri.
        TabelDataSiswa.getColumnModel().getColumn(0).setCellRenderer(
                new SelRenderer(SwingConstants.CENTER, null) {
            @Override
            protected String teks(Object nilai, int barisTampil) {
                return String.valueOf(barisTampil + 1); // nomor ikut berubah saat tabel diurutkan
            }
        });
        TabelDataSiswa.getColumnModel().getColumn(1).setCellRenderer(new SelRenderer(SwingConstants.CENTER, null));
        TabelDataSiswa.getColumnModel().getColumn(2).setCellRenderer(new SelRenderer(SwingConstants.LEFT, null));
        TabelDataSiswa.getColumnModel().getColumn(3).setCellRenderer(new SelRenderer(SwingConstants.LEFT, null));
        // Kolom tagihan: angka ditampilkan sebagai rupiah, nilai 0 ditampilkan sebagai "-".
        TabelDataSiswa.getColumnModel().getColumn(4).setCellRenderer(new SelRenderer(SwingConstants.RIGHT,
                nilai -> nilai instanceof Integer n && n > 0 ? rupiah(n) : "-"));
        // Kolom status: ditampilkan sebagai pil berwarna (hijau = lunas, oranye = menunggak).
        TabelDataSiswa.getColumnModel().getColumn(5).setCellRenderer(new StatusRenderer());
    }

    /** Memberi gaya kaca pada tiga tombol aksi sesuai referensi (Ubah, Hapus, Tambah Siswa). */
    private void gayaTombolAksi() {
        // Setiap tombol memakai "gaya" sendiri: Tambah = isi mint, Ubah = garis mint, Hapus = garis merah.
        pasangGayaAksi(TambahSiswa, TombolKacaUI.Gaya.UTAMA);
        pasangGayaAksi(Ubah, TombolKacaUI.Gaya.SEKUNDER);
        pasangGayaAksi(Hapus, TombolKacaUI.Gaya.BAHAYA);

        Ubah.setToolTipText("Pilih siswa pada tabel terlebih dahulu");
        Hapus.setToolTipText("Pilih siswa pada tabel terlebih dahulu");
        // Ubah dan Hapus baru aktif setelah ada baris yang dipilih (lihat siapkanLogika).
        Ubah.setEnabled(false);
        Hapus.setEnabled(false);
    }

    /** Pengaturan yang sama untuk ketiga tombol aksi. */
    private void pasangGayaAksi(JButton tombol, TombolKacaUI.Gaya gaya) {
        tombol.setUI(new TombolKacaUI(gaya));
        tombol.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        tombol.setMargin(new Insets(0, 0, 0, 0));
        tombol.setFont(tombol.getFont().deriveFont(java.awt.Font.BOLD, 13f));
        tombol.setOpaque(false);
        tombol.setContentAreaFilled(false);
        tombol.setFocusPainted(false);
        tombol.setRolloverEnabled(true);
        tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /** Menghubungkan semua komponen ke logika. */
    private void siapkanLogika() {
        TambahSiswa.addActionListener(e -> tambahSiswa());
        Ubah.addActionListener(e -> ubahSiswa());
        Hapus.addActionListener(e -> hapusSiswa());
        exit.addActionListener(e -> keluar());
        ComboBoxTahunAjaran.addActionListener(e -> muatData()); // ganti tahun ajaran -> muat ulang tabel

        // Pencarian langsung berjalan setiap kali teks berubah.
        fieldCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                muatData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                muatData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                muatData();
            }
        });

        // Tombol Ubah/Hapus hanya aktif jika ada baris yang dipilih.
        TabelDataSiswa.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean dipilih = TabelDataSiswa.getSelectedRow() != -1;
                Ubah.setEnabled(dipilih);
                Hapus.setEnabled(dipilih);
            }
        });

        // Klik dua kali pada baris sama dengan menekan tombol Ubah.
        TabelDataSiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && TabelDataSiswa.getSelectedRow() != -1) {
                    ubahSiswa();
                }
            }
        });

        // Hentikan timer jam saat halaman ditutup/berpindah.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (timerJam != null) {
                    timerJam.stop();
                }
            }
        });
    }

    /** Jam dan tanggal berjalan otomatis (format jam dipendekkan agar muat di label). */
    private void jam() {
        DateTimeFormatter formatJam = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Runnable perbarui = () -> {
            Time.setText(LocalTime.now().format(formatJam));
            Date.setText(LocalDate.now().format(formatTanggal));
        };
        perbarui.run();
        timerJam = new Timer(1000, e -> perbarui.run()); // diperbarui setiap 1000 ms
        timerJam.start();
    }

    /** Konfirmasi keluar; jika "Ya", kembali ke halaman Login. */
    private void keluar() {
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Apakah kamu yakin ingin keluar?", "Konfirmasi keluar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pilihan == JOptionPane.YES_OPTION) {
            properties.ganti_halaman.gantihalaman(this, new LoginAdmin());
        }
    }

    // ------------------------------ DATA ------------------------------

    /** Tahun ajaran yang sedang dipilih di combo box, misalnya "2026/2027". */
    private String tahunAjaranDipilih() {
        return String.valueOf(ComboBoxTahunAjaran.getSelectedItem());
    }

    /**
     * Membuat potongan SQL "CASE ... END" agar nama bulan bisa diurutkan
     * Juli -> Juni (bukan urutan abjad).
     */
    private static String urutBulan(String kolom) {
        StringBuilder sb = new StringBuilder("CASE ").append(kolom);
        for (int i = 0; i < URUTAN_BULAN.length; i++) {
            sb.append(" WHEN '").append(URUTAN_BULAN[i]).append("' THEN ").append(i + 1);
        }
        return sb.append(" ELSE 13 END").toString();
    }

    /** Mengubah angka menjadi format rupiah, contoh 350000 -> "Rp350.000". */
    private static String rupiah(int nominal) {
        return String.format(Locale.of("id", "ID"), "Rp%,d", nominal);
    }

    /**
     * Mengisi tabel berdasarkan tahun ajaran dan kata pencarian.
     * TAGIHAN / BULAN diambil dari bulan terdekat yang belum lunas
     * (atau bulan terakhir jika semua sudah lunas).
     */
    private void muatData() {
        if (fieldCari == null) {
            return; // komponen belum siap (dipanggil saat inisialisasi)
        }
        String tahun = tahunAjaranDipilih();
        String kata = fieldCari.getText().trim();
        Integer nisSebelumnya = nisSebelumnyaTerpilih(); // supaya baris yang dipilih tetap terpilih setelah muat ulang

        // Menyusun query: satu baris per siswa + hitungan bulan lunas/total dari tabel Pembayaran.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.id_siswa, s.nama, s.kelas, ");
        sql.append("COALESCE(");
        // nominal bulan pertama yang belum lunas ...
        sql.append("(SELECT p2.nominal FROM Pembayaran p2 WHERE p2.id_siswa = s.id_siswa ");
        sql.append("AND p2.tahun_ajaran = ? AND COALESCE(p2.status, '') <> 'lunas' ");
        sql.append("ORDER BY ").append(urutBulan("p2.bulan")).append(" LIMIT 1), ");
        // ... jika semua lunas, pakai nominal bulan terakhir ...
        sql.append("(SELECT p3.nominal FROM Pembayaran p3 WHERE p3.id_siswa = s.id_siswa ");
        sql.append("AND p3.tahun_ajaran = ? ORDER BY ").append(urutBulan("p3.bulan")).append(" DESC LIMIT 1), ");
        // ... jika belum ada tagihan sama sekali, nominalnya 0.
        sql.append("0) AS nominal, ");
        sql.append("COUNT(DISTINCT p.bulan) AS total, ");
        sql.append("COUNT(DISTINCT CASE WHEN p.status = 'lunas' THEN p.bulan END) AS lunas ");
        sql.append("FROM siswa s ");
        sql.append("LEFT JOIN Pembayaran p ON p.id_siswa = s.id_siswa AND p.tahun_ajaran = ? ");
        if (!kata.isEmpty()) {
            sql.append("WHERE (s.nama LIKE ? OR s.kelas LIKE ? OR CAST(s.id_siswa AS TEXT) LIKE ?) ");
        }
        sql.append("GROUP BY s.id_siswa, s.nama, s.kelas ORDER BY s.id_siswa");

        // try-with-resources: koneksi dan statement otomatis ditutup.
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            // Mengisi tanda tanya (?) pada query sesuai urutannya.
            int i = 1;
            ps.setString(i++, tahun);
            ps.setString(i++, tahun);
            ps.setString(i++, tahun);
            if (!kata.isEmpty()) {
                String pola = "%" + kata + "%";
                ps.setString(i++, pola);
                ps.setString(i++, pola);
                ps.setString(i++, pola);
            }

            modelSiswa.setRowCount(0); // kosongkan tabel sebelum diisi ulang
            try (ResultSet rs = ps.executeQuery()) {
                int nomor = 1;
                while (rs.next()) {
                    int total = rs.getInt("total");
                    int lunas = rs.getInt("lunas");
                    // Menentukan teks status dari jumlah bulan yang sudah lunas.
                    String status;
                    if (total == 0) {
                        status = "Belum ada tagihan";
                    } else if (lunas >= total) {
                        status = "Lunas";
                    } else {
                        status = "Belum lunas (" + lunas + "/" + total + " bulan)";
                    }
                    modelSiswa.addRow(new Object[]{
                        nomor++, rs.getInt("id_siswa"), rs.getString("nama"),
                        rs.getString("kelas"), rs.getInt("nominal"), status});
                }
            }
        } catch (SQLException e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal memuat data siswa", e);
            JOptionPane.showMessageDialog(this,
                    "Gagal mengambil data siswa: " + e.getMessage(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nisSebelumnya != null) {
            pilihBarisNis(nisSebelumnya); // pulihkan pilihan baris sebelumnya
        }
    }

    /** NIS baris yang sedang dipilih tanpa menampilkan peringatan (dipakai saat memuat ulang). */
    private Integer nisSebelumnyaTerpilih() {
        int baris = TabelDataSiswa.getSelectedRow();
        if (baris == -1) {
            return null;
        }
        // convertRowIndexToModel: baris yang tampil bisa berbeda dengan baris model jika tabel diurutkan.
        return (Integer) modelSiswa.getValueAt(TabelDataSiswa.convertRowIndexToModel(baris), 1);
    }

    /** Memilih (dan menggulir ke) baris dengan NIS tertentu. */
    private void pilihBarisNis(int nis) {
        for (int m = 0; m < modelSiswa.getRowCount(); m++) {
            if ((Integer) modelSiswa.getValueAt(m, 1) == nis) {
                int tampil = TabelDataSiswa.convertRowIndexToView(m);
                if (tampil != -1) {
                    TabelDataSiswa.setRowSelectionInterval(tampil, tampil);
                    TabelDataSiswa.scrollRectToVisible(TabelDataSiswa.getCellRect(tampil, 0, true));
                }
                return;
            }
        }
    }

    /** Baris model dari baris yang dipilih, atau -1 (dengan peringatan) jika belum memilih. */
    private int barisModelTerpilih() {
        int baris = TabelDataSiswa.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih siswa pada tabel terlebih dahulu.",
                    "Siswa belum dipilih", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return TabelDataSiswa.convertRowIndexToModel(baris);
    }

    // ------------------------------ AKSI ------------------------------

    /** Data isian dari dialog Tambah/Ubah (record = wadah data sederhana yang tidak bisa diubah). */
    private record FormSiswa(String nama, String kelas, int nominal) {
    }

    /** Potongan kode yang dijalankan di dalam satu transaksi database. */
    @FunctionalInterface
    private interface Transaksi {

        void jalankan(Connection conn) throws SQLException;
    }

    /** Menjalankan beberapa perintah SQL sebagai satu kesatuan; gagal berarti semuanya dibatalkan. */
    private void transaksi(Transaksi isi) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false); // mulai transaksi
            try {
                isi.jalankan(conn);
                conn.commit();         // semua berhasil -> simpan
            } catch (SQLException | RuntimeException e) {
                conn.rollback();       // ada yang gagal -> batalkan semua
                throw e;
            }
        }
    }

    /** Tombol "+ Tambah Siswa": tampilkan form, simpan siswa baru, lalu buat tagihannya (jika diisi). */
    private void tambahSiswa() {
        String tahun = tahunAjaranDipilih();
        FormSiswa data = tampilkanForm("Tambah Siswa", "", "", 0, tahun);
        if (data == null) {
            return; // dibatalkan
        }

        int[] nisBaru = {0}; // array 1 elemen agar bisa diisi dari dalam lambda
        try {
            transaksi(conn -> {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO siswa (nama, kelas) VALUES (?, ?)")) {
                    ps.setString(1, data.nama());
                    ps.setString(2, data.kelas());
                    ps.executeUpdate();
                }
                // Ambil NIS (id) yang baru saja dibuat oleh database.
                try (Statement st = conn.createStatement();
                     ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                    if (rs.next()) {
                        nisBaru[0] = rs.getInt(1);
                    }
                }
                if (data.nominal() > 0) {
                    buatTagihanSiswa(conn, nisBaru[0], tahun, data.nominal());
                }
            });
        } catch (SQLException e) {
            tampilkanErrorDatabase("menyimpan siswa", e);
            return;
        }

        fieldCari.setText(""); // pastikan siswa baru tidak tersaring
        muatData();
        pilihBarisNis(nisBaru[0]);
        String info = "Siswa berhasil ditambahkan dengan NIS " + nisBaru[0] + ".";
        if (data.nominal() > 0) {
            info += "\nTagihan " + rupiah(data.nominal()) + " per bulan dibuat untuk tahun ajaran " + tahun + ".";
        }
        JOptionPane.showMessageDialog(this, info, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Tombol "Ubah": ubah nama/kelas siswa dan (opsional) nominal tagihan bulan yang belum lunas. */
    private void ubahSiswa() {
        int m = barisModelTerpilih();
        if (m == -1) {
            return;
        }
        // Ambil data lama dari baris yang dipilih untuk mengisi form.
        int nis = (Integer) modelSiswa.getValueAt(m, 1);
        String namaLama = String.valueOf(modelSiswa.getValueAt(m, 2));
        String kelasLama = String.valueOf(modelSiswa.getValueAt(m, 3));
        int nominalLama = (Integer) modelSiswa.getValueAt(m, 4);
        String tahun = tahunAjaranDipilih();

        FormSiswa data = tampilkanForm("Ubah Data Siswa (NIS " + nis + ")", namaLama, kelasLama, nominalLama, tahun);
        if (data == null) {
            return;
        }

        // Nominal hanya diubah jika benar-benar berbeda dari yang tampil di tabel.
        boolean nominalBerubah = data.nominal() > 0 && data.nominal() != nominalLama;
        try {
            transaksi(conn -> {
                try (PreparedStatement ps = conn.prepareStatement(
                        "UPDATE siswa SET nama = ?, kelas = ? WHERE id_siswa = ?")) {
                    ps.setString(1, data.nama());
                    ps.setString(2, data.kelas());
                    ps.setInt(3, nis);
                    ps.executeUpdate();
                }
                if (nominalBerubah) {
                    // Bulan yang sudah lunas tidak diubah agar riwayat pembayaran tetap benar.
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE Pembayaran SET nominal = ? WHERE id_siswa = ? "
                            + "AND tahun_ajaran = ? AND COALESCE(status, '') <> 'lunas'")) {
                        ps.setInt(1, data.nominal());
                        ps.setInt(2, nis);
                        ps.setString(3, tahun);
                        ps.executeUpdate();
                    }
                    buatTagihanSiswa(conn, nis, tahun, data.nominal());
                }
            });
        } catch (SQLException e) {
            tampilkanErrorDatabase("mengubah data siswa", e);
            return;
        }

        muatData();
        pilihBarisNis(nis);
        JOptionPane.showMessageDialog(this,
                nominalBerubah
                ? "Data siswa diperbarui. Tagihan bulan yang belum lunas kini " + rupiah(data.nominal()) + "."
                : "Data siswa berhasil diperbarui.",
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Tombol "Hapus": hapus siswa beserta seluruh catatan pembayarannya setelah dikonfirmasi. */
    private void hapusSiswa() {
        int m = barisModelTerpilih();
        if (m == -1) {
            return;
        }
        int nis = (Integer) modelSiswa.getValueAt(m, 1);
        String nama = String.valueOf(modelSiswa.getValueAt(m, 2));

        // Hitung dulu berapa catatan pembayaran yang akan ikut terhapus, untuk ditampilkan di peringatan.
        int totalCatatan = 0;
        int totalLunas = 0;
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT COUNT(*), COALESCE(SUM(CASE WHEN status = 'lunas' THEN 1 ELSE 0 END), 0) "
                     + "FROM Pembayaran WHERE id_siswa = ?")) {
            ps.setInt(1, nis);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCatatan = rs.getInt(1);
                    totalLunas = rs.getInt(2);
                }
            }
        } catch (SQLException e) {
            tampilkanErrorDatabase("memeriksa data siswa", e);
            return;
        }

        String pesan = "Hapus siswa \"" + nama + "\" (NIS " + nis + ")?";
        if (totalCatatan > 0) {
            pesan += "\n\nSeluruh catatan pembayaran siswa ini ikut terhapus: "
                    + totalCatatan + " catatan, " + totalLunas + " di antaranya sudah lunas.";
        }
        pesan += "\nTindakan ini tidak dapat dibatalkan.";

        // Tombol bawaan dialog diganti "Hapus" / "Batal"; "Batal" dibuat sebagai pilihan awal agar aman.
        int pilihan = JOptionPane.showOptionDialog(this, pesan, "Konfirmasi hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Hapus", "Batal"}, "Batal");
        if (pilihan != 0) {
            return;
        }

        try {
            // Catatan pembayaran dihapus lebih dulu, baru datanya siswa.
            transaksi(conn -> {
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM Pembayaran WHERE id_siswa = ?")) {
                    ps.setInt(1, nis);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM siswa WHERE id_siswa = ?")) {
                    ps.setInt(1, nis);
                    ps.executeUpdate();
                }
            });
        } catch (SQLException e) {
            tampilkanErrorDatabase("menghapus siswa", e);
            return;
        }

        muatData();
        JOptionPane.showMessageDialog(this, "Siswa \"" + nama + "\" berhasil dihapus.",
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Membuat tagihan 12 bulan untuk satu siswa; bulan yang sudah ada tidak dibuat ulang. */
    private void buatTagihanSiswa(Connection conn, int nis, String tahun, int nominal) throws SQLException {
        // "WHERE NOT EXISTS" mencegah tagihan ganda untuk siswa + bulan + tahun yang sama.
        String sql = """
            INSERT INTO Pembayaran (id_siswa, bulan, tahun_ajaran, nominal, tgl_bayar, status)
            SELECT ?, ?, ?, ?, NULL, 'menunggak'
            WHERE NOT EXISTS (
                SELECT 1 FROM Pembayaran
                WHERE id_siswa = ? AND bulan = ? AND tahun_ajaran = ?
            )
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String bulan : URUTAN_BULAN) { // satu insert untuk setiap bulan (Juli - Juni)
                ps.setInt(1, nis);
                ps.setString(2, bulan);
                ps.setString(3, tahun);
                ps.setInt(4, nominal);
                ps.setInt(5, nis);
                ps.setString(6, bulan);
                ps.setString(7, tahun);
                ps.executeUpdate();
            }
        }
    }

    /**
     * Dialog isian siswa untuk Tambah dan Ubah. Mengembalikan null jika dibatalkan.
     * Nominal 0 berarti dikosongkan (tagihan tidak dibuat/diubah).
     */
    private FormSiswa tampilkanForm(String judul, String nama, String kelas, int nominal, String tahun) {
        JTextField fNama = new JTextField(nama, 24);
        JTextField fKelas = new JTextField(kelas, 24);
        JTextField fNominal = new JTextField(nominal > 0 ? String.valueOf(nominal) : "", 24);
        fNama.putClientProperty("JTextField.placeholderText", "Contoh: Budi Santoso");
        fKelas.putClientProperty("JTextField.placeholderText", "Contoh: X IPA 1");
        fNominal.putClientProperty("JTextField.placeholderText", "Contoh: 500000 (boleh dikosongkan)");

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 4));
        form.add(new JLabel("Nama siswa"));
        form.add(fNama);
        form.add(new JLabel("Kelas"));
        form.add(fKelas);
        form.add(new JLabel("Tagihan per bulan (Rp) - tahun ajaran " + tahun));
        form.add(fNominal);

        // Dialog diulang terus sampai isiannya valid atau pengguna menekan Batal.
        while (true) {
            int pilihan = JOptionPane.showConfirmDialog(this, form, judul,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (pilihan != JOptionPane.OK_OPTION) {
                return null;
            }

            String namaBaru = fNama.getText().trim();
            String kelasBaru = fKelas.getText().trim();
            String angka = fNominal.getText().replaceAll("[^0-9]", ""); // buang titik/koma/"Rp"

            if (namaBaru.isEmpty() || kelasBaru.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama siswa dan kelas wajib diisi.",
                        "Data belum lengkap", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            int nominalBaru = 0;
            if (!angka.isEmpty()) {
                try {
                    nominalBaru = Integer.parseInt(angka);
                } catch (NumberFormatException e) {
                    nominalBaru = -1; // terlalu besar untuk int -> dianggap tidak valid
                }
                if (nominalBaru <= 0 || nominalBaru > 100_000_000) {
                    JOptionPane.showMessageDialog(this,
                            "Nominal tagihan harus berupa angka lebih dari 0, contoh: 500000.",
                            "Nominal tidak valid", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
            }
            return new FormSiswa(namaBaru, kelasBaru, nominalBaru);
        }
    }

    /** Menampilkan dan mencatat (log) kesalahan database dengan pesan yang seragam. */
    private void tampilkanErrorDatabase(String aksi, SQLException e) {
        logger.log(java.util.logging.Level.SEVERE, "Gagal " + aksi, e);
        JOptionPane.showMessageDialog(this, "Gagal " + aksi + ": " + e.getMessage(),
                "Database error", JOptionPane.ERROR_MESSAGE);
    }

    // =====================================================================
    // KELAS BANTU DESAIN (kelas bersarang)
    // =====================================================================

    // ---------------------------- LATAR KACA ----------------------------

    /**
     * Panel latar seluruh jendela: foto hutan berkabut yang digelapkan tipis.
     * Selain menggambar latar, panel ini menyimpan versi BURAM dari latar itu.
     * Panel kaca (PanelKacaUI) memotong bagian buram tersebut sebagai isi kacanya,
     * itulah yang membuat efek "frosted glass" yang sebenarnya.
     */
    static class LatarKaca extends JPanel {

        private final BufferedImage foto;     // foto asli (forest.jpg)
        private BufferedImage latarJadi;      // foto yang sudah dipotong pas jendela + digelapkan
        private BufferedImage latarBuram;     // versi buram dari latarJadi

        LatarKaca() {
            foto = muatFoto();
            setOpaque(true);
        }

        /** Memuat foto latar yang sama dengan halaman Login. */
        private static BufferedImage muatFoto() {
            try {
                java.net.URL url = DataSiswa.class.getResource("/gambar/forest.jpg");
                return url == null ? null : ImageIO.read(url);
            } catch (java.io.IOException e) {
                return null; // jika gagal, dipakai latar warna polos hijau gelap
            }
        }

        /** Membuat ulang gambar latar bila ukuran jendela berubah. */
        private void siapkan(int w, int h) {
            if (w <= 0 || h <= 0) {
                return;
            }
            if (latarJadi != null && latarJadi.getWidth() == w && latarJadi.getHeight() == h) {
                return; // ukuran sama, tidak perlu dibuat ulang
            }
            BufferedImage hasil = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = hasil.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            if (foto != null) {
                // Mode "cover": foto diperbesar sampai menutup seluruh jendela, lalu dipusatkan.
                double skala = Math.max((double) w / foto.getWidth(), (double) h / foto.getHeight());
                int fw = (int) Math.ceil(foto.getWidth() * skala);
                int fh = (int) Math.ceil(foto.getHeight() * skala);
                g.drawImage(foto, (w - fw) / 2, (h - fh) / 2, fw, fh, null);
            } else {
                g.setPaint(new GradientPaint(0, 0, new Color(70, 90, 80), 0, h, new Color(14, 34, 26)));
                g.fillRect(0, 0, w, h);
            }
            // Lapisan gelap tipis agar teks putih di atas kaca tetap jelas terbaca.
            g.setPaint(new GradientPaint(0, 0, new Color(0, 0, 0, 100), 0, h, new Color(0, 0, 0, 55)));
            g.fillRect(0, 0, w, h);
            g.dispose();
            latarJadi = hasil;
            latarBuram = buramkan(hasil);
        }

        /** Versi buram latar; dipakai PanelKacaUI. Mengembalikan null jika panel belum punya ukuran. */
        BufferedImage getBuram() {
            siapkan(getWidth(), getHeight());
            return latarBuram;
        }

        @Override
        protected void paintComponent(Graphics g) {
            siapkan(getWidth(), getHeight());
            if (latarJadi != null) {
                g.drawImage(latarJadi, 0, 0, null);
            }
        }

        /**
         * Membuat gambar buram: gambar dikecilkan (supaya cepat), dikaburkan beberapa kali
         * dengan box blur, lalu diperbesar kembali ke ukuran semula.
         */
        private static BufferedImage buramkan(BufferedImage sumber) {
            final int pengecil = 4;
            int w = sumber.getWidth();
            int h = sumber.getHeight();
            int kw = Math.max(1, w / pengecil);
            int kh = Math.max(1, h / pengecil);

            BufferedImage kecil = new BufferedImage(kw, kh, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = kecil.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(sumber, 0, 0, kw, kh, null);
            g.dispose();

            int[] piksel = kecil.getRGB(0, 0, kw, kh, null, 0, kw);
            for (int ulang = 0; ulang < 3; ulang++) { // 3x kabur mendatar + menurun = buram halus
                piksel = kabur(piksel, kw, kh, 6, true);
                piksel = kabur(piksel, kw, kh, 6, false);
            }
            kecil.setRGB(0, 0, kw, kh, piksel, 0, kw);

            BufferedImage besar = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = besar.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(kecil, 0, 0, w, h, null);
            g2.dispose();
            return besar;
        }

        /** Satu putaran box blur satu arah: setiap piksel diganti rata-rata tetangganya sejauh radius. */
        private static int[] kabur(int[] a, int w, int h, int radius, boolean mendatar) {
            int[] hasil = new int[a.length];
            int jumlah = radius * 2 + 1;
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int r = 0, gg = 0, b = 0;
                    for (int k = -radius; k <= radius; k++) {
                        // Math.max/min menahan koordinat agar tidak keluar dari gambar.
                        int xx = mendatar ? Math.max(0, Math.min(w - 1, x + k)) : x;
                        int yy = mendatar ? y : Math.max(0, Math.min(h - 1, y + k));
                        int p = a[yy * w + xx];
                        r += (p >> 16) & 0xFF;
                        gg += (p >> 8) & 0xFF;
                        b += p & 0xFF;
                    }
                    hasil[y * w + x] = ((r / jumlah) << 16) | ((gg / jumlah) << 8) | (b / jumlah);
                }
            }
            return hasil;
        }
    }

    /**
     * Cara menggambar panel kaca: potongan latar buram + warna tint transparan
     * + kilau tipis di bagian atas + garis tepi terang. Sudut membulat dan halus.
     * Dipasang dengan panel.setUI(...) sehingga panel dari palette tidak perlu diganti.
     */
    static class PanelKacaUI extends BasicPanelUI {

        private final int sudut;                                 // kelengkungan sudut (px)
        private final Color tint;                                // warna isi kaca (transparan)
        private final Color garis;                               // warna garis tepi
        private final BiConsumer<Graphics2D, JComponent> hiasan; // gambar tambahan (boleh null)

        // Hasil gambar disimpan (cache) supaya tidak dihitung ulang setiap repaint.
        private BufferedImage cache;
        private BufferedImage cacheBuram;
        private int cacheW, cacheH, cacheX, cacheY;

        PanelKacaUI(int sudut, Color tint, Color garis, BiConsumer<Graphics2D, JComponent> hiasan) {
            this.sudut = sudut;
            this.tint = tint;
            this.garis = garis;
            this.hiasan = hiasan;
        }

        @Override
        public void update(Graphics g, JComponent c) {
            paint(g, c); // tanpa mengisi latar solid seperti UI bawaan
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            int w = c.getWidth();
            int h = c.getHeight();
            if (w < 4 || h < 4) {
                return;
            }
            // Cari panel latar di atasnya untuk mengambil potongan gambar buramnya.
            LatarKaca latar = (LatarKaca) SwingUtilities.getAncestorOfClass(LatarKaca.class, c);
            BufferedImage buram = latar == null ? null : latar.getBuram();
            Point posisi = latar == null ? new Point() : SwingUtilities.convertPoint(c, 0, 0, latar);

            // Gambar ulang hanya bila ukuran, posisi, atau latar berubah.
            if (cache == null || cacheW != w || cacheH != h || cacheX != posisi.x
                    || cacheY != posisi.y || cacheBuram != buram) {
                cache = buatKaca(w, h, buram, posisi);
                cacheW = w;
                cacheH = h;
                cacheX = posisi.x;
                cacheY = posisi.y;
                cacheBuram = buram;
            }
            g.drawImage(cache, 0, 0, null);

            // Hiasan tambahan (misalnya ikon admin di sidebar) digambar di atas kaca.
            if (hiasan != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                hiasan.accept(g2, c);
                g2.dispose();
            }

            // Garis tepi yang bergantung pada fokus (dipakai kapsul pencarian) digambar terpisah
            // karena nilainya bisa berubah tanpa ukuran panel berubah.
            if (Boolean.TRUE.equals(c.getClientProperty("fokus"))) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(MINT);
                g2.setStroke(new BasicStroke(1.8f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w - 3, h - 3, sudut, sudut));
                g2.dispose();
            }
        }

        /** Menyusun gambar kaca (ARGB) seukuran panel. */
        private BufferedImage buatKaca(int w, int h, BufferedImage buram, Point posisi) {
            BufferedImage hasil = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = hasil.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            Shape bentuk = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, sudut, sudut);

            // 1) Bentuk kaca sebagai "cetakan"; isi buram hanya muncul di dalam cetakan (AlphaComposite.SrcIn).
            g.setColor(Color.WHITE);
            g.fill(bentuk);
            if (buram != null) {
                g.setComposite(AlphaComposite.SrcIn);
                g.drawImage(buram, 0, 0, w, h, posisi.x, posisi.y, posisi.x + w, posisi.y + h, null);
                g.setComposite(AlphaComposite.SrcOver);
            }

            // 2) Warna tint tipis di atas latar buram.
            g.setColor(tint);
            g.fill(bentuk);

            // 3) Kilau: gradasi putih transparan di bagian atas, memberi kesan permukaan kaca.
            g.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 46), 0, h * 0.55f, new Color(255, 255, 255, 0)));
            g.fill(bentuk);

            // 4) Garis tepi terang.
            g.setColor(garis);
            g.setStroke(new BasicStroke(1.2f));
            g.draw(bentuk);

            g.dispose();
            return hasil;
        }
    }

    // ------------------------------ TOMBOL ------------------------------

    /**
     * Cara menggambar tombol bergaya kaca. Satu kelas dipakai untuk semua tombol di halaman ini;
     * tampilan dibedakan oleh enum Gaya.
     */
    static class TombolKacaUI extends BasicButtonUI {

        /** Jenis tampilan tombol. */
        enum Gaya {
            MENU,       // tombol menu sidebar: pil kaca saat aktif/hover + garis aksen mint di kiri
            KELUAR,     // tombol Keluar: hanya teks, berubah merah muda saat hover
            UTAMA,      // Tambah Siswa: terisi mint
            SEKUNDER,   // Ubah: garis mint
            BAHAYA      // Hapus: garis merah
        }

        private final Gaya gaya;

        TombolKacaUI(Gaya gaya) {
            this.gaya = gaya;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel m = b.getModel();
            boolean hover = m.isRollover();
            boolean ditekan = m.isPressed() && m.isArmed();
            boolean aktif = b.isSelected();
            boolean mati = !b.isEnabled();
            int w = c.getWidth();
            int h = c.getHeight();

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            float alfa = mati ? 0.45f : 1f; // tombol nonaktif dibuat pudar
            g2.setComposite(AlphaComposite.SrcOver.derive(alfa));

            switch (gaya) {
                case MENU -> {
                    // Pil setinggi tetap (34 px) di tengah tombol, jadi tampilannya seragam
                    // walaupun tinggi tombol dari palette berbeda-beda.
                    int tinggi = Math.min(h - 2, 34);
                    float y = (h - tinggi) / 2f;
                    RoundRectangle2D pil = new RoundRectangle2D.Float(8, y, w - 17, tinggi, 18, 18);
                    if (aktif) {
                        g2.setColor(new Color(255, 255, 255, 64));
                        g2.fill(pil);
                        g2.setColor(new Color(255, 255, 255, 125));
                        g2.setStroke(new BasicStroke(1.1f));
                        g2.draw(pil);
                        // Garis aksen kecil di kiri, menandai menu yang sedang aktif (seperti referensi).
                        g2.setColor(new Color(190, 232, 208));
                        g2.fill(new RoundRectangle2D.Float(3, y + 7, 4, tinggi - 14, 4, 4));
                    } else if (hover) {
                        g2.setColor(new Color(255, 255, 255, ditekan ? 50 : 30));
                        g2.fill(pil);
                    }
                }
                case UTAMA -> {
                    RoundRectangle2D bentuk = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 14, 14);
                    g2.setColor(ditekan ? new Color(140, 178, 156) : hover ? new Color(192, 224, 205) : MINT);
                    g2.fill(bentuk);
                    g2.setColor(new Color(255, 255, 255, 140));
                    g2.draw(bentuk);
                }
                case SEKUNDER, BAHAYA -> {
                    boolean merah = gaya == Gaya.BAHAYA;
                    RoundRectangle2D bentuk = new RoundRectangle2D.Float(0.5f, 0.5f, w - 2, h - 2, 14, 14);
                    Color dasar = merah ? new Color(255, 120, 120) : MINT_TERANG;
                    g2.setColor(new Color(dasar.getRed(), dasar.getGreen(), dasar.getBlue(),
                            ditekan ? 85 : hover ? 60 : 28));
                    g2.fill(bentuk);
                    g2.setColor(merah ? new Color(255, 150, 150, 230) : new Color(190, 228, 208, 235));
                    g2.setStroke(new BasicStroke(1.3f));
                    g2.draw(bentuk);
                }
                case KELUAR -> {
                    // Tidak ada latar; perubahan warna teks dan ikon sudah cukup sebagai efek hover.
                }
            }
            g2.dispose();

            // Ikon dan teks digambar oleh kelas induk; teksnya mengikuti paintText di bawah.
            super.paint(g, c);
        }

        /** Menentukan warna teks sesuai gaya dan keadaan tombol (hover, aktif, nonaktif). */
        @Override
        protected void paintText(Graphics g, JComponent c, Rectangle ruangTeks, String teks) {
            AbstractButton b = (AbstractButton) c;
            boolean hover = b.getModel().isRollover();
            Color warna;
            switch (gaya) {
                case MENU -> warna = (b.isSelected() || hover) ? Color.WHITE : new Color(255, 255, 255, 215);
                case KELUAR -> warna = hover ? new Color(255, 170, 170) : new Color(255, 255, 255, 225);
                case UTAMA -> warna = TEKS;
                case SEKUNDER -> warna = new Color(228, 246, 235);
                default -> warna = new Color(255, 182, 182);
            }
            if (!b.isEnabled()) {
                warna = new Color(warna.getRed(), warna.getGreen(), warna.getBlue(), 115);
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(b.getFont());
            g2.setColor(warna);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(teks, ruangTeks.x, ruangTeks.y + fm.getAscent());
            g2.dispose();
        }
    }

    // -------------------------------- IKON --------------------------------

    /**
     * Ikon garis/bidang sederhana yang digambar dengan Java2D (vektor).
     * Keuntungannya: tajam di ukuran apa pun, tidak butuh file gambar, dan warnanya
     * bisa mengikuti keadaan tombol (aktif / hover).
     * Semua ikon dirancang pada kotak 24 x 24 lalu diskalakan ke ukuran yang diminta.
     */
    static class IkonVektor implements Icon {

        /** Daftar ikon yang tersedia. */
        enum Jenis {
            DASHBOARD,  // empat kotak
            SISWA,      // kepala + bahu
            TAGIHAN,    // lembar dengan garis teks
            RIWAYAT,    // jam
            LAPORAN,    // grafik batang
            PENGGUNA,   // orang (garis luar) untuk admin
            KELUAR,     // pintu dengan panah keluar
            LOGO        // logo aplikasi: topi wisuda + koin SPP dalam lencana kaca
        }

        private final Jenis jenis;
        private final int ukuran;

        IkonVektor(Jenis jenis, int ukuran) {
            this.jenis = jenis;
            this.ukuran = ukuran;
        }

        @Override
        public int getIconWidth() {
            return ukuran;
        }

        @Override
        public int getIconHeight() {
            return ukuran;
        }

        /** Logo aplikasi sebagai gambar (untuk ikon jendela), digambar di atas latar transparan. */
        static BufferedImage buatGambarLogo(int px) {
            BufferedImage gambar = new BufferedImage(px, px, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = gambar.createGraphics();
            new IkonVektor(Jenis.LOGO, px).lukis(g, 0, 0, Color.WHITE);
            g.dispose();
            return gambar;
        }

        /** Dipanggil Swing ketika ikon dipasang pada tombol; warna mengikuti keadaan tombol. */
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color warna = new Color(255, 255, 255, 215);
            if (c instanceof AbstractButton b) {
                if (b.isSelected() || b.getModel().isRollover()) {
                    warna = Color.WHITE;
                }
                if (!b.isEnabled()) {
                    warna = new Color(255, 255, 255, 110);
                }
            }
            lukis((Graphics2D) g, x, y, warna);
        }

        /** Menggambar ikon pada posisi (x, y) dengan warna tertentu. */
        void lukis(Graphics2D asli, int x, int y, Color warna) {
            Graphics2D g = (Graphics2D) asli.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g.translate(x, y);
            g.scale(ukuran / 24.0, ukuran / 24.0); // semua koordinat di bawah memakai kotak 24 x 24
            g.setColor(warna);
            g.setStroke(new BasicStroke(1.9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            switch (jenis) {
                case DASHBOARD -> {
                    // Empat kotak membulat (2 x 2).
                    g.fill(new RoundRectangle2D.Double(3, 3, 8, 8, 2.5, 2.5));
                    g.fill(new RoundRectangle2D.Double(13, 3, 8, 8, 2.5, 2.5));
                    g.fill(new RoundRectangle2D.Double(3, 13, 8, 8, 2.5, 2.5));
                    g.fill(new RoundRectangle2D.Double(13, 13, 8, 8, 2.5, 2.5));
                }
                case SISWA -> {
                    // Kepala (lingkaran) dan bahu (setengah lingkaran).
                    g.fill(new Ellipse2D.Double(8, 3.5, 8, 8));
                    g.fill(new Arc2D.Double(4, 13, 16, 16, 0, 180, Arc2D.CHORD));
                }
                case TAGIHAN -> {
                    // Lembar dokumen dengan tiga garis teks.
                    g.draw(new RoundRectangle2D.Double(5, 3, 14, 18, 3.5, 3.5));
                    g.draw(new Line2D.Double(8.5, 9, 15.5, 9));
                    g.draw(new Line2D.Double(8.5, 13, 15.5, 13));
                    g.draw(new Line2D.Double(8.5, 17, 12.5, 17));
                }
                case RIWAYAT -> {
                    // Jam: lingkaran + jarum jam dan menit.
                    g.draw(new Ellipse2D.Double(3, 3, 18, 18));
                    Path2D jarum = new Path2D.Double();
                    jarum.moveTo(12, 7);
                    jarum.lineTo(12, 12);
                    jarum.lineTo(15.5, 14);
                    g.draw(jarum);
                }
                case LAPORAN -> {
                    // Tiga batang grafik dengan tinggi berbeda.
                    g.fill(new RoundRectangle2D.Double(4, 12, 4.5, 9, 1.5, 1.5));
                    g.fill(new RoundRectangle2D.Double(9.8, 4, 4.5, 17, 1.5, 1.5));
                    g.fill(new RoundRectangle2D.Double(15.5, 9, 4.5, 12, 1.5, 1.5));
                }
                case PENGGUNA -> {
                    // Orang bergaya garis: kepala dan bahu.
                    g.draw(new Ellipse2D.Double(8, 3.5, 8, 8));
                    g.draw(new Arc2D.Double(4, 14, 16, 14, 0, 180, Arc2D.OPEN));
                }
                case LOGO -> {
                    // Lencana kaca bersudut membulat sebagai dasar logo.
                    RoundRectangle2D lencana = new RoundRectangle2D.Double(0.6, 0.6, 22.8, 22.8, 8, 8);
                    g.setPaint(new GradientPaint(0, 0, new Color(120, 190, 150, 235), 0, 24, new Color(34, 92, 64, 235)));
                    g.fill(lencana);
                    g.setColor(new Color(255, 255, 255, 150));
                    g.setStroke(new BasicStroke(1f));
                    g.draw(lencana);

                    // Topi wisuda: papan belah ketupat + alas melengkung + tali jumbai.
                    g.setColor(Color.WHITE);
                    Path2D papan = new Path2D.Double();
                    papan.moveTo(12, 3.6);
                    papan.lineTo(21, 7.6);
                    papan.lineTo(12, 11.6);
                    papan.lineTo(3, 7.6);
                    papan.closePath();
                    g.fill(papan);
                    Path2D alas = new Path2D.Double();
                    alas.moveTo(7, 10.6);
                    alas.lineTo(7, 13.2);
                    alas.quadTo(12, 16.4, 17, 13.2);
                    alas.lineTo(17, 10.6);
                    alas.lineTo(12, 13);
                    alas.closePath();
                    g.setColor(new Color(255, 255, 255, 215));
                    g.fill(alas);
                    g.setColor(new Color(255, 226, 150));
                    g.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g.draw(new Line2D.Double(21, 7.6, 21, 12.4));
                    g.fill(new Ellipse2D.Double(20.1, 12, 1.8, 2.2));

                    // Koin di bawah topi (melambangkan pembayaran SPP).
                    g.setColor(new Color(255, 226, 150));
                    g.fill(new Ellipse2D.Double(8.2, 15.6, 7.6, 7.6));
                    g.setColor(new Color(34, 92, 64));
                    g.setStroke(new BasicStroke(1.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g.draw(new Ellipse2D.Double(9.6, 17, 5.2, 5.2)); // cincin dalam koin
                }
                case KELUAR -> {
                    // Kurung pintu di kiri dan panah menuju keluar di kanan.
                    Path2D pintu = new Path2D.Double();
                    pintu.moveTo(10, 4);
                    pintu.lineTo(5, 4);
                    pintu.lineTo(5, 20);
                    pintu.lineTo(10, 20);
                    g.draw(pintu);
                    g.draw(new Line2D.Double(9.5, 12, 20, 12));
                    Path2D kepala = new Path2D.Double();
                    kepala.moveTo(16, 8);
                    kepala.lineTo(20, 12);
                    kepala.lineTo(16, 16);
                    g.draw(kepala);
                }
            }
            g.dispose();
        }
    }

    // ---------------------------- RENDERER TABEL ----------------------------

    /**
     * Renderer judul kolom: pita hijau pucat dengan huruf tebal, garis tipis di bawahnya.
     * Perataan teks mengikuti isi kolomnya (NO/NIS tengah, TAGIHAN kanan, lainnya kiri).
     */
    static class HeaderRenderer extends DefaultTableCellRenderer {

        static final Color LATAR_HEADER = new Color(226, 236, 230);

        @Override
        public Component getTableCellRendererComponent(JTable tabel, Object nilai, boolean dipilih,
                boolean fokus, int baris, int kolom) {
            super.getTableCellRendererComponent(tabel, nilai, false, false, baris, kolom);
            int modelKolom = tabel.convertColumnIndexToModel(kolom);
            setHorizontalAlignment(modelKolom <= 1 ? SwingConstants.CENTER
                    : modelKolom == 4 ? SwingConstants.RIGHT : SwingConstants.LEFT);
            setFont(tabel.getFont().deriveFont(java.awt.Font.BOLD, 12f));
            setForeground(new Color(52, 82, 68));
            setBackground(LATAR_HEADER);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(170, 190, 180)),
                    BorderFactory.createEmptyBorder(0, 10, 0, 10)));
            return this;
        }
    }

    /** Renderer sel: perataan, format teks, warna selang-seling, dan warna seleksi mint transparan. */
    static class SelRenderer extends DefaultTableCellRenderer {

        private final int rata;                      // perataan teks (kiri/tengah/kanan)
        private final Function<Object, String> format; // pengubah nilai menjadi teks (boleh null)
        protected Color latarBaris;                  // warna latar baris saat ini (boleh null = transparan)

        SelRenderer(int rata, Function<Object, String> format) {
            this.rata = rata;
            this.format = format;
        }

        /** Teks yang ditampilkan; subclass dapat menimpanya (contoh: nomor urut). */
        protected String teks(Object nilai, int barisTampil) {
            if (format != null) {
                return format.apply(nilai);
            }
            return nilai == null ? "" : nilai.toString();
        }

        protected Color warnaTeks(Object nilai) {
            return TEKS;
        }

        protected boolean tebal(Object nilai) {
            return false;
        }

        @Override
        public Component getTableCellRendererComponent(JTable tabel, Object nilai, boolean dipilih,
                boolean fokus, int baris, int kolom) {
            super.getTableCellRendererComponent(tabel, teks(nilai, baris), dipilih, false, baris, kolom);
            setHorizontalAlignment(rata);
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            setForeground(warnaTeks(nilai));
            setFont(tabel.getFont().deriveFont(tebal(nilai) ? java.awt.Font.BOLD : java.awt.Font.PLAIN));
            // Warna latar disimpan lalu digambar sendiri (paintComponent) karena memakai transparansi.
            latarBaris = dipilih ? BARIS_DIPILIH : (baris % 2 == 1 ? BARIS_GANJIL : null);
            return this;
        }

        @Override
        public boolean isOpaque() {
            return false; // latar digambar manual di paintComponent
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (latarBaris != null) {
                g.setColor(latarBaris);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            super.paintComponent(g);
        }
    }

    /** Renderer kolom status: teks ditampilkan sebagai pil berwarna (hijau = lunas, oranye = menunggak). */
    static class StatusRenderer extends SelRenderer {

        private String label = "";
        private Color latarPil = Color.LIGHT_GRAY;
        private Color teksPil = TEKS;

        StatusRenderer() {
            super(SwingConstants.LEFT, null);
        }

        @Override
        protected String teks(Object nilai, int barisTampil) {
            return ""; // teks digambar sendiri di dalam pil
        }

        @Override
        public Component getTableCellRendererComponent(JTable tabel, Object nilai, boolean dipilih,
                boolean fokus, int baris, int kolom) {
            super.getTableCellRendererComponent(tabel, nilai, dipilih, fokus, baris, kolom);
            String status = String.valueOf(nilai);
            label = status.toUpperCase();
            // Warna pil ditentukan dari awal teks status.
            if (status.startsWith("Lunas")) {
                latarPil = new Color(198, 240, 214);
                teksPil = new Color(21, 110, 61);
            } else if (status.startsWith("Belum lunas")) {
                latarPil = new Color(254, 228, 192);
                teksPil = new Color(146, 64, 14);
            } else {
                latarPil = new Color(224, 231, 235);
                teksPil = new Color(75, 90, 105);
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // gambar latar baris
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(getFont().deriveFont(java.awt.Font.BOLD, 11f));
            FontMetrics fm = g2.getFontMetrics();
            int lebarPil = fm.stringWidth(label) + 24;
            int tinggiPil = 22;
            int x = 10;
            int y = (getHeight() - tinggiPil) / 2;
            g2.setColor(latarPil);
            g2.fill(new RoundRectangle2D.Float(x, y, lebarPil, tinggiPil, tinggiPil, tinggiPil)); // sudut penuh = kapsul
            g2.setColor(teksPil);
            g2.drawString(label, x + 12, y + (tinggiPil - fm.getHeight()) / 2 + fm.getAscent());
            g2.dispose();
        }
    }

    public static void main(String args[]) {
        // Tema FlatLaf yang sama dengan halaman Login.
        AppTheme.setup();
        java.awt.EventQueue.invokeLater(() -> new DataSiswa().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxTahunAjaran;
    private javax.swing.JLabel Date;
    private javax.swing.JButton Hapus;
    private javax.swing.JPanel SideBar;
    private javax.swing.JTable TabelDataSiswa;
    private javax.swing.JButton TambahSiswa;
    private javax.swing.JLabel Time;
    private javax.swing.JButton Ubah;
    private javax.swing.JLabel cari_nis_nama_kelas;
    private javax.swing.JToggleButton dashboard_variable;
    private javax.swing.JToggleButton data_variable;
    private javax.swing.JButton exit;
    private javax.swing.JLabel header;
    private javax.swing.JLabel header2;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton laporan_variable;
    private javax.swing.JToggleButton riwayat_variable;
    private javax.swing.JToggleButton tagihan_variable;
    // End of variables declaration//GEN-END:variables
}
