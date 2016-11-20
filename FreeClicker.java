/**
 * Created by Vishnu on 11/20/2016.
 */


import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

import static spark.Spark.*;

public class FreeClicker {

    private static ArrayList<String> users = new ArrayList<>();
    private static ArrayList<String> options = new ArrayList<>();
    private static int[] results = new int[5];
    private static ViewBar vb = new ViewBar();
    private static int numVotes = 0;
    private static String chartPage = "";

    private static void updateLists(String user, String option) {

        System.out.print("\nUsers in list: ");
        for(int i = 0;i<users.size();i++)
            System.out.print(users.get(i)+" ");
        System.out.println();

        if(users==null || users.size()==0) {
            users.add(user);
            options.add(option);
            numVotes++;
            vb.numVotesLabel.setText(""+numVotes);

            if(option.equalsIgnoreCase("A")) results[0]++;
            else if(option.equalsIgnoreCase("B")) results[1]++;
            else if(option.equalsIgnoreCase("C")) results[2]++;
            else if(option.equalsIgnoreCase("D")) results[3]++;
            else if(option.equalsIgnoreCase("E")) results[4]++;
        }
        else {
            for(int i = 0;i<users.size();i++) {
                if(users.get(i).equals(user)) {
                    String coption = options.get(i);
                    System.out.println("Current Option: "+coption+" Current user: "+users.get(i));
                    options.set(i, option);
                    if(option.equalsIgnoreCase("A")) results[0]++;
                    else if(option.equalsIgnoreCase("B")) results[1]++;
                    else if(option.equalsIgnoreCase("C")) results[2]++;
                    else if(option.equalsIgnoreCase("D")) results[3]++;
                    else if(option.equalsIgnoreCase("E")) results[4]++;

                    if(coption.equalsIgnoreCase("A")) results[0]--;
                    else if(coption.equalsIgnoreCase("B")) results[1]--;
                    else if(coption.equalsIgnoreCase("C")) results[2]--;
                    else if(coption.equalsIgnoreCase("D")) results[3]--;
                    else if(coption.equalsIgnoreCase("E")) results[4]--;
                    return;
                }
            }

            users.add(user);
            options.add(option);
            numVotes++;
            vb.numVotesLabel.setText(""+numVotes);
            if(option.equalsIgnoreCase("A")) results[0]++;
            else if(option.equalsIgnoreCase("B")) results[1]++;
            else if(option.equalsIgnoreCase("C")) results[2]++;
            else if(option.equalsIgnoreCase("D")) results[3]++;
            else if(option.equalsIgnoreCase("E")) results[4]++;
        }
    }

    private static void newQuestion() {
        results[0] = results[1] = results[2] = results[3] = results[4] = 0;
        users = new ArrayList<>();
        options = new ArrayList<>();
        vb.numVotesLabel.setText("0");
        numVotes = 0;
        vb.timeLabel.setText("0:00");
    }

    public static void main(String ar[]) throws IOException {

        newQuestion();

        JSONParser parser = new JSONParser();
        Random r = new Random();
        int port = 1000 + r.nextInt(64535);
        String roomName = "CS 182";



        setIpAddress("0.0.0.0");
        setPort(port);

        try {
            FileReader fr = new FileReader("chart.html");
            BufferedReader br = new BufferedReader(fr);
            String S;
            while((S=br.readLine())!=null) {
                if(S.indexOf("%%IPPORT%%")!=-1)
                    S = S.replace("%%IPPORT%%", "localhost:"+port);
                chartPage += S+"\n";
            }

            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            DatagramSocket ds = new DatagramSocket();
            ds.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String json = String.format("{ \"room\" : \"%s\" , \"ip\" : \"%s\" , \"port\" : %d }", roomName, ds.getLocalAddress().getHostAddress(), port);
            QRScreen qr = new QRScreen(json);
        } catch (Exception e) {
            e.printStackTrace();
        }


        get("/results", (req, res) -> {
            String S = String.format("[%d,%d,%d,%d,%d]",results[0], results[1], results[2], results[3], results[4]);
            return S;
        });

        get("/", (req, res) -> chartPage);

        post("/answer/", (req, res) -> {
            try {
                Object obj = parser.parse(req.body());
                JSONArray array = (JSONArray)obj;
                updateLists((String)array.get(0), (String)array.get(1));
                System.out.println((String)array.get(0)+": "+(String)array.get(1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "success";
        });
    }

}
