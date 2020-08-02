var c= 0;
var timer=0;
var t;
var timer_is_on = 0;
function timedCount() {
    timer=c;
    c=c+1;
    t = setTimeout(timedCount,1);
}

function startCount() {
    if (!timer_is_on) {
        timer_is_on = 1;
        timedCount();
    }
}

function stopCount() {
    clearTimeout(t);
    timer_is_on = 0;
    c=0;
}
startCount()
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
timeout: 1000
});
var move = chessAI.play(gamePgnSoFar);
stopCount()
var aiTime=timer;

//Connecting mysql....
var mysql = require('mysql');

var con = mysql.createConnection({
host: 'localhost',
user: 'root',
password: 'admin123',
database: 'chessgame_database'
});

con.connect(function(err) {
if (err) throw err;
var sql = "insert into gamemoves (GameID,MoveNo,Moves,TimeTaken,DrawClaimedStatus) values('"+gameID+"','"+moveNo+"','"+move+"','"+aiTime+"',0)";
con.query(sql, function (err, result) {
if (err) throw err;
//console.log(result.affectedRows + " record(s) updated (Ai Move)");
con.end();

});
});