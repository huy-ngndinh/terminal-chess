package com.huy_ngndinh.client;

import com.huy_ngndinh.client.pieces.Queen;

public class LogicHandler {
	private PiecesHandler pieces;
	public LogicHandler(PiecesHandler pieces) {
		this.pieces = pieces;
	}
	public boolean kingInCheck(boolean side) {
		int kingPosition = pieces.getKing(side).getPosition();
		PositionInterface attackPositions = pieces.getTotalAttackPositions(!side, this);
		return attackPositions.isToggle(kingPosition);
	}
	public boolean ableToCastle(boolean side, boolean shortSide) {
		int[] relevantSquares = shortSide ? new int[]{7, 6, 5, 4} : new int[]{0, 1, 2, 3, 4};
		if (side) for (int index = 0; index < relevantSquares.length; index++) relevantSquares[index] += 56;
		for (int index = 1; index + 1 < relevantSquares.length; index++) {
			if (pieces.hasPiece(relevantSquares[index])) {
				return false;
			}
		}
		if (!pieces.hasPiece(relevantSquares[0]) || !pieces.hasPiece(relevantSquares[relevantSquares.length-1])) return false;
		PieceInterface rook = pieces.getPiece(relevantSquares[0]);
		if (!rook.getClass().getSimpleName().equals("Rook")) return false;
		if (rook.getCounter() != 0) return false;
		PieceInterface king = pieces.getPiece(relevantSquares[relevantSquares.length-1]);
		if (!king.getClass().getSimpleName().equals("King")) return false;
		if (king.getCounter() != 0) return false;
		PositionInterface attackPositions = pieces.getTotalAttackPositions(!side, this);
		for (int index = 1; index < relevantSquares.length; index++) {
			if (attackPositions.isToggle(relevantSquares[index])) {
				return false;
			}
		}
		return true;
	}
	public void promotion(boolean side) {
		for (int position = (side ? 0 : 56); position < (side ? 8 : 64); position++) {
			if (!pieces.hasPiece(position)) continue;
			PieceInterface occupyingPiece = pieces.getPiece(position);
			if (occupyingPiece.getSide() == side && occupyingPiece.getClass().getSimpleName().equals("Pawn")) {
				pieces.removePiece(position);
				pieces.addPiece(new Queen(new PositionInterface(1L << position), side, 0));
			}
		}
	}
	public boolean ableToEnPassant(int from, int to, boolean side) {
		boolean validateBoundary = (from + 1 == to && (from + 1) % 8 != 0) || (from - 1 == to && from % 8 != 0);
		boolean validatePosition = (!side && 32 <= from && from <= 39) || (side && 24 <= from && from <= 31);
		if (!pieces.hasPiece(to)) return false;
		PieceInterface piece = pieces.getPiece(to);
		boolean validateOpponent = piece.getSide() != side && piece.getClass().getSimpleName().equals("Pawn") && piece.getCounter() == 1 && piece.pieceMovePreviousTurn();
		return validateBoundary && validatePosition && validateOpponent; 
	}
	public boolean adjacentToKing(int position, boolean side) {
		PieceInterface opponentKing = pieces.getKing(!side);
		PositionInterface opponentAttackPositions = opponentKing.getAttackPositions(pieces, this);
		return opponentAttackPositions.isToggle(position);
	}
	public boolean isCheckmate(boolean side) {
		return pieces.getTotalPossiblePositions(side, this).isEmpty();	
	}
}
