package properties;

import javax.swing.JFrame;

public class ganti_halaman {

    public static void gantihalaman(JFrame halamanLama, JFrame halamanBaru) {
        halamanBaru.setVisible(true);
        halamanLama.dispose();
    }
}