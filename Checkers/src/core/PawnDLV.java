package core;

public class PawnDLV {

	private int x;
	private int y;
	private int checker;
	
	public PawnDLV() {}
	
	public PawnDLV(int x, int y, int checker) {
		
		this.x = x;
		this.y = y;
		this.checker = checker;
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

	public int getChecker() {
		return checker;
	}

	public void setChecker(int checker) {
		this.checker = checker;
	}

	@Override
	public boolean equals(Object object) {
		return this.x == ((PawnDLV)object).x && this.y == ((PawnDLV)object).y;
	}

	@Override
	public String toString() {
		return "PawnDLV " + "(" + x + ";" + y + ")"; 
	}
}
