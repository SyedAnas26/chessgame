onmessage = function (e) {
    var xhr=new XMLHttpRequest();
    var data = e.data;
    switch (data.do) {
        case 'getAiMove':
            xhr.open('POST','/aiMove');
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send("gameFEN="+ data.fen+"&skillLevel="+ data.skillLevel);
            xhr.responseType = 'json';
            xhr.onload = function() {
                let responseObj = xhr.response;
                postMessage({'from': 'ai', 'aiMove': responseObj.aiMove});
            };
            break;

        case 'storeMove':
            xhr.open('POST', '/storeMove');
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send(
                "uniqueId="+data.uniqueId+"&"
                +"FEN="+data.fen+"&"
                +"move="+data.move+"&"
                +"timeTaken="+data.time+"&"
                +"gameId="+data.gameId+"&"
                +"moveNo="+data.moveNo+"&"
                +"gamePgn="+data.gamePgn+"&"
            );
            xhr.responseType = 'json';
            xhr.onload = function() {
                let responseObj = xhr.response;
                postMessage({'from': 'storage', 'oppTime': responseObj.opponentRemainingTime});
            };
            break;

        case 'analyse':
            xhr.open('POST','/analyseBoard');
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send("fen="+data.fen);
            xhr.responseType = 'json';
            xhr.onload = function() {
                var responseObj = xhr.response;
                console.log("res " +responseObj)
                postMessage({'from': 'analyse', 'score': responseObj.Score });
            };
            break;
    }
}