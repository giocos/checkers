package gui;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundProvider {
	
	public static File movement;
	public static File take;
	
	public static AudioInputStream audio;
	public static Clip movementClip;
	public static Clip takeClip;
	
	static {
		try {
			movement = new File("sounds/movement.wav");
			take = new File("sounds/take.wav");
			
			audio =  AudioSystem.getAudioInputStream(movement);
			movementClip = AudioSystem.getClip();
			movementClip.open(audio);
			
			audio =  AudioSystem.getAudioInputStream(take);
			takeClip = AudioSystem.getClip();
			takeClip.open(audio);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
