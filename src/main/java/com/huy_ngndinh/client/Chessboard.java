package com.huy_ngndinh.client;

public class Chessboard {

	private final String RESET 			      = "\033[0m"; 
	private final String BOLD 				  = "\033[1m";
	private final String TEXT 				  = "\033[38;5;33m";
	private final String LIGHT_BACKGROUND     = "\033[48;2;87;123;193m";
	private final String DARK_BACKGROUND      = "\033[48;2;52;76;183m";
	private final String SELECTED_BACKGROUND  = "\033[45m";
	private final String HIGHLIGHT_BACKGROUND = "\033[48;2;121;215;190m";

	private final String WHITESPACE 	   	  = "          ";
	private final String WHITESPACE_LARGE     = "               ";

	private final String VERTICAL             = "\u2551"; 
	private final String HORIZONTAL 	      = "\u2550";
	private final String UPLEFT 		      = "\u2554";
	private final String UPRIGHT 		      = "\u2557";
	private final String DOWNLEFT 		      = "\u255A";
	private final String DOWNRIGHT 		      = "\u255D";
	
	private final String WHITE_ROOK 	   = "♜";
	private final String WHITE_KNIGHT 	   = "♞";
	private final String WHITE_BISHOP 	   = "♝";
	private final String WHITE_QUEEN 	   = "♛";
	private final String WHITE_KING 	   = "♚";
	private final String WHITE_PAWN 	   = "♟";

	private final String BLACK 			   = "\033[38;5;0m";
	private final String BLACK_ROOK 	   = BLACK + "♜";
	private final String BLACK_KNIGHT 	   = BLACK + "♞";
	private final String BLACK_BISHOP 	   = BLACK + "♝";
	private final String BLACK_QUEEN 	   = BLACK + "♛";
	private final String BLACK_KING 	   = BLACK + "♚";
	private final String BLACK_PAWN 	   = BLACK + "♟";
	
	private String getSymbol(String name, boolean side) {
		if (!side) {
			if (name.equals("Rook")) return WHITE_ROOK;
			else if (name.equals("Knight")) return WHITE_KNIGHT;
			else if (name.equals("Bishop")) return WHITE_BISHOP;
			else if (name.equals("Queen")) return WHITE_QUEEN;
			else if (name.equals("King")) return WHITE_KING;
			else return WHITE_PAWN;
		} else {
			if (name.equals("Rook")) return BLACK_ROOK;
			else if (name.equals("Knight")) return BLACK_KNIGHT;
			else if (name.equals("Bishop")) return BLACK_BISHOP;
			else if (name.equals("Queen")) return BLACK_QUEEN;
			else if (name.equals("King")) return BLACK_KING;
			else return BLACK_PAWN;
		}
	}

	private void displayTitle() {
		// Disable cursor
		System.out.print("\033[?25l");
		String TITLE = " TERMINAL CHESS ";
		String UNDERLINE = "";
		for (int index = 0; index < TITLE.length(); index++) UNDERLINE += HORIZONTAL;

		System.out.println();
		System.out.println(WHITESPACE_LARGE + UPLEFT + UNDERLINE + UPRIGHT);
		System.out.println(WHITESPACE_LARGE + VERTICAL + BOLD + TEXT + TITLE + RESET + VERTICAL);
		System.out.println(WHITESPACE_LARGE + DOWNLEFT + UNDERLINE + DOWNRIGHT);
		System.out.println();
	}

	public void display(PiecesHandler pieces, LogicHandler logic, HighlightHandler highlight, SelectionHandler selection) {
		// Title
		displayTitle();
		// Chessboard
		String BAR = "";
		for (int index = 0; index < 26; index++) BAR += HORIZONTAL;
		System.out.println(WHITESPACE + UPLEFT + BAR + UPRIGHT);
		for (int row = 0; row < 8; row++) {
			System.out.print(WHITESPACE + VERTICAL + " ");
			for (int column = 0; column < 8; column++) {
				int position = (64 - row*8) - (8 - column);
				String pieceSymbol = "   ";
				if (pieces.hasPiece(position)) {
					PieceInterface occupyingPiece = pieces.getPiece(position);
					boolean side = occupyingPiece.getSide();
					pieceSymbol = " " + this.getSymbol(occupyingPiece.getClass().getSimpleName(), side) + " ";
				}
				if (position == selection.getCurrent()) System.out.print(SELECTED_BACKGROUND + pieceSymbol + RESET);
				else if (highlight.isToggle(position)) System.out.print(HIGHLIGHT_BACKGROUND + pieceSymbol + RESET);
				else if (row % 2 == column % 2) System.out.print(LIGHT_BACKGROUND + pieceSymbol + RESET);
				else System.out.print(DARK_BACKGROUND + pieceSymbol + RESET);
			}
			System.out.println(" " + VERTICAL + WHITESPACE);
		}
		System.out.println(WHITESPACE + DOWNLEFT + BAR + DOWNRIGHT);
	}
}
