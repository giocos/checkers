package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import core.GameManager;
import core.Pawn;
import core.Position;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseListener, Runnable {
	
	private final int SIZE = 100;
	private final int CELLS = 8;
	private final int DIM_LABEL = 120;
	private final int DELAY = 1000;
	
	private MainFrame frame;
	private GameManager gameManager;
	private JLabel[] labels;
	private JButton[] buttons;
	private Thread thread;
	private Runnable task;
	private boolean isStarted;
	private boolean exists;
	private boolean playerHasPlayed;
	private boolean clicked;
	private boolean mandatory;
	private boolean running;
	private int countClick;
	private int widthLabel;
	private int currentX;
	private int currentY;
	private Point min;
	private Point max;
	
	public GamePanel(final MainFrame frame) {
		
		this.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
		this.setBackground(Color.decode("#996633"));
		this.addMouseListener(this);
		this.setLayout(null);
		this.frame = frame;
		reset();
		initButtons();
		initPoints();
	}
	
	private void reset() {
		
		this.task = this;//assign the task
		isStarted = false;
		exists = false;	
		playerHasPlayed = false;
		clicked = false;
		mandatory = false;
		running = true;
		countClick = 0;
	}
	
	private void initButtons() {
		
		final int DIM = 2;
		final int WIDTH_BUTTON = 150;
		final int HEIGHT_BUTTON = 50;
		final String[] texts = {"Start", "Back"};
		buttons = new JButton[DIM];
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(texts[i]);
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 50));
			buttons[i].setBorder(null);
			buttons[i].setBounds((int) (this.getPreferredSize().getWidth() / 2) + (i * WIDTH_BUTTON / 2) - ((buttons.length - i) * WIDTH_BUTTON / 2),
					0, WIDTH_BUTTON, HEIGHT_BUTTON);
			buttons[i].setFocusPainted(false);
			buttons[i].setContentAreaFilled(false);
			buttons[i].addMouseListener(new MouseAdapter() {
			
			Color oldColor = null;
			
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
			public void mouseClicked(MouseEvent me) {
			
				if(((JButton)me.getSource()).getText().equals("Start")) {
					reset();
					isStarted = true;
					gameManager = new GameManager();
					setLabels(widthLabel);
					repaint();
					thread = new Thread(task);
					thread.start();
					
				} else
					if(((JButton)me.getSource()).getText().equals("Back")) { 
						frame.switchTo(frame.getMenuPanel());
					}
				}
			});
			this.add(buttons[i]);
		}
	}
	
	private void initPoints() {
		
		min = new Point();
		min.x = (int) (this.getPreferredSize().getWidth() / 2) - (SIZE * CELLS / 2);
		min.y = (int) (this.getPreferredSize().getHeight() / 2) - (SIZE * CELLS / 2);
		
		max = new Point();
		max.x = (int) (this.getPreferredSize().getWidth() / 2) - (SIZE * CELLS / 2);
		max.y = (int) (this.getPreferredSize().getHeight() / 2) - (SIZE * CELLS / 2);
		
		max.x += SIZE * CELLS;
		max.y += SIZE * CELLS; 
	}
	
	private String sizes() {
		
		int whites = 0, blacks = 0;
		
		List<Pawn> all = new ArrayList<>();
		all.addAll(gameManager.getWhitePawns());
		all.addAll(gameManager.getBlackPawns());
		
		for(Pawn p:all) {
			if(p.getColor().equals(core.Color.BLACK)) {
				blacks ++;
			} else {
				whites ++;
			}
		}
		return String.valueOf(blacks) + ":" + String.valueOf(whites);
	}
	
	private void removeLabels() {
		
		if(labels != null) {
			for(int i = 0; i < labels.length; i++) {
				this.remove(labels[i]);
			}
		}
	}
	
	private void setLabels(final int W) {
		
		final int DIM = 2;
		final String[] sizes = sizes().split(":");
		
		if(labels == null) { 
			labels = new JLabel[DIM];
		} else {
			removeLabels();
		}
		 
		for(int i = 0; i < DIM; i++) {
			labels[i] = new JLabel(sizes[i]);
			labels[i].setForeground(Color.WHITE);
			labels[i].setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 80));
			labels[i].setBounds(100, W + (i * DIM_LABEL), DIM_LABEL, DIM_LABEL);
			this.add(labels[i]);
		}
	}
	
	private void addCharAndNumber(Graphics g, int x, int y) {
		
		JLabel[] numbers = new JLabel[CELLS];
		JLabel[] characters = new JLabel[CELLS];
		
		for(int i = 0; i < numbers.length; i++) {
			numbers[i] = new JLabel(String.valueOf(i + 1));
			characters[i] = new JLabel(String.valueOf(Character.toChars(65 + i)));
			
			numbers[i].setForeground(Color.decode("#ffcc66"));
			characters[i].setForeground(Color.decode("#ffcc66"));
			
			numbers[i].setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 50));
			characters[i].setFont(new Font("Times New Roman", Font.TRUETYPE_FONT, 50));
			
			numbers[i].setBounds(x + 10, y + (i * SIZE + DIM_LABEL / 3), DIM_LABEL, DIM_LABEL);
			characters[i].setBounds(x + (int) (i * SIZE + DIM_LABEL / 1.5), y - 35, DIM_LABEL, DIM_LABEL);
			this.add(numbers[i]);
			this.add(characters[i]);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		final int WIDTH = (int) this.getPreferredSize().getWidth() / 2;
		final int HEIGHT = (int) this.getPreferredSize().getHeight() / 2;
		widthLabel = WIDTH - (SIZE * CELLS);
		//border
		g.setColor(Color.decode("#993300"));
		g.fillRect((WIDTH) - (SIZE * CELLS / 2) - 50, 
				(HEIGHT) - (SIZE * CELLS / 2) - 50, SIZE * CELLS + 100, SIZE * CELLS + 100);
		g.setColor(Color.decode("#ffcc66"));
		g.drawRect((WIDTH) - (SIZE * CELLS / 2) - 1, 
				(HEIGHT) - (SIZE * CELLS / 2) - 1, SIZE * CELLS + 1, SIZE * CELLS + 1);
		//characters + numbers
		int coordX = WIDTH - (SIZE * CELLS / 2) - 50;
		int coordY = (HEIGHT) - (SIZE * CELLS / 2) - 50;
		addCharAndNumber(g, coordX, coordY);
		//checker board
		for(int i = 0; i < CELLS; i++) {
			for(int j = 0; j < CELLS; j++) {
				if((i + j) % 2 == 0) {
					g.setColor(Color.decode("#993300"));
				} else {
					g.setColor(Color.decode("#ffcc66"));
				}
				g.fillRect(WIDTH - (SIZE * CELLS / 2) + j * SIZE, 
						HEIGHT - (SIZE * CELLS / 2) + i * SIZE, SIZE, SIZE);
			}
		}
		
		if(isStarted) {
			//drawing rect when click on pawn
			if(clicked) {
				((Graphics2D)g).setStroke(new BasicStroke(5));
				if(exists && !mandatory) {
					g.setColor(Color.GREEN);
				} else if(!exists){
					g.setColor(Color.RED);
					countClick = 0;
				}
				g.drawRect(WIDTH - (SIZE * CELLS / 2) + currentY * SIZE, 
						HEIGHT - (SIZE * CELLS / 2) + currentX * SIZE, SIZE, SIZE);
				
				if(g.getColor().equals(Color.GREEN)) {
					Image compositePawn = null;
					if(gameManager.getSelectedPawn().isChecker()) {
						compositePawn = ImageProvider.whitePawnChecker;
					} else {
						compositePawn = ImageProvider.whitePawn;
					}
					
					((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .2f));
					for(Position it:gameManager.getPossibleJump()) {
						g.drawImage(compositePawn, WIDTH - (SIZE * CELLS / 2) + it.getY() * SIZE, 
								HEIGHT - (SIZE * CELLS / 2) + it.getX() * SIZE, SIZE, SIZE, this);
					}
					((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
			}
			g.drawImage(ImageProvider.blackPawn, 0, WIDTH - (SIZE * CELLS), this);
			g.drawImage(ImageProvider.whitePawn, 0, WIDTH - (SIZE * CELLS) + DIM_LABEL, this);
			//draw black pawns
			for(int i = 0; i < gameManager.getBlackPawns().size(); i++) {
				
				int x = gameManager.getBlackPawns().get(i).getPositionX();
				int y = gameManager.getBlackPawns().get(i).getPositionY();
				Image pawn = null;
				
				if(gameManager.getBlackPawns().get(i).isChecker()) {
					pawn = ImageProvider.blackPawnChecker;
				} else {
					pawn = ImageProvider.blackPawn;
				}		
				g.drawImage(pawn, WIDTH - (SIZE * CELLS / 2) + y * SIZE,
						HEIGHT - (SIZE * CELLS / 2) + x * SIZE, this);
			}
			//draw white pawns
			for(int i = 0; i < gameManager.getWhitePawns().size(); i++) {
				
				int x = gameManager.getWhitePawns().get(i).getPositionX();
				int y = gameManager.getWhitePawns().get(i).getPositionY();
				Image pawn = null;
			
				if(gameManager.getWhitePawns().get(i).isChecker()) {
					pawn = ImageProvider.whitePawnChecker;
				} else {
					pawn = ImageProvider.whitePawn;
				}
				g.drawImage(pawn, WIDTH - (SIZE * CELLS / 2) + y * SIZE,
					HEIGHT - (SIZE * CELLS / 2) + x * SIZE, this);
			}
			setLabels(widthLabel);
		}
	}

	//PLAYER
	private synchronized void playerEvent(MouseEvent e) {
	
		currentX = (e.getY() - min.y) / SIZE;
		currentY = (e.getX() - min.x) / SIZE;
		
		if(!gameManager.insideChecker(currentX, currentY)) {
			JOptionPane.showMessageDialog(this, "out of checkers!");
			return;
		}
		
		if((++ countClick) % 2 != 0) {
			clicked = true;
			if(exists = gameManager.existsWhitePawn(currentX, currentY)) {
				List<Pawn> p = gameManager.mandatoryJumpList();
				if(p.size() > 0) {
					gameManager.setMandatoryJump(mandatory = true);
					if(!p.contains(gameManager.getSelectedPawn())) {
						countClick = 0;
						gameManager.setSelectedPawn(null);
						JOptionPane.showMessageDialog(this, "mandatory jump!");
						return;
					} else {
						mandatory = mandatory ? false : mandatory;
					}
				}
			} repaint(); 
			
		} else if(exists && countClick % 2 == 0) {
				int newX = (e.getY() - min.y) / SIZE;
				int newY = (e.getX() - min.x) / SIZE;
				exists = false;
				clicked = false;
				
				if(!gameManager.insideChecker(newX, newY)) {
					JOptionPane.showMessageDialog(this, "out of checkers!");
					return;
				}
				
				if(gameManager.isEmpty(newX, newY)) {
					if(gameManager.canJump(gameManager.getSelectedPawn(), newX, newY)) {
						if(!gameManager.isMandatoryJump()) {
							gameManager.makeJump(newX, newY);
							repaint();
						} else {
							if(gameManager.existsBlackPawn(gameManager.getSelectedPawn().isChecker(), (newX + 1), (newY + 1)) 
									|| gameManager.existsBlackPawn(gameManager.getSelectedPawn().isChecker(), (newX + 1), (newY - 1))) {
								gameManager.makeJump(newX, newY);
								repaint();
							} else if(gameManager.getSelectedPawn().isChecker() && 
								(gameManager.existsBlackPawn(gameManager.getSelectedPawn().isChecker(), (newX - 1), (newY + 1)) 
										|| gameManager.existsBlackPawn(gameManager.getSelectedPawn().isChecker(), (newX - 1), (newY - 1)))){
									gameManager.makeJump(newX, newY);
									repaint();
								} else {									
									countClick = 0;
									repaint();
									JOptionPane.showMessageDialog(this, "mandatory jump!");
									return;
								}
						}
						//CHECK IF I WIN OR NOT
						if(gameManager.getBlackPawns().size() == 0 || gameManager.getIntelligence().getResult().size() == 0) {		
							isStarted = false;
							removeLabels();
							SoundProvider.movementClip.setFramePosition(0);
							SoundProvider.movementClip.start();
							JOptionPane.showMessageDialog(this, "You WIN!");
							thread.interrupt();
						} else {
							SoundProvider.movementClip.setFramePosition(0);
							SoundProvider.movementClip.start();
							Position p[] = gameManager.computeAdjiacentPositions(gameManager.getSelectedPawn());
							Pawn selected = gameManager.getSelectedPawn();
							if(gameManager.isMandatoryJump() && 
									gameManager.found(selected.isChecker(), p, selected.getPositionX(), selected.getPositionY())) {
								return;
							}
							gameManager.setMandatoryJump(mandatory = false);
							playerHasPlayed = true;
							this.notify();
						}
					} else {
						exists = false;
						countClick = 0;
						repaint();
						JOptionPane.showMessageDialog(this, "wrong position!");
					}
				} else {
					exists = false;
					countClick = 0;
					repaint();
					JOptionPane.showMessageDialog(this, "wrong position!");
				}
				gameManager.setSelectedPawn(null);
		}
	}

	//OPPONENT
	public synchronized void opponentGame() throws InterruptedException {
		
		while(!playerHasPlayed) {
			this.wait();
		}		
		Thread.sleep(DELAY);
		
		Map<Pawn,List<Pawn>> result = gameManager.getResultFromDLV();
		Pawn black = null;
		if(result != null) {
			black = result.keySet().stream().findFirst().get();
			List<Pawn> pawnsToTake = result.get(black);
			
			for(Pawn it:pawnsToTake) {
				int oldX = black.getPositionX();
				int oldY = black.getPositionY();
				int newX = 0, newY = 0;
				newX = it.getPositionX() > oldX ? it.getPositionX() + 1 : it.getPositionX() - 1;
				newY = it.getPositionY() > oldY ? it.getPositionY() + 1 : it.getPositionY() - 1;
				gameManager.takeWhitePawn(it);
				gameManager.updateBlackPawn(black, newX, newY);
				repaint();
				SoundProvider.movementClip.setFramePosition(0);
				SoundProvider.movementClip.start();
				Thread.sleep(DELAY);
			}
		} else {
			Map<Pawn,Position> pawn = gameManager.getPawnToMove();
			black = pawn.keySet().stream().findFirst().get();
			Position newPosition = pawn.get(black);
			gameManager.updateBlackPawn(black, newPosition.getX(), newPosition.getY());
			repaint();
			SoundProvider.movementClip.setFramePosition(0);
			SoundProvider.movementClip.start();			
		}
		
		if(gameManager.getWhitePawns().size() == 0 || !gameManager.getIntelligence().playerCanMove()) {
			isStarted = false;
			running = false;
			removeLabels();
			repaint();
			JOptionPane.showMessageDialog(this, "You LOSE!");
		}
		playerHasPlayed = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(!playerHasPlayed) {
			if(gameManager.getWhitePawns().size() > 0 && isStarted) {
				playerEvent(e);
			}
		}
	}
	
	@Override
	public void run() {
		
		try {
			while(running) {
				opponentGame(); 
			}
		} catch(InterruptedException e) {
			System.err.println("thread interrupted");
		}
//		System.out.println("Thread stop running!");
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}

