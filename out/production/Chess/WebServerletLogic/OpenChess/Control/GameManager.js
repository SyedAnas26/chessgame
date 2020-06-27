
	this.GameManager = function()
	{
	    var playingTeam = Team.White; //initially its a white's turn.	    
	    
		var timer;
		var lastMove = null;
		var isPiecePicked; /* All the three variables are connected to each other, */ 
		var pickedPosition;   /* pickedPosition and possibleMovements will have a value only if isPiecePicked is true */		
		var possibleMovements; 
		
		console.log("I am here");
		var board = new Board();
		board.assignPiecesToInitialBoard();
		
		timer = new Timer();
		 
		/**
		 * Called to pick a piece.But before that it answers 2 questions. 
		 * Is the board has piece at Pos?, if so, whether it plays for current turn's team?
  		 *         turn  can be 0 or 1. 0 -> black's turn, 1 -> white's turn.
 	     * @returns  boolean: 0  not successfully picked. Either piece is not there or it is an opponents piece.
  		 *                    1  piece picked successfully. 
        */
		function pickThePiece(pos,turn){
			return board.isCurrentTeamPieceExistsAt(pos,turn);
		};
		
	/*
		/**
		 * Called when white to move
		 * Return  : The status of check-mate, if mated returns 1, else 0
		 * Args : pos -> The clicked cell's position. 
		 * 
		this.whitesMove = function(pos){
			do{
				result = makeMove(pos,1);
			}while(result != 1);
		
			if(isCheckMate(0)){
				console.log("White Wins. Black is in check mate!!!");
				return 1;
			}
			else
				return 0;
		};
		
		
		/**
	 * Called when black to move
		 * @returns The status of checkmate, if mated returns 1, else 0
		 * @param   pos  The clicked cell's position. 
		 
		this.blacksMove = function(pos){
			do{
				result = makeMove(pos,0);
			}while(result != 1);
		
			if(isCheckMate(1)){
				console.log("White Wins. Black is in check mate!!!");
				return 1;
			}
			else
				return 0;
		};
		*/
				
		/**
		 * Called when a cell is clicked on the display board.
		 * @Args clickedId  - the id of the chess_board table's clicked td.
		 * @Return  
		 * [0,null]  -> piece successfully picked. </br>
		 * [1,moveListForUI]  -> some move successfully made, the moveListForUI contains the move that front user interface has to make in the board. </br>
		 * [-1,null] -> invalid request!! illegal move; </br>
		 * 
		 * */
		this.cellClicked = function(clickedId){
			var movesLogForUI = new Array();
			var x = clickedId[0];
			var y = clickedId[1];
		
			/**
			 * Used to add a new piece to the chess board.
			 * @param Position contains position where piece is to be added. </br> 
			 * But, the position is already available with us in lastMove.get_toPos() </br>
			 * Still, to improve re usability  and adoptability pos is added as argument, because3 if the same module is taken for chinese chess, there rules may be quite different. </br>
			 *  @param pieceType contains type of the piece (like rook or pawn or bishop etc..) 
			 * */
			this.addPiece = function(pos,pieceType){
				board.addPiece(pos,pieceType,playingTeam); /*!turn -> Because the pawn up-gradation will be of previous team move.*/
			};
			
			/**
			 * Will be called by UI_Manager to trigger the turn change. */
			function togglePlayingTeam(){
				playingTeam = playingTeam == Team.White? Team.Black : Team.White; 				
			};
						
			/** Used to get the current team that is playing.
			 * @returns Team.White or Team.Black.			 * 
			 * */
			function getPlayingTeamColor(){
				return playingTeam;
			};
				
			/** Used to get the opponent team that is playing.
			 * @returns Team.White or Team.Black.			 * 
			 * */
			function getOpponentTeamColor(){
				return Team.getOpponentTeamColor (getPlayingTeamColor());
			};
			
			function logPossibleMovements(possibleMovements){
				for( var i = 0 ;i < possibleMovements.length ; i++ )
					    console.log( " " + possibleMovements[i].get_toPos().toString() + " " ) ;
			}
		
			/**
			 * Checks whether the king is in check in the given position. 
			 * */
			function isKingInCheck(king){				
				return board.isInCheckLaneOf(Team.getOpponentTeamColor(king.getTeamColor()),king.getPosition());
			};
			
			function isKingInCheckMate(king){
				/*Returns false if the king is not in check.*/
				if(!isKingInCheck(king))
					return false;
				
				/*
				 * isLeftCastlingPossible(listOfPieces[i]) , isRightPossible(listOfPieces[i]) is taken as false.
				 * Because, we here want to check whether our cell is in check by other pieces,
				 * i.e we want to check whether our piece could be captured by opponent, if we place at the checking cell.
				 * Capturing a piece is not possible in castling. Hence checking the castling positions are not necessary. 	
				 * */
				var allPossibleMovementsOfKing = king.getPossibleMovements(board.getAllCells(),board.getTeams(),false,false);
				
				/*
				 * for(var possiMovement in allPossibleMovementsOfKing )
					if ( !board.isInCheckLaneOf(king.getTeamColor(),possiMovement.get_toPos()) )
						return false; // Return false, if the king is not in the check at at least one possible Movement.
				*/
				
				var i = 0;
				for( i = 0 ; i < allPossibleMovementsOfKing.length ; i++)
					if ( !board.isInCheckLaneOf(king.getTeamColor(),allPossibleMovementsOfKing[i].get_toPos()) )
						return false; // Return false, if the king is not in the check at at least one possible Movement.
						
				var allPossibleMovements = board.getAllPossibleMovementsOfAllPieces(king.getTeamColor());
				
				
				/* A duplicate copy of the board. */
				var dupBoard = board.deepClone();
				
				
				for(var possiMovement in allPossibleMovements ){
					
					board.movePiece(possiMovement);
					if ( isKingInCheck(king) == false){
						board = dupBoard; /*Making the board to be an old board every time of the move to cancel, the previous move.*/
						return false;
					}
					
					board = dupBoard; /*Making the board to be an old board every time of the move to cancel, the previous move.*/
				}
				
				/*Returning that the king is in Check mate*/
				return true;
			};
					
			function isKingInStaleMate(king){
				
				if(!isKingInCheck(king)) //For stale mate the king must not be in check in current position.
					return false;
				
			    /*
			    * isLeftCastlingPossible(listOfPieces[i]) , isRightPossible(listOfPieces[i]) is taken as false.
			    * Because, we here want to check whether our cell is in check by other pieces,
			    * i.e we want to check whether our piece could be captured by opponent, if we place at the checking cell.
			    * Capturing a piece is not possible in castling. Hence checking the castling positions are not necessary. 
			    * */
				var allPossibleMovementsOfKing = king.getPossibleMovements(board.getAllCells(),false,false);
			
				var i = 0;
				for( i = 0 ; i < allPossibleMovementsOfKing.length ; i++)
					if ( !board.isInCheckLaneOf(king.getTeamColor(),allPossibleMovementsOfKing[i].get_toPos()) )
						return false; // Return false, if the king is not in the check at at least one possible Movement.
				
				var allPossibleMovements = board.getAllPossibleMovementsOfAllPieces(king.getTeamColor());
			
				if(allPossibleMovements == null)
					return true;
				else 
					return false;
			};
		
    		if(!isPiecePicked){
    				console.log("inside piece picker");
					  var clickedPosition = new Position(x,y);
					  var pickResult = pickThePiece(clickedPosition,getPlayingTeamColor()); //Pick the white piece;
					  if(pickResult == 1){
						  isPiecePicked = true;
						  pickedPosition = clickedPosition;
						  console.log("I am in game manager. getPossibleMovements called");
					  	  possibleMovements = board.getPossibleMovements(pickedPosition, lastMove);
					  	  
					  	  /*Removable*/
					  	  logPossibleMovements(possibleMovements);
					  	  
					  	  return [0,null]; 
					  }
					  else{
						 console.log("Invalid Move  : current Turn  : "  +  (getPlayingTeamColor()?"white" : "black"));
						 return [-1,null];
					  }
				  }
				  else{
					  console.log("inside piece mover");
					  var requestedPos = new Position(x,y);
					 
					
					  //Except pawn all other pieces will ignore the lastMove.
					  var reqMove = board.couldMoveTo(requestedPos,pickedPosition,lastMove);
					  
					  if ( reqMove != null) {
						  
						  /* A duplicate copy of the board. */
						  var dupBoard = board.deepClone();
						  
					  	  board.movePiece(reqMove);					  	  
					 
					  	  var currentTeamKing = board.getTeamHandle(getPlayingTeamColor()).getPiece(Piece.King) ;
					  	 
						  if ( isKingInCheck( currentTeamKing ) ){
							  
								  alert("Invalid move since the king is in Still check. Protect king first.");
								  board = dupBoard; /*Making the board to be an old board, the previous move.*/
								  resetPicking();// The move ended up in failure, the board is made ready for next move by backtracking.
								  return [-1,null];
						  }								
						  

						  movesLogForUI.push(reqMove);
						  
						  var consequenceMoves = board.makeConsequenceMoves(reqMove);
						  
						  /*If any consequence moves are available, then append it!!!*/
						  if(consequenceMoves != null)
						  {
							  for( var i=0 ; i<consequenceMoves.length ; i++ )
								  movesLogForUI.push( consequenceMoves[i] );
						  }
						  
						  lastMove = reqMove;
						  console.log( reqMove.toString() );						  						 
						  						  
						  var currentTeamKing = board.getTeamHandle(getPlayingTeamColor()).getPiece(Piece.King) ;
					      var opponentTeamKing = board.getTeamHandle(getOpponentTeamColor()).getPiece(Piece.King);
					      
					      var currentTeamName     = board.getTeamHandle(getPlayingTeamColor()).toString();
					      var opponentTeamName    = board.getTeamHandle(getOpponentTeamColor()).toString();
					      
						  //Since this is for checking the opponent team king., opposite teamKing is traced from currentTeamKing and it is send to the isKingInCheckMate
						  if ( isKingInCheckMate(opponentTeamKing))
							  console.log("Match over. " + currentTeamName + " wins. The king is in checkmate");
						  
						  else if ( isKingInCheck(opponentTeamKing))
						     alert("The " + opponentTeamName + " is in check");
							  						 
						  else if( isKingInStaleMate(opponentTeamKing))
							  console.log("Match drawn. " + opponentTeamName + " king is in statemate");
						  
						  
					      resetPicking(); //On successful move, the board is made ready for next move.
						  togglePlayingTeam(); 
						  return [1,movesLogForUI];
						  
					  }
					  resetPicking();// The move ended up in failure, the board is made ready for next move by backtracking.
					  return [-1,null];  
				  }
    		
    		/**
    		 * Resetting the picked details.., since on a wrong move, the selection highlighting will disappear.
    		 * */
    		 function resetPicking(){
				  isPiecePicked =  false;
				  pickedPosition = null;
    		 };
				    
		};
};
