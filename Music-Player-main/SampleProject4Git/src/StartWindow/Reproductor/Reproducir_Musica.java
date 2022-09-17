package StartWindow.Reproductor;
import javazoom.jlgui.basicplayer.BasicPlayer;
import java.io.File;

public class Reproducir_Musica {

    private static BasicPlayer player;

    
    public static void Play() throws Exception{
        player= new BasicPlayer();
        player.open(new File("test.mp3"));
        player.play();
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
