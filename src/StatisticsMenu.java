import sun.audio.AudioPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.POPUP_LAYER;

public class StatisticsMenu {
    int time;
    double efficiency;
    int statMode = 0;
    JPanel statisticPanel = new JPanel();
    JLabel effectivnessLabel = new JLabel("Your path is " + efficiency + "% effective");
    JLabel movesLabel = new JLabel("");
    JLabel timeLabel = new JLabel("00:00");
    JLabel statLabel = new JLabel("Stats");
    JButton toMenu = new JButton("To Main Menu");
    JLabel pic = new JLabel(new ImageIcon("D:\\Photoes\\mnmnbckgrd.png"));

    public StatisticsMenu(JFrame frame, StopWatch wt, Maze myMaze, Sounds audiow) throws InterruptedException {
        frame.setContentPane(statisticPanel);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);


        frame.setLayout(null);
        statisticPanel.setBorder(new EmptyBorder(120, 350, 700, 350));

        effectivnessLabel.setFont(new Font("impact", Font.PLAIN, 20));
        movesLabel.setFont(new Font("impact", Font.PLAIN, 20));
        timeLabel.setFont(new Font("impact", Font.PLAIN, 20));
        statLabel.setFont(new Font("impact", Font.PLAIN, 30));
        toMenu.setFont(new Font("impact", Font.PLAIN, 22));

        pic.setBounds(-350, -250, 1800, 1800);
        movesLabel.setBounds(70, 300, 500, 40);
        timeLabel.setBounds(70, 340, 500, 40);
        effectivnessLabel.setBounds(70, 380, 500, 40);
        statLabel.setBounds(70, 140, 200, 80);
        toMenu.setBounds(70, 440, 360, 60);


        statisticPanel.add(effectivnessLabel, POPUP_LAYER);
        statisticPanel.add(movesLabel, POPUP_LAYER);
        statisticPanel.add(timeLabel, POPUP_LAYER);
        statisticPanel.add(statLabel, POPUP_LAYER);
        statisticPanel.add(toMenu, POPUP_LAYER);
        pic.setOpaque(true);
        statisticPanel.add(pic, DEFAULT_LAYER);
        toMenu.setBorderPainted(false);
        pic.setVisible(true);
        time = wt.CurrentAmountOfTime;
        efficiency = myMaze.perfectPathLength / myMaze.playerPathLength * 100;
        efficiency = Math.round(efficiency * 10) / 10.d;


        timeLabel.setText("Time is " + String.format("%02d:%02d", time / 60, time % 60));
        movesLabel.setText("Length of your path is " + String.valueOf(myMaze.playerPathLength));
        effectivnessLabel.setText("Path is " + efficiency + "% effective");


        this.toMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                statMode = 1;
            }
        });
        statisticPanel.setVisible(true);
        synchronized (this) {
            while (statMode == 0) {
                this.wait(1000);
            }
        }
        statisticPanel.setVisible(false);
    }
}
