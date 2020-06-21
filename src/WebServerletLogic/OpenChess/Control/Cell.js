                                                                       
	Cell = function(pos)
	{
		var hasPiece = false;
		var pieceID = null;
		var cellID = Cell.cellID =  ++Cell.cellID ||  0;
		
	  this.isInEndRank = function(team){
	   	if(team == Team.White)
	   		 if( pos.getY()==7)
	    			 return true;
	    		 else
	    			 return false;  
	   	else //Color will be 0 
	    		 if( pos.getY()==0)
	    			 return true;
	    		 else
	    			 return false;
	};
	    
    this.setPieceID = function (pieceIDArg){
    	    if(pieceIDArg!=null){
    	    	hasPiece = true;
    	    	pieceID  = pieceIDArg;
    	    	//console.log("The pieceArg value at setpiece is " + pieceArg + pos.toString());
    	    }
    	    else{
    	    //	console.log(" I am inside pieceArg is null : The pieceArg value at setpiece is " + pieceArg + pos.toString());
    	    	hasPiece = false;
    	    	pieceID  = null;
    	    }
	};
    
	    
	this.getPieceID = function(){
		
		//console.log("The piece Value at getPiece is "  + piece + pos.toString());
		
		if(hasPiece){
			return pieceID;
		}
		else{
			return null;
		}
	};
	
	
	/**
	 * Returns whether the corresponding cell has piece or not
	 * @returns 0 if no piece, 1 if any piece is available.
	 * */
	this.hasPiece = function(){
	    	return hasPiece;
	};
	
	this.deepClone = function(){
		   var cell = new Cell(pos.deepClone());
		   cell.setPieceID( this.hasPiece()? pieceID : null); // The hasPiece parameter will be initialized inside setPiece itself. see @Cell.SetPiece for implementation. 
		   
	   return cell;
	};
		    
};
	


