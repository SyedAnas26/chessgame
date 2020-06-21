/**
 * 
 */

/**
 * The default storage format is x,y is numeric x, numeric y. 
 * However to provide flexibility constructor will allow charector names for columns.
 * */

Position = function(X,Y)
{
	var x;
	var y;
  
	/**
	 * In Default it will return the numeric x.
	 * */		
    this.getX = function(){
    	return x;			
    };
    
    function isEqualsXY(x1,y1){
    	return x==x1 && y==y1;
    };

    this.isEqualsXY = function(x1,y1){
    	return x==x1 && y==y1;
    };
    
    this.isEqualsPos = function(pos){
       return isEqualsXY(pos.getX(),pos.getY());	
    };
    
	this.getNumericXval = function(){
		return x;		
	};
	
	function getAlgebraicX(x){
	      return  String.fromCharCode('a'.charCodeAt(0) + x );  	
	};
	
	this.getAlgebraicXval = function(){
		return getAlgebraicX(x);
	};
	
	
	this.getY = function(){
		   return y;
	};
	
	
	/**
	 * @returns Returns the relative position.
	 * @Example ('a',1) -> relatively ('a',1).getRelativePosition(+1,+1) will return ('b',2);<br\>
	 * 			where ('a',1).getRelativePosition(-1,+1) will return null;<br\>			  
	 * */
	this.getRelativePosition = function(x1,y1){
		if ( Position.isValidPosition(x+x1, y+y1))
			return new Position(x+x1,y+y1);
		else
			return null;
	};
	
	this.deepClone = function(){		
		 return new Position(x,y);
	};   
	
	/**
	 * Returns the string version of the position.
	 * @example toString(3,5) will return " d5 "
	 * Why y+1? instead of y? 
	 * The piece is in (x,y). That's okay.
	 * But, in algebraic notation  ('a',1), will be equivalent to numeric notation of (0,0). Note the difference of y there.
	 * Since, y is in numeric notation, and we are here using algebraic notation, this 1 lag is there.  
	 * Same thing is followed here.
	 * */
	this.toString = function(){
			 return getAlgebraicX(x) + (y+1);
	};
	
	/**
	 * It stores only the numeric format of the given number.
	 * */
			
	if( Position.isValidPosition(X, Y) ) { //Constructor...
		/**
		 * Since we are using 2 different formats, for giving compatibility to both formats.
		 * @see Algrebric noation and Numeric notation comments in <table id="chess_board"..> in index.html 
		 * */
		  if($.isNumeric(X)){
			x = X;
			y = Y;
		  }
		  else{
			x = Position.getNumericX(X);
			y = Y -  1;
		  }
	}
	else
		throw "Invalid Position Arguments. Ensure you notation formats.."  + X + Y;

};

/**All about notation..
    *  a b c d e f g h
    *  0 1 2 3 4 5 6 7
    *  These functions are to convert into one to another form..
*/  

Position.getNumericX = function(i){ 	
		//console.log("You could see me i :  " +  i  +" I am in getNumeric X");
		if(!$.isNumeric(i) && Position.isValidPosition(i,1/*Dummy Data to validate x. NOTE: y in algebraic format ranges from 1 to 8 only.*/)){
		  return i.charCodeAt(0) - 'a'.charCodeAt(0);
		}
		else throw "Invalid input in numeric" + i;  
};

Position.getAlgebraicX = function(i)
{
	if($.isNumeric(i) && Position.isValidPosition(i,0/*Dummy Data to validate x. NOTE: y in numeric format ranges from 0 to 7 only.*/))
		  return String.fromCharCode('a'.charCodeAt(0) + i);
		else throw "Invalid input in Algebraic" + i;	
};

/**
 * Returns whether the given position is valid or not in the board. 
 * */ 
Position.isValidPosition = function(X,Y){
	if($.isNumeric(X)){
		if( ( (0 <= X) && (X <= 7) ) &&  ( (0 <= Y) && (Y <= 7) ) ) 
		return 1;
	}
	else{
		if( ( ('a' <= X) && (X <= 'h') ) &&  ( (1 <= Y) && (Y <= 8) ) )		
		return 1;
	}
	
	return 0; //if validation fails

};

/**
 * @return the next possible charector for the row positioning (i.e) will return 'b' on 'a', 'c' on 'b' and so on... <br/>
 * 		   If the char is 'h', then it must be the last charector, hence a null will be returned, denoting that no char is possible.<br/>	
 *         This will be particularly useful for the for loops. 
 * */
Position.getNextChar = function(ch) {
	if( ( 'a' <= ch ) &&  ( ch <= 'g' ) ) 
		return String.fromCharCode(ch.charCodeAt(0) + 1);
	else if( ch == 'h')
		return null;
	else throw "INVALID CHAR EXCEPTION. Calling for the char : " + ch + ",which is not in the range of 'a' to 'h' ";
};





