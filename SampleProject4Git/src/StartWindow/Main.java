package StartWindow;
import javax.swing.*;
import java.awt.event.*;
import java.io.ObjectOutputStream.PutField;

import StartWindow.ListasEnlazadas.Double_CircularLinkedList;
import StartWindow.ListasEnlazadas.LinkedList;
import StartWindow.Reproductor.Reproducir_Musica;

public class Main extends JFrame implements ActionListener {


    JButton Playbtn;
    public Main(){
        setLayout(null);
        Playbtn= new JButton("Play");
        Playbtn.setBounds(100, 100, 100, 50);
        add(Playbtn);
        Playbtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==Playbtn) {
            try {
                Reproducir_Musica.Play();
            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }
        
    }
        

    //Main Method
    public static void main(String[] args){
        //Instantiate launchScreen for the app
        //Credentials launchPage = new Credentials();
        Main RepdroductorVentana = new Main();
        RepdroductorVentana.setBounds(0, 0, 500, 500); //Tamaño provicional, posiblemente cambie
        RepdroductorVentana.setVisible(true);
        RepdroductorVentana.setTitle("El mp3 con la tula mas grande que hay");
        RepdroductorVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
   

}

}
