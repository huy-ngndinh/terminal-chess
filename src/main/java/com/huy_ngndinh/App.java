package com.huy_ngndinh;

import com.huy_ngndinh.client.*;
import com.huy_ngndinh.client.pieces.*;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class App {
	private static char[] initialBoard = new char[64];
	private static void setup(PiecesHandler pieces) {
		// white
		initialBoard[0] = initialBoard[7] = 'r';
		initialBoard[1] = initialBoard[6] = 'n';
		initialBoard[2] = initialBoard[5] = 'b';
		initialBoard[3] = 'q';
		initialBoard[4] = 'k';
		for (int position = 8; position <= 15; position++) initialBoard[position] = 'p';
		// black
		initialBoard[56] = initialBoard[63] = 'R';
		initialBoard[57] = initialBoard[62] = 'N';
		initialBoard[58] = initialBoard[61] = 'B';
		initialBoard[59] = 'Q';
		initialBoard[60] = 'K';
		for (int position = 48; position <= 55; position++) initialBoard[position] = 'P';
		// insert pieces
		for (int position = 0; position < 64; position++) {
			if (initialBoard[position] == 'r') pieces.addPiece(new Rook(new PositionInterface(1L << position), false, 0));
			else if (initialBoard[position] == 'n') pieces.addPiece(new Knight(new PositionInterface(1L << position), false, 0));
			else if (initialBoard[position] == 'b') pieces.addPiece(new Bishop(new PositionInterface(1L << position), false, 0));
			else if (initialBoard[position] == 'q') pieces.addPiece(new Queen(new PositionInterface(1L << position), false, 0));
			else if (initialBoard[position] == 'k') pieces.addPiece(new King(new PositionInterface(1L << position), false, 0));
			else if (initialBoard[position] == 'p') pieces.addPiece(new Pawn(new PositionInterface(1L << position), false, 0));
			else if (initialBoard[position] == 'R') pieces.addPiece(new Rook(new PositionInterface(1L << position), true, 0));
			else if (initialBoard[position] == 'N') pieces.addPiece(new Knight(new PositionInterface(1L << position), true, 0));
			else if (initialBoard[position] == 'B') pieces.addPiece(new Bishop(new PositionInterface(1L << position), true, 0));
			else if (initialBoard[position] == 'Q') pieces.addPiece(new Queen(new PositionInterface(1L << position), true, 0));
			else if (initialBoard[position] == 'K') pieces.addPiece(new King(new PositionInterface(1L << position), true, 0));
			else if (initialBoard[position] == 'P') pieces.addPiece(new Pawn(new PositionInterface(1L << position), true, 0));
		}
	}

	private static void runner(LogicHandler logic, PiecesHandler pieces, Chessboard chessboard, SelectionHandler selection, HighlightHandler highlight, SideHandler side, CounterHandler counter) throws Exception {
		String endMessage = "";
		Terminal terminal = TerminalBuilder.builder().system(true).build();
		terminal.enterRawMode();
		while (true) {
			System.out.print("\033\143");
			// Checkmate
			boolean currentSide = side.getSide();
			if (logic.isCheckmate(currentSide)) {
				endMessage = (currentSide ? "Black" : "White") + " Checkmated!";
				break;
			}
			// Display
			chessboard.display(pieces, logic, highlight, selection);
			// Selection
			int character = terminal.reader().read();
			if (character == 27) return;
			else if (character == 104) selection.moveLeft();
			else if (character == 106) selection.moveDown();
			else if (character == 107) selection.moveUp();
			else if (character == 108) selection.moveRight();
			else if (character == 13) {
				int selectedPosition = selection.getCurrent();
				if (highlight.isEmpty()) {
					if (pieces.hasPiece(selectedPosition) && pieces.getPiece(selectedPosition).getSide() != side.getSide()) continue;
					highlight.update(selectedPosition, pieces, logic);
				} else if (highlight.samePosition(selectedPosition)) {
					highlight.reset();
				} else if (!highlight.isToggle(selectedPosition)) {
					highlight.reset();
					highlight.update(selectedPosition, pieces, logic);
				} else {
					int previousPosition = highlight.getPreviousPosition();
					highlight.reset();
					pieces.movePiece(previousPosition, selectedPosition, currentSide);
					side.switchSide();
					counter.incrementCounter();
				}
			}
			// Promotion
			logic.promotion(currentSide);
		}
		System.out.println(endMessage);
	}

    public static void main( String[] args ) throws Exception {
		CounterHandler counter = new CounterHandler(0);
		SideHandler side = new SideHandler(false);
		SelectionHandler selection = new SelectionHandler();
		HighlightHandler highlight = new HighlightHandler();
		PiecesHandler pieces = new PiecesHandler();
		LogicHandler logic = new LogicHandler(pieces);
		Chessboard chessboard = new Chessboard();
		setup(pieces);
		runner(logic, pieces, chessboard, selection, highlight, side, counter);

		// Enable cursor
		System.out.print("\033[?25h");
    }
}
