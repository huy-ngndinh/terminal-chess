package com.huy_ngndinh.client;

import java.util.ArrayList;

public class PiecesHandler {
	private ArrayList<PieceInterface>[] pieces = new ArrayList[2]; 
	public PiecesHandler() {
		pieces[0] = new ArrayList<PieceInterface>();
		pieces[1] = new ArrayList<PieceInterface>();
	}
	public void addPiece(PieceInterface piece) {
		boolean side = piece.getSide();
		pieces[side ? 1 : 0].add(piece);
	} 
	public void removePiece(int position) {
		pieces[0].removeIf(piece -> piece.isToggle(position));
		pieces[1].removeIf(piece -> piece.isToggle(position));
	}
	private void removePieceEnPassant(int from, int to, boolean currentSide) {
		int position = !currentSide ? (to - 8) : (to + 8);
		this.removePiece(position);	
	}
	private void moveCastleShort(boolean side) {
		if (!side) this.movePiece(7, 5, side);
		else this.movePiece(63, 61, side);
	}
	private void moveCastleLong(boolean side) {
		if (!side) this.movePiece(0, 3, side);
		else this.movePiece(56, 59, side);
	}
	public void movePiece(int from, int to, boolean currentSide) {
		boolean emptyPosition = !this.hasPiece(to);
		boolean isEnPassant = false;
		boolean isCastleShort = false, isCastleLong = false;
		removePiece(to);
		for (int side = 0; side < 2; side++) {
			for (PieceInterface piece : pieces[side]) {
				piece.toggleMovePreviousTurn(false);
			}
		}	
		for (PieceInterface piece : pieces[currentSide ? 1 : 0]) {
			if (piece.isToggle(from)) {
				if (piece.getClass().getSimpleName().equals("Pawn") && emptyPosition) {
					isEnPassant = true;
				}
				if (piece.getClass().getSimpleName().equals("King")) {
					if (!currentSide && from == 4 && to == 6) isCastleShort = true;
					else if (!currentSide && from == 4 && to == 2) isCastleLong = true;
					else if (currentSide && from == 60 && to == 62) isCastleShort = true;
					else if (currentSide && from == 60 && to == 58) isCastleLong = true;
				}
				piece.toggle(from);
				piece.toggle(to);
				piece.toggleMovePreviousTurn(true);
				piece.incrementCounter();
			}
		}
		if (isEnPassant) removePieceEnPassant(from, to, currentSide);
		if (isCastleShort) moveCastleShort(currentSide);
		if (isCastleLong) moveCastleLong(currentSide);
	}
	public void simulateMovePiece(int from, int to, boolean currentSide) {
		removePiece(to);
		for (PieceInterface piece : pieces[currentSide ? 1 : 0]) {
			if (piece.isToggle(from)) {
				piece.toggle(from);
				piece.toggle(to);
			}
		}
	}
	public boolean hasPiece(int position) {
		boolean answer = false;
		for (int side = 0; side < 2; side++) {
			for (PieceInterface piece : pieces[side]) {
				answer |= piece.isToggle(position);
			}
		}
		return answer;
	}
	public PieceInterface getPiece(int position) {
		PieceInterface answer = pieces[0].get(0);
		for (int side = 0; side < 2; side++) {
			for (PieceInterface piece : pieces[side]) {
				answer = piece.isToggle(position) ? piece : answer;
			}
		}
		return answer;
	}
	public PieceInterface getKing(boolean side) {
		PieceInterface answer = pieces[0].get(0);
		for (PieceInterface piece : pieces[side ? 1 : 0]) answer = piece.getClass().getSimpleName().equals("King") ? piece : answer;
		return answer;
	}
	public PositionInterface getTotalAttackPositions(boolean side, LogicHandler logic) {
		PositionInterface answer = new PositionInterface(0);
		for (PieceInterface piece : pieces[side ? 1 : 0]) answer.toggle(piece.getAttackPositions(this, logic));
		return answer;
	}
	public PositionInterface getTotalPossiblePositions(boolean side, LogicHandler logic) {
		PositionInterface answer = new PositionInterface(0);
		for (PieceInterface piece : pieces[side ? 1 : 0]) answer.toggle(piece.getPossiblePositions(this, logic));
		return answer;
	}
}
