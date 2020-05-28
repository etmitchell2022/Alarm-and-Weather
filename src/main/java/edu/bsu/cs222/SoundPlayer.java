package edu.bsu.cs222;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static edu.bsu.cs222.AlarmUI.soundChosen;

class SoundPlayer {

    void WAVPlayer() {
        try {
            File f = new File(soundChosen);
            String path = f.getCanonicalPath();
            String pathPart1 = path.replaceAll("TeamDAlarmClock", "").replaceAll(soundChosen, "");
            String pathPart2 = "TeamDAlarmClock\\src\\main\\java\\edu\\bsu\\cs222\\sounds\\";
            String finalPath = pathPart1 + pathPart2 + soundChosen;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((new File(finalPath)).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000L);
        } catch (Exception var9) {
            var9.printStackTrace();
        }
    }
}