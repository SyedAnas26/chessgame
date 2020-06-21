function InitPos(){};
	/**
	 * Initial Position of the pieces.
	 * Eg. There are two rooks ,  left rook , right rook,
	 * Which could be accessed via (team[Team.White].getPiece(Piece.Rook))[InitPos.Left]
	 * The Initial Position is always calculated with respected to Kings Perspective.
	 * That too Board is always static.
	 * @see initPieces() for more details about convention of board.
	 * */
InitPos.Left =  0;
InitPos.Right = 1;