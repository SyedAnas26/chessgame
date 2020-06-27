/**
 * Any Other teams should not be added while inheriting this class.
 * In it is alternative to enum variables in OOPS.
 * @param teamColorArg => Must parameter. other parameters will be initialized to default value
 */

function Team(teamColorArg,listOfPiecesArg,isCastlingDoneArg) {
	/**
	*  Contains all the 16 white pieces to the list, so that they can be easily accessed with index.
	*  Caution : Order must not be changed while adding pieces. This order will be used to add the pieces to the cells.
	*/
	var  listOfPieces = listOfPiecesArg;
	
	/**
	 * Color Id of the team, may be white or black.
	 * */
	var  teamColor = teamColorArg;
	
	if( listOfPiecesArg === undefined)
		initListOfPieces(teamColor);	 // InitListOfPieces will be called inside.null;
	
	var  isCastlingDone = isCastlingDoneArg || false;
	
	
	/**
	* Adds all the 16 black pieces to the list, so that they can be easily accessed with index.
	* The order of pieces will be from the left to right
	* i.e from the left rook to right rook, -> 8 forces.
	* then from left pawn to right pawn. -> 8 pawns
	* The left and right are not relative to team, it is absolute to board.
	* But, If you initialize the list of pieces by means of another list of pieces, you have to call @see setListOfPieces()  
	*/
	
	/**
	 * Initializing the Team. Here Algebraic format is used.
	 * 
	 * Convention :
	 * Example : 
	 *    rook_black   = [new Rook(new Position('a',8),Team.Black),new Rook(new Position('h',8),Team.Black)];
	 *               <-  The Rook in the left of the king      -> ,<- The Rook in the right of the king    -->    
	 *                    
	 *    bishop_black = [new Bishop(new Position('c',8),Team.Black),new Bishop(new Position('f',8),Team.Black)]; //bishop_black[0] plays on black square and bishop_black[1] plays on white square.
	 *            <-  The bishop in the left of the  king        -> ,<- The bishop in the right of the king     -->
	 * 
	 * 
	 * Actually the board is static in logic perspective.
	 * The board will get inverted for the user at the another end in UI only.
	 * 
	 * All the positions, the left,right standard are same,constant for both the players.
	 * 
	 *  </br>
	 *  |---------Black coins-----------------| </br>
	 *  |---------- Black Pawns---------------| </br> 
	 *  |									  | </br>	
	 *  |       		  ^  Top      		  | </br>
	 *	|				  |					  | </br>							
	 *  | 		  Left <-   -> Right		  | </br>
	 *  |           	  |                   | </br>
	 *  |            	  V  Bottom           | </br>
	 *  |									  | </br>
	 *  |---------- White Pawns---------------| </br>
	 *  |----------White coins----------------| </br>
	*/
	function initListOfPieces(teamColor){
		
	    var pieces = new Array();
		
		/**
		 * Row in which all the forces will be placed.
		 * */
		var forceRank = (teamColor == Team.White)? 1 : 8;
		var pawnRank   = (teamColor == Team.White)? 2 : 7;  
		
		pieces[Piece.Pawn]   = [8];
		pieces[Piece.Rook]   = [new Rook(new Position('a',forceRank),teamColor),new Rook(new Position('h',forceRank),teamColor)];	
		pieces[Piece.Knight] = [new Knight(new Position('b',forceRank),teamColor),new Knight(new Position('g',forceRank),teamColor)];
		pieces[Piece.Bishop] = [new Bishop(new Position('c',forceRank),teamColor),new Bishop(new Position('f',forceRank),teamColor)]; 
		pieces[Piece.Queen]  =  new Queen(new Position('d',forceRank),teamColor);
		pieces[Piece.King]   =  new King(new Position('e',forceRank),teamColor);
			
		var i = 0;
		for(var ch='a'; ch!=null ;ch = Position.getNextChar(ch)){ //since java script does not support increment of charecters...				 
			pieces[Piece.Pawn][i] = new Pawn(new Position(ch,pawnRank),teamColor); //Position , PlayFor
			i++;
		}
		
		listOfPieces = new Array();
		
		/**
		 * Caution : Order must not be changed while adding pieces. This order will be used to add the pieces to the cells.
		 * */
		listOfPieces.push(pieces[Piece.Rook][InitPos.Left]);
		listOfPieces.push(pieces[Piece.Knight][InitPos.Left]);
		listOfPieces.push(pieces[Piece.Bishop][InitPos.Left]);
		
		listOfPieces.push(pieces[Piece.Queen]);
		listOfPieces.push(pieces[Piece.King]);	
		
		
		listOfPieces.push(pieces[Piece.Bishop][InitPos.Right]);
		listOfPieces.push(pieces[Piece.Knight][InitPos.Right]);
		listOfPieces.push(pieces[Piece.Rook][InitPos.Right]);

		
		for(var i = 0; i< 8 ;i++)
			listOfPieces.push(pieces[Piece.Pawn][i]);

	};
	
	this.setListOfPieces = function(listOfPiecesArg){
		listOfPieces = listOfPiecesArg;
	};
	

	
	this.getListOfPieces = function(){
		return listOfPieces;
	};
	
	
	/**
	 * Returns the team color for the team.
	 * */
	this.getTeamColor = function(){
		return teamColor;
	};
	
	/**
	 * Used for getting a particular piece like a king , or to get a set of pieces,
	 * like this.getPiece(Piece.Rook) will return an array of two pieces.
	 * @return a single or array of pieces. If it is array then it could be accessed via [initPos.Left] or [initPos.Right]
	 *         The arrays that return will be a new copy of the array, but it will  contain the same reference to the pieces as of original array.
	 *  */
	this.getPiece = function(pieceName){
		
		switch (pieceName){
		
			case Piece.Pawn : 
				return listOfPieces.slice(8);
			case Piece.Rook : 
				return [listOfPieces[0],listOfPieces[7]];
			case Piece.Knight : 
				return [listOfPieces[1],listOfPieces[6]];
			case Piece.Bishop : 
				return [listOfPieces[2],listOfPieces[5]];
			case Piece.Queen :
				return listOfPieces[3];
			case Piece.King :
				return listOfPieces[4];
		   default :
			   throw "Invalid Piece option";
		};
		
	};
	
	/**
	 * Returns control to access to a particular piece.
	 * Piece could be accessed as,
	 * 
	 * team[Team.Black].getPiece(Piece.King)
	 *  */
	this.getAllPieces = function(){
		return pieces;
	};

	/**
	 * @return returns the status of castling.
	 * */
	this.isCastlingDone = function(){
		return isCastlingDone;
	};
	
	/**
	 * Sets the castling to done status.
	 * */
	this.setCastlingDone = function(){
		isCastlingDone = true;;
	};
	
	this.toString = function(){
		return teamColor == Team.White? "White" : "Black" + " team";
	};
	
	this.deepClone = function(){
		
		var dupListOfPieces = new Array();
		for (var i=0 ; i < listOfPieces.length ; i++ )
			dupListOfPieces[i] = listOfPieces[i].deepClone();
		
		var dupTeam = new Team(teamColor,dupListOfPieces,isCastlingDone);
		
		return dupTeam;
		
	};
};

/**
 * Two Teams of the match.
 *
 * */
Team.Black = 0; /* Denotes the blacks team */
Team.White = 1; // Denotes the white team 


Team.getOpponentTeamColor = function(team){
	if(team == 0)
		return 1;
	else if(team == 1)
		return 0;
	else
		throw "NO SUCH TEAM EXISTS!!! ";
};


Team.getPieceByID = function(pieceID,teams){
	for(tIndex in teams){
		var allPieces = teams[tIndex].getListOfPieces();
		for(pIndex in allPieces)
			if(allPieces[pIndex].getPieceID() == pieceID)
				return allPieces[pIndex];
	}
	
	throw "The pieceID is not matched!!! No such piece ID exists" + pieceID;
};




