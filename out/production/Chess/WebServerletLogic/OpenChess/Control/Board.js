	
	Board = function(cellsArg,teamsArg,lastClickPosition)
	{
		var  cells = cellsArg; 
		var  teams  = teamsArg || [new Team(Team.Black),new Team(Team.White)]; // if teamsArg is undefined.
		
		/**
		 * Contains the last Moved coins position.
		 * */
		var lastClickPosition = lastClickPosition || null;
		
		if(cellsArg === undefined){
			
			cells = [8]; // if cellsArg is undefined.
			
			/**
			 * Initializing every cell using numeric format, only if cells are not passed as argument!!!
			 * */
			for (var i=0 ; i<8; i++){
				cells[i] = [8];
				for (var j=0 ; j<8; j++)
					cells[i][j] = new Cell(new Position(i,j));
			}
			
		}
		
		
		this.deepClone = function(){
		
		    var dupCells  = new Array();
		    
			for (var i=0 ; i<8; i++){
				
				dupCells[i] = new Array();
				
				for (var j=0 ; j<8; j++)
			          dupCells[i][j] = cells[i][j].deepClone();                  		
			}
					    
			var dupTeams = new Array();
			
			for(var teamIndex = 0 ; teamIndex < teams.length ; teamIndex++)
				dupTeams[teamIndex] =  teams[teamIndex].deepClone();
			
			var dupBoard = new Board(dupCells,dupTeams,lastClickPosition);
			return dupBoard;
		};
		
		this.setLastClickPosition = function(lastClickPositionArg){
			lastClickPosition = lastClickPositionArg;
		};
		
		this.setTeam = function(teamArg,teamColor){
			teams[teamColor] = teamArg; 
		};
		
		/*
		 * Removes the piece (if any ) from the cell.
		 * */
		function clearCell(pos){
			getCellAtPos(pos).setPieceID(null); //clearing the current piece.
		};
		
		/**
		 * Removes a piece from the board, and makes appropriate changes.
		 * */
		function killPiece(piece){
			piece.killPiece();
			clearCell(piece.getPosition()); //Clearing the cell that containing the piece.
		};
		
		/**
		 * @description These move comes as a result of current piece move because of move of the piece from fromPos to toPos.
		 * @example For example..,
		 *  <p> <b> Move  </b> : if the king is moved from e1 to c1 for left castling,<br/>
		 *  <b> ConsequenceMoves </b>: Then the left rook must be moved from a1 to d1. </p>
		 * @param currentMove
		 * @return consequenceMoves A List of consequence moves. 
		 * */
		this.makeConsequenceMoves = function(currentMove){
			var consequenceMoves = new Array(); 
			var toPos = currentMove.get_toPos();
			var fromPos = currentMove.get_fromPos();
			var teamColor = Team.getPieceByID(getCellAtPos(toPos).getPieceID(),teams).getTeamColor();
			console.log("I am here at Secondclick of makeConsequenceMoves");
			switch(currentMove.getMoveCode()){
			
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
					   * 	+1,-1		0,-1 	 -1,-1
					   * 				0,-2
					   * 
				* */
					
				case  MoveCode.SpecialPawnCaptureLeft :
						
						killPiece(Team.getPieceByID(getCellAtPos(fromPos.getRelativePosition(-1,0)).getPieceID(), teams)); //(-1,0)
						consequenceMoves.push( new Move(fromPos.getRelativePosition(-1,0) , null, MoveCode.SpecialPawnCaptureLeft));
						break;
						
				case  MoveCode.SpecialPawnCaptureRight :
					    killPiece(Team.getPieceByID(getCellAtPos(fromPos.getRelativePosition(+1,0)).getPieceID(), teams)); //(+1,0)
						consequenceMoves.push( new Move(fromPos.getRelativePosition(+1,0) , null, MoveCode.SpecialPawnCaptureRight));
						break;
						
				case  MoveCode.PawnUpgrade :
					
					    //Removing the source pawn.
					    killPiece(Team.getPieceByID(getCellAtPos(toPos).getPieceID(), teams)); 
						consequenceMoves.push( new Move( toPos , null, MoveCode.PawnUpgrade));
						
						
						//Request to add an  the extra piece, this request will be satisfied by addPiece() function @see addPiece() in GameManager.js for more details.
						consequenceMoves.push( new Move( null, toPos ,  MoveCode.PawnUpgrade));
						break;
					   
				case  MoveCode.LeftCastling :
					   /*
					    * The y+1 is used instead of y , because y is in numeric notation. 
					    * But here , we are using algebraic notation, for that notation conversion this +1 is there.
					    * Example ('a',1) in algebraic will be equivalent to (0,0) in notation. So, for that y, we have added +1.
					    * @see toString function in Position.js for more details.  
					    * Here, currentMove.get_fromPos().getY() will be equal to currentMove.get_toPos().getY(),
					    * because both will be of same rank. 
					    * */
					   // Moving the Left Rook as consequence.
					    var leftRookMove = new Move ( new Position('a',currentMove.get_toPos().getY() + 1),new Position('d',currentMove.get_toPos().getY() +1),MoveCode.Normal);
					    this.movePiece(leftRookMove); 
					    consequenceMoves.push(leftRookMove);
					    teams[teamColor].setCastlingDone();
					    break;
					    
				case  MoveCode.RightCastling :
					   
						// Moving the Right Rook as consequence.
				    	var rightRookMove = new Move ( new Position('h',currentMove.get_toPos().getY() +1),new Position('f',currentMove.get_toPos().getY() + 1),MoveCode.Normal);
				    	this.movePiece(rightRookMove); 
				    	consequenceMoves.push(rightRookMove);
				    	teams[teamColor].setCastlingDone();
				    	break;
			
			   default :
				    return null; /* No consequence move is possible NOTE : Do not return [ ] 'empty array' , because empty array is considered as an array with length 1*/
					
			}
			return consequenceMoves;
		};
		
		/**
		 * Called when either black or white to move.
		 * This function will execute the move present in the reqMove variable.
		 * @param reqMove It will contain user requested move. It will be null, if illegal requested move is made. 
		 * @returns nothing. And the caller has the responsibility to ensure that , the move is valid.
		 * @caution It wont check whether it is a valid move or invalid move, the user have to check the move validity before calling the function. 
		 * */
		this.movePiece = function(reqMove){
			console.log("I am inside move, I goona move" + reqMove + reqMove.toString());
			var fromPos = reqMove.get_fromPos();
			var toPos = reqMove.get_toPos();
			
			if(getCellAtPos( toPos ).hasPiece()){ /*If that cell has piece, then surely it must be the opponent piece, Hence conquering will be done. */
		      var pieceToDie = Team.getPieceByID(getCellAtPos(toPos).getPieceID(),teams);
			  killPiece(pieceToDie); 
			}
			
			//Updating the piece.
			var pieceToBeUpdated = Team.getPieceByID(getCellAtPos(fromPos).getPieceID(),teams);
			pieceToBeUpdated.setPosition(toPos);
			
			//Updating the cell.
			getCellAtPos(toPos).setPieceID(getCellAtPos(fromPos).getPieceID());
			clearCell(fromPos);
		};
		
		
		 /**
		  * Protected Purposes...
		  * */
		this.getCellAtPos = function(pos){
			return this.getCellAt(pos.getX(),pos.getY());
		};
		
		/**
		 * @return Returns the control to any cell at Position pos.
		 * */
		function getCellAtPos(pos){
			return getCellAt(pos.getX(),pos.getY());
		};
		
				
		this.getPieceColor = function(pos){
		   return getCellAtPos(pos).getPieceColor();	
		};
		
		/**
		 * Protected Purposes..
		 * */
		this.getCellAt = function(x,y){
			
			if($.isNumeric(x))
				return cells[x][y];
			else{
				var cellPosition = Position.getNumericX(x);				
				return cells[cellPosition][y-1];
			}
		};
		
		/**
		 * Used to get the cell at particular x and y.
		 * The X an Y may be in either numeric or algebraic format.
		 * */
		function getCellAt(x,y){
		  if( Position.isValidPosition(x, y)){
			  if($.isNumeric(x))
				  return cells[x][y];
			  else{
				  var cellPosition = Position.getNumericX(x);				
				  return cells[cellPosition][y-1];
			  }
		  }
		  else throw "Invalid position exception at the call of getCellAt(x,y) x : " + x + "y : " + y;
 		};
			 
		/**
		 * Checks whether the particular cell has piece or not.
		 * @returns 0 -> no, 1-> yes
		 * @param 	pos -> current Position where we have to check.      	
		 * */
		this.hasPiece = function(pos){
		   return getCellAtPos(pos).hasPiece();	
		};
		
		   /**
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
			*   */
		function isLeftCastlingPossible(king){
		
			
			var teamColor = king.getTeamColor();
			
			//Because castling can be done only one time.
			if(teams[teamColor].isCastlingDone())
				return false;
			
			if(king.hasMoved())
				return false;
			
			if(  ( (teams[teamColor].getPiece(Piece.Rook)) [InitPos.Left] )  .  hasMoved())
				  return false;
				
			 /*
			    * The y+1 is used instead of y , because y is in numeric notation. 
			    * But here , we are using algebraic notation, for that notation conversion this +1 is there.
			    * Example ('a',1) in algebraic will be equivalent to (0,0) in notation. So, for that y, we have added +1.
			    * @see toString function in Position.js for more details.  
			    * */
			   // Moving the Left Rook as consequence.
			
			var kingsAlgebricY = king.getPosition().getY()+1;
			
			for(var ch = 'b' ; ch  <= 'd' ; ch = Position.getNextChar(ch) )
				if(getCellAt(ch,kingsAlgebricY).hasPiece())
					return false;
			
			//Yet to be replaced.
			for(var ch = 'c' ; ch  <= 'e' ; ch = Position.getNextChar(ch) )
				if(this.isInCheckLaneOf(Team.getOpponentTeamColor ( teamColor ) ,new Position(ch,kingsAlgebricY ) ))	
					return false;
				
			//True Will be returned, if all the above criteria are matched
			return true;
			
		};
		
		/**
		 * @returns An handle to the Game Manager such that, game manager can access the team by pieces.
		 * */
		this.getTeamHandle = function(teamColor){
			return teams[teamColor];
		};
		
		/**
		 * @returns An all the team, but the order should not be changed. Hence it better to return the array as it is.
		 * */
		this.getTeams = function(){
			return teams;
		};
		
		/**
		 * @returns Cell arrays. 
		 * */
		this.getAllCells = function(){
			return cells;
		};	
		
		function isRightCastlingPossible(king){
		
			var teamColor = king.getTeamColor();
			
			//Because castling can be done only one time.
			if(teams[teamColor].isCastlingDone())
				return false;
			
			if(king.hasMoved())
				return false;
			
			
			if(  ( (teams[teamColor].getPiece(Piece.Rook)) [InitPos.Right] )  .  hasMoved())
				  return false;
						
			 /*
			    * The y+1 is used instead of y , because y is in numeric notation. 
			    * But here , we are using algebraic notation, for that notation conversion this +1 is there.
			    * Example ('a',1) in algebraic will be equivalent to (0,0) in notation. So, for that y, we have added +1.
			    * @see toString function in Position.js for more details.  
			    * */
			   // Moving the Right Rook as consequence.
			
			var kingsAlgebricY = king.getPosition().getY()+1;
			
			if(getCellAt('f',kingsAlgebricY).hasPiece() ||  getCellAt('g',kingsAlgebricY).hasPiece() )
					return false;
			
			//Yet to be replaced.
			for(var ch = 'e' ; ch  <= 'g' ; ch = Position.getNextChar(ch) )
				if(this.isInCheckLaneOf(Team.getOpponentTeamColor ( teamColor ) , new Position(ch,kingsAlgebricY) ))
				return false;
			
			//True Will be returned, if all the above criteria are matched
			return true;
		};
		
		/**
		 * @returns all possible position of the given team pieces.
         */
		this.getAllPossibleMovementsOfAllPieces = function(teamColor){
		
		   var allPossibleMovements =  new Array();
		   var listOfPieces = teams[teamColor].getListOfPieces();
		   
		   for( var i=0 ; i < listOfPieces.length ;  i++ )
			   switch(listOfPieces[i].getType()){
			   
			   case Piece.Pawn   : 
				   /*Since the normal step forward move and special double move will not contribute to king capturing.
				    * A Special function is called to get the list of  
				    * */
				   var pawnMovements = listOfPieces[i].getPossibleMovements(cells,null);
				   allPossibleMovements = allPossibleMovements.concat(pawnMovements);
				   break;
				   
			   case Piece.King  :
				   /*
				    * isLeftCastlingPossible(listOfPieces[i]) , isRightPossible(listOfPieces[i]) is taken as false.
				    * Because, we here want to check whether our cell or dummy king is in check by other pieces,
				    * i.e we want to check whether our piece could be captured by opponent, if we place at the checking cell.
				    * Capturing a piece is not possible in castling. Hence checking the castling possitions are not necessary. 
				    * 
				    * */
				   var kingMovements = listOfPieces[i].getPossibleMovements(cells,false,false);
				   allPossibleMovements = allPossibleMovements.concat(kingMovements);
				   break;
				   
			   case Piece.Rook   : 				   
			   case Piece.Knight :
			   case Piece.Bishop :
			   case Piece.Pawn   :
			   case Piece.Queen  : 
				  var otherPiecesMovements = listOfPieces[i].getPossibleMovements(cells);
				  allPossibleMovements = allPossibleMovements.concat(otherPiecesMovements);
				  break;	
			   }
		      
		      return allPossibleMovements;    
		};
		
		/**
		* Returns whether the given cell lies in check.
		* @param cell where we have to find for check
        * @param teamColor Denotes the opponent team Color. The cell will be enquired for having check from the given Opponent team.   
		*/
		this.isInCheckLaneOf = function(teamColor,pos){
			
			/**
			 * List Of Pieces of opposite team.
			 * */
			 var listOfPieces = teams[teamColor].getListOfPieces();
			   
			   for( var i=0 ; i < listOfPieces.length ;  i++ )
				   switch(listOfPieces[i].getType()){
				   
				   	case Piece.Pawn   : 
				   	case Piece.King   :
					   
					   if( listOfPieces[i].couldConquerIfOppKingAvailableAt(pos) )
						   return true;
					   
				   	case Piece.Rook   : 				   
				   	case Piece.Knight :
				   	case Piece.Bishop :
				   	case Piece.Pawn   :
				   	case Piece.Queen  :  /*Returns whether the move is possible or not. Doesnt return the */
					  if(listOfPieces[i].couldMoveTo(pos,cells,teams)!=null) 
					   return true;
				   }
			   
			   //If all the cases fails
			   return false;
		};
		
		
		this.assignPiecesToInitialBoard = function(){
			
			function setPiecesInCells(teamColor){
				
				var listOfPieces = teams[teamColor].getListOfPieces();
				
				var forceRank = (teamColor == Team.White)? 1 : 8;
				var pawnRank   = (teamColor == Team.White)? 2 : 7;  
				
				var i = 0;
				for(var ch='a';ch != null; ch = Position.getNextChar(ch)){
					// Loop runs from col 'a' to col 'h' //since Javascript does not support increment of characters getNextChar is used...
					//console.log("I am here to set the pieces");
					getCellAt(ch,forceRank).setPieceID(listOfPieces[i].getPieceID());
					i++;
				}
				
				for(var ch='a';ch != null; ch = Position.getNextChar(ch)){ 
					getCellAt(ch,pawnRank).setPieceID(listOfPieces[i].getPieceID()); 
					i++;
				}
				
			};
			
			setPiecesInCells(Team.White);
			setPiecesInCells(Team.Black);

			//console.log("I am here after setting all the pieces" +"the queen" +cells[3][0].getPiece().getType());
			
		};
						
		/**
		 * @Description  Called for click move of the player.
		 * @param   
		 * 	pos  the Position of clicking.
		 *  currentPlayer  either white or black.
		 * @returns Either a move is done successfully by the current player, or it will return an Invalid move.
		 * 
		 * */
		this.makeMove = function(pos,currentPlayer){
			
			if(isPiecePicked){
				if(getCellAt(x,y).hasPiece(currentPlayer)){
					isPiecePicked = false;
					return [-1,"Invalid Move"];
				}
				else{
					
				}
				
			}
			else{
			}
  	  };
  	  
     	this.isCurrentTeamPieceExistsAt = function(pos,currentTeam){
  		
     		if ( this.hasPiece(pos) ){
     			if ( Team.getPieceByID(this.getCellAtPos(pos).getPieceID(),teams).getTeamColor() == currentTeam )
     				return true;			
     		}
     		return false;
     	};
  	
  	this.addPiece = function(pos,pieceType,turn){
  		switch(pieceType){
  		
  			case Piece.Queen:
  				getCellAtPos(pos).setPiece(new Queen(new Position(pos.getX(),pos.getY()),turn));
  				break;
  			case Piece.Rook:
  				getCellAtPos(pos).setPiece(new Rook(new Position(pos.getX(),pos.getY()),turn));
  				break;
  			case Piece.Bishop:
  				getCellAtPos(pos).setPiece(new Bishop(new Position(pos.getX(),pos.getY()),turn));
  				break;
  			case Piece.Knight:
  				getCellAtPos(pos).setPiece(new Knight(new Position(pos.getX(),pos.getY()),turn));
  				break;
  			default :
  				throw "Can Only add Queen,Rook,Bishop,Knight";
  		}
  		
  	};
  	
		/**
		 * Returns an array of possible movements like ['a1','b2','c3'] etc....
		 * On no possible movement, it will return array with single element [0]
		 * On Invalid move, it will return array with 2 elements [-1,logMessage]
		 * @param lastMove will be null on the first move.	
		 * lastMove is passed, only for the pawn's future reference for checking the specialMoveCapture.
		 * Otherwise lastMovePos is not necessary. 
		 * */
		this.getPossibleMovements = function(pos,lastMove){
			
			function removeCheckMovements(possibleMovements){
					
				
			}
			
			var x = pos.getX();
			var y = pos.getY(); 
			console.log("The before clicked x and y is .." + x + y );

			var piece =  Team.getPieceByID(getCellAt(x,y).getPieceID(), teams);
					
			//Returns the possible drop targets.... It may return [0] also.
			//Dynamically either rooks , or knights or bishops or etc.. any one piece dropTarget will be called!!!
			
			console.log("gonna call pawn click movement");
			
	        if(piece.getType() == Piece.King) //if the piece is king. (King has NO or INFINITE points. hence -1 Ref: king.js).
				//return removeCheckPositions( piece.getPossibleMovements(isLeftCastlingPossible(),isRightCastlingPossible(),/*isCaslingPossible()*/cells )) ;
				return  piece.getPossibleMovements(cells,teams,isLeftCastlingPossible.call(this,piece),isRightCastlingPossible.call(this,piece)) ;
			else //for other pieces..
				return piece.getPossibleMovements(cells,teams,lastMove);
			
		};
		
		/**
		 * Checks whether the given piece could move to the given pos.
		 * @param reqPos requested Position to check.
		 * @param lastMove it is additional and only pawn require that. 
		 * */
		this.couldMoveTo = function(reqPos,pickedPosition,lastMove){
			
			var piece = Team.getPieceByID( getCellAtPos(pickedPosition).getPieceID() ,teams);
			
			if ( piece.getType()  == Piece.King)
				return piece.couldMoveTo(reqPos,cells,teams,isLeftCastlingPossible.call(this,piece),isRightCastlingPossible.call(this,piece));
			else
			    return piece.couldMoveTo(reqPos,cells,teams,lastMove);
			
		};
		
};	


