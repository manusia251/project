// Package controller: Berisi kelas-kelas kontroler antarmuka (GUI) dan logika tampilan
package controller;
import dao.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
        tampilkanDataPembayaran();
    }
    
    
    
    private void tampilkanDataPembayaran() {
        // Saat halaman pertama kali dibuka, tampilkan seluruh data.
        tampilkanDataPembayaran("", "Semua");
    }

    /**
     * Mengisi tabel berdasarkan kata pencarian dan pilihan filter.
     * Pencarian dilakukan pada nama siswa, NIS, dan bulan pembayaran.
     */
    private void tampilkanDataPembayaran(String kataKunci, String filter) {
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{
            "NO. KWITANSI",
            "TANGGAL",
            "NIS",
            "NAMA SISWA",
            "KELAS",
            "BULAN",
            "JUMLAH",
            "STATUS"
        }, 0
    );

    StringBuilder sql = new StringBuilder("""
        SELECT 
            p.id_pembayaran,
            p.tgl_bayar,
            p.id_siswa,
            s.nama,
            s.kelas,
            p.bulan,
            p.nominal,
            p.status
        FROM Pembayaran p
        LEFT JOIN siswa s ON p.id_siswa = s.id_siswa
        WHERE 1 = 1
        """);

    List<String> parameter = new ArrayList<>();
    String pencarian = kataKunci == null ? "" : kataKunci.trim();

    // Jangan gunakan teks placeholder sebagai kata pencarian.
    if (!pencarian.isBlank() && !pencarian.equalsIgnoreCase("cari nama")) {
        sql.append(" AND (s.nama LIKE ? OR CAST(p.id_siswa AS TEXT) LIKE ? OR p.bulan LIKE ?)");
        String pola = "%" + pencarian + "%";
        parameter.add(pola);
        parameter.add(pola);
        parameter.add(pola);
    }

    if ("Pembayaran".equals(filter)) {
        // Pembayaran berarti transaksi yang sudah lunas.
        sql.append(" AND p.status = 'lunas'");
    } else if ("Tagihan".equals(filter)) {
        // Tagihan berarti transaksi yang masih menunggak.
        sql.append(" AND p.status = 'menunggak'");
    }

    if ("A-Z".equals(filter)) {
        sql.append(" ORDER BY s.nama COLLATE NOCASE ASC, p.tgl_bayar DESC");
    } else {
        sql.append(" ORDER BY p.tgl_bayar DESC, p.id_pembayaran DESC");
    }

    try (
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql.toString());
    ) {
        // Parameter diisi setelah query selesai dibentuk agar aman dari SQL injection.
        for (int i = 0; i < parameter.size(); i++) {
            ps.setString(i + 1, parameter.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_pembayaran"),
                rs.getString("tgl_bayar"),
                rs.getInt("id_siswa"),
                rs.getString("nama"),
                rs.getString("kelas"),
                rs.getString("bulan"),
                rs.getInt("nominal"),
                rs.getString("status")
            });
        }
        }

        tampilan_database.setModel(model);
        tampilan_database.setAutoCreateRowSorter(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Gagal mengambil data pembayaran: " + e.getMessage()
        );
    }
    }

    /** Menjalankan pencarian ketika tombol Cari ditekan atau Enter digunakan. */
    private void cariData() {
        String kataKunci = name_search.getText();
        String filter = String.valueOf(jComboBox1.getSelectedItem());
        tampilkanDataPembayaran(kataKunci, filter);
    }

    /**
     * Mengambil ID pembayaran dari baris yang sedang dipilih pada tabel.
     * Nilai null dikembalikan jika pengguna belum memilih baris apa pun.
     */
    private Integer getIdPembayaranTerpilih() {
        int selectedRow = tampilan_database.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih transaksi pada tabel terlebih dahulu.",
                    "Transaksi belum dipilih",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Konversi diperlukan jika tabel sedang diurutkan oleh pengguna.
        int modelRow = tampilan_database.convertRowIndexToModel(selectedRow);
        return Integer.valueOf(tampilan_database.getModel().getValueAt(modelRow, 0).toString());
    }

    /**
     * Membuat tagihan satu tahun ajaran untuk seluruh siswa.
     * Tagihan yang sudah ada tidak akan dibuat ulang karena menggunakan NOT EXISTS.
     */
    private void buatTagihanSatuTahun() {
        String tahunAjaran = JOptionPane.showInputDialog(this,
                "Masukkan tahun ajaran:", "2026/2027");
        if (tahunAjaran == null || tahunAjaran.isBlank()) {
            return;
        }

        String nominalInput = JOptionPane.showInputDialog(this,
                "Masukkan nominal SPP per bulan:", "500000");
        if (nominalInput == null || nominalInput.isBlank()) {
            return;
        }

        try {
            int nominal = Integer.parseInt(nominalInput.trim());
            if (nominal <= 0) {
                throw new NumberFormatException("Nominal harus lebih besar dari 0");
            }

            // Urutan bulan mengikuti tahun ajaran sekolah, bukan urutan alfabet.
            String[] bulan = {
                "Juli", "Agustus", "September", "Oktober", "November", "Desember",
                "Januari", "Februari", "Maret", "April", "Mei", "Juni"
            };

            String sql = """
                INSERT INTO Pembayaran (id_siswa, bulan, tahun_ajaran, nominal, tgl_bayar, status)
                SELECT id_siswa, ?, ?, ?, NULL, 'menunggak'
                FROM siswa s
                WHERE NOT EXISTS (
                    SELECT 1 FROM Pembayaran p
                    WHERE p.id_siswa = s.id_siswa
                      AND p.bulan = ?
                      AND p.tahun_ajaran = ?
                )
                """;

            int jumlahTagihan = 0;
            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false);

                for (String namaBulan : bulan) {
                    ps.setString(1, namaBulan);
                    ps.setString(2, tahunAjaran.trim());
                    ps.setInt(3, nominal);
                    ps.setString(4, namaBulan);
                    ps.setString(5, tahunAjaran.trim());
                    jumlahTagihan += ps.executeUpdate();
                }

                conn.commit();
            }

            tampilkanDataPembayaran();
            JOptionPane.showMessageDialog(this,
                    jumlahTagihan + " tagihan baru berhasil dibuat.",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Nominal harus berupa angka, contoh: 500000.",
                    "Input tidak valid",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal membuat tagihan: " + e.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menandai tagihan terpilih sebagai sudah dibayar dan menyimpan tanggal hari ini.
     */
    private void terimaPembayaran() {
        Integer idPembayaran = getIdPembayaranTerpilih();
        if (idPembayaran == null) {
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Tandai transaksi #" + idPembayaran + " sebagai lunas?",
                "Konfirmasi pembayaran",
                JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "UPDATE Pembayaran SET status = 'lunas', tgl_bayar = ? WHERE id_pembayaran = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, LocalDate.now().toString());
            ps.setInt(2, idPembayaran);
            ps.executeUpdate();

            tampilkanDataPembayaran();
            JOptionPane.showMessageDialog(this,
                    "Pembayaran berhasil diterima.",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan pembayaran: " + e.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menampilkan detail kwitansi berdasarkan transaksi yang dipilih.
     * Fitur ini belum mencetak ke printer, tetapi sudah menampilkan isi kwitansi.
     */
    private void tampilkanKwitansi() {
        Integer idPembayaran = getIdPembayaranTerpilih();
        if (idPembayaran == null) {
            return;
        }

        String sql = """
            SELECT p.id_pembayaran, p.tgl_bayar, p.id_siswa, s.nama, s.kelas,
                   p.bulan, p.tahun_ajaran, p.nominal, p.status
            FROM Pembayaran p
            LEFT JOIN siswa s ON p.id_siswa = s.id_siswa
            WHERE p.id_pembayaran = ?
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPembayaran);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String kwitansi = """
                        KWITANSI PEMBAYARAN SPP
                        =========================
                        No. Kwitansi : %d
                        Tanggal      : %s
                        NIS          : %d
                        Nama         : %s
                        Kelas        : %s
                        Bulan        : %s
                        Tahun Ajaran : %s
                        Jumlah       : Rp%,d
                        Status       : %s
                        """.formatted(
                            rs.getInt("id_pembayaran"),
                            rs.getString("tgl_bayar") == null ? "Belum dibayar" : rs.getString("tgl_bayar"),
                            rs.getInt("id_siswa"),
                            rs.getString("nama"),
                            rs.getString("kelas"),
                            rs.getString("bulan"),
                            rs.getString("tahun_ajaran"),
                            rs.getInt("nominal"),
                            rs.getString("status")
                        );

                    JOptionPane.showMessageDialog(this, kwitansi,
                            "Kwitansi #" + idPembayaran,
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengambil data kwitansi: " + e.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
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
        panel_untuk_tgl_bln_dll = new properties.Blurr();
        name_search = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tampilkan_jam = new javax.swing.JLabel();
        tampilkan_kalender = new javax.swing.JLabel();
        cari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tampilan_database = new javax.swing.JTable();
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
        log_out = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(32, 172, 91));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        name_search.setText("cari nama");
        name_search.addActionListener(this::name_searchActionPerformed);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "A-Z", "Pembayaran", "Tagihan" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jButton3.setText("Buat Tagihan 1 Tahun");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Kwitansi");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Terima Pembayaran");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jLabel2.setText("Tagihan & Pembayaran");

        jLabel5.setText("Tanggal");

        jLabel6.setText("Jam");

        tampilkan_jam.setText("jLabel7");

        tampilkan_kalender.setText("jLabel7");

        cari.setText("cari");
        cari.addActionListener(this::cariActionPerformed);

        javax.swing.GroupLayout panel_untuk_tgl_bln_dllLayout = new javax.swing.GroupLayout(panel_untuk_tgl_bln_dll);
        panel_untuk_tgl_bln_dll.setLayout(panel_untuk_tgl_bln_dllLayout);
        panel_untuk_tgl_bln_dllLayout.setHorizontalGroup(
            panel_untuk_tgl_bln_dllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_untuk_tgl_bln_dllLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panel_untuk_tgl_bln_dllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_untuk_tgl_bln_dllLayout.createSequentialGroup()
                        .addComponent(name_search, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addGroup(panel_untuk_tgl_bln_dllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_untuk_tgl_bln_dllLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_untuk_tgl_bln_dllLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tampilkan_kalender, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tampilkan_jam, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        panel_untuk_tgl_bln_dllLayout.setVerticalGroup(
            panel_untuk_tgl_bln_dllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_untuk_tgl_bln_dllLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_untuk_tgl_bln_dllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(tampilkan_jam)
                    .addComponent(tampilkan_kalender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_untuk_tgl_bln_dllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_untuk_tgl_bln_dllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jButton5)
                        .addComponent(name_search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1)))
                .addGap(30, 30, 30))
        );

        jPanel2.add(panel_untuk_tgl_bln_dll, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 920, 130));

        tampilan_database.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "no", "Tanggal", "Nis", "Nama", "Kelas", "Bulan", "Jumlah", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tampilan_database);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 920, 460));

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

        log_out.setText("Keluar");
        log_out.addActionListener(this::log_outActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(log_out))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(log_out)
                .addGap(9, 9, 9))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 620));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        // Event handler: Dipanggil saat tombol 'Dashboard' diklik
    private void dashboard_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Dashboard());
    }//GEN-LAST:event_dashboard_variableActionPerformed

    // Event handler: Dipanggil saat tombol 'Laporan' diklik
    private void Laporan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Laporan_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Laporan());
    }//GEN-LAST:event_Laporan_variableActionPerformed

    // Event handler: Dipanggil saat tombol 'Data Siswa' diklik
    private void Siswa_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Siswa_variableActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new DataSiswa());
    }//GEN-LAST:event_Siswa_variableActionPerformed

    private void Tagihan_variableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tagihan_variableActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null,"kamu sudah di tab tagihan");
    }//GEN-LAST:event_Tagihan_variableActionPerformed

    private void Riwayat_variabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Riwayat_variabelActionPerformed
        // Berpindah halaman menggunakan method statis gantihalaman dari class properties.ganti_halaman
        properties.ganti_halaman.gantihalaman(this, new Riwayat());
    }//GEN-LAST:event_Riwayat_variabelActionPerformed

    private void log_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_log_outActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(null, "apakah kamu yakin log out?", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION){
            properties.ganti_halaman.gantihalaman(this, new LoginAdmin());
        }
        else{
            
        }
    }//GEN-LAST:event_log_outActionPerformed

    // Tombol ini membuat tagihan bulanan yang belum tersedia untuk semua siswa.
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        buatTagihanSatuTahun();
    }

    // Tombol ini menampilkan kwitansi dari baris tabel yang sedang dipilih.
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        tampilkanKwitansi();
    }

    // Tombol ini menerima pembayaran dari tagihan yang sedang dipilih.
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        terimaPembayaran();
    }

    // Tombol Cari menjalankan filter berdasarkan teks pada name_search.
    private void cariActionPerformed(java.awt.event.ActionEvent evt) {
        cariData();
    }

    // Menekan Enter di kotak pencarian memiliki fungsi yang sama dengan tombol Cari.
    private void name_searchActionPerformed(java.awt.event.ActionEvent evt) {
        cariData();
    }

    // Saat pilihan filter berubah, isi tabel langsung diperbarui.
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        cariData();
    }

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
    private javax.swing.JButton cari;
    private javax.swing.JToggleButton dashboard_variable;
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
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton log_out;
    private javax.swing.JTextField name_search;
    private javax.swing.JPanel panel_untuk_tgl_bln_dll;
    private javax.swing.JTable tampilan_database;
    private javax.swing.JLabel tampilkan_jam;
    private javax.swing.JLabel tampilkan_kalender;
    // End of variables declaration//GEN-END:variables
}
