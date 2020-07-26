
	this.GameManager = function()
	{
	    const { Chess } = require('./chess.js')
        const chess = new Chess()

	    var playingTeam = Team.White; //initially its a white's turn.
	    
		var timer;
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

		this.getCurrentTeam=function () {
			return playingTeam;
		};

		this.cellClicked = function(clickedId){
			var movesLogForUI = new Array();
			var x = clickedId[0];
			var y = clickedId[1];

			/**
			 * Will be called by UI_Manager to trigger the turn change. */
			function togglePlayingTeam(){
				playingTeam = playingTeam==Team.White? Team.Black: Team.White;
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
			     return false;
				//return board.isInCheckLaneOf(Team.getOpponentTeamColor(king.getTeamColor()),king.getPosition());
			};

			function isKingInCheckMate(king){
				/*Returns false if the king is not in check.*/
				if(!isKingInCheck(king))
					return false;

				/*Returning that the king is in Check mate*/
				return true;
			};

			function isKingInStaleMate(king){

				if(!isKingInCheck(king)) //For stale mate the king must not be in check in current position.
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

						  // //Since this is for checking the opponent team king., opposite teamKing is traced from currentTeamKing and it is send to the isKingInCheckMate
						  // if ( isKingInCheckMate(opponentTeamKing))
							//   console.log("Match over. " + currentTeamName + " wins. The king is in checkmate");
						  //
						  // else if ( isKingInCheck(opponentTeamKing))
						  //    alert("The " + opponentTeamName + " is in check");
							//
						  // else if( isKingInStaleMate(opponentTeamKing))
							//   console.log("Match drawn. " + opponentTeamName + " king is in statemate");
						  //

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

    		 };

		};
};
