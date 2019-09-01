package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dlv.Intelligence;

public class GameManager{

	private int DIM = 8;
	private List<Pawn> blackPawns;
	private List<Pawn> whitePawns;
	private List<PawnDLV> whitesDLV;
	private List<PawnDLV> blacksDLV;
	private List<Position> emptyPositions;
	private List<PawnDLV> dlvResult;
	private Intelligence ai;
	private Pawn selectedPawn;
	private Position takePosition;
	private boolean mandatoryJump;
	//SERVONO NELLA FUNZIONE RICORSIVA
	private int sourceX;
	private int sourceY;
	private boolean becameChecker;
	
	public GameManager() {
		
		//Java Lists
		blackPawns = new ArrayList<>();
		whitePawns = new ArrayList<>();
		ai = new Intelligence();
		//DLV Lists
		blacksDLV = new ArrayList<>();
		whitesDLV = new ArrayList<>();
		//DLV Functions
		emptyPositions = new ArrayList<>();
		takePosition = null;
		addPawns();
		ai.setBlackPawnsDLV(blacksDLV);
		ai.setWhitePawnsDLV(whitesDLV);
		ai.setEmptyPositions(emptyPositions);
	}	
	
	//------------------------------- PRIVATE METHODS -------------------------------
	private void addPawns() {

		int k = 0;
		for(int i = 0; i < DIM; i++) {
			for(int j = 0; j < DIM; j++) {
				if((i + j) % 2 == 0) {
					if(i <= 2) {
						blackPawns.add(new Pawn(i,j,Color.BLACK));
						blackPawns.get(k++).setPawnDLV(new PawnDLV(i,j,0));
					} else if(i >= 5) { 
						whitePawns.add(new Pawn(i,j,Color.WHITE));
						whitePawns.get(k++).setPawnDLV(new PawnDLV(i,j,0));
					} else {
						if(k > 0) {k = 0;}
						emptyPositions.add(new Position(i,j));
					}
				}
			}
		}

		for(int i = 0; i < whitePawns.size(); i++) {
			whitesDLV.add(whitePawns.get(i).getPawnDLV());
			blacksDLV.add(blackPawns.get(i).getPawnDLV());
		}
		
//		blackPawns.add(new Pawn(5,7,Color.BLACK));
//		blackPawns.get(0).setPawnDLV(new PawnDLV(5,7,0));
//		whitePawns.add(new Pawn(6,6,Color.WHITE));
//		whitePawns.add(new Pawn(6,4,Color.WHITE));
//		whitePawns.add(new Pawn(1,1,Color.WHITE));
//		whitePawns.get(0).setPawnDLV(new PawnDLV(6, 6, 0));
//		whitePawns.get(1).setPawnDLV(new PawnDLV(6, 4, 0));
//		whitePawns.get(2).setPawnDLV(new PawnDLV(1, 1, 0));
//		
//		blacksDLV.add(blackPawns.get(0).getPawnDLV());
//		whitesDLV.add(whitePawns.get(0).getPawnDLV());
//		whitesDLV.add(whitePawns.get(1).getPawnDLV());
//		whitesDLV.add(whitePawns.get(2).getPawnDLV());
//		
//		emptyPositions.remove(new Position(5,7));
//		emptyPositions.remove(new Position(6,4));
//		emptyPositions.remove(new Position(6,6));
//		emptyPositions.remove(new Position(1,1));
	}
	
	private void takeBlackPawn(boolean checker, Position blackPos) {
			
		if(blackPos == null) {
			return;
		}
		
		if(!checker) {
			for(Pawn it:blackPawns) {
				if(it.getPositionX() == blackPos.getX() && it.getPositionY() == blackPos.getY()) {
					if(!it.isChecker()) {
						emptyPositions.add(new Position(it.getPositionX(), it.getPositionY()));
						blacksDLV.remove(it.getPawnDLV());
						blackPawns.remove(it);
//						System.out.println("black pawn removed");
					}
					break;
				}
			}
		} else {
			for(Pawn it:blackPawns) {
				if(it.getPositionX() == blackPos.getX() && it.getPositionY() == blackPos.getY()) {
					emptyPositions.add(new Position(it.getPositionX(), it.getPositionY()));
					blacksDLV.remove(it.getPawnDLV());
					blackPawns.remove(it);
//					System.out.println("black pawn removed");
					break;
				}
			}
		}
	}
		
	private void removeEmptyPosition(int oldX, int oldY) {
		
		for(Position p:emptyPositions) {
			if(p.getX() == oldX && p.getY() == oldY) {
				emptyPositions.remove(p);
				break;
			}
		}
	}
	
	private boolean getPositionForPaint(int x, int y) {
		
		//serve quando richiamo il metodo per controllare il salto obbligatorio
		if(!insideChecker(x, y)) {
			return false;
		}
		
		for(Pawn it:blackPawns) {
			if(it.getPositionX() == x && it.getPositionY() == y) {
				return true;
			}
		}
		return false;
	}
	
	//-------------------------------- PUBLIC METHODS ----------------------------
	public boolean existsWhitePawn(int x, int y) {
		
		for(Pawn p:whitePawns) {
			if(p.getPositionX() == x && p.getPositionY() == y) {
				selectedPawn = p;
				return true;
			}
		}
		return false;
	}
	
	public boolean existsBlackPawn(boolean c, int x, int y) {
		
		//serve quando richiamo il metodo per controllare il salto obbligatorio
		if(!insideChecker(x, y)) {
			return false;
		}
		
		for(Pawn it:blackPawns) {
			if(it.getPositionX() == x && it.getPositionY() == y) {
				if(it.isChecker() == c || !it.isChecker()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isEmpty(int newX, int newY) {
		
		if((newX + newY) % 2 != 0) {
			return false;
		}
		
		for(Position p:emptyPositions) {
			if(p.getX() == newX && p.getY() == newY) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canJump(Pawn p, int newX, int newY) {
		
		if(!p.isChecker()) {
			if(p.getPositionX() <= newX) {
				return false;
			} else {
				if(p.getPositionX() - newX == 1 && Math.abs(newY - p.getPositionY()) == 1) {
					return true;
				} else if(p.getPositionX() - newX == 2 && Math.abs(newY - p.getPositionY()) == 2) {
					takePosition = new Position();
					takePosition.setX(newX + 1);
					if(p.getPositionY() > newY) { //from right to left
						takePosition.setY(newY + 1);
					} else {
						takePosition.setY(newY - 1);
					}
					return existsBlackPawn(false, takePosition.getX(), takePosition.getY());
				}
			}
		} else {
			if(Math.abs(newX - p.getPositionX()) == 1 && Math.abs(newY - p.getPositionY()) == 1) {
				return true;
			} else 
				if(Math.abs(newX - p.getPositionX()) == 2 && Math.abs(newY - p.getPositionY()) == 2) {
					newX =  newX < p.getPositionX() ? newX + 1 : newX - 1;//if move from down to up || up to down
					takePosition = new Position();
					takePosition.setX(newX);
					if(p.getPositionY() > newY) {
						takePosition.setY(newY + 1);
					} else {
						takePosition.setY(newY - 1);
					}
					return existsBlackPawn(true, takePosition.getX(), takePosition.getY());
				}
		}//else
		return false;
	}
	
	public void makeJump(int newX, int newY) {
			
		for(Pawn p:whitePawns) {	
			if(p.equals(selectedPawn)) {
				takeBlackPawn(p.isChecker(), takePosition);
				takePosition = null;
				emptyPositions.add(new Position(p.getPositionX(), p.getPositionY()));
				removeEmptyPosition(newX, newY);
				p.setPositionX(newX);
				p.setPositionY(newY);
				//DLV
				p.getPawnDLV().setX(newX);
				p.getPawnDLV().setY(newY);
				
				if(p.getPositionX() == 0 && !p.isChecker()) {
					p.setChecker(true);
					p.getPawnDLV().setChecker(1);
				}
				break;	
			}
		}
	}
	
	public List<Position> getPossibleJump() {
		
		List<Position> pp = new ArrayList<>();
		if(selectedPawn.isChecker()) {
			for(Position p:emptyPositions) {
				if((Math.abs(p.getX() - selectedPawn.getPositionX()) == 1 && 
					Math.abs(p.getY() - selectedPawn.getPositionY()) == 1)) {
						pp.add(p);
				} else if(Math.abs(p.getX() - selectedPawn.getPositionX()) == 2 && 
							Math.abs(p.getY() - selectedPawn.getPositionY()) == 2 ) {
					
					int x = p.getX() > selectedPawn.getPositionX() ? p.getX() - 1 : p.getX() + 1;
					int y = p.getY() > selectedPawn.getPositionY() ? p.getY() - 1 : p.getY() + 1; 
					if(getPositionForPaint(x,y)) {
						pp.add(p);
					}
				}
			}
		} else {
			for(Position p:emptyPositions) {
				if((selectedPawn.getPositionX() - p.getX() == 1 && 
					Math.abs(p.getY() - selectedPawn.getPositionY()) == 1)) {
						pp.add(p);
				} else if(selectedPawn.getPositionX() - p.getX() == 2 && 
							Math.abs(p.getY() - selectedPawn.getPositionY()) == 2 ) {
					
					int x = p.getX() + 1;
					int y = p.getY() > selectedPawn.getPositionY() ? p.getY() - 1 : p.getY() + 1;    
					if(getPositionForPaint(x,y)) {
						pp.add(p);
					}
				}
			}
		}
		return pp;
	}
	
	public Position[] computeAdjiacentPositions(Pawn p) {
		
		if(p.isChecker()) {
			return new Position[] {
					new Position(p.getPositionX() + 1, p.getPositionY() + 1),
					new Position(p.getPositionX() + 1, p.getPositionY() - 1),
					new Position(p.getPositionX() - 1, p.getPositionY() - 1),
					new Position(p.getPositionX() - 1, p.getPositionY() + 1),
			};
		} else {
			if(p.getColor().equals(Color.WHITE)){
				return new Position[] {
						new Position(p.getPositionX() - 1, p.getPositionY() - 1),
						new Position(p.getPositionX() - 1, p.getPositionY() + 1),
				};
			} else {
				return new Position[] {
						new Position(p.getPositionX() + 1, p.getPositionY() - 1),
						new Position(p.getPositionX() + 1, p.getPositionY() + 1),
				};
			}
		}
	}
	
	public boolean found(boolean c, Position p[], int whiteX, int whiteY) {
		
		int x = 0;
		int y = 0;
		for(Position it:p) {
			if(existsBlackPawn(c, it.getX(), it.getY())) {
				y = it.getY() > whiteY ? it.getY() + 1 : it.getY() - 1;
				x = it.getX() > whiteX ? it.getX() + 1 : it.getX() - 1;
				if(isEmpty(x, y) || isEmpty(x, y)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<Pawn> mandatoryJumpList() {
		
		List<Pawn> mandatoryWhites = new ArrayList<>();
		for(Pawn it:whitePawns) {
			if(it.isChecker()) {
				Position p[] = computeAdjiacentPositions(it);
				if(found(true, p, it.getPositionX(), it.getPositionY())) {
					mandatoryWhites.add(it);
				}
			} else {
				Position p[] = computeAdjiacentPositions(it);
				if(found(false, p, it.getPositionX(), it.getPositionY())) {
					mandatoryWhites.add(it);
				}
			}
		}
		return mandatoryWhites;
	}
	
	//-------------------------- OPPONENT FUNCTIONS -----------------------------
	public void takeWhitePawn(Pawn w) {
		emptyPositions.add(new Position(w.getPositionX(), w.getPositionY()));
		whitesDLV.remove(w.getPawnDLV());
		whitePawns.remove(w);
//		System.out.println("white pawn removed");
	}
	
	public void updateBlackPawn(Pawn p, int newX, int newY) {
		
		for(Pawn b:blackPawns) {
			if(b.equals(p)) {
				emptyPositions.add(new Position(p.getPositionX(), p.getPositionY()));
				removeEmptyPosition(newX, newY);
				b.setPositionX(newX);
				b.setPositionY(newY);
				//DLV
				b.getPawnDLV().setX(newX);
				b.getPawnDLV().setY(newY);
					
				if(b.getPositionX() == 7 && !b.isChecker()) {
					b.setChecker(true);
					b.getPawnDLV().setChecker(1);
				}
//				System.out.println("black pawn updated");
				return;
			}
		}
	}

	public Map<Pawn, Position> getPawnToMove() {
		
		Map<Pawn, Position> pawn = new HashMap<>();
		Pawn p = getPawnFromPawnDLV(dlvResult.get(new Random().nextInt(dlvResult.size())), "BLACK");
		Position newPos[] = computeAdjiacentPositions(p);
		List<Position> emptyPos = new ArrayList<>();
		
		for(Position it:newPos) {
			if(isEmpty(it.getX(), it.getY())) {
				emptyPos.add(it);
			}
		}
		pawn.put(p,emptyPos.get(new Random().nextInt(emptyPos.size())));
		return pawn;
	}
	
	
	public Pawn getPawnFromPawnDLV(PawnDLV pd, String flag) {
		List<Pawn> pawns = flag.equals("WHITE") ? whitePawns : blackPawns;
		Pawn p = null;
		for(Pawn it:pawns) {
			if(it.getPawnDLV().equals(pd)) {
				p = it;
				break;
			}
		}
		return p;
	}
	
	////////////////////////////////////////RECURSIVE FUNCTION/////////////////////////////////////////////////
	public Map<Pawn, List<Pawn>> getResultFromDLV() {
		
		boolean canTake = false;
		dlvResult = ai.getResult();//DLV program
		Map<PawnDLV, List<PawnDLV>> moves = new HashMap<>();
		for(PawnDLV it:dlvResult) {
			sourceX = it.getX();
			sourceY = it.getY();
			becameChecker = false;
			int checker = it.getChecker();
			
			if(checker == 1) {emptyPositions.remove(new Position(sourceX, sourceY));}
			List<PawnDLV> solution = new ArrayList<>();
			
			for(Position p:computeAdjiacentDLV(it)) {
				if(!(p.getX() < 0 || p.getY() > 7)) {
					List<PawnDLV> currentPath = new ArrayList<>();
					findAllPathsRecursive(sourceX, sourceY, checker, p.getX(), p.getY(), currentPath, solution);
				}
			}
			if(checker == 1 || becameChecker) {emptyPositions.add(new Position(sourceX, sourceY));}
			
			if(solution.isEmpty()) {
				break;
			} else {
				moves.put(it, solution);
				canTake = true;
			}
		}
		if(canTake) {
			return getBestMoves(moves);
		}
		return null;
	}
	
	private void findAllPathsRecursive(int sX, int sY, int c, int k, int z, List<PawnDLV> p, List<PawnDLV> s) {
		
		try {
			List<PawnDLV> splitToRight = null;
			List<PawnDLV> splitToLeft = null;
			//pawn to jump
			PawnDLV w = new PawnDLV();
			
			if(!existsPawnDLV(k, z, w) || !canJump(c, w.getChecker())) {
				updateSolution(s, p);
				return;
			}
			Position newPos = new Position();
			
			if(!existsEmptyPosition(sX, sY, k, z, newPos)) {
				updateSolution(s, p);
				return;
			} 
			
			if(!p.contains(w)) {
				p.add(w);
			}
			//update source X and Y
			sX = newPos.getX();
			sY = newPos.getY();
			
			if(sX == 7 && c == 0) {
				c = 1;
				becameChecker = true;
				emptyPositions.remove(new Position(sourceX, sourceY));
			}
			splitToRight = new ArrayList<>();
			splitToLeft = new ArrayList<>();
			splitToRight.addAll(p);
			splitToLeft.addAll(p);
			
			findAllPathsRecursive(sX, sY, c, sX + 1, sY + 1, splitToRight, s);
			findAllPathsRecursive(sX, sY, c, sX + 1, sY - 1, splitToLeft, s);
		
			if(c == 1) {
				if(!p.contains(new PawnDLV(sX - 1, sY + 1, 0))) {
					splitToRight = new ArrayList<>();
					splitToRight.addAll(p);
					findAllPathsRecursive(sX, sY, 1, sX - 1, sY + 1, splitToRight, s);
				}
				
				if(!p.contains(new PawnDLV(sX - 1, sY - 1, 0))) {
					splitToLeft = new ArrayList<>();
					splitToLeft.addAll(p);
					findAllPathsRecursive(sX, sY, 1, sX - 1, sY - 1, splitToLeft, s);
				}
			}
		} catch(StackOverflowError e) {
			System.err.println("stack_overflow");
		}
	}
	
	private Map<Pawn, List<Pawn>> getBestMoves(Map<PawnDLV, List<PawnDLV>> m) {
		
		int maxSize = 0;
		boolean canTake = false;
		Pawn bestPawn = null;
		PawnDLV bestDlvPawn = null;
		List<Pawn> pawns = new ArrayList<>();
		Map<Pawn, List<Pawn>> best = new HashMap<>(); 
		
		for(PawnDLV it:m.keySet()) {
			//if i don't have pawn with take
			if(m.get(it) == null) {
				break;
			}
			if(maxSize < m.get(it).size()) {
				maxSize = m.get(it).size();
				bestDlvPawn = it;
				canTake = !canTake ? true : canTake;
			} else if(maxSize == m.get(it).size()) {
				if(it.getChecker() > bestDlvPawn.getChecker()) {
					maxSize = m.get(it).size();
					bestDlvPawn = it;
				}
			}
		}
		bestPawn = getPawnFromPawnDLV(bestDlvPawn, "BLACK");
		convertPawn(pawns, m.get(bestDlvPawn));
		best.put(bestPawn, pawns);
		return best;
	}
	
	private void convertPawn(List<Pawn> p, List<PawnDLV> pd) {
		for(PawnDLV it:pd) {
			p.add(getPawnFromPawnDLV(it, "WHITE"));
		}
	}
	
	private Position[] computeAdjiacentDLV(PawnDLV p) {
		
		Position[] pos = null; 
		
		if(p.getChecker() == 1) {
			pos = new Position[] {
					new Position(p.getX() + 1, p.getY() - 1),
					new Position(p.getX() + 1, p.getY() + 1),
					new Position(p.getX() - 1, p.getY() - 1),
					new Position(p.getX() - 1, p.getY() + 1)
			};
		} else {
			pos = new Position[] {
					new Position(p.getX() + 1, p.getY() - 1),
					new Position(p.getX() + 1, p.getY() + 1),
			};
		}
		return pos;
	}
	
	private boolean existsPawnDLV(int x, int y, PawnDLV w) {
		
		if(x < 0 || y > 7) {
			return false;
		} else {
			for(PawnDLV p:whitesDLV) {
				if(p.getX() == x && p.getY() == y) {
					w.setX(p.getX());
					w.setY(p.getY());
					w.setChecker(p.getChecker());
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean canJump(int c1, int c2) {
		return c1 == 1 || c1 == c2;
	}
	
	private boolean existsEmptyPosition(int a1, int b1, int a2, int b2, Position newP) {
		
		int newX = a1 < a2 ? a2 + 1 : a2 - 1;
		int newY = b1 < b2 ? b2 + 1 : b2 - 1;
		
		for(Position p:emptyPositions) {
			if(p.getX() == newX && p.getY() == newY) {
				newP.setX(newX);
				newP.setY(newY);
				return true;
			}
		}
		return false;
	}
	
	private void updateSolution(List<PawnDLV> s, List<PawnDLV> p) {
		
		if(s.size() == 0 && !p.isEmpty()) {
			s.addAll(p);
			return;
		}
		
		if(s.size() < p.size()) {
			s.clear();
			s.addAll(p);
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean insideChecker(int newX, int newY) {
		return (newX >= 0 && newX < DIM) && (newY >= 0 && newY < DIM);
	}
	
	public boolean isMandatoryJump() {
		return mandatoryJump;
	}

	public void setMandatoryJump(boolean mandatoryJump) {
		this.mandatoryJump = mandatoryJump;
	}

	public Pawn getSelectedPawn() {
		return selectedPawn;
	}
	
	public void setSelectedPawn(Pawn selectedPawn) {
		this.selectedPawn = selectedPawn;
	}

	public Intelligence getIntelligence() {
		return ai;
	}
	
	public List<Pawn> getBlackPawns() {
		return blackPawns;
	}

	public List<Pawn> getWhitePawns() {
		return whitePawns;
	}
}
