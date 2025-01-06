package com.huy_ngndinh.client;

public class HighlightHandler {
	private PositionInterface board;
	int selectedPosition;
	public HighlightHandler() {
		board = new PositionInterface(0L);
		selectedPosition = -1;
	}
	public boolean isEmpty() {
		return board.isEmpty();
	}
	public boolean isToggle(int position) {
		return board.isToggle(position);
	}
	public boolean samePosition(int position) {
		return selectedPosition == position;
	}
	public int getPreviousPosition() {
		return selectedPosition;
	}
	public void update(int position, PiecesHandler pieces, LogicHandler logic) {
		if (!pieces.hasPiece(position)) return;
		PieceInterface piece = pieces.getPiece(position);
		board.toggle(piece.getPossiblePositions(pieces, logic));
		selectedPosition = position;
	}
	public void reset() {
		board.reset();
		selectedPosition = -1;
	}
}
