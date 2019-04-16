import sun.audio.AudioPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BackToMenu {
    JPanel backToMenuPanel = new JPanel();
    JButton toMenu = new JButton("To main menu");
    int backMode = 0;
    JLabel pic = new JLabel(new ImageIcon("D:\\Photoes\\mnmnbckgrd.png"));

    public BackToMenu(JFrame frame, Sounds audiow) throws InterruptedException {
        synchronized (this) {
            Thread.sleep(2000);
        }
        frame.setContentPane(backToMenuPanel);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);

        frame.setLayout(null);
        backToMenuPanel.setBorder(new EmptyBorder(120, 350, 700, 350));

        toMenu.setFont(new Font("impact", Font.PLAIN, 22));

        toMenu.setBounds(70, 440, 360, 60);
        pic.setBounds(-350, -250, 1800, 1800);

        backToMenuPanel.add(toMenu);
        backToMenuPanel.add(pic);
        toMenu.setBorderPainted(false);

        this.toMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backMode = 1;
                AudioPlayer.player.start(audiow.audioStream);
            }
        });
        backToMenuPanel.setVisible(true);
        synchronized (this) {
            while (backMode == 0) {
                this.wait(1000);
            }
        }
        backToMenuPanel.setVisible(false);
    }
}
