package gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProvider {
	
	public final static int PIXEL = 100;
	public static Image background;
	public static Image whitePawn;
	public static Image blackPawn;
	public static Image whitePawnChecker;
	public static Image blackPawnChecker;
	
	static {
		try {
			background = ImageIO.read(new File("images/background.png")).getScaledInstance(1920, 1240, java.awt.Image.SCALE_SMOOTH);
			whitePawn = ImageIO.read(new File("images/whitePawn.png")).getScaledInstance(PIXEL, PIXEL,java.awt.Image.SCALE_SMOOTH);
			blackPawn = ImageIO.read(new File("images/blackPawn.png")).getScaledInstance(PIXEL, PIXEL,java.awt.Image.SCALE_SMOOTH);
			whitePawnChecker = ImageIO.read(new File("images/whitePawnChecker.png")).getScaledInstance(PIXEL, PIXEL,java.awt.Image.SCALE_SMOOTH);
			blackPawnChecker = ImageIO.read(new File("images/blackPawnChecker.png")).getScaledInstance(PIXEL, PIXEL,java.awt.Image.SCALE_SMOOTH);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
