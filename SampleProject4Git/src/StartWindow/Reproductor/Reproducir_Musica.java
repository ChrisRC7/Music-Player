package StartWindow.Reproductor;
import javazoom.jlgui.basicplayer.BasicPlayer;


import java.io.File;

import StartWindow.ListasEnlazadas.Double_CircularLinkedList;

public class Reproducir_Musica extends Thread {
    Boolean Estado;

    private  BasicPlayer player = new BasicPlayer();
    
    
    public void Play(String Canción) throws Exception{
        Stop();
        player.open(new File(Canción));
        player.play();
        
    }

    public void Reprodución_Continua(String Canción, Double_CircularLinkedList Lista) throws Exception {
        if (Status()!=0 && Status()!=1 && Status()!=2){

        
                player.open(new File(Canción));
                Play(Canción);
                Thread hilo1 = new Hilo("Canciones", Lista);
                hilo1.start();
        }else {
            
        }
        
    }

    public void Play2() throws Exception{
        player.open(new File("test.mp3"));
        player.play();
        
    }

    public int Status(){
 
        return player.getStatus();
    }

    public void EscogerMusica(String ruta) throws Exception{
        player.open(new File(ruta));
    }

    public void Pausa() throws Exception{
        player.pause();
    }

    public void Continuar() throws Exception{
        player.resume();
    }

    public void Stop() throws Exception{
        player.stop();
    }

    
    

}
