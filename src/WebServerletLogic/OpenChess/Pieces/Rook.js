
Rook = function(initialPos,teamColor,pieceArg,hasMovedArg)
{
	/*
	 * Caution : InitialPos will be always same as the initial position of the piece in the board. use piece.getPosition() to get the current position of the piece.
	 * */
	
	var piece =  pieceArg || new Piece(initialPos,teamColor,Piece.Rook);
	var hasMoved = hasMovedArg === undefined? false : hasMovedArg;
    
	Rook.prototype.getPossibleMovements = function(cells,teams){
		//Always The move code is normal. No special moves are there for rook.
		var possibleMovements = new Array();
		
		addPossibleTopDownMovements();
		addPossibleLeftRightMovements();
		 

		 /*
		   * Relative Movements :
		   * ---------------------------------
		   * 						....
		   * 
		   * 						0,+2
		   * 						0,+1 	 
		   * ....		-1, 0		0, 0	 +1, 0	....
		   * 						0,-1
		   * 						0,+1
		   * 
		   *						....  		
		   * */
		
		/**
		 * It calculates the possible top and bottom Movements.
		 * @Algorithm 
		 *     1. add the empty boxes in the top, till the end of the board.
		 *     2. if a box is not empty, then stop there check if it has opponent piece, if so add that too else break.
		 * 	   3. do the same thing (step 1 and 2 )to bottom boxes also.
		 * @returns nothing	
		 * */
		

		function addPossibleTopDownMovements(){
			
			 	var x = piece.getPosition().getX();
			 	var y = piece.getPosition().getY();
			 	var currentPos = new Position(x,y);
			 		
			 	var yIter = 1;
			 	while(Position.isValidPosition(x,y+yIter)){
			 		if( !cells[x][y+yIter].hasPiece() ){
			 			possibleMovements.push(new Move( currentPos, new Position(x,y+yIter),MoveCode.Normal));
			 			yIter++;
			 		}
			 		else{
			 			if ( Team.getPieceByID( cells[x][y + yIter].getPieceID(), teams).getTeamColor() != teamColor )
			 				possibleMovements.push(new Move( currentPos,  new Position(x,y+yIter),MoveCode.Normal));
			 			    break;
			 		}
			 	}
			 	
			 	var yIter = -1;
			 	while(Position.isValidPosition(x,y+yIter)){
			 		if( !cells[x][y+yIter].hasPiece() ){
			 			possibleMovements.push(new Move( currentPos, new Position(x,y+yIter),MoveCode.Normal));
			 			yIter--;
			 		}
			 		else{
			 			if ( Team.getPieceByID( cells[x][y + yIter].getPieceID(), teams).getTeamColor() != teamColor )
			 				possibleMovements.push(new Move( currentPos,  new Position(x,y+yIter),MoveCode.Normal));
			 			break;
			 		}
			 	}
			 	
		 }
		 
		 /**
			 * It calculates the possible left and right Movements.
			 * @Algorithm 
			 *     1. same as @link addPossibleTopDownMovements but it does with x axis.
			 *     
			 * @returns nothing	
			 * */
		 function addPossibleLeftRightMovements(){
			 
			 	var x = piece.getPosition().getX();
			 	var y = piece.getPosition().getY();
			 	var currentPos = new Position(x,y);
				
			 	var xIter = 1;
			 	while(Position.isValidPosition(x+xIter,y)){
			 		if( !cells[x+xIter][y].hasPiece() ){
		 				possibleMovements.push(new Move( currentPos, new Position(x+xIter,y),MoveCode.Normal));
			 			xIter++;
			 		}
			 		else{
			 			if ( Team.getPieceByID( cells[x + xIter][y].getPieceID(), teams).getTeamColor() != teamColor )
			 				possibleMovements.push(new Move( currentPos,  new Position(x+xIter,y),MoveCode.Normal));
			 			    break;
			 		}
			 	}
			 	
			 	var xIter = -1;
			 	while(Position.isValidPosition(x+xIter,y)){
			 		if( !cells[x+xIter][y].hasPiece() ){
			 			possibleMovements.push(new Move( currentPos, new Position(x+xIter,y),MoveCode.Normal));
			 			xIter--;
			 		}
			 		else{
			 			if ( Team.getPieceByID( cells[x + xIter][y].getPieceID(), teams).getTeamColor() != teamColor )
			 				possibleMovements.push(new Move( currentPos,  new Position(x+xIter,y),MoveCode.Normal));
			 			break;
			 		}
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
		if ( this.couldMoveTo(reqPos,cells) == null )
			return false;
		else return true;
	};
	
	/**
	 * CouldMoveTo : Returns whether given move is possible or not to Position reqPos.
	 * @returns the corresponding move is move is valid
	 *          null if not.
	 * */

	Rook.prototype.couldMoveTo = function(toPos,cells,teams){
		
		var fromPos = piece.getPosition();
		
		var toX = toPos.getX();
	    var toY = toPos.getY();
	    
		
		if(cells[toX][toY].hasPiece() ) //If there is a piece
			if ( toPos.isEqualsPos(fromPos) || Team.getPieceByID(cells[toX][toY].getPieceID(),teams).getTeamColor() == piece.getTeamColor() )  // Same team or Same Piece.
				return null;
			    	   
		var fromX = piece.getPosition().getX();
 		var fromY = piece.getPosition().getY();
 		
 		var diffX = toX - fromX;
 		var diffY = toY - fromY;
 		
 		if(diffX == 0 || diffY == 0)
 		{
 		
 			/**
 			 * Used to iterate form one cell to another cell.
 			 * if the difference is positive, then xIter will be +1,
 			 * if the difference is negative, then xIter will be -1,
 			 * else Iter will be zero.
 			 * */ 
 			var xIter =  diffX == 0 ? 0 : (diffX > 0 ? +1 : -1);
 			var yIter =  diffY == 0 ? 0 : (diffY > 0 ? +1 : -1);
 			
 			var x = fromX + xIter;
 			var y =	fromY + yIter;
 			
 			while(x != toX || y != toY){
				
 				if(cells[x][y].hasPiece())
 					return null;
 				
 				 x += xIter ;
 				 y += yIter ;
 				 
 			}
 			
 			return new Move(piece.getPosition(),toPos,MoveCode.NormalMove);
 		}
 		else
 			return null;
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

	/**
	 * Kills the piece, to make it out of the board.
	 * */
	this.killPiece = function(){
		piece.killPiece();
	};
	
	this.getPieceID = function(){
		return piece.getPieceID();
	};
		

	this.hasMoved = function(){
		return hasMoved;
	};
	
    this.deepClone = function(){
   	   return new Rook(null,null,piece.deepClone(),this.hasMoved()); // duplicate Piece.		   
	};

	
};
