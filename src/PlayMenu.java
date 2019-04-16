import sun.audio.AudioPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.POPUP_LAYER;

public class PlayMenu {

    int gameMode = 0;
    JPanel playPanel = new JPanel();
    JButton customButton = new JButton("CUSTOM MODE");
    JButton gameButton = new JButton("GAME MODE");
    JButton returnButton = new JButton(new ImageIcon("D:\\Photoes\\tcross.jpg"));
    JLabel modNameLabel = new JLabel("CHOOSE MODE");
    JLabel pic = new JLabel(new ImageIcon("D:\\Photoes\\mnmnbckgrd.png"));

    public PlayMenu(JFrame frame, Sounds audiow) throws InterruptedException {
        frame.setContentPane(playPanel);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);
        frame.setLayout(null);
        playPanel.setBorder(new EmptyBorder(120, 350, 700, 350));
        customButton.setFont(new Font("impact", Font.PLAIN, 30));
        gameButton.setFont(new Font("impact", Font.PLAIN, 30));
        returnButton.setFont(new Font("impact", Font.PLAIN, 20));
        modNameLabel.setFont(new Font("impact", Font.PLAIN, 40));

        modNameLabel.setForeground(Color.black);

        pic.setBounds(-350, -250, 1800, 1800);

        gameButton.setBounds(100, 400, 300, 60);
        customButton.setBounds(100, 340, 300, 60);
        returnButton.setBounds(990, 20, 50, 50);
        modNameLabel.setBounds(110, 250, 410, 50);


        playPanel.add(customButton, POPUP_LAYER);
        playPanel.add(gameButton, POPUP_LAYER);
        playPanel.add(returnButton, POPUP_LAYER);
        playPanel.add(modNameLabel, POPUP_LAYER);
        pic.setOpaque(true);
        playPanel.add(pic, DEFAULT_LAYER);
        gameButton.setBorderPainted(false);
        customButton.setBorderPainted(false);
        returnButton.setBorderPainted(false);
        this.customButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                gameMode = 1;
            }
        });

        this.gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                gameMode = 2;

            }
        });
        this.returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                gameMode = 3;
            }
        });

        playPanel.setVisible(true);

        synchronized (this) {
            while (gameMode == 0) {
                this.wait(1000);
            }
        }
        playPanel.setVisible(false);
    }
}
