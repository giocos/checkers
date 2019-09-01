package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private final int WIDTH = 1920;
	private final int HEIGHT = 1080;
	private MenuPanel menuPanel;
	
	public MainFrame() {
		
		menuPanel = new MenuPanel(this);
		this.setSize(WIDTH, HEIGHT);
		this.add(menuPanel);
		this.setLocationRelativeTo(this);
		this.setAlwaysOnTop(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void switchTo(final JPanel newPanel) {
		
		this.getContentPane().removeAll();
		this.add(newPanel);
		this.pack();
		this.validate();
		this.repaint();
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	public MenuPanel getMenuPanel() {
		return menuPanel;
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
