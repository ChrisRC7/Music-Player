package StartWindow.Usuarios;

import StartWindow.Main.Main;

import javax.swing.*;

//import StartWindow.NewWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Credentials extends JFrame implements ActionListener {
    //Global variables
    JButton button;
    JTextField username;
    JTextField pass;
    JTextField email;
    JTextField province;

    public Credentials(){
        //Methods for the screen's interface
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CE Music Player");

        //Constructor
        JLabel askName = new JLabel("Enter your name");
        JLabel askPass = new JLabel("Enter your pass");
        button = new JButton("Submit");
        username = new JTextField();
        pass = new JTextField();
        email = new JTextField();
        province = new JTextField();

        button.addActionListener(this);
        username.setPreferredSize(new Dimension(250, 40));
        pass.setPreferredSize(new Dimension(250, 40));
        email.setPreferredSize(new Dimension(250,40));
        province.setPreferredSize(new Dimension(250,40));

        //Add what is shown in screen
        this.add(button);
        this.add(askName);
        this.add(username);
        this.add(askPass);
        this.add(pass);
        this.add(email);
        this.add(province);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if the button is pressed
        if (e.getSource() == button){
            System.out.println("Checking your info in the data base");
            if (username.getText().equals("Chris") && (pass.getText().equals("pass"))){
                System.out.println("Access allowed");
                this.dispose(); //Closes current window
                try {
                    Main.VentanaInicio();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                //NewWindow mainWindow = new NewWindow();
            }else{
                System.out.println("Access denied");
            }
        }
    }
}
