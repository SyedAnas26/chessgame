var chessAI = require('chess-ai-kong');
var diff=sessionStorage.getItem("Difficulty")
chessAI.setOptions(
{
 depth: diff,
 monitor: false,
 strategy: 'basic',
 timeout: 10
});
console.log(chessAI.play(process.argv.slice(2)));