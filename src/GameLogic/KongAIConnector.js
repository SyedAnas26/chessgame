var chessAI = require('chess-ai-kong');
var args = process.argv;
var thinkDepth = parseInt(args[2]);
var gameID = parseInt(args[3]);
var moveNo = parseInt(args[4]);
var gamePgnSoFar = args.slice(5);

chessAI.setOptions(
{
depth: parseInt(thinkDepth),
monitor: false,
strategy: 'basic',
timeout: 10
});
var move = chessAI.play(gamePgnSoFar);


//Connecting mysql....
var mysql = require('mysql');

var con = mysql.createConnection({
host: 'localhost',
user: 'root',
password: '',
database: 'chessgame_database'
});

con.connect(function(err) {
if (err) throw err;
var sql = "insert into gamemoves (GameID,MoveNo,Moves,TimeTaken,DrawClaimedStatus) values('"+gameID+"','"+moveNo+"','"+move+"',0,0)";
con.query(sql, function (err, result) {
if (err) throw err;
//console.log(result.affectedRows + " record(s) updated (Ai Move)");
con.end();

});
});