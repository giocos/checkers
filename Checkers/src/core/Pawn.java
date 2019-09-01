package core;

public class Pawn implements Cloneable {

	private int positionX;
	private int positionY;
	private Color color;
	private boolean checker;
	private PawnDLV pawnDLV;
	
	public Pawn() {}
	
	public Pawn(int positionX, int positionY, Color color) {
		
		this.positionX = positionX;
		this.positionY = positionY;
		this.color = color;
		this.checker = false;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isChecker() {
		return checker;
	}

	public void setChecker(boolean checker) {
		this.checker = checker;
	}
	
	public PawnDLV getPawnDLV() {
		return pawnDLV;
	}

	public void setPawnDLV(PawnDLV pawnDLV) {
		this.pawnDLV = pawnDLV;
	}
	
	@Override
	public Pawn clone() {
		
		try {
			return (Pawn)super.clone();
		} catch(CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public boolean equals(Object object) {
		return this.positionX == ((Pawn)object).positionX && this.positionY == ((Pawn)object).positionY && 
				this.color.equals(((Pawn)object).color); 
	}
	
	@Override
	public String toString() {
		return color.name().toLowerCase() + "_pawn " + "(" + positionX + ";" + positionY + ")"; 
	}
}

