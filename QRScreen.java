import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;

/**
 * Created by Vishnu on 11/20/2016.
 */
public class QRScreen {

    JFXPanel fxPanel;
    WebView wv;
    JFrame frame;

    String text;

    QRScreen(String message) {

        text = "";

        try {
            setQR(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            fxPanel = new JFXPanel ();

            // create JavaFX scene
            com.sun.javafx.application.PlatformImpl.runLater ( new Runnable () {
                @Override
                public void run () {
                    wv = new WebView ();
                    System.out.println("Reading file");
                    URL urlHello = getClass().getResource("webqr.html");
                    //wv.getEngine().load(urlHello.toExternalForm());
                    wv.getEngine().loadContent(text);
                    fxPanel.setScene ( new Scene( wv, 400, 500 ) );
                    frame = new JFrame ( "Join the session" );
                    frame.add ( new JScrollPane ( fxPanel ) );
                    frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
                    frame.setVisible ( true );
                    frame.pack ();
                }
            } );

        } catch ( Exception ex ) {

        }
    }

    private void setQR(String message) throws IOException {
        try {
            URL urlHello = getClass().getResource("qr.html");
            FileReader fr = new FileReader(urlHello.getPath());
            BufferedReader br = new BufferedReader(fr);
            text = "";
            String S = "";

            while((S=br.readLine())!=null) {
                if(S.indexOf("%%QRCODEDATA%%")!=-1)
                    S = S.replace("%%QRCODEDATA%%", message);
                text += S+"\n";
            }

            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String ar[]) {
        QRScreen sc = new QRScreen("ass");
    }

}
