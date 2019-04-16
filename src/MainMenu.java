import sun.audio.AudioPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.POPUP_LAYER;

public class MainMenu {
    boolean checkForNewLevel = false;
    int mainMode = 0;
    JLayeredPane mainPanel = new JLayeredPane();
    JLabel nameLabel = new JLabel("Maze");
    JButton playButton = new JButton("Play");
    JButton exitButton = new JButton("Quit");
    JButton settingsButton = new JButton("Options");
    JLabel tikhonLabel = new JLabel("©Tikhon");
    JLabel pic = new JLabel(new ImageIcon("D:\\Photoes\\mnmnbckgrd.png"));

    public MainMenu(JFrame frame, Sounds audiow) throws InterruptedException, IOException {
        frame.setContentPane(mainPanel);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);
        frame.setLayout(null);
        mainPanel.setBorder(new EmptyBorder(120, 350, 700, 350));
        nameLabel.setFont(new Font("impact", Font.PLAIN, 100));
        playButton.setFont(new Font("impact", Font.PLAIN, 30));
        exitButton.setFont(new Font("impact", Font.PLAIN, 30));
        settingsButton.setFont(new Font("impact", Font.PLAIN, 30));
        tikhonLabel.setFont(new Font("impact", Font.PLAIN, 18));
        pic.setFont(new Font("impact", Font.PLAIN, 10));

        pic.setBounds(-350, -250,1800, 1800);

        nameLabel.setBounds(130, 120, 300, 90);
        playButton.setBounds(70, 300, 360, 60);
        exitButton.setBounds(70, 420, 360, 60);
        settingsButton.setBounds(70, 360, 360, 60);
        tikhonLabel.setBounds(980, 1000, 80, 20);


        nameLabel.setForeground(Color.black);
        mainPanel.add(pic, DEFAULT_LAYER);
        mainPanel.add(playButton, POPUP_LAYER);
        mainPanel.add(exitButton, POPUP_LAYER);
        mainPanel.add(nameLabel, POPUP_LAYER);
        mainPanel.add(settingsButton, POPUP_LAYER);
        mainPanel.add(tikhonLabel, POPUP_LAYER);
        pic.setOpaque(true);
        playButton.setBorderPainted(false);
        settingsButton.setBorderPainted(false);
        exitButton.setBorderPainted(false);


        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                mainMode = 1;
            }
        });

        this.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                mainMode = 2;
            }
        });
        this.settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                mainMode = 3;
            }
        });

        mainPanel.setVisible(true);

        synchronized (this) {
            while (mainMode == 0) {
                this.wait(1000);
            }
        }
        mainPanel.setVisible(false);
    }
}

