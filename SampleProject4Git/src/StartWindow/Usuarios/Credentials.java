package StartWindow.Usuarios;

import StartWindow.Main.Main;
import com.opencsv.CSVWriter;

import javax.swing.*;

//import StartWindow.NewWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Credentials extends JFrame implements ActionListener {
    //Global variables
    JButton button;
    JTextField username;
    JTextField pass;


    public Credentials(){

        int varX = 17;
        int varY = 20;

        int varWH = 12;

        //Methods for the screen's interface
        this.setBounds(0, 0, varWH*75, varWH*50); //Tamaño provicional, posiblemente cambie
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("CE Music Player");

        //Constructor + Add what is shown in screen

        JLabel titlescreen = new JLabel("WELCOME TO CE MUSIC PLAYER");
        titlescreen.setBounds(varX*17, varY, varWH*20, varWH);
        this.add(titlescreen);

        JLabel askName = new JLabel("ENTER YOUR E-MAIL");
        askName.setBounds(varX, varY*3, varWH*20, varWH*4);
        this.add(askName);

        username = new JTextField();
        username.setBounds(varX, varY*5, varWH*20, varWH*4);
        this.add(username);

        JLabel askPass = new JLabel("ENTER YOUR PASS");
        askPass.setBounds(varX*30, varY*3, varWH*20, varWH*4);
        this.add(askPass);

        pass = new JTextField();
        pass.setBounds(varX*30, varY*5, varWH*20, varWH*4);
        this.add(pass);

        button = new JButton("SUBMIT");
        button.setBounds(varX*15, varY*15, varWH*20, varWH*4);
        button.addActionListener(this);
        this.add(button);

    }

    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the button is pressed
        if (e.getSource() == button){
            System.out.println("\n\n\n\n\nChecking your info in the data base\n");

            CSVWriter csvWriter;
            BufferedReader csvDataBase;
            BufferedReader csvSolicitud;

            String Usuario= username.getText();
            String Pass= pass.getText();
            String Path= "SampleProject4Git/src/StartWindow/Usuarios/"+Usuario+".csv";
            try {
                csvSolicitud = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Solicitudes.csv"));
                csvDataBase = new BufferedReader(new FileReader(Path));

                String[] lineaSolicitud= {Usuario, Pass};

                csvWriter = new CSVWriter(new FileWriter("SampleProject4Git/src/StartWindow/Usuarios/Solicitudes.csv"));
                csvWriter.writeNext(lineaSolicitud);
                csvWriter.close();

                String lineaSolicitada = csvSolicitud.readLine();
                String lineaDataBase = csvDataBase.readLine();

                System.out.println("Datos requeridos: "+lineaDataBase);
                System.out.println("Datos suministrados: "+lineaSolicitada);

                //if (username.getText().equals("Chris") && (pass.getText().equals("pass"))){
                if (lineaDataBase.equals(lineaSolicitada)){

                    System.out.println("Access allowed\n");
                    this.dispose(); //Closes current window
                    try {
                        Main.VentanaInicio(Path);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    //NewWindow mainWindow = new NewWindow();
                    csvSolicitud.close();
                    csvDataBase.close();
                }else{
                    System.out.println("Access denied");
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
