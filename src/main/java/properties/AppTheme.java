package properties;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

/**
 * Pengaturan tampilan global aplikasi.
 *
 * Class ini dipisahkan dari kode hasil GUI Builder agar perubahan tampilan
 * tidak hilang saat form diedit kembali menggunakan NetBeans Designer.
 */
public final class AppTheme {

    private AppTheme() {
        // Utility class tidak dibuat menjadi object.
    }

    /** Mengaktifkan FlatLaf dan mengatur tampilan dasar semua komponen Swing. */
    public static void setup() {
        FlatLightLaf.setup();

        // Font utama yang lebih bersih dan nyaman dibaca.
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));

        // Membuat tombol, field, dan combo box memiliki sudut membulat.
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 0);

        // Warna utama aplikasi: biru modern dengan kontras teks yang jelas.
        UIManager.put("Button.default.background", new Color(37, 99, 235));
        UIManager.put("Button.default.foreground", Color.WHITE);
        UIManager.put("Button.default.hoverBackground", new Color(29, 78, 216));
        UIManager.put("Component.focusColor", new Color(37, 99, 235));
        UIManager.put("TextComponent.selectionBackground", new Color(191, 219, 254));

        // Membuat tabel lebih mudah dibaca.
        UIManager.put("Table.rowHeight", 30);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines", false);
        UIManager.put("Table.gridColor", new Color(226, 232, 240));
        UIManager.put("Table.alternateRowColor", new Color(248, 250, 252));
        UIManager.put("Table.selectionBackground", new Color(219, 234, 254));
        UIManager.put("Table.selectionForeground", new Color(15, 23, 42));
        UIManager.put("TableHeader.background", new Color(241, 245, 249));
        UIManager.put("TableHeader.foreground", new Color(30, 41, 59));

        // Scrollbar yang lebih ramping agar tampilan tidak terlihat berat.
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.thumbArc", 8);
    }
}
