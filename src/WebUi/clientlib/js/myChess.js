var $status = $('#status')
var whiteSquareGrey = '#a9a9a9'
var blackSquareGrey = '#696969'
var alertStatus = null;
var interval;
var moveNo = 0;
var baseTime;
var player2Timer = document.querySelector('#player2Time');
var player1Timer = document.querySelector('#player1Time');
var mode;
var left=false  ;
let chessboard = null;
var GameStatus = "waiting";
var playerColor = localStorage["playerColor"];
var statusCalled = false;
var uniqueidServer;
var alterStatus = null;
var disableOtherCall = false;
var c = 0;
var aiMove;
var timer = 0;
var t;
var timer_is_on = 0;
var x = window.location.origin;
var position_to = '';
var position_from = '';
var difficulty;
var gameId;
var winner;
var changedBg = 0;
var extraTime;
var notWrong;
var player2CurrenSeconds;
var player1CurrenSeconds;
var uniqueId;
var gamePgnSoFar;
var alertStatus;
var winTeam;
var fullName;
var player2Score;
var Score;
var mute = false;
var player1turn;
var videoCheck = false;
var peerConnection = null;
const chess = new Chess()
var boardFen = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR';
var webSocket;
var stream;
let board1 = null;
var webSocketConnection = "notConnected";
var url = location.href;
var ws = x.replace('https://', 'wss://').replace('http://', 'ws://');
gameId = url.split('&gameid=')[1].split('&')[0];
uniqueId = url.split('id=')[1].split('&')[0];
var webSocketUrl = ws + '/socket/game/' + gameId + '/' + uniqueId;
webSocket = new WebSocket(webSocketUrl);
var mightBeBack=false;
var backgroundWorker = undefined;
if (typeof (Worker) !== "undefined") {
    if (typeof (backgroundWorker) == "undefined") {
        backgroundWorker = new Worker('/clientlib/js/backgroundWorker.js');
    }
} else {
    alert("Web Worker Not Supported Please Inform to the Team about ur webBrowser!")
}
var configuration = {
    "iceServers": [{"url": "stun:stun2.1.google.com:19302"}]
};


$('.chat-input input').keyup(function (e) {
    if ($(this).val() == '')
        $(this).removeAttr('good');
    else
        $(this).attr('good', '');
});

$.ajax({
    url: x + "/getUserId",
    type: 'POST',
    success: function (response) {
        if (response.user_id === 'null') {
            uniqueidServer = uniqueId ;
            fullName = 'Guest User';
            localStorage.setItem("guestId",uniqueId);
        } else {
            fullName = response.user_name;
            uniqueidServer = response.unique_id;
        }
        if (uniqueId !== uniqueidServer) {
            console.log("uniqueId = " + uniqueId + "server " + uniqueidServer)
            alert('Invalid Acess!');
            location = "/homepage/" + uniqueidServer;
        }
        console.log("uni S " + uniqueidServer)
        console.log('difficulty ajax success!!');
    }
});
window.onload = function () {

    var url = location.href;
    mode = url.split('mode=')[1].split('&')[0];
    if (mode === "computer") {
        difficulty = localStorage["skillLevel"];
        GameStatus = "starts";
    }
    extraTime = parseInt(localStorage["extraTime"]);
    baseTime = parseInt(localStorage["baseTime"]);
    console.log("extraTime " + extraTime)
    console.log("gameId " + gameId)
    console.log("skillLevel " + difficulty)
    console.log("playerColor  " + playerColor)
    console.log("unique " + uniqueId)
    console.log("baseTime " + baseTime)
    if (uniqueId === '0' || uniqueId.includes('g')) {
        document.getElementById("goToHome").action = "/";
    } else {
        document.getElementById("goToHome").action = "/homepage/" + uniqueId;
    }
    player2CurrenSeconds = baseTime;
    player1CurrenSeconds = baseTime;
    webSocketConnection = "connected";
    $.ajax({
        url: x + "/getGamePosition",
        type: 'POST',
        data: {"gameId": gameId, "uniqueId": uniqueId},
        success: function (response) {
            console.log(response);
            if (response !== null) {
                gamePgnSoFar = response.gamePosition;
                setPositions(gamePgnSoFar);
            }
        }
    })

    if (playerColor === 'b') {
        chessboard.setOrientation('b')
        if (mode === 'computer') {
            chessboard.setPosition(chess.fen());
            window.setTimeout(makeAiMove, 1000);
        }
        document.getElementById("scoreBox").style.transform = "rotate(180deg)";
    }
    var videoElements = document.getElementById('videoElements');
    var localVideo = document.getElementById('localVideo');
    var remoteVideo = document.getElementById('remoteVideo');
    var remoteAudio = document.getElementById('remoteAudio');
    var chatBox = document.getElementById('chatBox');
    var timerBox = document.getElementById('timerBox');


    if (mode==='multi') {
        chatBox.style.visibility='visible';
    }
    else
    {
        timerBox.style.paddingTop='100px';
    }
}


import {
    INPUT_EVENT_TYPE,
    MOVE_INPUT_MODE,
    MARKER_TYPE,
    COLOR,
    Chessboard
} from "/clientlib/cm-chessboard/Chessboard.js"

function inputHandler(event) {
    console.log("event", event)
    if (event.type === INPUT_EVENT_TYPE.moveStart) {
        chessboard.setPosition(chess.fen());
        removeGreySquares();
        highlightLegalMoves(event.square);
    }
    if (event.type === INPUT_EVENT_TYPE.moveDone) {
        removeGreySquares();
        position_to = event.squareTo;
        position_from = event.squareFrom;
        const move = {from: event.squareFrom, to: event.squareTo}
        const result = chess.move(move)
        if (result) {
            playerMoved(event, result);
        } else {
            if (!checkPromotionMove(event)) {
                if (event.squareFrom !== event.squareTo) {
                    wrongMoveSound();
                }
            }
        }
        return result
    } else {
        return true
    }
}

chessboard = new Chessboard(document.getElementById("myBoard"), {
    position: chess.fen(),
    sprite: {url: "/img/chessboard-sprite.svg"},
    responsive: true,
    style: {aspectRatio: 0.9},
    moveInputMode: MOVE_INPUT_MODE.dragPiece
})
if (playerColor === 'w') {
    chessboard.enableMoveInput(inputHandler, COLOR.white)
}


// board1 = new Chessboard(document.getElementById("board1"),
//     {
//         position: "start",
//         sprite: {url: "/img/chessboard-sprite.svg"},
//         responsive: true
//     });
function call() {
    var x = document.getElementById("callStatus");
    if (x.innerHTML === "Calling..." || x.innerHTML === "Connected!") {
        x.innerHTML = "Call Ended";
        disableOtherCall = false;
        hangUp();
    } else {
        x.innerHTML = "Calling...";
        disableOtherCall = true;
        makeCall();
    }
}


async function makeCall() {
    peerConnection = new RTCPeerConnection(configuration);
    if (videoCheck) {
        stream = await navigator.mediaDevices.getUserMedia({audio: true, video: true});
    } else {
        stream = await navigator.mediaDevices.getUserMedia({audio: true, video: false});
    }
    peerConnection.addStream(stream);
    peerConnection.onicecandidate = function (event) {
        if (event.candidate) {
            webSocket.send(JSON.stringify({
                type: "candidate",
                candidate: event.candidate
            }));
        }
    };
    if (videoCheck) {
        chatBox.style.visibility = 'hidden';
        videoElements.style.display = 'block';
        document.getElementById('videoHangUp').style.display = 'block';
        localVideo.srcObject = stream;
        peerConnection.onaddstream = function (e) {
            remoteVideo.srcObject = e.stream;
        };

        peerConnection.createOffer(function (offer) {
            webSocket.send(JSON.stringify({
                type: "videoOffer",
                offer: offer
            }));
            peerConnection.setLocalDescription(offer);
        }, function (error) {
            alert("Error when creating an offer");
        });
    } else {
        peerConnection.onaddstream = function (e) {
            remoteAudio.srcObject = e.stream;
        };
        peerConnection.createOffer(function (offer) {
            webSocket.send(JSON.stringify({
                type: "audioOffer",
                offer: offer
            }));
            peerConnection.setLocalDescription(offer);
        }, function (error) {
            alert("Error when creating an offer");
        });
    }

    // Setup ice handling

}

window.voiceCall = function () {
    var voiceCall = document.getElementById('voiceCall');
    if (!disableOtherCall || voiceCall.classList.contains('fa-phone-alt')) {
        voiceCall.classList.toggle('fa-phone-alt');
        voiceCall.classList.toggle('fa-phone-slash');
        call();
    }
}

window.videoCall = function () {
    videoCheck = true;
    var videoCall = document.getElementById('videoCall');
    if (!disableOtherCall || videoCall.classList.contains('fa-video')) {
        videoCall.classList.toggle('fa-video');
        videoCall.classList.toggle('fa-video-slash');
        call();
    }
}


function playerMoved(event, move) {

    event.chessboard.disableMoveInput()
    stopTimer()
    updateStatus()
    if (mode === 'computer') {
        if (moveNo === 0) {
            startTimer(player2CurrenSeconds, player2Timer);
        } else {
            startTimer(player2CurrenSeconds + extraTime, player2Timer);
        }
    } else if (mode === 'multi') {
        webSocket.send(JSON.stringify({type: "move", content: move.san}));
        if (!statusCalled) {
            if (moveNo === 0) {
                setTimeout(() => {
                    startTimer(player2CurrenSeconds, player2Timer);
                }, 800);
            } else {
                setTimeout(() => {
                    startTimer(player2CurrenSeconds + extraTime, player2Timer);
                }, 700);
            }
        }
    }
    var userTime = player1CurrenSeconds;
    sendUserMove(move, userTime)
    playMoveSound(move);
    var fen = chess.fen();
    boardFen = chessboard.getPosition();
    if (!statusCalled) {
        backgroundWorker.postMessage({'do': 'analyse', 'fen': fen});
    }
    player1turn = false;
    if (mode == 'computer') {
        window.setTimeout(makeAiMove, 1000);
    }
}


function checkPromotionMove(event) {
    chess.moves({square: event.squareFrom})
    var posMoves = chess.moves({verbose: true})
    if (movePossible(posMoves, position_from, position_to)) {
        if (position_to.charAt(1) == 1 && position_from.charAt(1) == 2 || position_to.charAt(1) == 8 && position_from.charAt(1) == 7) {
            // if (orientation === 'white') {
            if (chess.turn() === 'w' && position_to.charAt(1) == 8) {
                document.getElementById("white_promotion_pieces").style.display = "block";
                return true;
            } else if (chess.turn() === 'b' && position_to.charAt(1) == 1) {
                document.getElementById("black_promotion_pieces").style.display = "block";
                return true;
            }
        }
    }
    return false;
}

function setPositions(gamePgnSoFar) {

    var result = '';
    chess.load_pgn(gamePgnSoFar);
    chessboard.setPosition(chess.fen());
    var arr = gamePgnSoFar.split(/\d+. |\s+/g);
    var filtered = arr.filter(function (el) {
        return el !== "";
    });
    console.log(arr[arr.length - 1])
    if (arr[arr.length - 1] === "0-1") {
        result = " 0-1";
        statusCalled = true;
        alert("Match Over, Player 2 Won (Black)");
    } else if (arr[arr.length - 1] === "1-0") {
        result = " 1-0";
        statusCalled = true;
        alert("Match Over, Player 1 Won (White)");
    } else if (arr[arr.length - 1] === "1/2-1/2") {
        result = " 1/2-1/2";
        statusCalled = true;
        alert("Match Over, Draw");
    }
    moveNo = filtered.length;
    if (moveNo > 1) {
        player2CurrenSeconds = parseInt(window.localStorage.getItem("" + gameId + "Player2")) - 2;
        player1CurrenSeconds = parseInt(window.localStorage.getItem("" + gameId + "Player1")) - 2;
    }
    console.log(extraTime)
    var secondsFromStorage;
    var display;
    GameStatus = "starts";
    if (chess.turn() !== playerColor) {
        secondsFromStorage = player2CurrenSeconds;
        display = player2Timer;
    } else {
        secondsFromStorage = player1CurrenSeconds;
        display = player1Timer;
    }
    if (!statusCalled) {
        stopTimer();
        console.log('sec From storage ' + secondsFromStorage)
        startTimer(secondsFromStorage, display);
        if (chess.turn() === playerColor) {
            if (playerColor === 'w') {
                chessboard.enableMoveInput(inputHandler, COLOR.white);
            } else if (playerColor === 'b') {
                chessboard.enableMoveInput(inputHandler, COLOR.black);
            }
        }
    }else {
    chessboard.disableMoveInput();
    }
    addPgnDisplayToUi(gamePgnSoFar);
}

function removeGreySquares() {
    chessboard.removeMarkers(null, null);
    // chessboard.removeMarkers(null, MARKER_TYPE.capture);

}

window.promotePiece = function (piece) {
    var promMove
    var userTime = player1CurrenSeconds;
    console.log("timer on prom" + userTime)
    document.getElementById("white_promotion_pieces").style.display = "none";
    document.getElementById("black_promotion_pieces").style.display = "none";
    var checkPieceExist = "" + position_to;
    if (chess.get(checkPieceExist) === null) {
        promMove = "" + position_to + "=" + piece;
    } else {
        promMove = "" + position_from.charAt(0) + "x" + position_to + "=" + piece;
    }
    var move = chess.move(promMove)
    chessboard.setPosition(chess.fen())
    sendUserMove(move, userTime)
    updateStatus()
    startTimer(player2CurrenSeconds, player2Timer);
    if (mode === 'computer') {
        window.setTimeout(makeAiMove, 250)
    } else if (mode === 'multi') {
        webSocket.send(JSON.stringify({type: "move", content: move.san}));
    }
}

// document.body.addEventListener("click", function (evt) {
//     document.getElementById("board1").style.visibility = "hidden";
// });

window.askForDraw = function (event) {
    if (!statusCalled) {
        if (event === 'ask') {
            if (mode === 'computer') {
                sendStatus('3');
            } else {
                webSocket.send(JSON.stringify({type: "draw", content: "claiming"}));
                alert("Draw Request Sent ! Waiting for Opponent response");
            }
        } else {
            if (mode === 'computer') {
                sendStatus('3');
            } else {
                webSocket.send(JSON.stringify({type: "status", content: "left"}));
            }
        }
    }
}

function stopTimer() {
    clearInterval(interval);
}

function playMoveSound(move) {
    if (mute === false) {
        var san = move.san;
        for (var i = 0; i < san.length; i++) {
            if (san.charAt(i) === 'x') {
                document.getElementById("captureSound").play();
                return;
            }
        }
        if (chess.turn() === 'b') {
            document.getElementById("moveSound").play();
        } else {
            document.getElementById('blackMoveSound').play();
        }
    }
}

function wrongMoveSound() {
    if (!mute) {
        document.getElementById("wrongMove").play();
    }
}

function startTimer(duration, display) {
    var timer = duration, minutes, seconds;
    if (baseTime !== 0) {
        interval = setInterval(function () {
            minutes = parseInt(timer / 60, 10);
            seconds = parseInt(timer % 60, 10);
            if (display === player2Timer) {
                player2CurrenSeconds = (minutes * 60) + seconds;
            } else if (display === player1Timer) {
                player1CurrenSeconds = (minutes * 60) + seconds;
            }
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;
            display.textContent = minutes + ":" + seconds;
            window.localStorage.setItem("" + gameId + "Player2", player2CurrenSeconds);
            window.localStorage.setItem("" + gameId + "Player1", player1CurrenSeconds);
            if (baseTime !== 0 && minutes === "00" && seconds === "00") {
                if (display === player1Timer) {
                    alert('Time Up Opponent Won');
                    if (playerColor === 'b') {
                        sendStatus('1');
                    } else {
                        sendStatus('2');
                    }
                } else {
                    alert('Opponent Time Up You Won');
                    if (playerColor === 'b') {
                        sendStatus('2');
                    } else {
                        sendStatus('1');
                    }
                }
            }
            if (--timer < 0) {
                timer = duration;
            }
        }, 1000);
    }
}


var input = document.getElementById("myMesg");

// Execute a function when the user releases a key on the keyboard
input.addEventListener("keyup", function(event) {
    // Number 13 is the "Enter" key on the keyboard
    if (event.keyCode === 13) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        document.getElementById("chatInput").click();
    }
});

$('.speaker').click(function (e) {
    e.preventDefault();
    e.stopPropagation();
    $(this).toggleClass('on');
    mute = (mute == false) ? true : false;
});

function saveTextAsFile() {
    var result = '';
    if (statusCalled === true) {
        if (winner === '1') {
            result = ' 1-0';
        } else if (winner === '2') {
            result = ' 0-1';
        } else if (winner === '3') {
            result = ' 1/2-1/2';
        }
    }
    var pgn = chess.pgn();
    var textToSave = pgn + result;
    var textToSaveAsBlob = new Blob([textToSave], {type: "text/plain"});
    var textToSaveAsURL = window.URL.createObjectURL(textToSaveAsBlob);
    var fileNameToSaveAs = gameId + '.pgn';
    var downloadLink = document.createElement("a");
    downloadLink.download = fileNameToSaveAs;
    downloadLink.innerHTML = "Download File";
    downloadLink.href = textToSaveAsURL;
    downloadLink.onclick = destroyClickedElement;
    downloadLink.style.display = "none";
    document.body.appendChild(downloadLink);
    downloadLink.click();
}

function destroyClickedElement(event) {
    document.body.removeChild(event.target);
}

function sendStatus(sts) {
    var gamePgn = chess.pgn()
    if (moveNo >= 1) {
        moveNo++
        if (statusCalled === false && gamePgn !== null) {
            $.ajax({
                url: x + "/gamestatus",
                type: 'POST',
                data: {
                    "gameStatus": sts,
                    "gamePgn": gamePgn,
                    "gameId": gameId,
                    "moveNo": moveNo,
                    "uniqueId": uniqueId
                },
                success: function () {

                    console.log('draw ajax success!!');
                },
                error: function (jqXHR, exception) {
                    console.log('draw ajax Error occured!!');
                }

            });
            statusCalled = true;
            chessboard.disableMoveInput();
            winner = sts;
            stopTimer();
            if (sts === '1' && playerColor === 'w') {
                if (!mute) {
                    document.getElementById("win").play();
                    document.getElementById("win").onended = function () {
                        alert("You Won the Match");
                    };
                }
            } else if (sts === '2' && playerColor === 'b') {
                if (!mute) {
                    document.getElementById("win").play();
                    document.getElementById("win").onended = function () {
                        alert("You Won the Match");
                    };
                } else {
                    alert("You Won the Match")
                }
            } else if (sts === '2' && playerColor === 'w' || sts === '1' && playerColor === 'b') {
                if (!mute) {
                    document.getElementById("lose").play();
                    document.getElementById("lose").onended = function () {
                        alert("Opponent Won the Match");
                    };
                } else {
                    alert("Opponent Won the Match");
                }
            }
            if (sts === '3') {
                alert('Match Draw');
                winTeam = 'Draw';
            }
            stopTimer();
            console.log("winner" + winner)
        }
    }

}


function greySquare(square) {
    if (!statusCalled) {
        if (square.flags === 'c') {
            chessboard.addMarker(square.to, MARKER_TYPE.capture);
        }
        chessboard.addMarker(square.to, MARKER_TYPE.dot);
    }
}

webSocket.onmessage = function (event) {
    var data = JSON.parse(event.data);
    var reply;
    console.log('onmessage::' + data);

    switch (data.type) {

        case "message":
            sendMessage('msg-remote', data.content, data.user);
            break;

        case "status":

            if (data.content === 'start') {
                if (mightBeBack) {
                    alert('Opponent rejoined !');
                    webSocket.send(JSON.stringify({type: "status", content: "alive"}));
                    mightBeBack = false;
                } else if (GameStatus !== "starts") {
                    GameStatus = "starts";
                    alert(" Opponent Joined Game Starts ! ");
                    if (playerColor == 'w') {
                        chessboard.enableMoveInput(inputHandler, COLOR.white)
                    }
                    webSocket.send(JSON.stringify({type: "status", content: "start"}));
                }
            }
            else if (data.content === 'left') {
                alert("Opponent left the Match !");
                sendStatus('3');
                left=true;
                hangUp();
                return;
            } else if (data.content === 'oppleft' && left!==true) {
                alert(" Opponent Left the page , opponent might be back !");
                mightBeBack = true;
                return;
            }else if(data.content==='alive'){
                alert("Your opponent is still here , continue your game !");
            }

            break;

        case "draw":
            if (data.content === 'claiming') {
                if (confirm("Shall we draw the Match ?")) {
                    sendStatus('3');
                    reply = "ok";
                } else {
                    reply = "no";
                }
                webSocket.send(JSON.stringify({type: "draw", content: reply}));
            } else if (data.content === "ok") {
                alert("Opponent Accepted Draw Request !");
                sendStatus('3');
            } else if (data.content === "no") {
                alert("Opponent Rejected ur Draw Request !")
            }
            break;

        case "move":
            var move = chess.move(data.content, {sloppy: true});
            if (move === null) {
                return;
            }
            chessboard.setPosition(chess.fen());
            if (playerColor == 'w' && statusCalled !== true) {
                chessboard.enableMoveInput(inputHandler, COLOR.white);
            } else if (playerColor == 'b' && statusCalled !== true) {
                chessboard.enableMoveInput(inputHandler, COLOR.black);
            }
            if (!statusCalled) {
                backgroundWorker.postMessage({'do': 'analyse', 'fen': chess.fen()});
            }
            moveNo++;
            player1turn = true;
            playMoveSound(move);
            boardFen = chessboard.getPosition();
            stopTimer();
            if (moveNo <= 1) {
                startTimer((player1CurrenSeconds), player1Timer);
            } else {
                startTimer((player1CurrenSeconds + extraTime), player1Timer);
            }
            updateStatus();
            break;

        case "videoOffer":
            if (confirm("Opponent asking for a Video Call")) {

                handleOffer('Video', data.offer);
            } else {
                webSocket.send(JSON.stringify({type: "decline", decline: "decline"}));
            }
            break;

        case "audioOffer":
            var x = window.confirm("Opponent asking for a Audio Call");
            if (x === true) {
                handleOffer('Audio', data.offer);

            } else {
                webSocket.send(JSON.stringify({type: "decline", decline: "decline"}));

            }

            break;


        case "answer":
            handleAnswer(data.answer);
            break;
        //when a remote peer sends an ice candidate to us
        case "candidate":
            handleCandidate(data.candidate);
            break;

        case "leave":
            if (peerConnection != null) {
                alert("Opponent Hanged up the call!");
                hangUp();
            }
            break;

        case "decline":
            alert("Opponent declined your call Request!");
            hangUp();
            break;

        default:
            break;

    }
}

function handleAnswer(answer) {
    document.getElementById("callStatus").innerHTML = "Connected!";
    peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
};

//when we got an ice candidate from a remote user
function handleCandidate(candidate) {
    peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
};


async function handleOffer(type, offer) {
    if (type === 'Video') {
        videoCheck = true;

    }
    peerConnection = new RTCPeerConnection(configuration);
    if (videoCheck) {
        stream = await navigator.mediaDevices.getUserMedia({audio: true, video: true});
        peerConnection.addStream(stream);
        chatBox.style.visibility = 'hidden';
        videoElements.style.display = 'block';
        document.getElementById('videoHangUp').style.display = 'block';
        localVideo.srcObject = stream;
        peerConnection.onaddstream = function (e) {
            remoteVideo.srcObject = e.stream;
        };
    } else {
        var voiceCall = document.getElementById('voiceCall');
        voiceCall.classList.remove('fa-phone-slash');
        voiceCall.classList.add('fa-phone-alt');
        stream = await navigator.mediaDevices.getUserMedia({audio: true, video: false});
        peerConnection.addStream(stream);
        peerConnection.onaddstream = function (e) {
            remoteAudio.srcObject = e.stream;
        };
    }

    // Setup ice handling
    peerConnection.onicecandidate = function (event) {
        if (event.candidate) {
            webSocket.send(JSON.stringify({
                type: "candidate",
                candidate: event.candidate
            }));
        }
    };

    peerConnection.setRemoteDescription(new RTCSessionDescription(offer));
    peerConnection.createAnswer(function (answer) {
        peerConnection.setLocalDescription(answer);
        webSocket.send(JSON.stringify({
            type: "answer",
            answer: answer
        }));
    }, function (error) {
        alert("Error when creating an answer");
    });
    document.getElementById('callStatus').innerHTML = 'Connected!';


}


window.hangUp = function () {
    if (peerConnection !== null) {
        if (videoCheck) {
            chatBox.style.visibility = 'visible';
            videoElements.style.display = 'none';
            document.getElementById('videoHangUp').style.display = 'none';
            remoteVideo.src = null;
            localVideo.src = null;
            videoCheck = false;
            var videoCall = document.getElementById('videoCall');
            videoCall.classList.remove('fa-video');
            videoCall.classList.add('fa-video-slash');
        } else {
            var voiceCall = document.getElementById('voiceCall');
            voiceCall.classList.remove('fa-phone-alt');
            voiceCall.classList.add('fa-phone-slash');
        }
        document.getElementById('callStatus').innerHTML = 'Call Ended';
        stream.getTracks().forEach(track => track.stop());
        stream = null;
        peerConnection.close();
        peerConnection = null;
        webSocket.send(JSON.stringify({type: "leave"}));
        disableOtherCall = false;
    }
}

//     if(msg.includes('msgf')){
//         sendMessage('msg-remote',msg.split(',')[1])
//     }
//     if (msg === 'start' && GameStatus !== "starts") {
//         GameStatus = "starts";
//         if (playerColor == 'w') {
//             chessboard.enableMoveInput(inputHandler, COLOR.white)
//         }
//         alert(" Opponent Joined Game Starts ! ");
//         webSocket.send('start');
//         return;
//     }
//     if (msg === 'left') {
//         alert("Opponent left the Match !");
//         sendStatus('3');
//     }
//     if (msg === "ok") {
//         alert("Opponent Accepted Draw Request !");
//         sendStatus('3');
//         return;
//     } else if (msg === "no") {
//         alert("Opponent Rejected ur Draw Request !")
//         return;
//     }
//     if (msg === "draw") {
//         if (confirm("Shall we draw the Match ?")) {
//             sendStatus('3');
//             reply = "ok";
//         } else {
//             reply = "no";
//         }
//         webSocket.send(reply);
//         return;
//     }
//     var move = chess.move(msg, {sloppy: true});
//     if (move === null) {
//         return;
//     }
//     chessboard.setPosition(chess.fen());
//     if (playerColor == 'w' && statusCalled !== true) {
//         chessboard.enableMoveInput(inputHandler, COLOR.white);
//     } else if (playerColor == 'b' && statusCalled !== true) {
//         chessboard.enableMoveInput(inputHandler, COLOR.black);
//     }
//     if (!statusCalled) {
//         backgroundWorker.postMessage({'do': 'analyse', 'fen': chess.fen()});
//     }
//     moveNo++;
//     player1turn = true;
//     playMoveSound(move);
//     boardFen = chessboard.getPosition();
//     stopTimer();
//     if (moveNo <= 1) {
//         startTimer((player1CurrenSeconds), player1Timer);
//     } else {
//         startTimer((player1CurrenSeconds + extraTime), player1Timer);
//     }
//     updateStatus();
// }

function movePossible(posMoves, source, target) {
    for (var i = 0; i < posMoves.length; i++) {
        if (posMoves[i].from === source && posMoves[i].to === target) {
            return true;
        }
    }
    return false;
}


window.sendMessage = function (from, inputMesg, fromUser) {
    if (from.includes('msg-self')) {
        fromUser = fullName;
        webSocket.send(JSON.stringify({type: "message", content: inputMesg, user: fullName}));
        console.log(inputMesg);
        document.getElementById('myMesg').value = '';
    }
    var mesgCont = document.createElement('p');
    mesgCont.classList.add('msg');
    mesgCont.textContent = inputMesg;
    var mesg = document.createElement('div');
    mesg.classList.add('messages');
    mesg.appendChild(mesgCont);
    var postTime = document.createElement('span');
    postTime.classList.add('posttime');
    const date = Date().slice(16, 21);
    postTime.textContent = date;
    var userName = document.createElement('span');
    userName.classList.add('username');
    userName.textContent = fromUser;
    var timeStamp = document.createElement('span');
    timeStamp.classList.add('timestamp');
    timeStamp.appendChild(userName);
    timeStamp.innerHTML = timeStamp.innerHTML + '&bull;';
    timeStamp.appendChild(postTime);
    var flr = document.createElement('div');
    flr.classList.add('flr');
    flr.appendChild(mesg);
    flr.appendChild(timeStamp);
    var mesgBox = document.createElement('div');
    mesgBox.classList.add('msg-box');
    mesgBox.appendChild(flr);
    var article = document.createElement('ARTICLE');
    article.classList.add('msg-container');
    article.classList.add(from);
    article.appendChild(mesgBox);
    document.getElementById('windowChat').appendChild(article);
    $(".chat-window").stop().animate({scrollTop: $(".chat-window")[0].scrollHeight}, 1000);

}

function sendUserMove(move, userTime) {
    moveNo++;
    var gameTillNow = chess.pgn();
    var fen = chess.fen();
    backgroundWorker.postMessage({
        'do': 'storeMove',
        'uniqueId': uniqueId,
        'fen': fen,
        'move': move.san,
        'time': userTime,
        'gameId': gameId,
        'moveNo': moveNo,
        'gamePgn': gameTillNow
    })
}

function highlightLegalMoves(square) {
    // get list of possible moves for this square
    var moves = chess.moves({
        square: square,
        verbose: true
    })
    // exit if there are no moves available for this square
    if (moves.length === 0) return
    // highlight the square they moused over
    var fen = chess.fen()
    console.log(fen)

    if (chessboard.getPosition() === fen.split(' ')[[0]] && chess.turn() === playerColor) {
        // highlight the possible squares for this piece
        for (var i = 0; i < moves.length; i++) {
            greySquare(moves[i])
        }
    }
}

function makeAiMove() {
    callAIToPlay();
    player1turn = true;
    if (moveNo === 2) {
        startTimer(player1CurrenSeconds, player1Timer);
    } else {
        var sec = player1CurrenSeconds + extraTime;
        console.log('Seconds = ' + sec)
        startTimer(sec, player1Timer);
    }
}

function callAIToPlay() {
    moveNo++;
    var fen = chess.fen();
    var gameTillNow = chess.pgn();
    boardFen = chessboard.getPosition();
    stopTimer();
    backgroundWorker.postMessage({'do': 'getAiMove', 'fen': fen, 'skillLevel': difficulty})
}

backgroundWorker.onmessage = function (response) {
    var data = response.data;
    switch (data.from) {
        case 'ai':
            aiMove = chess.move(data.aiMove, {sloppy: true})
            playMoveSound(aiMove)
            chessboard.setPosition(chess.fen())
            var fen = chess.fen();
            if (!statusCalled) {
                backgroundWorker.postMessage({'do': 'analyse', 'fen': fen});
            }
            var gameTillNow = chess.pgn();
            updateStatus();
            if (playerColor == 'w' && statusCalled !== true) {
                chessboard.enableMoveInput(inputHandler, COLOR.white);
            } else if (playerColor == 'b' && statusCalled !== true) {
                chessboard.enableMoveInput(inputHandler, COLOR.black);
            }
            backgroundWorker.postMessage({
                'do': 'storeMove',
                'uniqueId': uniqueId,
                'fen': fen,
                'move': aiMove.san,
                'time': player2CurrenSeconds,
                'gameId': gameId,
                'moveNo': moveNo,
                'gamePgn': gameTillNow
            });
            break;

        case 'storage':
            break;

        case 'analyse':
            var res = data.score;
            if (chess.turn() === 'b') {
                if (res.toString().startsWith("-")) {
                    res = "+" + Math.abs(parseFloat(res));
                } else {
                    res = "-" + Math.abs(parseFloat(res));
                }
            }
            if (res.toString().startsWith("-")) {
                var increase = 50 + (Math.abs(parseFloat(res)));
                if (increase > 100) {
                    increase = 100;
                }
                if (increase < 0) {
                    increase = 0;
                }
                var set = increase + "%";
                document.getElementById("blackColor").style.height = set;
            } else {
                var decrease = 50 - (parseFloat(res));
                if (decrease > 100) {
                    decrease = 100;
                }
                if (decrease < 0) {
                    decrease = 0;
                }
                var set = decrease + "%";
                document.getElementById("blackColor").style.height = set;
            }
            Score = parseFloat(res);
            document.getElementById("Score").innerHTML = Score;
            break;
    }

}

window.bgReverse = function () {
    if (changedBg >= 2) {
        changedBg = 0;
    } else {
        changedBg++;
    }
    if (changedBg == 1) {
        document.getElementById("body").style.background = "none";
        document.getElementById("scoreBox").style.backgroundColor = "#C0C7CE";
        changedBg = true;
    } else if (changedBg == 0) {
        document.getElementById("body").style.backgroundImage = "linear-gradient(rgba(255, 255, 255, .3), rgba(255, 255, 255, .3)), url('/img/background-min.jpg')";
        document.getElementById("scoreBox").style.backgroundColor = "white";
        document.getElementById("body").style.backgroundPosition = "center";
        document.getElementById("body").style.backgroundRepeat = "no-repeat";
        document.getElementById("body").style.backgroundSize = "cover";
        document.getElementById("body").style.backgroundAttachment = "fixed";
    } else if (changedBg == 2) {
        document.getElementById("body").style.backgroundImage = "linear-gradient(rgba(255, 255, 255, .3), rgba(255, 255, 255, .3)), url('/img/watchpg-min.jpg')";
        document.getElementById("scoreBox").style.backgroundColor = "white";
        document.getElementById("body").style.backgroundPosition = "center";
        document.getElementById("body").style.backgroundRepeat = "no-repeat";
        document.getElementById("body").style.backgroundSize = "cover";
        document.getElementById("body").style.backgroundAttachment = "fixed";
    }

}

function updateStatus() {
    var status = ''
    var moveColor = 'White'
    if (chess.turn() === 'b') {
        moveColor = 'Black'
    }

    // checkmate?
    if (chess.in_checkmate()) {
        status = 'Game over, ' + moveColor + ' is in checkmate.'
        if (moveColor === 'White' && statusCalled === false) {
            alertStatus = 'Black won the Match';
            sendStatus('2')
        }
        if (moveColor === 'Black' && statusCalled === false) {
            alertStatus = 'White won the Match';
            sendStatus('1')
        }
    }

    // draw?
    else if (chess.in_draw()) {
        status = 'Game over, drawn position'
        stopTimer();
        sendStatus('3')
    }

    // game still on
    else {
        status = moveColor + ' to move'

        // check?
        if (chess.in_check()) {
            status += ', ' + moveColor + ' is in check'
        }
    }

    $status.html(status)
    addPgnDisplayToUi(chess.pgn());
}

function removePgnListChilds() {
    const deleteWhitePgnMoves = document.getElementById("whiteMove");
    while (deleteWhitePgnMoves.lastElementChild) {
        deleteWhitePgnMoves.removeChild(deleteWhitePgnMoves.lastElementChild);
    }
    const deleteBlackPgnMoves = document.getElementById("blackMove");
    while (deleteBlackPgnMoves.lastElementChild) {
        deleteBlackPgnMoves.removeChild(deleteBlackPgnMoves.lastElementChild);
    }
}

function addPgnDisplayToUi(gamePgn) {
    removePgnListChilds();
    if (gamePgn !== 'null') {
        var arr = gamePgn.split(/\d+. |\s+/g);
        var movesMade = arr.filter(function (el) {
            return el != "";
        });
        for (var i = 0; i < movesMade.length; i++) {
            if (i === 0 || i % 2 === 0) {
                showPgnInDisplay("whiteMove", movesMade, i, Score);
            } else if (i > 0) {
                showPgnInDisplay("blackMove", movesMade, i, Score);
            }
        }
    }
}

function showPgnInDisplay(elementId, movesMade, i, sc) {
    var store;
    var score;
    localStorage.setItem("" + i, sc);
    var player2ScoreHere;
    var player1ScoreHere;
    var onClickIsCalled = false;
    var node = document.createElement("LI");
    var a = document.createElement("a");
    node.onmouseover = function () {
        store = i;
        player2ScoreHere = player2Score;
        player1ScoreHere = Score;
        var length = movesMade.length;
        if (movesMade[movesMade.length - 1] === "1/2-1/2" || movesMade[movesMade.length - 1] === "1-0" || movesMade[movesMade.length - 1] === "0-1") {
            length = length - 1;
        }
        var game = new Chess();
        game.load_pgn(chess.pgn());
        var loopfor = ((length) - store) - 1;
        for (var j = 0; j < loopfor; j++) {
            game.undo();
        }
        board1.setPosition(game.fen())
        document.getElementById("board1").style.visibility = "visible";
    }

    node.onclick = function (event) {
        $('li').removeClass('active');
        $(this).addClass('active');
        onClickIsCalled = true;
        event.preventDefault()
        score = localStorage.getItem("" + i);
        var length = movesMade.length;
        if (movesMade[movesMade.length - 1] === "1/2-1/2" || movesMade[movesMade.length - 1] === "1-0" || movesMade[movesMade.length - 1] === "0-1") {
            length = length - 1;
        }
        var game = new Chess();
        game.load_pgn(chess.pgn());
        var loopfor = ((length) - store) - 1;
        for (var j = 0; j < loopfor; j++) {
            game.undo();
        }
        chessboard.setPosition(game.fen());
        removeGreySquares();
        backgroundWorker.postMessage({'do': 'analyse', 'fen': game.fen()});
        if (!mute) {
            document.getElementById('moveSound').play();
        }
    }
    a.textContent = "" + movesMade[i];
    a.setAttribute("href", "#");
    node.appendChild(a);
    if (i === (movesMade.length - 1)) {
        node.classList.add("active");
    }
    node.classList.add("displaySmallBoard");
    document.getElementById(elementId).appendChild(node);
}
