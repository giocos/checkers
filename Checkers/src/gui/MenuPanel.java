package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {

	private JLabel title;
	private JButton[] buttons;
	private final MainFrame frame;
	
	public MenuPanel(final MainFrame frame) {
		
		this.frame = frame;
		this.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
		this.setBackground(Color.decode("#996633"));
		this.setLayout(null);
		setLabel();
		initButtons();
	}
	
	private void setLabel() {
		
		title = new JLabel("Checkers");
		title.setSize(800, 200);
		title.setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 150));
		title.setForeground(Color.WHITE);
		title.setBounds((int)(this.getPreferredSize().getWidth() / 2 - title.getSize().getWidth() / 3), 0, 
				(int)title.getSize().getWidth(), (int)title.getSize().getHeight());
		this.add(title);
	}
	
	private void initButtons() {
		
		final int DIM = 2;
		final int WIDTH_BUTTON = 150;
		final int HEIGHT_BUTTON = 100;
		buttons = new JButton[DIM];
		final String[] texts = {"Play", "Exit"};
		
		for(int i = 0; i < buttons.length; i++) {
			
			buttons[i] = new JButton(texts[i]);
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 50));
			buttons[i].setBorder(null);
			buttons[i].setFocusPainted(false);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBounds((int) ((this.getPreferredSize().getWidth() / 2) - (WIDTH_BUTTON / 2)), 
					(int) ((this.getPreferredSize().getHeight() / 2 - HEIGHT_BUTTON)) + ((HEIGHT_BUTTON * (i + 1))), WIDTH_BUTTON, HEIGHT_BUTTON);
			buttons[i].addMouseListener(new MouseAdapter() {
				
				Color oldColor;
				
				@Override
				public void mouseEntered(MouseEvent me) {
					
					oldColor = ((JButton)me.getSource()).getForeground();
					((JButton)me.getSource()).setForeground(Color.RED);
		        }
				
				@Override
		        public void mouseExited(MouseEvent me) {
					((JButton)me.getSource()).setForeground(oldColor);
		        }
				
				@Override
				public void mousePressed(MouseEvent me) {
					
					if(((JButton)me.getSource()).getText().equals("Play")) {
						
						frame.switchTo(new GamePanel(frame));
						((JButton)me.getSource()).setForeground(oldColor);
					}
					else {
						final int option = JOptionPane.showConfirmDialog(getParent(), "Are you sure?", "", 0);
						if(option == 0) {
							System.exit(0);
						}
					}
				}
				
			});
			this.add(buttons[i]);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ImageProvider.background, 0, 0, 
				ImageProvider.background.getWidth(null), ImageProvider.background.getHeight(null), this);
	}
}
