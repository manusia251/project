package controller;

import controller.DataSiswa.HeaderRenderer;
import controller.DataSiswa.IkonVektor;
import controller.DataSiswa.PanelKacaUI;
import controller.DataSiswa.SelRenderer;
import controller.DataSiswa.TombolKacaUI;
import dao.Database;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.function.Function;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import properties.AppTheme;

/**
 * Halaman Riwayat Transaksi (daftar pembayaran lunas, pembatalan, dan penghapusan catatan ganda).
 *
 * Cara kerja file ini (sama dengan DataSiswa.java):
 *  1. initComponents()  -> kode GUI Builder NetBeans (hasil drag and drop, tidak diubah).
 *  2. siapkanTampilan() -> "mendandani" komponen dengan tema glassmorphism yang sama dengan
 *                          halaman Data Siswa (kelas desainnya dipakai bersama dari DataSiswa).
 *  3. siapkanLogika()   -> menghubungkan tombol, tabel, dan combo box dengan aksi.
 *  4. muatData()        -> mengambil riwayat dari database SQLite lalu menampilkannya di tabel.
 */
public class Riwayat extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Riwayat.class.getName());

    // Warna khusus halaman ini (warna tema lain dipakai dari DataSiswa agar tampilannya seragam).
    private static final Color MERAH_TEKS = new Color(153, 27, 27);
    private static final Color BARIS_DUPLIKAT = new Color(254, 202, 202, 150);

    private static final String KET_LUNAS = "Lunas";
    private static final String KET_DUPLIKAT = "DUPLIKAT";
    private static final int KOLOM_KWITANSI = 0;
    private static final int KOLOM_NAMA = 3;
    private static final int KOLOM_BULAN = 5;
    private static final int KOLOM_KETERANGAN = 7;

    // Catatan lunas dianggap duplikat jika ada catatan lunas lain untuk siswa, bulan,
    // dan tahun ajaran yang sama dengan nomor kwitansi lebih kecil (yang pertama dianggap asli).
    private static final String KONDISI_DUPLIKAT = """
        EXISTS (SELECT 1 FROM Pembayaran d
                WHERE d.id_siswa = p.id_siswa
                  AND d.bulan = p.bulan
                  AND d.tahun_ajaran = p.tahun_ajaran
                  AND d.status = 'lunas'
                  AND d.id_pembayaran < p.id_pembayaran)""";

    // Kolom: NO. KWITANSI, TANGGAL, NIS, NAMA SISWA, KELAS, BULAN, JUMLAH, KETERANGAN.
    private final DefaultTableModel modelRiwayat = new DefaultTableModel(
            new Object[]{"NO. KWITANSI", "TANGGAL", "NIS", "NAMA SISWA", "KELAS", "BULAN", "JUMLAH", "KETERANGAN"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // tabel hanya untuk dilihat
        }

        @Override
        public Class<?> getColumnClass(int column) {
            // Angka dan tanggal diurutkan sebagai angka/tanggal, bukan sebagai teks.
            return switch (column) {
                case 0, 2, 6 -> Integer.class;
                case 1 -> LocalDate.class;
                default -> String.class;
            };
        }
    };

    // Perataan teks tiap kolom (dipakai isi sel dan judul kolom agar sejajar).
    private static final int[] RATA = {SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER,
        SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.RIGHT, SwingConstants.LEFT};

    private Timer timerJam;         // timer yang menggerakkan jam dan tanggal

    public Riwayat() {
        // Latar hutan dipasang SEBELUM initComponents() supaya GUI Builder
        // menaruh semua komponen di atas panel latar ini.
        setContentPane(new DataSiswa.LatarKaca());
        initComponents();
        siapkanTampilan();   // finishing desain (di luar kode GUI Builder)
        siapkanLogika();     // menghubungkan tombol, tabel, dan combo box
        jam();               // menjalankan jam dan tanggal
        muatData();          // mengisi tabel untuk pertama kali
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Riwayat_variable = new javax.swing.JToggleButton();
        Tagihan_variable = new javax.swing.JToggleButton();
        Data_siswa_variable = new javax.swing.JToggleButton();
        Laporan_variable = new javax.swing.JToggleButton();
        Dashboard = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Date = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Time = new javax.swing.JLabel();
        ComboBoxTahunAjaran = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableRiwayatTransaksi = new javax.swing.JTable();
        HapusSemuaDuplikat = new javax.swing.JButton();
        HapusTransaksi = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Riwayat_variable.setText("Riwayat");
        Riwayat_variable.addActionListener(this::Riwayat_variableActionPerformed);

        Tagihan_variable.setText("Tagihan");
        Tagihan_variable.addActionListener(this::Tagihan_variableActionPerformed);

        Data_siswa_variable.setText("Data Siswa");
        Data_siswa_variable.addActionListener(this::Data_siswa_variableActionPerformed);

        Laporan_variable.setText("Laporan");
        Laporan_variable.addActionListener(this::Laporan_variableActionPerformed);

        Dashboard.setText("Dashboard");
        Dashboard.addActionListener(this::DashboardActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel1.setText("Sistem SPP");

        icon.setBackground(new java.awt.Color(150, 150, 150));
        icon.setText("icon");

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
                    .addComponent(Dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Data_siswa_variable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tagihan_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Riwayat_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Laporan_variable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Data_siswa_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Tagihan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Riwayat_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Laporan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 155, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        jLabel5.setText("Riwayat Transaksi");

        jLabel6.setText("Catatan setoran dan penghapusan catatan ganda");

        Date.setText("Date");

        jLabel8.setText("Tahun Ajaran");

        Time.setText("Time");

        ComboBoxTahunAjaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2026/2027", "2025/2026", "2024/2025", "2023/2024" }));

        TableRiwayatTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NO. KWITANSI", "TANGGAL", "NIS", "NAMA SISWA", "KELAS", "BULAN", "JUMLAH", "KETERANGAN"
            }
        ));
        jScrollPane1.setViewportView(TableRiwayatTransaksi);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );

        HapusSemuaDuplikat.setText("Hapus Semua Duplikat");

        HapusTransaksi.setText("Hapus Transaksi");

        jLabel11.setText("Catatan yang tidak sengaja tercatat dua kali ditandai DUPLIKAT");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Date)
                        .addGap(18, 18, 18)
                        .addComponent(Time, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBoxTahunAjaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(HapusSemuaDuplikat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HapusTransaksi)))
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
                        .addComponent(jLabel5)
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(HapusSemuaDuplikat)
                        .addComponent(HapusTransaksi))
                    .addComponent(jLabel11))
                .addGap(24, 24, 24)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 30, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Data_siswa_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Data_siswa_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new DataSiswa());
    }//GEN-LAST:event_Data_siswa_variableActionPerformed

    private void Laporan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Laporan_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Laporan());
    }//GEN-LAST:event_Laporan_variableActionPerformed

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
       // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Dashboard());
    }//GEN-LAST:event_DashboardActionPerformed

    private void Tagihan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tagihan_variableActionPerformed
            // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Tagihan());
    }//GEN-LAST:event_Tagihan_variableActionPerformed

    private void Riwayat_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Riwayat_variableActionPerformed
        // Sudah berada di halaman Riwayat; ButtonGroup menjaga tombol ini tetap aktif.
    }//GEN-LAST:event_Riwayat_variableActionPerformed

    // =====================================================================
    // FINISHING DESAIN & LOGIKA
    // Semua kode di bawah ini sengaja ditulis di luar blok "Generated Code"
    // agar tidak hilang ketika form diedit lagi lewat NetBeans Designer.
    // =====================================================================

    /** Merapikan tampilan: kaca, warna, font, tabel, dan tombol (hanya "kulit"; posisi dari palette tetap). */
    private void siapkanTampilan() {
        gayaKaca();          // panel utama dibuat seperti kaca buram
        gayaSidebar();       // logo, ikon menu, ikon admin, dan tombol keluar
        gayaHeader();        // judul halaman, jam, dan tahun ajaran
        gayaTabel();         // tabel transparan dengan label keterangan berbentuk pil
        gayaTombolAksi();    // tombol Hapus Semua Duplikat dan Hapus Transaksi

        setTitle("Sistem SPP - Riwayat Transaksi");
        pack();
    }

    /** Mengubah panel hasil drag and drop menjadi kaca buram (cara menggambar diganti, layout tetap). */
    private void gayaKaca() {
        // Sidebar: kaca gelap, di sisi kiri.
        jPanel1.setUI(new PanelKacaUI(28, new Color(6, 22, 16, 150), new Color(255, 255, 255, 70),
                (g, sidebar) -> lukisHiasanSidebar(g)));
        // Panel isi utama: kaca asap yang lebih terang dari sidebar.
        jPanel3.setUI(new PanelKacaUI(28, new Color(16, 36, 28, 105), new Color(255, 255, 255, 80), null));
        // Kartu tabel: kaca putih (frosted) agar data mudah dibaca.
        jPanel8.setUI(new PanelKacaUI(20, new Color(255, 255, 255, 222), new Color(255, 255, 255, 190), null));
        jPanel8.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6)); // jarak tabel dari tepi kartu

        // PENTING: setUI() mengembalikan panel menjadi opaque; dimatikan lagi agar latar hutan terlihat.
        for (JPanel p : new JPanel[]{jPanel1, jPanel3, jPanel8}) {
            p.setOpaque(false);
        }
    }

    /** Warna dan font judul halaman (kanan atas: tanggal, jam, tahun ajaran). */
    private void gayaHeader() {
        jLabel5.setForeground(Color.WHITE);
        for (JLabel label : new JLabel[]{jLabel6, jLabel8, jLabel11, Date, Time}) {
            label.setForeground(DataSiswa.TEKS_REDUP);
        }
        Date.setFont(Date.getFont().deriveFont(java.awt.Font.BOLD));
        Time.setFont(Time.getFont().deriveFont(java.awt.Font.BOLD));
        jLabel11.setFont(jLabel11.getFont().deriveFont(java.awt.Font.ITALIC));

        // Combo box tahun ajaran memakai warna kolom input Login (sama dengan Data Siswa).
        ComboBoxTahunAjaran.putClientProperty("FlatLaf.style",
                "background:#b8c5c5; foreground:#183024; borderColor:#dce9e2;"
                + "buttonBackground:#b8c5c5; buttonArrowColor:#183024;"
                + "selectionBackground:#a9cbb7; selectionForeground:#183024");
        ComboBoxTahunAjaran.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Sidebar bergaya kaca: logo, ikon di setiap tombol menu, ikon admin, dan tombol keluar.
     * Kolom ikon sejajar di x = 24, kolom teks sejajar di x = 56.
     */
    private void gayaSidebar() {
        jLabel1.setForeground(Color.WHITE);                // "Sistem SPP"
        jLabel3.setForeground(DataSiswa.TEKS_REDUP);       // "Pembayaran Sekolah"
        jLabel4.setForeground(new Color(190, 215, 200));   // "MENU"
        jLabel4.setText(jLabel4.getText().toUpperCase());
        jLabel4.setFont(jLabel4.getFont().deriveFont(java.awt.Font.BOLD, 11f));
        jLabel19.setForeground(Color.WHITE);               // "admin"
        jLabel19.setFont(jLabel19.getFont().deriveFont(java.awt.Font.BOLD));
        jLabel18.setForeground(DataSiswa.TEKS_REDUP);      // "Petugas TU"

        // Logo aplikasi (topi wisuda + koin) yang sama dengan halaman Data Siswa.
        icon.setIcon(new IkonVektor(IkonVektor.Jenis.LOGO, 37));
        icon.setText("");
        setIconImage(IkonVektor.buatGambarLogo(64));

        // Tombol menu: urutan tombol dan ikonnya harus sama.
        JToggleButton[] menuTombol = {Dashboard, Data_siswa_variable, Tagihan_variable,
            Riwayat_variable, Laporan_variable};
        IkonVektor.Jenis[] menuIkon = {IkonVektor.Jenis.DASHBOARD, IkonVektor.Jenis.SISWA,
            IkonVektor.Jenis.TAGIHAN, IkonVektor.Jenis.RIWAYAT, IkonVektor.Jenis.LAPORAN};

        // ButtonGroup: hanya satu menu yang boleh aktif dalam satu waktu.
        ButtonGroup menu = new ButtonGroup();
        for (int i = 0; i < menuTombol.length; i++) {
            JToggleButton tombol = menuTombol[i];
            menu.add(tombol);
            tombol.setUI(new TombolKacaUI(TombolKacaUI.Gaya.MENU));
            tombol.setIcon(new IkonVektor(menuIkon[i], 22));
            tombol.setIconTextGap(10);
            tombol.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 12));
            tombol.setMargin(new Insets(0, 0, 0, 0));
            tombol.setHorizontalAlignment(SwingConstants.LEFT);
            tombol.setFont(tombol.getFont().deriveFont(java.awt.Font.BOLD, 13f));
            tombol.setOpaque(false);
            tombol.setContentAreaFilled(false);
            tombol.setFocusPainted(false);
            tombol.setRolloverEnabled(true); // dibutuhkan agar efek hover jalan
            tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        Riwayat_variable.setSelected(true); // halaman ini = Riwayat, jadi menunya aktif

        // Tombol Keluar: hanya teks; ikonnya digambar oleh lukisHiasanSidebar() di kolom ikon.
        jButton1.setUI(new TombolKacaUI(TombolKacaUI.Gaya.KELUAR));
        jButton1.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 16));
        jButton1.setMargin(new Insets(0, 0, 0, 0));
        jButton1.setHorizontalAlignment(SwingConstants.LEFT);
        jButton1.setFont(jButton1.getFont().deriveFont(java.awt.Font.BOLD, 13f));
        jButton1.setOpaque(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setRolloverEnabled(true);
        jButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Saat kursor melewati tombol Keluar, sidebar digambar ulang agar ikonnya ikut berubah warna.
        jButton1.getModel().addChangeListener(e -> jPanel1.repaint());
        // Klik pada ikon Keluar (di luar area teks) juga dianggap menekan tombol Keluar.
        jPanel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Rectangle areaIkon = new Rectangle(0, jButton1.getY(), jButton1.getX(), jButton1.getHeight());
                if (areaIkon.contains(e.getPoint())) {
                    jButton1.doClick();
                }
            }
        });
    }

    /** Hiasan di atas kaca sidebar: ikon admin di samping "admin / Petugas TU" dan ikon pintu di samping "Keluar". */
    private void lukisHiasanSidebar(Graphics2D g) {
        int tengah = (jLabel19.getY() + jLabel18.getY() + jLabel18.getHeight()) / 2;
        new IkonVektor(IkonVektor.Jenis.PENGGUNA, 26).lukis(g, 22, tengah - 13, new Color(255, 255, 255, 235));

        boolean hover = jButton1.getModel().isRollover();
        Color warna = hover ? new Color(255, 170, 170) : new Color(255, 255, 255, 225);
        new IkonVektor(IkonVektor.Jenis.KELUAR, 22).lukis(g, 24, jButton1.getY() + (jButton1.getHeight() - 22) / 2, warna);
    }

    /** Mengatur tabel agar transparan di atas kartu kaca, lengkap dengan header dan baris. */
    private void gayaTabel() {
        TableRiwayatTransaksi.setModel(modelRiwayat);
        TableRiwayatTransaksi.setAutoCreateRowSorter(true); // klik judul kolom untuk mengurutkan
        TableRiwayatTransaksi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableRiwayatTransaksi.setRowHeight(34);
        TableRiwayatTransaksi.setShowVerticalLines(false);
        TableRiwayatTransaksi.setShowHorizontalLines(true);
        TableRiwayatTransaksi.setGridColor(DataSiswa.GARIS);
        TableRiwayatTransaksi.setIntercellSpacing(new Dimension(0, 1));
        TableRiwayatTransaksi.setFillsViewportHeight(true);
        TableRiwayatTransaksi.setOpaque(false);  // agar kartu kaca terlihat di belakang tabel
        TableRiwayatTransaksi.getTableHeader().setReorderingAllowed(false);
        TableRiwayatTransaksi.getTableHeader().setPreferredSize(new Dimension(0, 36));
        TableRiwayatTransaksi.getTableHeader().setDefaultRenderer(new JudulKolomRenderer());

        // Scroll pane transparan tanpa garis tepi; tepinya sudah diwakili kartu kaca.
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane1.setViewportBorder(null);
        JPanel sudut = new JPanel();
        sudut.setBackground(HeaderRenderer.LATAR_HEADER);
        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, sudut);

        int[] lebar = {110, 100, 60, 190, 90, 90, 110, 120};
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (int i = 0; i < lebar.length; i++) {
            Function<Object, String> format = null;
            if (i == 1) {
                format = nilai -> nilai instanceof LocalDate t ? t.format(formatTanggal) : "-";
            } else if (i == 6) {
                format = nilai -> nilai instanceof Integer n ? rupiah(n) : "-";
            }
            TableRiwayatTransaksi.getColumnModel().getColumn(i).setPreferredWidth(lebar[i]);
            TableRiwayatTransaksi.getColumnModel().getColumn(i).setCellRenderer(
                    i == KOLOM_KETERANGAN ? new KeteranganRenderer() : new BarisRenderer(RATA[i], format));
        }
    }

    /** Memberi gaya kaca pada kedua tombol aksi. */
    private void gayaTombolAksi() {
        pasangGayaAksi(HapusSemuaDuplikat, TombolKacaUI.Gaya.BAHAYA);
        pasangGayaAksi(HapusTransaksi, TombolKacaUI.Gaya.BAHAYA);
        HapusTransaksi.setEnabled(false); // aktif setelah ada baris yang dipilih (lihat perbaruiTombolHapus)
        perbaruiTombolHapus();
    }

    /** Pengaturan yang sama untuk kedua tombol aksi. */
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
        HapusTransaksi.addActionListener(e -> hapusTransaksi());
        HapusSemuaDuplikat.addActionListener(e -> hapusSemuaDuplikat());
        jButton1.addActionListener(e -> keluar());
        ComboBoxTahunAjaran.addActionListener(e -> muatData()); // ganti tahun ajaran -> muat ulang tabel

        // Hapus Transaksi hanya aktif jika ada baris yang dipilih.
        TableRiwayatTransaksi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                perbaruiTombolHapus();
            }
        });

        // Tombol Delete di keyboard sama dengan menekan tombol Hapus Transaksi.
        TableRiwayatTransaksi.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "hapusTransaksi");
        TableRiwayatTransaksi.getActionMap().put("hapusTransaksi", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (HapusTransaksi.isEnabled()) {
                    hapusTransaksi();
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

    /** Menyesuaikan tombol Hapus Transaksi dengan baris yang sedang dipilih. */
    private void perbaruiTombolHapus() {
        int m = modelTerpilihTanpaPeringatan();
        HapusTransaksi.setEnabled(m != -1);
        if (m == -1) {
            HapusTransaksi.setToolTipText("Pilih transaksi pada tabel terlebih dahulu");
        } else if (adalahDuplikat(m)) {
            HapusTransaksi.setToolTipText("Hapus catatan duplikat ini");
        } else {
            HapusTransaksi.setToolTipText("Batalkan pembayaran ini (tagihan kembali menjadi menunggak)");
        }
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

    private String tahunAjaranDipilih() {
        return String.valueOf(ComboBoxTahunAjaran.getSelectedItem());
    }

    /** Mengubah angka menjadi format rupiah, contoh 500000 -> "Rp500.000". */
    private static String rupiah(int nominal) {
        return String.format(Locale.of("id", "ID"), "Rp%,d", nominal);
    }

    /** Menampilkan semua pembayaran lunas pada tahun ajaran terpilih; duplikat diberi tanda. */
    private void muatData() {
        String sql = """
            SELECT p.id_pembayaran, p.tgl_bayar, p.id_siswa, s.nama, s.kelas, p.bulan, p.nominal,
                   CASE WHEN %s THEN 1 ELSE 0 END AS duplikat
            FROM Pembayaran p
            LEFT JOIN siswa s ON p.id_siswa = s.id_siswa
            WHERE p.status = 'lunas' AND p.tahun_ajaran = ?
            ORDER BY p.tgl_bayar DESC, p.id_pembayaran DESC
            """.formatted(KONDISI_DUPLIKAT);

        // Ingat kwitansi yang sedang dipilih supaya tetap terpilih setelah muat ulang.
        Integer kwitansiSebelumnya = null;
        int m0 = modelTerpilihTanpaPeringatan();
        if (m0 != -1) {
            kwitansiSebelumnya = (Integer) modelRiwayat.getValueAt(m0, KOLOM_KWITANSI);
        }

        int jumlahDuplikat = 0;
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tahunAjaranDipilih());

            modelRiwayat.setRowCount(0); // kosongkan tabel sebelum diisi ulang
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boolean duplikat = rs.getInt("duplikat") == 1;
                    if (duplikat) {
                        jumlahDuplikat++;
                    }
                    modelRiwayat.addRow(new Object[]{
                        rs.getInt("id_pembayaran"), parseTanggal(rs.getString("tgl_bayar")),
                        rs.getInt("id_siswa"), rs.getString("nama"), rs.getString("kelas"),
                        rs.getString("bulan"), rs.getInt("nominal"),
                        duplikat ? KET_DUPLIKAT : KET_LUNAS});
                }
            }
        } catch (SQLException e) {
            tampilkanErrorDatabase("mengambil riwayat transaksi", e);
            return;
        }

        HapusSemuaDuplikat.setToolTipText(jumlahDuplikat > 0
                ? jumlahDuplikat + " catatan duplikat pada tahun ajaran " + tahunAjaranDipilih()
                : "Tidak ada catatan duplikat pada tahun ajaran " + tahunAjaranDipilih());

        if (kwitansiSebelumnya != null) {
            pilihBarisKwitansi(kwitansiSebelumnya);
        }
        perbaruiTombolHapus();
    }

    private static LocalDate parseTanggal(String teks) {
        if (teks == null || teks.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(teks.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Memilih (dan menggulir ke) baris dengan nomor kwitansi tertentu. */
    private void pilihBarisKwitansi(int kwitansi) {
        for (int m = 0; m < modelRiwayat.getRowCount(); m++) {
            if ((Integer) modelRiwayat.getValueAt(m, KOLOM_KWITANSI) == kwitansi) {
                int tampil = TableRiwayatTransaksi.convertRowIndexToView(m);
                if (tampil != -1) {
                    TableRiwayatTransaksi.setRowSelectionInterval(tampil, tampil);
                    TableRiwayatTransaksi.scrollRectToVisible(TableRiwayatTransaksi.getCellRect(tampil, 0, true));
                }
                return;
            }
        }
    }

    /** Baris model dari baris yang dipilih, atau -1 jika belum ada yang dipilih (tanpa peringatan). */
    private int modelTerpilihTanpaPeringatan() {
        int baris = TableRiwayatTransaksi.getSelectedRow();
        // convertRowIndexToModel: baris yang tampil bisa berbeda dengan baris model jika tabel diurutkan.
        return baris == -1 ? -1 : TableRiwayatTransaksi.convertRowIndexToModel(baris);
    }

    private boolean adalahDuplikat(int barisModel) {
        return KET_DUPLIKAT.equals(modelRiwayat.getValueAt(barisModel, KOLOM_KETERANGAN));
    }

    /** Nama siswa untuk ditampilkan di dialog; tanda "-" jika siswanya sudah tidak ada di database. */
    private String namaDiBaris(int barisModel) {
        Object nama = modelRiwayat.getValueAt(barisModel, KOLOM_NAMA);
        return nama == null ? "-" : nama.toString();
    }

    // ------------------------------ AKSI ------------------------------

    /**
     * Tombol "Hapus Transaksi" untuk transaksi yang dipilih:
     *  - catatan DUPLIKAT -> catatannya dihapus dari database;
     *  - catatan yang sah -> pembayaran dibatalkan: tagihannya kembali menjadi "menunggak"
     *    (tagihan tidak dihapus supaya tetap muncul di halaman Tagihan).
     */
    private void hapusTransaksi() {
        int m = modelTerpilihTanpaPeringatan();
        if (m == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih transaksi pada tabel terlebih dahulu.",
                    "Transaksi belum dipilih", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int kwitansi = (Integer) modelRiwayat.getValueAt(m, KOLOM_KWITANSI);
        String nama = namaDiBaris(m);
        String bulan = String.valueOf(modelRiwayat.getValueAt(m, KOLOM_BULAN));
        String tahun = tahunAjaranDipilih();

        if (adalahDuplikat(m)) {
            hapusCatatanDuplikat(kwitansi, nama, bulan);
        } else {
            batalkanPembayaran(kwitansi, nama, bulan, tahun);
        }
    }

    private void hapusCatatanDuplikat(int kwitansi, String nama, String bulan) {
        int pilihan = JOptionPane.showOptionDialog(this,
                "Hapus catatan duplikat kwitansi #" + kwitansi + "?\n"
                + nama + " - " + bulan + "\n\nPembayaran aslinya tetap disimpan.\n"
                + "Tindakan ini tidak dapat dibatalkan.",
                "Konfirmasi hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Hapus", "Batal"}, "Batal");
        if (pilihan != 0) {
            return;
        }

        // Kondisi duplikat diperiksa ulang di SQL agar catatan asli tidak ikut terhapus.
        String sql = "DELETE FROM Pembayaran WHERE id_pembayaran IN ("
                + "SELECT p.id_pembayaran FROM Pembayaran p WHERE p.id_pembayaran = ? AND p.status = 'lunas' AND "
                + KONDISI_DUPLIKAT + ")";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kwitansi);
            int terhapus = ps.executeUpdate();
            muatData();
            JOptionPane.showMessageDialog(this,
                    terhapus > 0 ? "Catatan duplikat berhasil dihapus."
                            : "Tidak ada catatan yang dihapus; catatan ini bukan duplikat.",
                    terhapus > 0 ? "Berhasil" : "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            tampilkanErrorDatabase("menghapus transaksi", e);
        }
    }

    private void batalkanPembayaran(int kwitansi, String nama, String bulan, String tahun) {
        int idSiswa = (Integer) modelRiwayat.getValueAt(modelTerpilihTanpaPeringatan(), 2);
        // Jika bulan yang sama punya catatan duplikat, duplikat itu harus dibereskan lebih dulu.
        int duplikatLain = hitungCatatanLunasLain(kwitansi, idSiswa, bulan, tahun);
        if (duplikatLain < 0) {
            return; // kesalahan database sudah ditampilkan
        }
        if (duplikatLain > 0) {
            JOptionPane.showMessageDialog(this,
                    "Bulan " + bulan + " untuk " + nama + " masih punya " + duplikatLain
                    + " catatan duplikat.\nHapus catatan DUPLIKAT-nya lebih dulu "
                    + "(atau pakai \"Hapus Semua Duplikat\"), baru batalkan pembayaran aslinya.",
                    "Masih ada duplikat", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int pilihan = JOptionPane.showOptionDialog(this,
                "Batalkan pembayaran kwitansi #" + kwitansi + "?\n"
                + nama + " - " + bulan + " " + tahun + "\n\n"
                + "Transaksi hilang dari riwayat dan tagihan bulan ini kembali berstatus menunggak.",
                "Konfirmasi hapus transaksi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Hapus transaksi", "Batal"}, "Batal");
        if (pilihan != 0) {
            return;
        }

        String sql = "UPDATE Pembayaran SET status = 'menunggak', tgl_bayar = NULL "
                + "WHERE id_pembayaran = ? AND status = 'lunas'";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kwitansi);
            int berubah = ps.executeUpdate();
            muatData();
            JOptionPane.showMessageDialog(this,
                    berubah > 0 ? "Transaksi dihapus. Tagihan " + bulan + " kembali menjadi menunggak."
                            : "Tidak ada yang berubah; transaksi ini sudah tidak berstatus lunas.",
                    berubah > 0 ? "Berhasil" : "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            tampilkanErrorDatabase("menghapus transaksi", e);
        }
    }

    /** Jumlah catatan lunas lain (selain kwitansi ini) untuk siswa, bulan, dan tahun ajaran yang sama; -1 jika error. */
    private int hitungCatatanLunasLain(int kwitansi, int idSiswa, String bulan, String tahun) {
        String sql = "SELECT COUNT(*) FROM Pembayaran WHERE id_siswa = ? AND bulan = ? "
                + "AND tahun_ajaran = ? AND status = 'lunas' AND id_pembayaran <> ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSiswa);
            ps.setString(2, bulan);
            ps.setString(3, tahun);
            ps.setInt(4, kwitansi);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            tampilkanErrorDatabase("memeriksa catatan duplikat", e);
            return -1;
        }
    }

    /** Menghapus semua catatan DUPLIKAT pada tahun ajaran terpilih; catatan pertama tetap disimpan. */
    private void hapusSemuaDuplikat() {
        String tahun = tahunAjaranDipilih();
        int jumlah = 0;
        for (int m = 0; m < modelRiwayat.getRowCount(); m++) {
            if (adalahDuplikat(m)) {
                jumlah++;
            }
        }
        if (jumlah == 0) {
            JOptionPane.showMessageDialog(this,
                    "Tidak ada catatan duplikat pada tahun ajaran " + tahun + ".",
                    "Tidak ada duplikat", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int pilihan = JOptionPane.showOptionDialog(this,
                "Hapus " + jumlah + " catatan duplikat pada tahun ajaran " + tahun + "?\n"
                + "Catatan pertama untuk setiap siswa dan bulan tetap disimpan.\n\n"
                + "Tindakan ini tidak dapat dibatalkan.",
                "Konfirmasi hapus semua duplikat", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, new String[]{"Hapus semua", "Batal"}, "Batal");
        if (pilihan != 0) {
            return;
        }

        String sql = "DELETE FROM Pembayaran WHERE id_pembayaran IN ("
                + "SELECT p.id_pembayaran FROM Pembayaran p WHERE p.status = 'lunas' AND p.tahun_ajaran = ? AND "
                + KONDISI_DUPLIKAT + ")";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tahun);
            int terhapus = ps.executeUpdate();
            muatData();
            JOptionPane.showMessageDialog(this, terhapus + " catatan duplikat berhasil dihapus.",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            tampilkanErrorDatabase("menghapus catatan duplikat", e);
        }
    }

    private void tampilkanErrorDatabase(String aksi, SQLException e) {
        logger.log(java.util.logging.Level.SEVERE, "Gagal " + aksi, e);
        JOptionPane.showMessageDialog(this, "Gagal " + aksi + ": " + e.getMessage(),
                "Database error", JOptionPane.ERROR_MESSAGE);
    }

    // ---------------------------- RENDERER TABEL ----------------------------

    /** Judul kolom: gaya sama dengan Data Siswa, perataannya mengikuti isi kolom Riwayat. */
    private static class JudulKolomRenderer extends HeaderRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable tabel, Object nilai, boolean dipilih,
                boolean fokus, int baris, int kolom) {
            super.getTableCellRendererComponent(tabel, nilai, dipilih, fokus, baris, kolom);
            setHorizontalAlignment(RATA[tabel.convertColumnIndexToModel(kolom)]);
            return this;
        }
    }

    /** Renderer sel biasa; baris DUPLIKAT diberi latar merah muda dan teks merah tua. */
    private class BarisRenderer extends SelRenderer {

        BarisRenderer(int rata, Function<Object, String> format) {
            super(rata, format);
        }

        @Override
        public Component getTableCellRendererComponent(JTable tabel, Object nilai, boolean dipilih,
                boolean fokus, int baris, int kolom) {
            super.getTableCellRendererComponent(tabel, nilai, dipilih, fokus, baris, kolom);
            if (adalahDuplikat(tabel.convertRowIndexToModel(baris))) {
                setForeground(MERAH_TEKS);
                if (!dipilih) {
                    latarBaris = BARIS_DUPLIKAT;
                }
            }
            return this;
        }
    }

    /** Renderer kolom keterangan: teks ditampilkan sebagai pil (hijau = lunas, merah = duplikat). */
    private class KeteranganRenderer extends BarisRenderer {

        private String label = "";
        private Color latarPil = Color.LIGHT_GRAY;
        private Color teksPil = DataSiswa.TEKS;

        KeteranganRenderer() {
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
            label = String.valueOf(nilai).toUpperCase();
            if (KET_DUPLIKAT.equals(nilai)) {
                latarPil = new Color(254, 202, 202);
                teksPil = new Color(153, 27, 27);
            } else {
                latarPil = new Color(198, 240, 214);
                teksPil = new Color(21, 110, 61);
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
        java.awt.EventQueue.invokeLater(() -> new Riwayat().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxTahunAjaran;
    private javax.swing.JToggleButton Dashboard;
    private javax.swing.JToggleButton Data_siswa_variable;
    private javax.swing.JLabel Date;
    private javax.swing.JButton HapusSemuaDuplikat;
    private javax.swing.JButton HapusTransaksi;
    private javax.swing.JToggleButton Laporan_variable;
    private javax.swing.JToggleButton Riwayat_variable;
    private javax.swing.JTable TableRiwayatTransaksi;
    private javax.swing.JToggleButton Tagihan_variable;
    private javax.swing.JLabel Time;
    private javax.swing.JLabel icon;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
