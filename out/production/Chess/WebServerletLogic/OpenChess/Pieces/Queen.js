
Queen = function(initialPos,teamColor,pieceArg)
{
	
	/*
	 * Caution : InitialPos will be always same as the initial position of the piece in the board. use piece.getPosition() to get the current position of the piece.
	 * */
         var piece = pieceArg ||  new Piece(initialPos,teamColor,Piece.Queen);
	
	
	
	this.getPossibleMovements = function(cells,teams){
		
	//Always The move code is normal. No special moves are there for rook.
	
	var possibleMovements = new Array();	

		 /*
		   * Relative Positions :
		   * -------------------------------------------------
		   * 	
		   * 	....		        ....			   ....
		   * 		  -2,+2			0,+2		 +2,+2
		   * 				-1,+1	0,+1  +1,+1	
		   *    ....  -2,0	-1, 0	0, 0  +1, 0  +2,0  ....	 
		   * 				-1,-1	0,-1  +1,-1
		   * 		   -2,-2        0,-2         -2,+2
		   * 	....				....       		   ....	
		   *							  		
		   * */
		
	  var steps = [  // All 8 directions.
	            /*0*/       -1,+1,
	            /*2*/        0,+1,
	            /*4*/       +1,+1,
	            /*6*/       +1, 0,
	            /*8*/       +1,-1,
	           /*10*/        0,-1,
	           /*12*/       -1,-1,
	           /*14*/        -1,0
	                  ];
	     
	     for(var i = 0; i < 16 ; i+=2 )
	    	 addPossibleMovements(steps[i],steps[i+1]);
	     
	     
	     /**
	 	 * It calculates the possible left diagonal ( \ ) , right diagonal (/) and topbottom and leftright Movements.
	 	 * @Algorithm 
	 	 *     1. same as @link addPossibleTopDownMovements but it does with all 8 directions.
	 	 *     
	 	 * @returns nothing	
	 	 */
	 	function addPossibleMovements(xInc,yInc){
	 		var x = piece.getPosition().getX();
	 		var y = piece.getPosition().getY();
	 				 	
	 		var xIter = xInc;
	 		var yIter = yInc;
	 		
	 		var currentPos  = new Position(x,y);
	 				 	 
	 		while(Position.isValidPosition(x+xIter,y+yIter)){
	 			
	 			   if( !cells[x+xIter][y+yIter].hasPiece() ){
	 						var possiPos = new Position(x+xIter,y+yIter);
	 						possibleMovements.push(new Move ( currentPos , possiPos , MoveCode.NormalMove) ) ;
	 			 			xIter += xInc;
	 			 			yIter += yInc;
	 		 		}
	 		 		else{	 		 			
	 		 				if ( Team.getPieceByID( cells[x + xIter][y + yIter].getPieceID(), teams).getTeamColor() != teamColor ){
	 			 				var possiPos = new Position(x+xIter,y+yIter);
	 			 				possibleMovements.push(new Move ( currentPos, possiPos ,MoveCode.NormalMove) );	 			 	
	 			 			}
	 		 				break;
	 		 		}
	 		}

	 	};
	 	
	     
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

this.couldMoveTo = function(toPos,cells,teams){
	
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
		
		if(Math.abs(diffX) == Math.abs(diffY)  || diffX == 0 ||  diffY == 0)
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
		return new Queen(null,null,piece.deepClone()); // duplicate Piece.		   
	};


};
