package StartWindow;

import javax.swing.*;
import StartWindow.Usuarios.*;


import java.awt.*;

public class NewWindow {
    JFrame frame = new JFrame();
    JLabel label = new JLabel("New Class");
    public NewWindow() {

        label.setBounds(0,0,300,50);
        label.setFont(new Font(null, Font.PLAIN,25));
        frame.setTitle("CE Music Player");
        frame.add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
