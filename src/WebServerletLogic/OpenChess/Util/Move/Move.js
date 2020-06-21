function Move(fromPosArg,toPosArg,moveCodeArg){
	
	var fromPos; 
	var toPos ;
	
	/*
	 * FromPosArg could be null, during adding a piece to the board. 
	 * */
	if(fromPosArg == null)
		fromPos = null;
	else
		fromPos = new Position(fromPosArg.getX(),fromPosArg.getY());
	
	/*
	 * FromPosArg could be null, during deleting a piece to the board. 
	 * */
	if(toPosArg == null)
		toPos = null;
	else
		toPos = new Position(toPosArg.getX(),toPosArg.getY());
	
	var moveCode = moveCodeArg;
	
	this.get_fromPos = function(){
		return fromPos;
	};
	
	this.get_toPos = function(){
		return toPos;
	};
	
	this.getMoveCode = function(){
		return moveCode;
	};
	
	this.toString = function(){
		
		var str =  "[From] " + fromPos.toString() + " [To] " + toPos.toString() + "[MoveCode] " ;
		switch(moveCode){
		
			case MoveCode.NormalMove:
					str += "MoveCode.NormalMove";
					break;
			case MoveCode.DoubleStepPawn :
					str += "MoveCode.DoubleStepPawn";
					break;
			case MoveCode.SpecialPawnCaptureLeft:
					str += "MoveCode.SpecialPawnCaptureLeft";
					break;
			case MoveCode.SpecialPawnCaptureRight:
					str += "MoveCode.SpecialPawnCaptureRight";
					break;
			case MoveCode.PawnUpgrade:
					str += "MoveCode.PawnUpgrade";
					break;
			case MoveCode.LeftCastling:
					str += "MoveCode.LeftCastling";
					break;
			case MoveCode.RightCastling:
					str += "MoveCode.RightCastling";
					break;

		}
	
		return str;
	};
	
};

