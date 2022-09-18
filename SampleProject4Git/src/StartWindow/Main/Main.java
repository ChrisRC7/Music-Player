package StartWindow.Main;
import java.io.FileReader;
import java.io.FileWriter;

import StartWindow.Usuarios.Credentials;
import com.opencsv.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
//import java.io.ObjectOutputStream.PutField;
//import StartWindow.ListasEnlazadas.*;
import StartWindow.Reproductor.*;


public class Main extends JFrame implements ActionListener {

    Reproducir_Musica Reproductor= new Reproducir_Musica();

    String Nombre_Canción, ruta;
  
    JButton Playbtn, Pausebtn, Continuebtn, Stopbtn, AgregarBtn, Play2btn, Statusbtn;
    public Main(){
        Playbtn= new JButton("Play");
        Playbtn.setBounds(100, 100, 70, 50);
        add(Playbtn);
        Playbtn.addActionListener(this);

        Pausebtn= new JButton("Pause");
        Pausebtn.setBounds(100, 150, 70, 50);
        add(Pausebtn);
        Pausebtn.addActionListener(this);

        Continuebtn= new JButton("Continue");
        Continuebtn.setBounds(100, 200, 70, 50);
        add(Continuebtn);
        Continuebtn.addActionListener(this);

        Stopbtn= new JButton("Stop");
        Stopbtn.setBounds(100, 250, 70, 50);
        add(Stopbtn);
        Stopbtn.addActionListener(this);

        AgregarBtn= new JButton("Agregar Canción");
        AgregarBtn.setBounds(120, 20, 150, 50);
        add(AgregarBtn);
        AgregarBtn.addActionListener(this);


        Statusbtn= new JButton("Status");
        Statusbtn.setBounds(200, 100, 70, 50);
        add(Statusbtn);
        Statusbtn.addActionListener(this);
        
        Play2btn= new JButton("Play2");
        Play2btn.setBounds(100, 300, 70, 50);
        add(Play2btn);
        Play2btn.addActionListener(this);

        JLabel Canciona_Selecionada = new JLabel("Sin selecionar");
        Canciona_Selecionada.setVerticalAlignment(JLabel.TOP);
        Canciona_Selecionada.setHorizontalAlignment(JLabel.CENTER);
        add(Canciona_Selecionada);
    }

    @Override
    public void actionPerformed(ActionEvent btn) {

        if (btn.getSource()==Playbtn) {
            try {
                Reproductor.Play();
            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }

        if (btn.getSource() == Pausebtn) {
            try {
                Reproductor.Pausa();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == Continuebtn) {
            try {
                Reproductor.Continuar();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == Stopbtn) {
            try {
                Reproductor.Stop();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == AgregarBtn) {
            
            Scanner entrada = null;
        JFileChooser Escoger_Canción = new JFileChooser();
        Escoger_Canción.showOpenDialog(Escoger_Canción);
        try {
            ruta = Escoger_Canción.getSelectedFile().getAbsolutePath();                                        
            File archivo = new File(ruta);
            Nombre_Canción= archivo.getName();
            entrada = new Scanner(archivo);
            while (entrada.hasNext()) {
                System.out.println(entrada.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }

        if (entrada!=null){
            CSVWriter csvWriter;
            BufferedReader archivocsv;
            try {
                
                archivocsv = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
                String linea= archivocsv.readLine();
                String[] datos= linea.split(",", -1);
                csvWriter = new CSVWriter(new FileWriter("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
                
                int largo_de_datos = datos.length; 
                int num_datos = 0;
                String[] Escribir= new String[largo_de_datos+1];
                
        
                while (num_datos < largo_de_datos) {
                    Escribir[num_datos]= datos[num_datos].replaceAll("\"", "");
                    num_datos++;
                }
                
                Escribir[num_datos]= Nombre_Canción;
                    System.out.println(Nombre_Canción);

                    csvWriter.writeNext(Escribir);
                    csvWriter.close();
                    archivocsv.close();
                    }
                    catch(Exception ee) {
                        System.out.println("error");
                    }
                    
                }
            }
    

        

        if (btn.getSource() == Play2btn) {
            try {
                Reproductor.Play2();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource()== Statusbtn) {
            Reproductor.Status();
        }

    }

    //Main Method
    public static void main(String[] args){
        new Credentials();
    }
}
