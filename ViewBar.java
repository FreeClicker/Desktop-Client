import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Vishnu on 11/20/2016.
 */
public class ViewBar {

    JFrame mainFrame;
    JPanel mainPanel;
    JPanel startStopPanel;
    JPanel graphPanel;
    JPanel startNewPanel;
    JPanel timerPanel;

    JPanel left, right, center;

    JButton startStopButton;
    JButton graphButton;
    JButton startNewButton;

    JLabel timeLabel;
    JLabel numVotesLabel;

    boolean paused = false;

    ViewBar() {
        mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(620, 120));
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridLayout(1,3));
        mainPanel.setPreferredSize(new Dimension(600, 100));
        mainFrame.add(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Left
        left = new JPanel();
        mainPanel.add(left);
        startStopPanel = new JPanel(new BorderLayout());
        startStopPanel.setPreferredSize(new Dimension(180, 55));
        startStopPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        startStopButton = new JButton("Multiple  Choice");
        startStopButton.setActionCommand("startStop");
        startStopPanel.add(startStopButton);
        startStopButton.setPreferredSize(new Dimension(177,52));
        left.add(startStopPanel);


        //Middle
        center = new JPanel(new GridLayout(1,2));
        center.setPreferredSize(new Dimension(300, 100));
        center.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        mainPanel.add(center);

        startNewPanel = new JPanel();
        center.add(startNewPanel);
        startNewButton = new JButton("strt");
        startNewButton.setActionCommand("newQuestion");
        startNewButton.setPreferredSize(new Dimension(60, 50));
        startNewPanel.add(startNewButton);

        graphPanel = new JPanel();
        center.add(graphPanel);
        graphButton = new JButton("grph");
        graphButton.setActionCommand("showGraph");
        graphButton.setPreferredSize(new Dimension(60, 50));
        graphPanel.add(graphButton);


        //Right
        right = new JPanel();
        right.setPreferredSize(new Dimension(300, 100));
        mainPanel.add(right);
        timerPanel = new JPanel(new BorderLayout());
        timerPanel.setPreferredSize(new Dimension(195, 55));
        right.add(timerPanel);
        timeLabel = new JLabel("00:56");
        numVotesLabel = new JLabel("69");
        timerPanel.add(timeLabel, BorderLayout.WEST);
        timerPanel.add(numVotesLabel, BorderLayout.EAST);

        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("startStop")) {
                    if (paused) {
                        startStopPanel.setBorder(BorderFactory.createLineBorder(Color.red));
                        startStopButton.setText("Pause");
                        paused = false;
                    } else {
                        startStopPanel.setBorder(BorderFactory.createLineBorder(Color.green));
                        startStopButton.setText("Start");
                        paused = true;
                    }
                }
            }
        });
    }

    public static void main(String ar[]) {
        ViewBar vb = new ViewBar();
    }

}
