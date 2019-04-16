import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sounds {
    AudioStream audioStream;
    AudioStream audioStream1;
    AudioStream audioStream2;
    AudioStream Audio1;
    AudioStream Audio2;
    AudioStream Audio3;
    AudioStream Audio4;

    public Sounds() throws IOException {
        String gongFile = "data\\Sounds\\good (2).wav";
        InputStream in = new FileInputStream(gongFile);
        audioStream = new AudioStream(in);
        String gongFile1 = "data\\Sounds\\good (1).wav";
        InputStream in1 = new FileInputStream(gongFile1);
        audioStream1 = new AudioStream(in1);
        String gongFile2 = "data\\Sounds\\win.wav ";
        InputStream in2 = new FileInputStream(gongFile2);
        audioStream2 = new AudioStream(in2);

        Audio1 = new AudioStream(new FileInputStream("D:\\Music\\" + Integer.toString(1) + ".wav"));
        Audio2 = new AudioStream(new FileInputStream("D:\\Music\\" + Integer.toString(2) + ".wav"));
        Audio3 = new AudioStream(new FileInputStream("D:\\Music\\" + Integer.toString(3) + ".wav"));
        Audio4 = new AudioStream(new FileInputStream("D:\\Music\\" + Integer.toString(4) + ".wav"));
    }
}
