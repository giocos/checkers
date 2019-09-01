package core;

public class Position {

	private int x;
	private int y;
	
	public Position() {}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object object) {
		return this.x == ((Position)object).x && this.y == ((Position)object).y; 
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
