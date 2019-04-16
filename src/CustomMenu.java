import sun.audio.AudioPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomMenu {
    int wayToSolveMode = 0;
    JPanel customPanel = new JPanel();
    JButton DFSButton = new JButton("DFS");
    JButton BFSButton = new JButton("BFS");
    JButton myselfButton = new JButton("Myself");
    JLabel questionLabel = new JLabel("How to solve maze?");

    public CustomMenu(JFrame frame, Maze myMaze, Sounds audio) throws InterruptedException {
        frame.setContentPane(myMaze);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);
        Thread.sleep(2000);
        customPanel.setLayout(new GridLayout(5, 4));
        frame.add(customPanel);

        customPanel.setBorder(new EmptyBorder(110, 440, 720, 440));

        myselfButton.setBorderPainted(false);
        BFSButton.setBorderPainted(false);
        DFSButton.setBorderPainted(false);


        questionLabel.setFont(new Font("impact", Font.PLAIN, 32));
        BFSButton.setFont(new Font("impact", Font.PLAIN, 30));
        DFSButton.setFont(new Font("impact", Font.PLAIN, 30));
        myselfButton.setFont(new Font("impact", Font.PLAIN, 30));

        customPanel.add(questionLabel);
        customPanel.add(DFSButton);
        customPanel.add(myselfButton);
        customPanel.add(BFSButton);
        customPanel.add(DFSButton);


        BFSButton.setMargin(new Insets(10, 10, 10, 10));
        DFSButton.setBounds(400, 500, 10, 40);
        this.DFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audio.audioStream);
                wayToSolveMode = 2;
            }
        });
        this.BFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audio.audioStream);
                wayToSolveMode = 3;
            }
        });
        this.myselfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audio.audioStream);
                wayToSolveMode = 1;
            }
        });
        Thread.sleep(1000);

        customPanel.setVisible(true);

        synchronized (myMaze) {
            while (wayToSolveMode == 0) {
                myMaze.wait(1000);
            }
        }
        this.customPanel.setVisible(true);

        synchronized (myMaze) {
            while (wayToSolveMode == -1) {
                myMaze.wait(1000);
            }
        }
        this.customPanel.setVisible(false);
    }
}
