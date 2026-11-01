package properties;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class blur_kotak extends JPanel {

    public blur_kotak() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        try {
            int w = getWidth();
            int h = getHeight();

            // 1. Isi latar transparan (siku-siku)
            g2.setColor(new Color(255, 255, 255, 35));
            g2.fillRect(0, 0, w, h);

            // 2. Garis tepi/border transparan (siku-siku)
            g2.setColor(new Color(255, 255, 255, 90));
            g2.drawRect(0, 0, w - 1, h - 1);
        } finally {
            g2.dispose();
        }
    }
}