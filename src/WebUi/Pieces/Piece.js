/**
 * Used to denote any piece, in the board.
 * @param pos  Denotes current pos of the piece
 * @param team denotes for which team, the piece is playing for. 
 *        pieceIDArg,isLiveArg is not mandatory. 
 *        The other fields are mandatory.
 * */
	Piece = function(posArg,teamArg,typeArg,pieceIDArg,isLiveArg)
	{
		
		/**
		 * @type boolean
		 * */
		var team = teamArg;
		
		/**
		 * @type integer.
		 * */
		var points;
		
		/**
		 * @type integer.
		 * */
		var pieceID = pieceIDArg!==undefined? pieceIDArg :  (Piece.pieceID =  Piece.pieceID === undefined? 0 : Piece.pieceID + 1);
		
		/**
		 * Denotes the type of the piece.
		 * @type Integer.(Constant Integer from function Piece. ( E.g ) Piece.Rook)
		 * */
         var type = typeArg;

        /**
         * Tells whether the piece exist or not in the board
         * */
        var isLive = isLiveArg || true ;
        
    	
    	/**
    	 * @type Position
    	 * */
		var position =  posArg;
		
		switch(typeArg){
		 
			case Piece.Pawn   :  points = 1; break; // Pawn has 1 point.....
					
			case Piece.Rook   :  points = 5; break; // Rook has 5 points.....
				
			case Piece.Knight :  points = 3; break; // Knight has 3 points.....
		
			case Piece.Bishop :  points = 3; break; // Bishop has 3 points.....
		
			case Piece.Queen  :  points = 9; break; // Queen has 9 points.....
		
			case Piece.King   :  points = -1; break; //King has no points... hence points for king is -1.
			
        }
        
		/**
		 * Returns the information of what the piece is playing for
		 */
		this.getTeamColor = function(){
			return team;
		};
		
		/**
		 * Returns the points of the piece accordingly.
		 *  
		 * */
		this.getType = function(){
			return type;
		};
		
     	/**
		 * The PieceID has no setter method. 
		 * */
		this.getPieceID = function(){
			return pieceID;
		};
		
		this.getPoints = function(){
			return points;
		};

		/**
		 * A shortcut function to access pos.getX easily.
		 * Example, a pawn could access the x as -> piece.getX() instead of piece.getPosition().getX();
		 * */
		this.getX = function(){
			return position.getX();
		};
		
		/**
		 * A shortcut function to access pos.getY easily.
		 * Example, a pawn could access the y as -> piece.getY() instead of piece.getPosition().getY();
		 * */
		this.getY = function(){
			return position.getY();
		};
		
		/**
		* Getter for Positions.
	        * */
		this.getPosition = function(){
		   return position;
	 	};
		
		/**
		 * Setter for Positions.
		 * */
		this.setPosition = function(posArg){
			position = posArg.deepClone();
	 	};
			
	    this.killPiece = function(){
		   isLive = false;
		};
				
		this.deepClone = function(){
			return new Piece(position.deepClone(),team,type,pieceID,isLive);
		};
	};
	
	Piece.Pawn = 0;
	
	Piece.Rook = 1;
	
	Piece.Knight = 2;
	
	Piece.Bishop = 3;
	
	Piece.Queen = 4;
	
	Piece.King = 5;
	
	