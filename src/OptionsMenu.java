import sun.audio.AudioPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.POPUP_LAYER;

public class OptionsMenu {
    int optionsMode = 0;
    JLayeredPane optionsPanel = new JLayeredPane();
    JLabel rowsLabel = new JLabel("Rows");
    JLabel columnsLabel = new JLabel("Columnns");
    JButton returnButton = new JButton(new ImageIcon("D:\\Photoes\\tcross.jpg"));
    JLabel intro = new JLabel("Custom mode options:");
    JTextField rowOption = new JTextField();
    JTextField colOption = new JTextField();
    JLabel pic = new JLabel(new ImageIcon("D:\\Photoes\\mnmnbckgrd.png"));
    int row;
    int col;

    public OptionsMenu(JFrame frame, Sounds audiow, OptionsChanges newRowsAndCols) throws InterruptedException {
        frame.setContentPane(optionsPanel);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);
        frame.setLayout(null);
        optionsPanel.setBorder(new EmptyBorder(120, 350, 700, 350));


        rowsLabel.setFont(new Font("impact", Font.PLAIN, 30));
        columnsLabel.setFont(new Font("impact", Font.PLAIN, 30));
        intro.setFont(new Font("impact", Font.PLAIN, 30));
        rowOption.setFont(new Font("impact", Font.PLAIN, 30));
        colOption.setFont(new Font("impact", Font.PLAIN, 30));
        pic.setBounds(-350, -250, 1800, 1800);

        intro.setBounds(40, 240, 400, 60);
        rowsLabel.setBounds(70, 300, 360, 60);
        columnsLabel.setBounds(70, 360, 360, 60);
        returnButton.setBounds(990, 20, 50, 50);
        rowOption.setBounds(270, 300, 160, 60);
        colOption.setBounds(270, 360, 160, 60);

        colOption.setFont(new Font("Dialog", Font.PLAIN, 25));
        rowOption.setFont(new Font("Dialog", Font.PLAIN, 25));
        optionsPanel.add(rowOption);
        optionsPanel.add(colOption);
        optionsPanel.add(intro, POPUP_LAYER);
        optionsPanel.add(rowsLabel, POPUP_LAYER);
        optionsPanel.add(columnsLabel, POPUP_LAYER);
        optionsPanel.add(returnButton, POPUP_LAYER);
        pic.setOpaque(true);
        optionsPanel.add(pic, DEFAULT_LAYER);

        rowOption.setText("15");
        colOption.setText("15");
        this.returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioPlayer.player.start(audiow.audioStream);
                optionsMode = 3;
                if (rowOption.getText().equals("")) {
                    colOption.setText("15");
                    rowOption.setText("15");
                }
                row = Integer.parseInt(rowOption.getText());
                col = Integer.parseInt(colOption.getText());
                newRowsAndCols.row = row;
                newRowsAndCols.col = col;

            }
        });


        optionsPanel.setVisible(true);

        synchronized (this) {
            while (optionsMode == 0) {
                this.wait(1000);
            }
        }
        optionsPanel.setVisible(false);
    }
}
