package StartWindow.Reproductor;
import StartWindow.ListasEnlazadas.Double_CircularLinkedList;
import StartWindow.Main.Main;
//import StartWindow.Reproductor.Reproducir_Musica;


public class Hilo extends Thread {
    Double_CircularLinkedList Canciones;
    Reproducir_Musica player ;

    public Hilo(String msg, Double_CircularLinkedList Lista) {
        super(msg);
      
    }



   
    public void run()  {
        while (true) {
           
            if (Main.StatusUsuario()==3) {
            break;
            
            }
            if (Main.Status()==2) {
                try {
                    Main.PlayNext();
                    System.out.println("Cambio Auto");
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            try {
                sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
        }
        

    }
    
}
