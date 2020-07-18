<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.*"%>
<html>
<head>
    <script>
        history.forward();
    </script>
</head>
<!-- Order of inclusion : Library -> Utilities -> Pieces -> Controls -->

<script type="text/javascript" src="../clientlib/jquery.min-3.5.js"></script>
<script type="text/javascript" src="../clientlib/jquery-ui.min.js"></script>

<script type="text/javascript" src="../Util/Move/MoveCode.js" ></script>
<script type="text/javascript" src="../Util/Move/Move.js" ></script>
<link rel="stylesheet" type="text/css" href="../Util/Theme/Default.css">

<script type="text/javascript" src="../Util/Position.js" ></script>
<script type="text/javascript" src="../Util/InitPos.js" ></script>
<script type="text/javascript" src="../Util/Copy.js" ></script>

<script type="text/javascript" src="../Pieces/Piece.js" ></script>

<script type="text/javascript" src="../Pieces/Pawn.js" ></script>
<script type="text/javascript" src="../Pieces/Rook.js" ></script>
<script type="text/javascript" src="../Pieces/Knight.js" ></script>
<script type="text/javascript" src="../Pieces/Bishop.js" ></script>
<script type="text/javascript" src="../Pieces/Queen.js" ></script>
<script type="text/javascript" src="../Pieces/King.js" ></script>

<script type="text/javascript" src="../Pieces/Team.js" ></script>

<script type="text/javascript" src="../Control/Cell.js" ></script>
<script type="text/javascript" src="../Control/Board.js" ></script>
<script type="text/javascript" src="../Control/Timer.js" ></script>
<script type="text/javascript" src="../Control/GameManager.js" ></script>




<script type="text/javascript">


    function UI_Manager(){

        var gameManager = new GameManager;
        /*
         * The current clicks and last two clicks are continuously tracked only for highlighting purposes.
         */
        var lastClickAttrId = null;
        var lastButOneClickAttrId = null;
        var currentClickAttrId = null;
        var playingTeam=gameManager.getCurrentTeam;
        /*
         * MoveCycle -> A complete cycle of picking and placing a piece from one position to another position.
         * The cycle may contain steps like picking*,placing. picking?->any n no of picking, where n > 0
         * (i.e)
         * This variable will be made true, for every successful one move, after that if the next move is started,
         * then again this variable will remain false, until the second move is successfully finished.
         * Then after second move again the variable is set to true, and this cycle continues for every move.
         */

        var hasMoveCycleCompleted = false;

        //Function definitions starts here...
        this.getPlayingTeam=function () {
            return playingTeam;

        };

        this.cellClickedAt = function(currAttrId){

            if(hasMoveCycleCompleted){
                removeHighlight(lastButOneClickAttrId);
                removeHighlight(lastClickAttrId);
                hasMoveCycleCompleted = false;
            }

            currentClickAttrId = currAttrId;

            clickResponseFromGameManager();

            lastButOneClickAttrId = lastClickAttrId;
            lastClickAttrId = currAttrId;

        };

        /**
         * Converts the position to Attribute Id, which could be used at the front end.
         */
        function getAttrID(pos){
            return pos.toString();
        }


        /**
         *  Dynamically generates element Id, based on the user selection.
         *
         *
         */
        function generatePieceID(pieceType,currentTeam){

            var newElement_id;

            switch(pieceType){
                case Piece.Queen:
                    id = "queen";
                    break;
                case Piece.Rook:
                    id = "rook";
                    break;
                case Piece.Bishop:
                    id = "bishop";
                    break;
                case Piece.Knight:
                    id = "knight";
                    break;
            }

            switch ( currentTeam ){
                case Team.Black:
                    id += "_black";
                    break;

                case Team.White:
                    id += "_white";
                    break;

            }

            return id;
        };

        function moveIcon(moveList){

            for( var i = 0 ; i < moveList.length ; i++){

                if(moveList[i].get_fromPos() == null && moveList[i].get_toPos() == null)
                    throw "Invalid positions for move in User Interface. Both positions are null";

                if(moveList[i].get_fromPos() == null){
                    /*Means request to add a new piece*/
                    //popup();
                    console.log("Hurray!!! I am here at get_fromPos");
                    var pieceType = Piece.Queen;
                    gameManager.addPiece(moveList[i].get_toPos() , pieceType);

                    var newPieceID = generatePieceID(pieceType,gameManager.getCurrentTurn());
                    gameManager.triggerChangeTurn();

                    $('#' + getAttrID( moveList[i].get_toPos() ) ).html($('#' + newPieceID ).html());


                }
                else if(moveList[i].get_toPos() == null){
                    /*Means a request to remove a piece from FromPos*/
                    console.log("Hurray!!! I am here at get_toPos");
                    $('#' + getAttrID( moveList[i].get_fromPos() ) ).html("");
                }
                else{
                    /*Means a request to move a piece from one pos to another.*/
                    $('#' + getAttrID( moveList[i].get_toPos() ) ).html($('#' + getAttrID( moveList[i].get_fromPos() ) ).html());
                    $('#' + getAttrID( moveList[i].get_fromPos() ) ).html("");
                }

                console.log(getAttrID( moveList[i].get_fromPos())+getAttrID( moveList[i].get_toPos() ))

                       $.ajax({
                           url: "http://localhost:8080/usermoves",
                           type: 'POST',
                           data: {"fromMove": getAttrID( moveList[i].get_fromPos()), "toMove": getAttrID( moveList[i].get_toPos() )},
                           success: function () {
                               console.log('ajax success!!');
                           },
                           error: function (jqXHR, exception) {
                               console.log('Error occured!!');
                           }
                       });

                console.log( " fromAttrId , toAttrId  is " +  ( moveList[i].get_fromPos()==null? null : getAttrID( moveList[i].get_fromPos() ) )+ ","+ ( moveList[i].get_toPos()==null? null : getAttrID( moveList[i].get_toPos() ) )) ;
            }

            // $('#d3').html($('#d2').html());
            // $('#d2').html("");

        };

        function addLightHighlight(attrId){
            $('#' + attrId).addClass("yellowLightHighlight");
        }

        function addDarkHighlight(attrId){
            $('#' + attrId).addClass("yellowDarkHighlight");
        }

        function removeHighlight(attrId){
            $('#' + attrId).removeClass("yellowDarkHighlight");
            $('#' + attrId).removeClass("yellowLightHighlight");
        }

        /*Changes the highlight from dark to light color.*/
        function changeHighlight(attrId){
            $('#' + attrId).removeClass("yellowDarkHighlight");
            $('#' + attrId).addClass("yellowLightHighlight")
        }

        function clickResponseFromGameManager(){
            var responseStatus_moveList  = gameManager.cellClicked(currentClickAttrId);
            var responseStatus = responseStatus_moveList[0];
            var moveList =  responseStatus_moveList[1];

            switch( responseStatus ){

                case -1: // Remove the highlight of the lastMoveAttrId.
                    console.log(" I am here in UI Manager drop the picked piece, due to invalid move");
                    removeHighlight(lastClickAttrId);
                    return;

                case 0 : //Make hightlight.
                    console.log(" I am here in UI Manager to pick the piece");
                    addDarkHighlight(currentClickAttrId);
                    return;

                case 1 : //Move the piece_icon from lastMoveAttrId to thisClickAttrId, remove icon previously present in thisClickAttrId ( if any ).
                    console.log(" I am here in UI Manager to move");
                    moveIcon(moveList);
                    addLightHighlight(currentClickAttrId); // Hightlight with dark color.
                    //changeHightlight(lastClickAttrId); // Changes the highlight from dark to light color.
                    hasMoveCycleCompleted = true;
                    return;
                /* The return value of cellClicked will never be other values than -1,0,1 */
            }
        };


    }; //End of UI_Manager.

    UI_Manager.cellGridHtmlGenerator = function(){
        var cellGridHtml;

        for(var i = 8 ; i >= 1 ; i--){

            cellGridHtml +=  "<tr>";

            //Giving the number infront of the left col*/
            cellGridHtml +=  '<td class="boardNumbering" id=' + i +'>' + i + '</td>'

            for(var j = 'a'  ; j != null ; j=Position.getNextChar(j) )
                cellGridHtml +=  '<td class="chess_squares" id=' + (j + i)  + '></td>';
            cellGridHtml +=  "</tr>";
        }

        /*Giving the number below the bottom rank*/
        cellGridHtml +=  "<tr>";
        cellGridHtml +=  '<td class="chess_numbering" id=Number0>WebChess</td>';

        for(var j = 'a'  ; j != null ; j=Position.getNextChar(j) )
            cellGridHtml +=  '<td class="chess_numbering" id=Number' + j  + '>' +   j  + '</td>';
        cellGridHtml +=  "</tr>";

        return cellGridHtml;
    };

    function assignIconsToCellGrids(){
        //The initial Positions are hard cored in the PlayGame.jsp file //Loading the initial positions...
        //This position starts from 11 and ends in 88 since nth-child parameter is in that way....

        for(var ch='a'; ch !=null ; ch = Position.getNextChar(ch)){
            $('#' + ch + 2).html($('#pawn_white').html());
            $('#' + ch + 7).html($('#pawn_black').html());
        }

        /*
            a8 b8 c8 d8 e8 f8 g8 h8
          a7 b7 c7 d7 e7 f7 g7 h7
          a6 b6 c6 d6 e6 f6 g6 h6
          a5 b5 c5 d5 e5 f5 g5 h5
          a4 b4 c4 d4 e4 f4 g4 h4
          a3 b3 c3 d3 e3 f3 g3 h3
          a2 b2 c2 d2 e2 f2 g2 h2
          a1 b1 c1 d1 e1 f1 g1 h1
        */

        /*
        *  RegExp to match any id in any jquery without containing '(' or ')' : \'[^(\.)]*\'
        *  Match for special jquery like  #chess_board tr:nth-child(1)  td:nth-child(7) -->  \'[\(\)||[^(\.)]]*\'
        */

        $('#a8').html($('#rook_black').html());
        $('#b8').html($('#knight_black').html());
        $('#c8').html($('#bishop_black').html());
        $('#d8').html($('#queen_black').html());
        $('#e8').html($('#king_black').html());
        $('#f8').html($('#bishop_black').html());
        $('#g8').html($('#knight_black').html());
        $('#h8').html($('#rook_black').html());

        $('#a1').html($('#rook_white').html());
        $('#b1').html($('#knight_white').html());
        $('#c1').html($('#bishop_white').html());
        $('#d1').html($('#queen_white').html());
        $('#e1').html($('#king_white').html());
        $('#f1').html($('#bishop_white').html());
        $('#g1').html($('#knight_white').html());
        $('#h1').html($('#rook_white').html());

    };


    $("#chess_board").ready(function() {

        var uiManager = new UI_Manager;
        var PlayingTeam=Team.White;
        /**
         *	 This jquery call, ill dynamically add chess grids to chess board @link UI_Manager.cellGridHtmlGenerator.
         */
        $("#chess_board").html(UI_Manager.cellGridHtmlGenerator());
        assignIconsToCellGrids();
        if(PlayingTeam===Team.Black){
            console.log("Inside ai Player");
            var Callai =new CallAi;
            Callai.moveAi;
            $.ajax({
                url: "http://localhost:8080/aiMove",
                type: 'POST',
                success: function(response) {
                    uiManager.cellClickedAt(response.from_pos);
                    uiManager.cellClickedAt(response.to_pos);
                    PlayingTeam=Team.White;
                }
            });
        }


            $(".chess_squares").click(function () {
                uiManager.cellClickedAt($(this).attr('id'));
                console.log("Some successful response for clicking");
                PlayingTeam=Team.Black;
            });


        $(".claimed_draw").click(function(){
            $.ajax({
                url: "http://localhost:8080/drawclaim",
                type: 'POST',
                data: {"claimDraw":"1"},
                success: function () {
                    alert("Match Draw");
                    window.location='http://localhost:8080/homepage';
                    console.log('draw ajax success!!');
                },
                error: function (jqXHR, exception) {
                    console.log('draw ajax Error occured!!');
                }
            });
        });

    });


</script>

<head>
    <style type="text/css">
        a {
            color:#000;
            display:block;
            font-size:60px;
            height:80px;
            position:relative;
            text-decoration:none;
            text-shadow:0 1px #fff;
            width:80px;
        }

        #chess_board { border:5px solid #333; }
        #chess_board td {
            background:#fff;
            background:-moz-linear-gradient(top, #fff, #eee);
            background:-webkit-gradient(linear,0 0, 0 100%, from(#fff), to(#eee));
            box-shadow:inset 0 0 0 1px #fff;
            -moz-box-shadow:inset 0 0 0 1px #fff;
            -webkit-box-shadow:inset 0 0 0 1px #fff;
            height:80px;
            text-align:center;
            vertical-align:middle;
            width:80px;
        }

        #chess_board tr:nth-child(odd) td:nth-child(even),
        #chess_board tr:nth-child(even) td:nth-child(odd) {
            background:#ccc;
            background:-moz-linear-gradient(top, #ccc, #eee);
            background:-webkit-gradient(linear,0 0, 0 100%, from(#ccc), to(#eee));
            box-shadow:inset 0 0 10px rgba(0, 0, 0, 0.4);
            -moz-box-shadow:inset 0 0 10px rgba(0,0,0,.4);
            -webkit-box-shadow:inset 0 0 10px rgba(0,0,0,.4);
        }
    </style>
</head>

<body>

<div style="display: none" id="chess_pieces">

    <div id="queen_white">
        <a href="#"><img src="../Util/PieceImages/queen_white.png"/></a>
    </div>
    <div id="king_white">
        <a href="#"><img src="../Util/PieceImages/king_white.png"/></a>
    </div>
    <div>
        <a href="#" id="rook_white"><img src="../Util/PieceImages/rook_white.png"/></a>
    </div>
    <div>
        <a href="#" id="bishop_white"><img src="../Util/PieceImages/bishop_white.png"/></a>
    </div>
    <div>
        <a href="#" id="knight_white"><img src="../Util/PieceImages/knight_white.png"/></a>
    </div>
    <div>
        <a href="#" id="pawn_white"><img src="../Util/PieceImages/pawn_white.png"/></a>
    </div>
    <div>
        <a href="#" id="queen_black"><img src="../Util/PieceImages/queen_black.png"/></a>
    </div>
    <div>
        <a href="#" id="king_black"><img src="../Util/PieceImages/king_black.png"/></a>
    </div>
    <div>
        <a href="#" id="rook_black"><img src="../Util/PieceImages/rook_black.png"/></a>
    </div>
    <div>
        <a href="#" id="bishop_black"><img src="../Util/PieceImages/bishop_black.png"/></a>
    </div>
    <div>
        <a href="#" id="knight_black"><img src="../Util/PieceImages/knight_black.png"/></a>
    </div>
    <div>
        <a href="#" id="pawn_black"><img src="../Util/PieceImages/pawn_black.png"/></a>
    </div>

</div>

<table id="chess_board" cellpadding="0" cellspacing="0">


    <!--

       In the program there are 2 notations are used.

       1. The Algebraic Noation...  id of table will be in this format..

          a8 b8 c8 d8 e8 f8 g8 h8
        a7 b7 c7 d7 e7 f7 g7 h7
        a6 b6 c6 d6 e6 f6 g6 h6
        a5 b5 c5 d5 e5 f5 g5 h5
        a4 b4 c4 d4 e4 f4 g4 h4
        a3 b3 c3 d3 e3 f3 g3 h3
        a2 b2 c2 d2 e2 f2 g2 h2
        a1 b1 c1 d1 e1 f1 g1 h1



        2. Numeric Notation...     cell[i][j] in board will be in this format...
        07 17 27 37 47 57 67 77    But you could use any format by calling getCellAt(new Position('a'),1) or getCellAt(new Position(1,1))
        06 16 26 36 46 56 66 76
        05 15 25 35 45 55 65 75
        04 14 24 34 44 54 64 74
        03 13 23 33 43 53 63 73
        02 12 22 32 42 52 62 72
        01 11 21 31 41 51 61 71
        00 10 20 30 40 50 60 70

        CAUTION :
         The cells will NOT FOLLOW matrix format, rather they will follow the numeric notation as explained above.
           Matrix format :
           00 01 02 03 04 05 06 07
           10 11 12 13 14 15 16 17
           20 21 22 23 24 25 26 27
           30 31 32 33 34 35 36 37   The Cells will not follow this format!!!!
           40 41 42 43 44 45 46 47
           50 51 52 53 54 55 56 57
           60 61 62 63 64 65 66 67
           70 71 72 73 74 75 76 77

     -->

</table>
<br><br>
<input type="button" class="claimed_draw" value="Claim Draw" cellpadding="0" cellspacing="0"/>
<br><br>
<form action="/homepage" class="claimed_draw" method="post" align="center" >
    <input type="submit" value="Go Back to Home Page"/>
</form>
</body>
</html>
 