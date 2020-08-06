
Knight = function(initialPos,teamColor,pieceArg)
{
	/*
	 * Caution : InitialPos will be always same as the initial position of the piece in the board. use piece.getPosition() to get the current position of the piece.
	 * */
	var piece = pieceArg || new Piece(initialPos,teamColor,Piece.Knight); 

	//Always The move code is normal. No special moves are there for knight.
	/**
	 * Used to check whether jump is possible or not.
	 * @Caution usage of cells[x][y] before validating (x,y), may cause an exception, since x,y may or may not be a valid Position. 
	 * 
	 * */	
	function isJumpPossible(cells,teams,toX,toY){
					
			if( Position.isValidPosition(toX,toY) ){
				
				 //Caution : it is not normal (x==3 || x==5) Either OR. Condition : if X is false, then at least Y must be true.
				if( (!cells[toX][toY].hasPiece()) || Team.getPieceByID(cells[toX][toY].getPieceID(),teams).getTeamColor() != piece.getTeamColor())
					return true; /*It mustn't have a piece or if it has , it must not be our Team's Piece. */
			}
			
			return false;
			
		};

	
	/**
	 * Returns the points of the piece accordingly.
	 * */
	this.getType = function(){
		return piece.getType();
	};
	
	this.getPossibleMovements = function(cells,teams){
		  var possibleMovements = new Array();
		  
		  //All 8 possible jumps.
		  
		  /*
		   * Relative Jump Movements :
		   * ------------------------------------------
		   * 		-1,+2			 +1,+2
		   * -2,+1							+2,+1				 	 		
		   * 				0, 0	 
		   * -2,-1							-2,+1
		   * 		-1,-2			 +1,+2		
		   * 				
		   * */
		  
		  var steps = [
		               -1,+2,
		               +1,+2,
		               +1,-2,
		               -1,-2,
		               -2,+1,
		               +2,+1,
		               +2,-1,
		               -2,-1
		               
		               ];
		  
		  for(var i=0;i<16;i+=2){
			  
			  var toX = steps[i]   + piece.getPosition().getX();
			  var toY = steps[i+1] + piece.getPosition().getY();

			  var currentPos = new Position(piece.getPosition().getX(),piece.getPosition().getY());
			  
			   if( isJumpPossible(cells,teams,toX,toY) ){
				   possibleMovements.push( new Move( currentPos, new Position(toX,toY),MoveCode.NormalMove)); 
			   }
		  }
				  
			  return possibleMovements;
	};
	
	/**
	 *  CouldConquerIfOppKingAvailableAt is whether the queen is able to conquer if a piece available at toX,toY
	 *  Calls couldMoveTo() in turn to get the result.
	 *  Except pawn for all other pieces these two function are same, because they does not have any special moves.
	 * */
	this.couldConquerIfOppKingAvailableAt  = function(reqPos,cells){
		if ( this.couldMoveTo(reqPos) == null )
			return false;
		else return true;
	};

	/**
	 * CouldMoveTo : Returns whether given move is possible or not to Position reqPos.
	 * @returns the corresponding move is move is valid
	 *          null if not.
	 * */

	this.couldMoveTo = function(reqPos){
		
	    var toX = reqPos.getX();
	    var toY = reqPos.getY();
	    
		var fromX = piece.getPosition().getX();
 		var fromY = piece.getPosition().getY();
 		
 		var diffX = toX - fromX;
 		var diffY = toY - fromY;
 		
 		if( ( Math.abs(diffX) == 1 && Math.abs(diffY) == 2 ) || ( Math.abs(diffX) == 2 && Math.abs(diffY) == 1  ) )
 			return new Move(piece.getPosition(),reqPos,MoveCode.NormalMove);
 		else
 			return null;
	};
	
	/*
	 * Returns the information of what the piece is playing for
	 * */
	this.getTeamColor = function(){
		return piece.getTeamColor();
	};
	
	
	/**
	 * Getter for Movements.
	 * */
	this.getPosition = function(){
		return piece.getPosition();
	};
	
	/**
	 * Setter for Positions.
	 * */
	this.setPosition = function(pos){
		piece.setPosition(pos);
	};
	
	/**
	 * Kills the piece, to make it out of the board.
	 * */
	this.killPiece = function(){
		piece.killPiece();
	};

	this.getPieceID = function(){
		return piece.getPieceID();
	};
	
	
    this.deepClone = function(){
          return new Knight(null,null,piece.deepClone());
 };
};

