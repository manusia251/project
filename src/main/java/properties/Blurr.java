package properties;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class Blurr extends JPanel {

    public Blurr() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 1. Panggil super di paling atas agar canvas disiapkan dulu
        super.paintComponent(g);

        // 2. Buat copy dari Graphics2D
        Graphics2D g2 = (Graphics2D) g.create();

        try {
            // Smooth edge (anti-aliasing)
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Warna kartu putih transparan; angka terakhir adalah tingkat transparansi (0-255).
            g2.setColor(new Color(255, 255, 255, 35));
            g2.fill(new RoundRectangle2D.Double(
                0, 0, getWidth() - 1, getHeight() - 1, 25, 25
            ));

            // Warna dan transparansi garis tepi kartu.
            g2.setColor(new Color(255, 255, 255, 90));
            g2.draw(new RoundRectangle2D.Double(
                0, 0, getWidth() - 1, getHeight() - 1, 25, 25
            ));
        } finally {
            // 3. Pastikan g2 selalu di-dispose dengan aman
            g2.dispose();
        }
    }
}
