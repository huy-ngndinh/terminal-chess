package com.huy_ngndinh.client.pieces;

import com.huy_ngndinh.client.PieceInterface;
import com.huy_ngndinh.client.LogicHandler;
import com.huy_ngndinh.client.PiecesHandler;
import com.huy_ngndinh.client.PositionInterface;

public class Bishop extends PieceInterface {
	private final int[] DELTA = {7, 9, -7, -9};
	public Bishop(PositionInterface position, boolean side, int counter) {
		super(position, side, counter);
	}
	public PositionInterface getAttackPositions(PiecesHandler pieces, LogicHandler logic) {
		PositionInterface answer = new PositionInterface(0);
		int currentPosition = this.getPosition();
		boolean side = this.getSide();
		int[] maxSteps = {
			Math.min(currentPosition % 8, (63 - currentPosition) / 8),
			Math.min(7 - currentPosition % 8, (63 - currentPosition) / 8),
			Math.min(7 - currentPosition % 8, 7 - (63 - currentPosition) / 8),
			Math.min(currentPosition % 8, 7 - (63 - currentPosition) / 8)
		};
		for (int dir = 0; dir < maxSteps.length; dir++) {
			for (int step = 1; step <= maxSteps[dir]; step++) {
				int newPosition = currentPosition + step*DELTA[dir];
				if (!pieces.hasPiece(newPosition)) {
					answer.toggle(newPosition);
				} else {
					PieceInterface occupyingPiece = pieces.getPiece(newPosition);
					if (occupyingPiece.getSide() != side) answer.toggle(newPosition);
					break;
				}
			}
		}
		return answer;
	}
	public PositionInterface getPossiblePositions(PiecesHandler pieces, LogicHandler logic) {
		PositionInterface answer = new PositionInterface(0);
		int currentPosition = this.getPosition();
		boolean side = this.getSide();
		int[] maxSteps = {
			Math.min(currentPosition % 8, (63 - currentPosition) / 8),
			Math.min(7 - currentPosition % 8, (63 - currentPosition) / 8),
			Math.min(7 - currentPosition % 8, 7 - (63 - currentPosition) / 8),
			Math.min(currentPosition % 8, 7 - (63 - currentPosition) / 8)
		};
		for (int dir = 0; dir < maxSteps.length; dir++) {
			for (int step = 1; step <= maxSteps[dir]; step++) {
				int newPosition = currentPosition + step*DELTA[dir];
				if (!pieces.hasPiece(newPosition)) {
					pieces.simulateMovePiece(currentPosition, newPosition, side);
					if (!logic.kingInCheck(side)) answer.toggle(newPosition);
					pieces.simulateMovePiece(newPosition, currentPosition, side);
				} else {
					PieceInterface occupyingPiece = pieces.getPiece(newPosition);
					if (occupyingPiece.getSide() == side) break;
					pieces.simulateMovePiece(currentPosition, newPosition, side);
					if (!logic.kingInCheck(side)) answer.toggle(newPosition);
					pieces.simulateMovePiece(newPosition, currentPosition, side);
					pieces.addPiece(occupyingPiece);
					break;
				}
			}
		}
		return answer;
	}
}

