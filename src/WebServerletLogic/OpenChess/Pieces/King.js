
King = function(initialPos,teamColor,pieceArg,hasMovedArg)
{
	/*
	 * Caution : InitialPos will be always same as the initial position of the piece in the board. use piece.getPosition() to get the current position of the piece.
	 * */
	
	var piece = pieceArg || new Piece(initialPos,teamColor,Piece.King);
	var isKingInCheck;
	var hasMoved = hasMovedArg === undefined? false : hasMovedArg;
		
	 /*
	   * Relative Movements :
	   * -------------------------------------------------
	   * 	
	   * 				-1,+1	0,+1  +1,+1 			// Only one step is possible, that too if that position is not in check.	
	   * 				-1, 0	0, 0  +1, 0	 
	   * 				-1,-1	0,-1  +1,-1
	   *							  		
	   * */
	
	 	var steps = [  // All 8 directions.
		                  -1,+1,
		                   0,+1,
		                  +1,+1,
		                  +1, 0,
		                  +1,-1,
		                   0,-1,
	                      -1,-1,
	                       -1,0
                  ];
	
	/**
	 * this.getPossibleMovements returns all possible movements of a king.
	 * @return An array of possible moves if moves are there
	 *         [] Empty array on failure (No moves are possible). 
	 * */
	this.getPossibleMovements = function(cells,teams,isLeftCastlingPossible,isRightCastlingPossible){
	
		
		var currentPos = piece.getPosition();
		var possibleMovements = new Array();
		var x = currentPos.getX();
		var y = currentPos.getY();
		
		function addPossibleMovements(){
			
			for(var i = 0; i < 16 ; i+=2 )
		 		if(Position.isValidPosition(x+steps[i],y+steps[i+1])){
		 			
		 				if( !cells[x+steps[i]][y+steps[i+1]].hasPiece() ){
		 			 			possibleMovements.push( new Move ( currentPos , new Position(x+steps[i],y+steps[i+1]), MoveCode.NormalMove )) ;
		 			 			
		 		 		}
		 		 		else{
		 			 	       if ( Team.getPieceByID(cells[x+steps[i]][y+steps[i+1]].getPieceID(),teams).getTeamColor() != teamColor )
		 			 				possibleMovements.push( new Move ( currentPos ,new Position(x+steps[i],y+steps[i+1]), MoveCode.NormalMove ));
		 		 		}
		 		}

			
			/*
			 * Again, left does not mean that left to the current player.
			 * This standard is as same as already explained in Pawn.js
  			 * 	
			 * Actually the board is static in logic perspective.
			 * The board will get inverted for the user at the another end in UI only.
			 * 
			 * All the positions, the left,right standard are same,constant for both the players.
			 * 
			 * 
			 *  |---------Black coins-----------------|
			 *  |---------- Black Pawns---------------|
			 *  |									 |	
			 *  |       		  ^  Top      		     |
			 *  |			  |  					 |							
			 *  | 	  Left <-   -> Right		     |
			 *  |           	  |                      |
 			 *  |           	  V  Bottom              |
 			 *  |									 |
			 *  |---------- White Pawns---------------|
			 *  |----------White coins----------------|
			 *  
			 *   Hence,
			 *    
			 *   -----------------------------------------
			 *  8| WR1|    |    |    | WK |    |    | WR2|
			 *  	-----------------------------------------
			 *  7|    |    |    |    |    |    |    |    |
			 *   ----------------------------------------   
			 *     WR1 <--Left Castling--> K  <---Right Castling--> WR2     ( NOTE: In Black Perspective, (e8,a8) must be right castling,
			 *   															But still in our developer perspective, (e8,a8) still a left castling )
			 *   
			 *     BR1 <--Left Castling--> K  <---Right Castling--> BR2
			 *    ---------------------------------------
			 *  2 |    |    |    |    |    |    |    |   |
			 *    ----------------------------------------
			 *  1 | BR1|    |    |    |  BK|    |    |BR2|
			 *    ----------------------------------------
			 *       a    b    c    d    e    f    g    h  
			 *   
			 *   
			 *   */
				
		};

		     
		addPossibleMovements();
		
		if(isLeftCastlingPossible)
			possibleMovements.push( new Move ( currentPos , new Position( x-2 , y ), MoveCode.LeftCastling )) ;
		
		if(isRightCastlingPossible)
			possibleMovements.push( new Move ( currentPos ,  new Position(x+2, y ), MoveCode.RightCastling )) ;


	 	
	 	return possibleMovements; 
	 	
	};
	
	/**
	 * 
	 * CouldConquerIfOppKingAvailableAt is whether the king is able to conquer if a piece available at toX,toY
	 * Except pawn for all other pieces these two function is same, because they does not have any special moves. 
	 * */
	this.couldConquerIfOppKingAvailableAt  = function(reqPos){
		
	    var toX = reqPos.getX();
	    var toY = reqPos.getY();
	    
		var fromX = piece.getPosition().getX();
 		var fromY = piece.getPosition().getY();
 		
 		var diffX = toX - fromX;
 		var diffY = toY - fromY;
 		
 		if(Math.abs(diffX) <= 1 && Math.abs(diffY) <=1)
 			return true;
 		else
 			return false;
 		
	};
	
	/**
	 * The move request may be valid or invalid. say suppose the user might clicks the rook and request it to move diagonally.
	 * or he may request to move the pawn to go for 5 steps ahead in single move.
	 * @return corresponding Movement,  if the requestedMove is in the possibleMovements.<br/> 
	 * 		   null  if no such move is possible.	
	 * */
	function findRequestedMoveInPossibleMovements(possibleMovements,requestedPos){
		for(var i = 0; i < possibleMovements.length ; i++)
			if(requestedPos.isEqualsPos(possibleMovements[i].get_toPos()))
				return possibleMovements[i];
		return null;
	};
	
	/**
	 * CouldMoveTo : Returns whether given move is possible or not to Position reqPos.
	 * */
	this.couldMoveTo = function(reqPos,cells,teams,isLeftCastlingPossible,isRightCastlingPossible){
		var possibleMovements = this.getPossibleMovements(cells,teams,isLeftCastlingPossible,isRightCastlingPossible);
		return findRequestedMoveInPossibleMovements(possibleMovements,reqPos);
			
	};
	
	
	
	/**
	 * Returns the points of the piece accordingly.
	 * */
	this.getType = function(){
		return piece.getType();
	};
	
	/**
	 * Returns the information of what the piece is playing for
	 */
	this.getTeamColor = function(){
		return piece.getTeamColor();
	};
	
	/**
	 * Getter for Positions.
	 * */
	this.getPosition = function(){
		return piece.getPosition();
	};
	
	/**
	 * Setter for Positions.
	 * */
	this.setPosition = function(pos){
		piece.setPosition(pos);
		hasMoved = true;
	};
	
	this.getPieceID = function(){
		return piece.getPieceID();
	};
	

	this.hasMoved = function(){
		return hasMoved;
	};
	
    this.deepClone = function(){
		return new King(null,null,piece.deepClone(),this.hasMoved()); // duplicate Piece.		   
	};

};
