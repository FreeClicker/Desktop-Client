/**
 * Created by Vishnu on 11/20/2016.
 */


import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import static spark.Spark.*;

public class FreeClicker {



    public static void main(String ar[]) {

        Random r = new Random();
        int port = 1000 + r.nextInt(64535);
        String roomName = "Get some yellow pungent snacks";



        setIpAddress("0.0.0.0");
        setPort(port);

        try {
            DatagramSocket ds = new DatagramSocket();
            ds.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String json = String.format("{ \"room\" : \"%s\" , \"ip\" : \"%s\" , \"port\" : %d }", roomName, ds.getLocalAddress().getHostAddress(), port);
            QRScreen qr = new QRScreen(json);
        } catch (Exception e) {
            e.printStackTrace();
        }


        get("/hello", (req, res) -> {
            System.out.println(req.port());
            return "Hello World";
        });

        get("/", (req, res) -> "Get the fuck out of this page");

        post("/answer/", (req, res) -> {
           System.out.println(req.body());
            System.out.println(req);
            System.out.println(req.contentLength());
            return "success";
        });
    }

}
