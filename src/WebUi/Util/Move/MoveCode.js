function MoveCode(){
	 
};

/**
 * Normal moves which does not go out of its own rules.
 * I.e Rules for pawn is move 1 step or capture front-side pieces (if any).
 * So for a Pawn,
 * Normal Moves: 
 *   1. Move 1 step ahead
 *    2. Side capture pawn which presents in 1 step diagonal  
 * 
 * Abnormal or Special Moves :
 *   1. Special pawn capture  at f6,  when it moves from e6 to f7.
 *   2. Pawn Upgradation at the end rank(row).
 *   
 *   
 * So for a King :
 * Normal Moves:
 *   1. Move 1 step in any adjacent boxes.
 *   
 *  Abnormal or Special Moves :
 *   1. Left or Right Castling.
 *   
 *Note :
 *Other than these, no pieces have any special moves. All the other moves comes under normal moves.
 *   
 *   
 */
MoveCode.NormalMove = 0;


/**
 * 
 * Abnormal moves.!! 
 */ 

MoveCode.DoubleStepPawn = 1;

MoveCode.SpecialPawnCaptureLeft = 2;

MoveCode.SpecialPawnCaptureRight = 3;

MoveCode.PawnUpgrade = 4;

MoveCode.LeftCastling = 5;

MoveCode.RightCastling = 6;


/**
 * They are not user Moves. Rather, the chess board will add or remove piece on other moves like specialPawnCaptureRight.
 * */

MoveCode.AddPiece = 6;
MoveCode.KillPiece = 7;
