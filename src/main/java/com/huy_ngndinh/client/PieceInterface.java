package com.huy_ngndinh.client;

public abstract class PieceInterface {
	private PositionInterface position;
	private boolean side;
	private int counter;
	private boolean movePreviousTurn;
	public PieceInterface(PositionInterface position, boolean side, int counter) {
		this.position = position;
		this.side = side;
		this.counter = counter;
		movePreviousTurn = false;
	}
	public int getPosition() {
		return position.findFirst();
	}
	public boolean isToggle(int position) {
		return this.position.isToggle(position);
	}
	public void toggle(int position) {
		this.position.toggle(position);
	}
	public boolean getSide() {
		return side;
	}
	public void incrementCounter() {
		counter++;
	}
	public int getCounter() {
		return counter;
	}
	public void toggleMovePreviousTurn(boolean value) {
		movePreviousTurn = value;
	}
	public boolean pieceMovePreviousTurn() {
		return movePreviousTurn;
	}
	public abstract PositionInterface getAttackPositions(PiecesHandler pieces, LogicHandler logic);
	public abstract PositionInterface getPossiblePositions(PiecesHandler pieces, LogicHandler logic); 
}
