package com.huy_ngndinh.client.pieces;

import com.huy_ngndinh.client.PieceInterface;
import com.huy_ngndinh.client.LogicHandler;
import com.huy_ngndinh.client.PiecesHandler;
import com.huy_ngndinh.client.PositionInterface;

public class Pawn extends PieceInterface {
	private int[] DELTA = {8, 16, 9, 7};
	public Pawn(PositionInterface position, boolean side, int counter) {
		super(position, side, counter);
		if (side) {
			for (int index = 0; index < DELTA.length; index++) {
				DELTA[index] *= -1;
			}
		}
	}
	public PositionInterface getAttackPositions(PiecesHandler pieces, LogicHandler logic) {
		PositionInterface answer = new PositionInterface(0);
		int currentPosition = this.getPosition();
		boolean side = this.getSide();
		int counter = this.getCounter();
		if (0 <= currentPosition + DELTA[0] && currentPosition + DELTA[0] < 64 && !pieces.hasPiece(currentPosition + DELTA[0])) {
			answer.toggle(currentPosition + DELTA[0]);
		}
		if (counter == 0 && !pieces.hasPiece(currentPosition + DELTA[0]) && !pieces.hasPiece(currentPosition + DELTA[1])) {
			answer.toggle(currentPosition + DELTA[1]);
		}
		for (int index = 2; index < DELTA.length; index++) {
			if (DELTA[index] == 7 && currentPosition % 8 == 0) continue;
			else if (DELTA[index] == 9 && 7 - currentPosition % 8 == 0) continue;
			else if (DELTA[index] == -7 && 7 - currentPosition % 8 == 0) continue;
			else if (DELTA[index] == -9 && currentPosition % 8 == 0) continue;
			if (0 <= currentPosition + DELTA[index] && currentPosition + DELTA[index] < 64 && pieces.hasPiece(currentPosition + DELTA[index])) {
				PieceInterface piece = pieces.getPiece(currentPosition + DELTA[index]);
				if (piece.getSide() != side) answer.toggle(currentPosition + DELTA[index]);
			}
		}
		if (logic.ableToEnPassant(currentPosition, currentPosition + DELTA[2] - DELTA[0], side)) {
			answer.toggle(currentPosition + DELTA[2]);
		}
		if (logic.ableToEnPassant(currentPosition, currentPosition + DELTA[3] - DELTA[0], side)) {
			answer.toggle(currentPosition + DELTA[3]);
		}
		return answer;
	}
	public PositionInterface getPossiblePositions(PiecesHandler pieces, LogicHandler logic) {
		PositionInterface answer = new PositionInterface(0);
		int currentPosition = this.getPosition();
		boolean side = this.getSide();
		int counter = this.getCounter();
		if (0 <= currentPosition + DELTA[0] && currentPosition + DELTA[0] < 64 && !pieces.hasPiece(currentPosition + DELTA[0])) {
			pieces.simulateMovePiece(currentPosition, currentPosition + DELTA[0], side);
			if (!logic.kingInCheck(side)) answer.toggle(currentPosition + DELTA[0]);
			pieces.simulateMovePiece(currentPosition + DELTA[0], currentPosition, side);
		}
		if (counter == 0 && !pieces.hasPiece(currentPosition + DELTA[1]) && !pieces.hasPiece(currentPosition + DELTA[1])) {
			pieces.simulateMovePiece(currentPosition, currentPosition + DELTA[1], side);
			if (!logic.kingInCheck(side)) answer.toggle(currentPosition + DELTA[1]);
			pieces.simulateMovePiece(currentPosition + DELTA[1], currentPosition, side);
		}
		for (int index = 2; index < DELTA.length; index++) {
			if (DELTA[index] == 7 && currentPosition % 8 == 0) continue;
			else if (DELTA[index] == 9 && 7 - currentPosition % 8 == 0) continue;
			else if (DELTA[index] == -7 && 7 - currentPosition % 8 == 0) continue;
			else if (DELTA[index] == -9 && currentPosition % 8 == 0) continue;
			if (0 <= currentPosition + DELTA[index] && currentPosition + DELTA[index] < 64 && pieces.hasPiece(currentPosition + DELTA[index])) {
				PieceInterface occupyingPiece = pieces.getPiece(currentPosition + DELTA[index]);
				if (occupyingPiece.getSide() != side) {
					pieces.simulateMovePiece(currentPosition, currentPosition + DELTA[index], side);
					if (!logic.kingInCheck(side)) answer.toggle(currentPosition + DELTA[index]);
					pieces.simulateMovePiece(currentPosition + DELTA[index], currentPosition, side);
					pieces.addPiece(occupyingPiece);
				}
			}
		}
		if (logic.ableToEnPassant(currentPosition, currentPosition + DELTA[2] - DELTA[0], side)) {
			PieceInterface occupyingPiece = pieces.getPiece(currentPosition + DELTA[2] - DELTA[0]);
			pieces.simulateMovePiece(currentPosition, currentPosition + DELTA[2], side);
			pieces.removePiece(currentPosition + DELTA[2] - DELTA[0]);
			if (!logic.kingInCheck(side)) answer.toggle(currentPosition + DELTA[2]);
			pieces.simulateMovePiece(currentPosition + DELTA[2], currentPosition, side);
			pieces.addPiece(occupyingPiece);
		}
		if (logic.ableToEnPassant(currentPosition, currentPosition + DELTA[3] - DELTA[0], side)) {
			PieceInterface occupyingPiece = pieces.getPiece(currentPosition + DELTA[3] - DELTA[0]);
			pieces.simulateMovePiece(currentPosition, currentPosition + DELTA[3], side);
			pieces.removePiece(currentPosition + DELTA[3] - DELTA[0]);
			if (!logic.kingInCheck(side)) answer.toggle(currentPosition + DELTA[3]);
			pieces.simulateMovePiece(currentPosition + DELTA[3], currentPosition, side);
			pieces.addPiece(occupyingPiece);
		}
		return answer;
	}
}
