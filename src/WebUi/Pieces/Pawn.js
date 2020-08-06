
Pawn = function(initialPos,teamColor,pieceArg,hasMovedArg)
{
	/*
	 * Caution : InitialPos will be always same as the initial position of the piece in the board. use piece.getPosition() to get the current position of the piece.
	 * */
	
	var piece = pieceArg ||  new Piece(initialPos,teamColor,Piece.Pawn); // Pawn has 1 point.....
	var hasMoved = hasMovedArg === undefined? false : hasMovedArg;
	
	this.getPossibleMovements = function(cells,teams,lastMove){
		  
		  //All 8 possible jumps. 
			
		  /*
		   * Relative Positions : For white pawn
		   * ---------------------------------
		   * 				0,+2
		   * 	-1,+1		0,+1 	 +1,+1
		   * 	-1, 0		0, 0	 +1, 0
		   * 			
		   * */
		
		/*
		   * Relative Positions : For black pawn
		   * ---------------------------------
		   * 	-1, 0		0, 0	 +1, 0
		   * 	-1,-1		0,-1 	 +1,-1
		   * 				0,-2
		   * 	
		   * 	
		   * 				
		   * */
		
		/*
		 * Comment id : cmtRLStandard (Comment on Right Left Standard)
		 * 
		 * Actually the board is static in logic perspective.
		 * The board will get inverted for the user at the another end in UI only.
		 * 
		 * All the positions, the left,right standard are same,constant for both the players.
		 * 
		 * 
		 *  |---------Black coins-----------------|
		 *  |---------- Black Pawns---------------|
		 *  |									  |	
		 *  |       		  ^  Top      		  |
		 *	|				  |					  |							
		 *  | 		  Left <-   -> Right		  |
		 *  |           	  |                   |
 		 *  |            	  V  Bottom           |
 		 *  |									  |
		 *  |---------- White Pawns---------------|
		 *  |----------White coins----------------|
		 *  
		 *   Hence, 
		 *   
		 *   --------------------------
		 *   |  7 |    |    |    |    |
		 *   --------------------------    BP -> Black Pawn
		 *   |  6 |    |    |    |    |    WP -> White Pawn
		 *   -------------------------     Hence,
		 *   |  5 | BP |    |'BP'|    |      The LeftCapture for white Pawn (within '' ) will be at c4 to b5 i.e (-1,+1)
		 *   -------------------------     But, 
		 *   |  4 |    |'WP'|    | WP |      The LeftCapture for black Pawn (within '' )  will be at d5 to c4 i.e (-1,-1)     
		 *   --------------------------         "Note: Even though as far as user has concern , the move of d5 to c4 is a right pawn capture,
		 *   |  3 |    |    |    |    |            As far as developer side, this capture is still, a left pawn capture." 
		 *   ---------------------------    
		 *   |  2 |    |    |    |    |      Similarly,
		 *   ---------------------------
		 *   |  1 |    |    |    |    |       The RightCapture for white Pawn (within '' ) will be at c4 to d6 i.e (+1,+1)  
		 *   --------------------------       But,
		 *   |  A |  B | C  | D  | E  |       The RightCapture for black Pawn (within '' )  will be at d5 to e4 i.e (+1,-1)
		 *   
		 *   
		 *                                   So,
		 *                                   
		 *                                      LeftCapture-White (-1,+1)					RightCapture-White (+1,+1)
		 *                                             	         	 	 	 Pawn(B/W)
		 *   									LeftCapture-Black (-1,-1)					RightCapture-Black (+1,-1)
		 * */
		  var steps;
		  if(piece.getTeamColor() == Team.White) {// if it is white color.
			  steps = [
		               
		      /*0*/         0,+1, //Forward. Always possible if no piece at front.
		      /*2*/         0,+2, //Double Forward. Only possible if the pawn is not moved.
		               		
		      /*4*/  		+1,+1, /*Side Clash: Top Right -> Ref: Comment id : cmtRLStandard*/ 
		      /*6*/         -1,+1  /*Side Clash: Top Left  -> Ref: Comment id : cmtRLStandard */  
		              ];
		   }
		  else{
			  steps = [
			           
		      /*0*/         0,-1, //Forward. Always possible if no piece at front.
		      /*2*/         0,-2, //Double Forward. Only possible if the pawn is not moved.
		               		
		      /*4*/		    +1,-1, /*Side Clash: Top Right  -> Ref: Comment id : cmtRLStandard*/
		      /*6*/			-1,-1  /*Side Clash: Top Left   -> Ref: Comment id : cmtRLStandard*/  
		               ];	  	
		  }
		  
		  
		  var possibleMovements = new Array();
		  
		  var currentPos = piece.getPosition();
		  var x = currentPos.getX();
		  var y = currentPos.getY();
		  
		  
		  /*
		   * In the if checks Position.isValidPosition(x+m,y+n) acts as a guard, for protecting against the accessing of invalid positions...
		   * where m,n are the step parameters...
		   * But the for the forward steps, the check is not necessary because it always exist. 
		   * */
		  // ( 0,+1 ) cell[x+0][y+1] for white, (0,-1) cell[x+0][y-1] for black.	  
		  if(!cells[x][y+steps[1]].hasPiece()){ //if the cell[x][y+steps[1]] does not have a piece.
			  
			  
			  /**
			   * possiPos : One of possible Positions.
			   * */
			  var possiPos = new Position(x,y+steps[1]);
			  
			  /*
			   * In the end rank, normal move will not be normal, it will be up-gradation.
			   * Hence, if the pawn is in last cell, then the move code will be pawnUpgrade, except that other things moveCode will be normal.
			   * Node : pawnUpgrade will take effect only in
			   *  
			   * MoveCode.Normal
			   * 
			   * Other MoveCodes will not happen in last rank. 
			   * */
			  if ( cells[x][y+steps[1]].isInEndRank(piece.getTeamColor()) )
				  possibleMovements.push(new Move(currentPos,possiPos,MoveCode.PawnUpgrade));
			  else
				  possibleMovements.push(new Move(currentPos,possiPos,MoveCode.Normal));
			  
			  
			  //For 0,+2. It will execute only if cell[x+0][y+1] is possible.
			  /*
			   * The two steps is not possible if the pawn has moved. 
			   */
			  if( (!hasMoved) && cells[x][y+steps[3]].hasPiece() == false){
				  /**
				   * possiPos : One of possible Movements.				  
				   * */
				  
				  var possiPos = new Position(x,y+steps[3]);
				  
				  possibleMovements.push(new Move(currentPos,possiPos,MoveCode.DoubleStepPawn));
			  }
		  }
		  
		  /**
		   * Whether the side cell is occupied by the opponent pawn in the last move.
		   * @returns 1 if yes, 0 if not.
		   */	 		
		  function isSpecialKillOfSidePawnAvailable(x1,y1){
			// Obviously we are here checking whether the piece is pawn!!! lastMove.getMoveCode() == MoveCode.DoubleStepPawn
			if(cells[x1][y1].hasPiece() && Team.getPieceByID(cells[x1][y1].getPieceID(),teams).getTeamColor() != teamColor){
				if(lastMove!=null && lastMove.getMoveCode() == MoveCode.DoubleStepPawn) //Here lastMovePos may be null at the beginning of the game.
				{
					return true;			
				}
			}
			else
				return false;
			  
		  };
		
		
		  //Right Pawn Capture  MoveCode: Normal capture or SpecialPawnCaptureRight.
		  /*
		   * Explanation in words:
		   *  if the new relative position is valid, then get the cell at the relative position and check whether it has any piece, if so check
		   *  whether it has the opponent piece,
		   *  if so, conquer,
		   *  Else check whether special movement is possible. if so do that.
		   *  Note : Either one of the move is possible i.e to (x+steps[4],y+steps[5]) or isOccupiedByLastMove, not both move is possible.
		   * */
		  if(Position.isValidPosition(x+steps[4],y+steps[5])){
	            		  
		    	if( cells[x + steps[4] ][y + steps[5]].hasPiece() == true ){ //Normal Side wise conquer.
		      		if(Team.getPieceByID( cells[x + steps[4]][y + steps[5]].getPieceID(), teams).getTeamColor() != teamColor ){
		      			
		      			/**
						 *	moveCode : The move code for these type of moves.
						 */
		      			var moveCode = MoveCode.Normal;
		      			
		      			/**
		      			 * One of Possible Position.
		      			 * */
		  			    var possiPos;
		  			  
		  			  /*
		  			   * In the end rank, normal move will not be normal, it will be up-gradation.
		  			   * Hence, if the pawn is in last cell, then the move code will be pawnUpgrade, except that other things moveCode will be normal.
		  			   * Node : pawnUpgrade will take effect only in
		  			   *  
		  			   * MoveCode.Normal
		  			   * 
		  			   * Other MoveCodes will not happen in last rank. 
		  			   * */
		  			    possiPos = new Position(x+steps[4],y+steps[5]);
		  			    var x1 =  x + steps[4] ;
		  			    var y1 =  y + steps[5] ;
		  			    if ( cells[x1][y1].isInEndRank(piece.getTeamColor()) )
		  			    	possibleMovements.push(new Move(currentPos,possiPos,MoveCode.PawnUpgrade));
		  			    else
		  			    	possibleMovements.push(new Move(currentPos,possiPos,MoveCode.Normal));
					  
		      		}
		    	}
				//i.e whether the cell has piece and also whether the piece comes at the very last move.
					//The last argument 1 denotes that the move is on right side pawn.
		    	else if ( isSpecialKillOfSidePawnAvailable(x + steps[4],y)) // The Special Move's Pawn will be in the positions +1,0
					{
						if(piece.getTeamColor() == Team.White)
							possiPos = new Position(x +  1, y + 1);
						else
							possiPos = new Position(x +  1, y - 1);
						
						moveCode = MoveCode.SpecialPawnCaptureRight;
													
	  				    possibleMovements.push(new Move(currentPos,possiPos,moveCode));
			
					}
		    	else console.log("Invalid Move");
		  }
			  	
		  /* Left Pawn capture or left special move..
		   * Note : Either one of the move i.e to (x+steps[6],y+steps[7]) or isOccupiedByLastMove is possible, not both move is possible.
		   *
		   *
		   * */
		 
		  if(Position.isValidPosition(x+steps[6],y+steps[7])){
		    	if( cells[x + steps[6]][y + steps[7]].hasPiece() == true ){ //Normal Side wise conquer.
		    		if(Team.getPieceByID( cells[x + steps[6]][y + steps[7]].getPieceID(), teams).getTeamColor() != teamColor ){

						/**
						 *	moveCode : The move code for these type of moves.
						 */
		      			var moveCode = MoveCode.Normal;			  
		  			    var possiPos;
		  			  
		  			    possiPos = new Position(x+steps[6],y+steps[7]);		  			    
		  			    var x1 =  x + steps[6] ;
		  			    var y1 =  y + steps[6] ;
		  			    
		  					
		  			  /*
			  			   * In the end rank, normal move will not be normal, it will be up-gradation.
			  			   * Hence, if the pawn is in last cell, then the move code will be pawnUpgrade, except that other things moveCode will be normal.
			  			   * Node : pawnUpgrade will take effect only in
			  			   *  
			  			   * MoveCode.Normal
			  			   * 
			  			   * Other MoveCodes will not happen in last rank. 
			  			   * */
			  			    if ( cells[x1][y1].isInEndRank(piece.getTeamColor()) )
			  			    	possibleMovements.push(new Move(currentPos,possiPos,MoveCode.PawnUpgrade));
			  			    else
			  			    	possibleMovements.push(new Move(currentPos,possiPos,MoveCode.Normal));
		  			  
			  		}
		    	}
				else if (isSpecialKillOfSidePawnAvailable(x + steps[6],y)){
					
					if(piece.getTeamColor() == Team.White)
						possiPos = new Position(x -  1, y + 1);
					else
						possiPos = new Position(x -  1, y - 1);
					
					moveCode = MoveCode.SpecialPawnCaptureLeft;
												
  				    possibleMovements.push(new Move(currentPos,possiPos,moveCode));
				}
				else console.log("Invalid Move");
		  }
				  
		  return possibleMovements;
	};
	
	
	/**
	 * CouldMoveTo : Returns whether given move is possible or not to Position reqPos.
	 * CouldConquerIfOppKingAvailableAt is whether the queen is able to conquer if a piece available at toX,toY
	 * Except pawn for all other pieces these two function is same, because they does not have any special moves. 
	 * */

	this.couldConquerIfOppKingAvailableAt  = function(reqPos){
	
		var toX = reqPos.getX();
		var toY = reqPos.getY();
		    
		var fromX = piece.getPosition().getX();
	 	var fromY = piece.getPosition().getY();
	 		
	 	var diffX = toX - fromX;
	 	var diffY = toY - fromY;
	 	
	 	if(piece.getTeamColor() == Team.White){
	 		if(diffY == 1 && Math.abs(diffX) == 1)
	 			return true;
	 	}	
	 	else{	
	 		if(diffY == -1 && Math.abs(diffX) == 1)
	 			return true;
 		}
 		
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
	
	this.couldMoveTo = function(reqPos,cells,teams,lastMove){
		
		var possibleMovements = this.getPossibleMovements(cells,teams,lastMove);
		return findRequestedMoveInPossibleMovements(possibleMovements,reqPos);
					
	};
	
	/**
	 * Getter for Positions.
	 * */
	this.getPosition = function(){
		return piece.getPosition();
	};
	
	 
	/**
	 * Setter for Positions.
	 * This setter is quite different from other ones. 
	 * This will check whether the pawn has moved already , if yes, it will leave. else it will check whether the first move is a double move, if so it will set the hasDoubleMove to true.
	 * */
	this.setPosition = function(pos){		
		hasMoved = true;
		piece.setPosition(pos);
	};
	
	/**
	 * Kills the piece, to make it out of the board.
	 * */
	this.killPiece = function(){
		piece.killPiece();
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
	
	this.getPieceID = function(){
		return piece.getPieceID();
	};
	
	this.hasMoved = function(){
		return hasMoved;
	};
	
	this.deepClone = function(){
     	       return new Pawn(null,null,piece.deepClone(),this.hasMoved()); // duplicate Piece.		   
	};
};
