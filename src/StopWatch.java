import sun.audio.AudioPlayer;

import javax.swing.*;
import java.io.IOException;

public class StopWatch {
    int CurrentAmountOfTime = 0;
    Thread pp;

    public StopWatch(JLabel label, Maze myMaze) {
        new Thread(new Runnable() {
            public void run() {
                while (myMaze.didYouWin(myMaze) == false) {
                    try {
                        Thread.sleep(1000);
                        CurrentAmountOfTime++;
                        if (myMaze.didYouWin(myMaze) == false) {
                            label.setText("Time: " + String.format("%02d:%02d", CurrentAmountOfTime / 60, CurrentAmountOfTime % 60));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    AudioPlayer.player.start((new Sounds()).audioStream2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void SetTextMove(JLabel label, Maze f) {

        pp = new Thread(new Runnable() {
            public void run() {
                while (f.didYouWin(f) == false) {
                    label.setText("Moves: " + String.valueOf(f.playerPathLength));
                }
            }
        });
        pp.start();
    }
}