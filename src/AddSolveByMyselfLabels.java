import javax.swing.*;
import java.awt.*;

public class AddSolveByMyselfLabels {
    JLabel timerLabel = new JLabel("Time: 00:00");
    JLabel pathLenghtLabel = new JLabel("Moves: 0");
    JLabel levelName = new JLabel();
    int level = 1;
    StopWatch timer;
    public void prep(JFrame frame, Maze myMaze) {
        frame.setContentPane(myMaze);
        frame.setLocation(400, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1080, 1080);
    }
    //useful
    public void doSomething(JFrame frame, Maze f) {
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        pathLenghtLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        pathLenghtLabel.setForeground(Color.white);
        timerLabel.setForeground(Color.white);

        frame.setLayout(null);
        timer = new StopWatch(timerLabel, f);
        timer.SetTextMove(pathLenghtLabel, f);
        pathLenghtLabel.setBounds(500, 20, 200, 60);
        timerLabel.setBounds(200, 20, 200, 60);

        frame.add(pathLenghtLabel);
        frame.add(timerLabel);
        pathLenghtLabel.setVisible(true);
        timerLabel.setVisible(true);
    }

    public void CheckSolveByMyselfLabels(Maze f, JFrame frame, CustomMenu menu) {
        if (menu.wayToSolveMode == 1) {
            doSomething(frame, f);
        }
    }

    public void showLevel(JFrame frame) {
        levelName.setFont(new Font("Arial", Font.PLAIN, 32));
        levelName.setForeground(Color.white);
        levelName.setBounds(800, 20, 200, 60);
        frame.add(levelName);
        levelName.setVisible(true);
        levelName.setText(String.valueOf("Level " + level));
    }
}
