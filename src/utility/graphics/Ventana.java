package utility.graphics;

import javax.swing.*;

public class Ventana extends JFrame {
    static public Panel panel;

    public Ventana() {
        panel = new Panel();
        add(panel);
        super.setSize(1024, 720);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
