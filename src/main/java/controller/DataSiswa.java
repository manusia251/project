package controller;

import dao.Database;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import properties.AppTheme;

public class DataSiswa extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DataSiswa.class.getName());

    // Warna tema; hijau yang sama dengan sidebar Dashboard.
    private static final Color HIJAU = new Color(41, 112, 69);
    private static final Color HIJAU_HOVER = new Color(53, 135, 90);
    private static final Color HIJAU_MUDA = new Color(220, 240, 228);
    private static final Color LATAR = new Color(241, 245, 249);
    private static final Color TEKS = new Color(30, 41, 59);
    private static final Color TEKS_REDUP = new Color(100, 116, 139);
    private static final Color GARIS = new Color(226, 232, 240);

    // Urutan bulan mengikuti tahun ajaran sekolah (Juli sampai Juni).
    private static final String[] URUTAN_BULAN = {
        "Juli", "Agustus", "September", "Oktober", "November", "Desember",
        "Januari", "Februari", "Maret", "April", "Mei", "Juni"
    };

    // Model tabel dibuat sekali dan dipakai ulang; sel tidak bisa diedit langsung.
    private final DefaultTableModel modelSiswa = new DefaultTableModel(
            new Object[]{"NO", "NIS", "NAMA SISWA", "KELAS", "TAGIHAN / BULAN", "STATUS TAHUN AJARAN"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return (column == 0 || column == 1 || column == 4) ? Integer.class : String.class;
        }
    };

    private JTextField fieldCari;
    private Timer timerJam;

    public DataSiswa() {
        initComponents();
        siapkanTampilan();   // finishing desain (di luar kode GUI Builder)
        siapkanLogika();     // menghubungkan tombol, pencarian, dan tabel
        jam();
        muatData();
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
            .addGroup(SideBarLayout.createSequentialGroup()
                .addGroup(SideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addComponent(riwayat_variable, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(laporan_variable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(exit))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TambahSiswa.setText("+ Tambah Siswa");

        Ubah.setText("Ubah");

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
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(SideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
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

    /** Merapikan tampilan: warna, font, tabel, tombol, dan kolom pencarian. */
    private void siapkanTampilan() {
        getContentPane().setBackground(LATAR);
        gayaSidebar();

        header2.setForeground(TEKS);
        for (JLabel label : new JLabel[]{jLabel6, jLabel8, Date, Time}) {
            label.setForeground(TEKS_REDUP);
        }
        Date.setFont(Date.getFont().deriveFont(java.awt.Font.BOLD));
        Time.setFont(Time.getFont().deriveFont(java.awt.Font.BOLD));

        pasangKolomCari();
        gayaTabel();
        gayaTombolAksi();

        setTitle("Sistem SPP - Data Siswa");
        pack();
    }

    /** Sidebar hijau seperti Dashboard, menu aktif ditandai putih. */
    private void gayaSidebar() {
        SideBar.setBackground(HIJAU);
        header.setForeground(Color.WHITE);
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
            dashboard_variable, data_variable, tagihan_variable, riwayat_variable, laporan_variable}) {
            menu.add(tombol);
            tombol.setHorizontalAlignment(SwingConstants.LEFT);
            tombol.setFocusPainted(false);
            tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            tombol.putClientProperty("FlatLaf.style",
                    "background:#297045; foreground:#ffffff; hoverBackground:#35875a;"
                    + "selectedBackground:#ffffff; selectedForeground:#297045;"
                    + "borderWidth:0; focusWidth:0; arc:12; margin:2,14,2,14");
        }
        data_variable.setSelected(true);

        exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exit.putClientProperty("FlatLaf.style",
                "background:#297045; foreground:#ffffff; borderColor:#ffffff;"
                + "hoverBackground:#35875a; focusWidth:0");
    }

    /**
     * Mengganti label "Cari NIS/Nama/Kelas" pada jPanel2 dengan kolom input,
     * teks label lama dipakai sebagai placeholder.
     */
    private void pasangKolomCari() {
        String petunjuk = cari_nis_nama_kelas.getText();
        jPanel2.removeAll();
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new BorderLayout());

        fieldCari = new JTextField();
        fieldCari.putClientProperty("JTextField.placeholderText", petunjuk);
        fieldCari.putClientProperty("JTextField.showClearButton", true);
        fieldCari.setToolTipText("Ketik NIS, nama, atau kelas untuk menyaring tabel");
        jPanel2.add(fieldCari, BorderLayout.CENTER);
        jPanel2.setPreferredSize(new Dimension(260, 32));
    }

    private void gayaTabel() {
        TabelDataSiswa.setModel(modelSiswa);
        TabelDataSiswa.setAutoCreateRowSorter(true);
        TabelDataSiswa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TabelDataSiswa.setRowHeight(32);
        TabelDataSiswa.setShowVerticalLines(false);
        TabelDataSiswa.setShowHorizontalLines(true);
        TabelDataSiswa.setGridColor(GARIS);
        TabelDataSiswa.setIntercellSpacing(new Dimension(0, 1));
        TabelDataSiswa.setFillsViewportHeight(true);
        TabelDataSiswa.getTableHeader().setReorderingAllowed(false);
        TabelDataSiswa.getTableHeader().setPreferredSize(new Dimension(0, 34));
        TabelDataSiswa.getTableHeader().setFont(
                TabelDataSiswa.getTableHeader().getFont().deriveFont(java.awt.Font.BOLD, 12f));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(GARIS));

        int[] lebar = {50, 80, 260, 110, 140, 220};
        for (int i = 0; i < lebar.length; i++) {
            TabelDataSiswa.getColumnModel().getColumn(i).setPreferredWidth(lebar[i]);
        }
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
        TabelDataSiswa.getColumnModel().getColumn(4).setCellRenderer(new SelRenderer(SwingConstants.RIGHT,
                nilai -> nilai instanceof Integer n && n > 0 ? rupiah(n) : "-"));
        TabelDataSiswa.getColumnModel().getColumn(5).setCellRenderer(new SelRenderer(SwingConstants.LEFT, null) {
            @Override
            protected Color warnaTeks(Object nilai) {
                String s = String.valueOf(nilai);
                if (s.startsWith("Lunas")) {
                    return new Color(21, 128, 61);
                }
                if (s.startsWith("Belum lunas")) {
                    return new Color(180, 83, 9);
                }
                return TEKS_REDUP;
            }

            @Override
            protected boolean tebal(Object nilai) {
                return true;
            }
        });
    }

    private void gayaTombolAksi() {
        TambahSiswa.putClientProperty("FlatLaf.style",
                "background:#297045; foreground:#ffffff; hoverBackground:#1f5836;"
                + "pressedBackground:#194a2d; borderWidth:0; focusWidth:0");
        Ubah.putClientProperty("FlatLaf.style",
                "background:#ffffff; foreground:#297045; borderColor:#297045; hoverBackground:#eaf5ee");
        Hapus.putClientProperty("FlatLaf.style",
                "background:#ffffff; foreground:#dc2626; borderColor:#dc2626; hoverBackground:#fef2f2");
        for (javax.swing.JButton tombol : new javax.swing.JButton[]{TambahSiswa, Ubah, Hapus}) {
            tombol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        Ubah.setToolTipText("Pilih siswa pada tabel terlebih dahulu");
        Hapus.setToolTipText("Pilih siswa pada tabel terlebih dahulu");
        Ubah.setEnabled(false);
        Hapus.setEnabled(false);
    }

    /** Menghubungkan semua komponen ke logika. */
    private void siapkanLogika() {
        TambahSiswa.addActionListener(e -> tambahSiswa());
        Ubah.addActionListener(e -> ubahSiswa());
        Hapus.addActionListener(e -> hapusSiswa());
        exit.addActionListener(e -> keluar());
        ComboBoxTahunAjaran.addActionListener(e -> muatData());

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

    private static String urutBulan(String kolom) {
        StringBuilder sb = new StringBuilder("CASE ").append(kolom);
        for (int i = 0; i < URUTAN_BULAN.length; i++) {
            sb.append(" WHEN '").append(URUTAN_BULAN[i]).append("' THEN ").append(i + 1);
        }
        return sb.append(" ELSE 13 END").toString();
    }

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
        Integer nisSebelumnya = nisSebelumnyaTerpilih();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.id_siswa, s.nama, s.kelas, ");
        sql.append("COALESCE(");
        sql.append("(SELECT p2.nominal FROM Pembayaran p2 WHERE p2.id_siswa = s.id_siswa ");
        sql.append("AND p2.tahun_ajaran = ? AND COALESCE(p2.status, '') <> 'lunas' ");
        sql.append("ORDER BY ").append(urutBulan("p2.bulan")).append(" LIMIT 1), ");
        sql.append("(SELECT p3.nominal FROM Pembayaran p3 WHERE p3.id_siswa = s.id_siswa ");
        sql.append("AND p3.tahun_ajaran = ? ORDER BY ").append(urutBulan("p3.bulan")).append(" DESC LIMIT 1), ");
        sql.append("0) AS nominal, ");
        sql.append("COUNT(DISTINCT p.bulan) AS total, ");
        sql.append("COUNT(DISTINCT CASE WHEN p.status = 'lunas' THEN p.bulan END) AS lunas ");
        sql.append("FROM siswa s ");
        sql.append("LEFT JOIN Pembayaran p ON p.id_siswa = s.id_siswa AND p.tahun_ajaran = ? ");
        if (!kata.isEmpty()) {
            sql.append("WHERE (s.nama LIKE ? OR s.kelas LIKE ? OR CAST(s.id_siswa AS TEXT) LIKE ?) ");
        }
        sql.append("GROUP BY s.id_siswa, s.nama, s.kelas ORDER BY s.id_siswa");

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
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

            modelSiswa.setRowCount(0);
            try (ResultSet rs = ps.executeQuery()) {
                int nomor = 1;
                while (rs.next()) {
                    int total = rs.getInt("total");
                    int lunas = rs.getInt("lunas");
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
            pilihBarisNis(nisSebelumnya);
        }
    }

    /** NIS baris yang sedang dipilih tanpa menampilkan peringatan (dipakai saat memuat ulang). */
    private Integer nisSebelumnyaTerpilih() {
        int baris = TabelDataSiswa.getSelectedRow();
        if (baris == -1) {
            return null;
        }
        return (Integer) modelSiswa.getValueAt(TabelDataSiswa.convertRowIndexToModel(baris), 1);
    }

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

    private record FormSiswa(String nama, String kelas, int nominal) {
    }

    @FunctionalInterface
    private interface Transaksi {

        void jalankan(Connection conn) throws SQLException;
    }

    /** Menjalankan beberapa perintah SQL sebagai satu kesatuan; gagal berarti semuanya dibatalkan. */
    private void transaksi(Transaksi isi) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);
            try {
                isi.jalankan(conn);
                conn.commit();
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private void tambahSiswa() {
        String tahun = tahunAjaranDipilih();
        FormSiswa data = tampilkanForm("Tambah Siswa", "", "", 0, tahun);
        if (data == null) {
            return;
        }

        int[] nisBaru = {0};
        try {
            transaksi(conn -> {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO siswa (nama, kelas) VALUES (?, ?)")) {
                    ps.setString(1, data.nama());
                    ps.setString(2, data.kelas());
                    ps.executeUpdate();
                }
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

    private void ubahSiswa() {
        int m = barisModelTerpilih();
        if (m == -1) {
            return;
        }
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

    private void hapusSiswa() {
        int m = barisModelTerpilih();
        if (m == -1) {
            return;
        }
        int nis = (Integer) modelSiswa.getValueAt(m, 1);
        String nama = String.valueOf(modelSiswa.getValueAt(m, 2));

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

        int pilihan = JOptionPane.showOptionDialog(this, pesan, "Konfirmasi hapus",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Hapus", "Batal"}, "Batal");
        if (pilihan != 0) {
            return;
        }

        try {
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
        String sql = """
            INSERT INTO Pembayaran (id_siswa, bulan, tahun_ajaran, nominal, tgl_bayar, status)
            SELECT ?, ?, ?, ?, NULL, 'menunggak'
            WHERE NOT EXISTS (
                SELECT 1 FROM Pembayaran
                WHERE id_siswa = ? AND bulan = ? AND tahun_ajaran = ?
            )
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String bulan : URUTAN_BULAN) {
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

        while (true) {
            int pilihan = JOptionPane.showConfirmDialog(this, form, judul,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (pilihan != JOptionPane.OK_OPTION) {
                return null;
            }

            String namaBaru = fNama.getText().trim();
            String kelasBaru = fKelas.getText().trim();
            String angka = fNominal.getText().replaceAll("[^0-9]", "");

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
                    nominalBaru = -1;
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

    private void tampilkanErrorDatabase(String aksi, SQLException e) {
        logger.log(java.util.logging.Level.SEVERE, "Gagal " + aksi, e);
        JOptionPane.showMessageDialog(this, "Gagal " + aksi + ": " + e.getMessage(),
                "Database error", JOptionPane.ERROR_MESSAGE);
    }

    // ---------------------------- RENDERER TABEL ----------------------------

    /** Renderer sel: perataan, format teks, warna selang-seling, dan warna seleksi hijau. */
    private static class SelRenderer extends DefaultTableCellRenderer {

        private final int rata;
        private final Function<Object, String> format;

        SelRenderer(int rata, Function<Object, String> format) {
            this.rata = rata;
            this.format = format;
        }

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
            setBackground(dipilih ? HIJAU_MUDA : (baris % 2 == 0 ? Color.WHITE : new Color(248, 250, 252)));
            return this;
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
