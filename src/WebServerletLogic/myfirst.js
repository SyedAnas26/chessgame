var chessAI = require('chess-ai-kong');

chessAI.setOptions(
{
 depth: 100,
 monitor: false,
 strategy: 'basic',
 timeout: 1000
});

console.log(chessAI.play(process.argv.slice(2)));