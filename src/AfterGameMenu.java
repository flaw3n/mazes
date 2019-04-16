import sun.audio.AudioPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.POPUP_LAYER;

public class AfterGameMenu {
    int time;
    double efficiency;
    int gameStatMode = 0;

    JPanel gameStatisticPanel = new JPanel();

    JLabel movesLabel = new JLabel("");
    JLabel timeLabel = new JLabel("00:00");
    JLabel statLabel = new JLabel("Stats");
    JButton nextLevel = new JButton("Next Level");
    JButton toMenu = new JButton("To main menu");
    JLabel starPic[]=new JLabel[3];
    JLabel pic =new JLabel(new ImageIcon("D:\\Photoes\\goodsaber.jpg"));


    public AfterGameMenu(JFrame frame, StopWatch wt, Maze f, Sounds audiow) throws InterruptedException {
        frame.setContentPane(gameStatisticPanel);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);
        frame.setLayout(null);
        gameStatisticPanel.setBorder(new EmptyBorder(120, 350, 700, 350));


        movesLabel.setFont(new Font("impact", Font.PLAIN, 20));
        timeLabel.setFont(new Font("impact", Font.PLAIN, 20));
        statLabel.setFont(new Font("impact", Font.PLAIN, 30));
        toMenu.setFont(new Font("impact", Font.PLAIN, 22));
        pic.setFont(new Font("impact", Font.PLAIN, 10));
        toMenu.setFont(new Font("impact", Font.PLAIN, 10));

        movesLabel.setBounds(70, 300, 500, 40);
        timeLabel.setBounds(70, 340, 500, 40);
        statLabel.setBounds(70, 140, 200, 80);
        toMenu.setBounds(70, 440, 360, 60);
        toMenu.setBounds(70, 440, 360, 60);

        pic.setBounds(-350, -250, 1800, 1800);

        gameStatisticPanel.add(toMenu,DEFAULT_LAYER);
        gameStatisticPanel.add(movesLabel, DEFAULT_LAYER);
        gameStatisticPanel.add(timeLabel, DEFAULT_LAYER);
        gameStatisticPanel.add(statLabel, DEFAULT_LAYER);
        gameStatisticPanel.add(toMenu, DEFAULT_LAYER);
        pic.setOpaque(true);
        gameStatisticPanel.add(pic, POPUP_LAYER);

        toMenu.setBorderPainted(false);
        nextLevel.setBorderPainted(false);

        time = wt.CurrentAmountOfTime;
        efficiency = f.perfectPathLength / f.playerPathLength * 100;
        efficiency = Math.round(efficiency * 10) / 10.d;


        timeLabel.setText("Time is " + String.format("%02d:%02d", time / 60, time % 60));
        movesLabel.setText("Length of your path is " + String.valueOf(f.playerPathLength));
        this.toMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                gameStatMode=1;
            }
        });
        this.toMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AudioPlayer.player.start(audiow.audioStream);
            }
        });
        gameStatisticPanel.setVisible(true);
        synchronized (this) {
            while (gameStatMode == 0) {
                this.wait(1000);
            }
        }
        gameStatisticPanel.setVisible(false);
    }
}
