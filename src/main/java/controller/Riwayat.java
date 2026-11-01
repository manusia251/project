package controller;

import dao.Database;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import properties.AppTheme;

public class Riwayat extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Riwayat.class.getName());

    // Warna tema; hijau yang sama dengan sidebar Dashboard.
    private static final Color HIJAU = new Color(41, 112, 69);
    private static final Color HIJAU_MUDA = new Color(220, 240, 228);
    private static final Color LATAR = new Color(241, 245, 249);
    private static final Color TEKS = new Color(30, 41, 59);
    private static final Color TEKS_REDUP = new Color(100, 116, 139);
    private static final Color GARIS = new Color(226, 232, 240);
    private static final Color MERAH_TEKS = new Color(153, 27, 27);
    private static final Color MERAH_LATAR = new Color(254, 242, 242);

    private static final String KET_LUNAS = "Lunas";
    private static final String KET_DUPLIKAT = "DUPLIKAT";
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

    private final DefaultTableModel modelRiwayat = new DefaultTableModel(
            new Object[]{"NO. KWITANSI", "TANGGAL", "NIS", "NAMA SISWA", "KELAS", "BULAN", "JUMLAH", "KETERANGAN"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return switch (column) {
                case 0, 2, 6 -> Integer.class;
                case 1 -> LocalDate.class;
                default -> String.class;
            };
        }
    };

    private Timer timerJam;

    public Riwayat() {
        initComponents();
        siapkanTampilan();   // finishing desain (di luar kode GUI Builder)
        siapkanLogika();     // menghubungkan tombol, tabel, dan pergantian tab
        jam();
        muatData();
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
                .addComponent(Riwayat_variable, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Laporan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton1))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
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
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
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

    /** Merapikan tampilan: warna, font, tabel, dan tombol. */
    private void siapkanTampilan() {
        getContentPane().setBackground(LATAR);
        gayaSidebar();

        jLabel5.setForeground(TEKS);
        for (JLabel label : new JLabel[]{jLabel6, jLabel8, Date, Time}) {
            label.setForeground(TEKS_REDUP);
        }
        Date.setFont(Date.getFont().deriveFont(java.awt.Font.BOLD));
        Time.setFont(Time.getFont().deriveFont(java.awt.Font.BOLD));
        jLabel11.setForeground(TEKS_REDUP);
        jLabel11.setFont(jLabel11.getFont().deriveFont(java.awt.Font.ITALIC));

        gayaTabel();
        gayaTombolAksi();

        setTitle("Sistem SPP - Riwayat Transaksi");
        pack();
    }

    /** Sidebar hijau seperti Dashboard, menu aktif ditandai putih. */
    private void gayaSidebar() {
        jPanel1.setBackground(HIJAU);
        jLabel1.setForeground(Color.WHITE);
        jLabel3.setForeground(new Color(209, 232, 218));
        jLabel4.setForeground(new Color(168, 205, 182));
        jLabel18.setForeground(new Color(209, 232, 218));
        jLabel19.setForeground(Color.WHITE);
        jLabel19.setFont(jLabel19.getFont().deriveFont(java.awt.Font.BOLD));

        // Logo: dipakai jika gambar ada di classpath, jika tidak tampil lencana "SPP".
        java.net.URL logo = getClass().getResource("/gambar/logo.png");
        if (logo != null) {
            java.awt.Image gambar = new javax.swing.ImageIcon(logo).getImage()
                    .getScaledInstance(37, 37, java.awt.Image.SCALE_SMOOTH);
            icon.setIcon(new javax.swing.ImageIcon(gambar));
            icon.setText("");
        } else {
            icon.setText("SPP");
            icon.setOpaque(true);
            icon.setBackground(Color.WHITE);
            icon.setForeground(HIJAU);
            icon.setFont(icon.getFont().deriveFont(java.awt.Font.BOLD, 12f));
            icon.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Hanya satu menu yang boleh aktif; menu halaman ini langsung aktif.
        ButtonGroup menu = new ButtonGroup();
        for (JToggleButton tombol : new JToggleButton[]{
            Dashboard, Data_siswa_variable, Tagihan_variable, Riwayat_variable, Laporan_variable}) {
            menu.add(tombol);
            tombol.setHorizontalAlignment(SwingConstants.LEFT);
            tombol.setFocusPainted(false);
            tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            tombol.putClientProperty("FlatLaf.style",
                    "background:#297045; foreground:#ffffff; hoverBackground:#35875a;"
                    + "selectedBackground:#ffffff; selectedForeground:#297045;"
                    + "borderWidth:0; focusWidth:0; arc:12; margin:2,14,2,14");
        }
        Riwayat_variable.setSelected(true);

        jButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jButton1.putClientProperty("FlatLaf.style",
                "background:#297045; foreground:#ffffff; borderColor:#ffffff;"
                + "hoverBackground:#35875a; focusWidth:0");
    }

    private void gayaTabel() {
        TableRiwayatTransaksi.setModel(modelRiwayat);
        TableRiwayatTransaksi.setAutoCreateRowSorter(true);
        TableRiwayatTransaksi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableRiwayatTransaksi.setRowHeight(32);
        TableRiwayatTransaksi.setShowVerticalLines(false);
        TableRiwayatTransaksi.setShowHorizontalLines(true);
        TableRiwayatTransaksi.setGridColor(GARIS);
        TableRiwayatTransaksi.setIntercellSpacing(new Dimension(0, 1));
        TableRiwayatTransaksi.setFillsViewportHeight(true);
        TableRiwayatTransaksi.getTableHeader().setReorderingAllowed(false);
        TableRiwayatTransaksi.getTableHeader().setPreferredSize(new Dimension(0, 34));
        TableRiwayatTransaksi.getTableHeader().setFont(
                TableRiwayatTransaksi.getTableHeader().getFont().deriveFont(java.awt.Font.BOLD, 12f));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(GARIS));

        int[] lebar = {100, 100, 60, 200, 100, 90, 110, 100};
        int[] rata = {SwingConstants.CENTER, SwingConstants.CENTER, SwingConstants.CENTER,
            SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.LEFT,
            SwingConstants.RIGHT, SwingConstants.CENTER};
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
                    new SelRenderer(rata[i], format, i == KOLOM_KETERANGAN));
        }
    }

    private void gayaTombolAksi() {
        // Hapus Semua Duplikat = tindakan besar (merah penuh); Hapus Transaksi = merah garis tepi.
        HapusSemuaDuplikat.putClientProperty("FlatLaf.style",
                "background:#dc2626; foreground:#ffffff; hoverBackground:#b91c1c;"
                + "pressedBackground:#991b1b; borderWidth:0; focusWidth:0");
        HapusTransaksi.putClientProperty("FlatLaf.style",
                "background:#ffffff; foreground:#dc2626; borderColor:#dc2626; hoverBackground:#fef2f2");
        HapusSemuaDuplikat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        HapusTransaksi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        HapusTransaksi.setToolTipText("Pilih catatan berstatus DUPLIKAT pada tabel");
        HapusTransaksi.setEnabled(false);
        HapusSemuaDuplikat.setEnabled(false);
    }

    /** Menghubungkan semua komponen ke logika. */
    private void siapkanLogika() {
        HapusTransaksi.addActionListener(e -> hapusTransaksi());
        HapusSemuaDuplikat.addActionListener(e -> hapusSemuaDuplikat());
        jButton1.addActionListener(e -> keluar());
        ComboBoxTahunAjaran.addActionListener(e -> muatData());

        // Hapus Transaksi hanya aktif jika baris terpilih berstatus DUPLIKAT.
        TableRiwayatTransaksi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                perbaruiTombolHapus();
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

    private void perbaruiTombolHapus() {
        int baris = TableRiwayatTransaksi.getSelectedRow();
        boolean duplikatDipilih = baris != -1 && KET_DUPLIKAT.equals(
                modelRiwayat.getValueAt(TableRiwayatTransaksi.convertRowIndexToModel(baris), KOLOM_KETERANGAN));
        HapusTransaksi.setEnabled(duplikatDipilih);
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
        timerJam = new Timer(1000, e -> perbarui.run());
        timerJam.start();
    }

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

        Integer kwitansiSebelumnya = null;
        int barisTerpilih = TableRiwayatTransaksi.getSelectedRow();
        if (barisTerpilih != -1) {
            kwitansiSebelumnya = (Integer) modelRiwayat.getValueAt(
                    TableRiwayatTransaksi.convertRowIndexToModel(barisTerpilih), 0);
        }

        int jumlahDuplikat = 0;
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tahunAjaranDipilih());

            modelRiwayat.setRowCount(0);
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

        HapusSemuaDuplikat.setEnabled(jumlahDuplikat > 0);
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

    private void pilihBarisKwitansi(int kwitansi) {
        for (int m = 0; m < modelRiwayat.getRowCount(); m++) {
            if ((Integer) modelRiwayat.getValueAt(m, 0) == kwitansi) {
                int tampil = TableRiwayatTransaksi.convertRowIndexToView(m);
                if (tampil != -1) {
                    TableRiwayatTransaksi.setRowSelectionInterval(tampil, tampil);
                }
                return;
            }
        }
    }

    // ------------------------------ AKSI ------------------------------

    /** Menghapus satu catatan yang terpilih; hanya catatan DUPLIKAT yang boleh dihapus. */
    private void hapusTransaksi() {
        int baris = TableRiwayatTransaksi.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih catatan DUPLIKAT pada tabel terlebih dahulu.",
                    "Catatan belum dipilih", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int m = TableRiwayatTransaksi.convertRowIndexToModel(baris);
        int kwitansi = (Integer) modelRiwayat.getValueAt(m, 0);
        String nama = String.valueOf(modelRiwayat.getValueAt(m, 3));
        String bulan = String.valueOf(modelRiwayat.getValueAt(m, 5));

        if (!KET_DUPLIKAT.equals(modelRiwayat.getValueAt(m, KOLOM_KETERANGAN))) {
            JOptionPane.showMessageDialog(this,
                    "Kwitansi #" + kwitansi + " adalah catatan pembayaran yang sah.\n"
                    + "Hanya catatan berstatus DUPLIKAT yang dapat dihapus.",
                    "Tidak dapat dihapus", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int pilihan = JOptionPane.showOptionDialog(this,
                "Hapus catatan duplikat kwitansi #" + kwitansi + "?\n"
                + nama + " - " + bulan + "\n\nTindakan ini tidak dapat dibatalkan.",
                "Konfirmasi hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Hapus", "Batal"}, "Batal");
        if (pilihan != 0) {
            return;
        }

        // Kondisi duplikat diperiksa ulang di SQL agar catatan asli tidak terhapus.
        String sql = "DELETE FROM Pembayaran AS p WHERE p.id_pembayaran = ? AND p.status = 'lunas' AND "
                + KONDISI_DUPLIKAT;
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

    /** Menghapus semua catatan DUPLIKAT pada tahun ajaran terpilih; catatan pertama tetap disimpan. */
    private void hapusSemuaDuplikat() {
        String tahun = tahunAjaranDipilih();
        int jumlah = 0;
        for (int m = 0; m < modelRiwayat.getRowCount(); m++) {
            if (KET_DUPLIKAT.equals(modelRiwayat.getValueAt(m, KOLOM_KETERANGAN))) {
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

        String sql = "DELETE FROM Pembayaran AS p WHERE p.status = 'lunas' AND p.tahun_ajaran = ? AND "
                + KONDISI_DUPLIKAT;
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

    /**
     * Renderer sel: perataan, format teks, warna selang-seling, dan seleksi hijau.
     * Baris DUPLIKAT diberi latar merah muda; kolom KETERANGAN ditebalkan dan diberi warna.
     */
    private class SelRenderer extends DefaultTableCellRenderer {

        private final int rata;
        private final Function<Object, String> format;
        private final boolean kolomKeterangan;

        SelRenderer(int rata, Function<Object, String> format, boolean kolomKeterangan) {
            this.rata = rata;
            this.format = format;
            this.kolomKeterangan = kolomKeterangan;
        }

        @Override
        public Component getTableCellRendererComponent(JTable tabel, Object nilai, boolean dipilih,
                boolean fokus, int baris, int kolom) {
            String teks = format != null ? format.apply(nilai) : (nilai == null ? "" : nilai.toString());
            super.getTableCellRendererComponent(tabel, teks, dipilih, false, baris, kolom);
            setHorizontalAlignment(rata);
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

            int m = tabel.convertRowIndexToModel(baris);
            boolean duplikat = KET_DUPLIKAT.equals(modelRiwayat.getValueAt(m, KOLOM_KETERANGAN));

            Color teksWarna = duplikat ? MERAH_TEKS : TEKS;
            if (kolomKeterangan) {
                teksWarna = duplikat ? MERAH_TEKS : new Color(21, 128, 61);
            }
            setForeground(teksWarna);
            setFont(tabel.getFont().deriveFont(kolomKeterangan ? java.awt.Font.BOLD : java.awt.Font.PLAIN));

            if (dipilih) {
                setBackground(HIJAU_MUDA);
            } else if (duplikat) {
                setBackground(MERAH_LATAR);
            } else {
                setBackground(baris % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
            }
            return this;
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
