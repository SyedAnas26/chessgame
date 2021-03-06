package WebServerletLogic;

import GameLogic.Managers.DbConnector;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/socket/{path}/{gameId}/{userId}")
public class WebSocketConnector {

    private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(@PathParam("gameId") String gameId, @PathParam("path") String path, @PathParam("userId") String userId, Session session) throws IOException {
        session.getUserProperties().put("gameId", gameId);
        session.getUserProperties().put("userId", userId);
        System.out.println("Session Id "+session.getId());
        allSessions.add(session);
        if (!path.equals("waiting")) {
            for (Session allSession : allSessions) {
                if (allSession.getUserProperties().get("gameId").equals(gameId)) {
                    if (!allSession.getId().equals(session.getId()) && !allSession.getUserProperties().get("userId").equals(userId)) {
                        allSession.getBasicRemote().sendText("{\"type\":\"status\",\"content\":\"start\"}");
                    }
                }
            }
        }

    }

    @OnClose
    public void onClose(@PathParam("gameId") String gameId,@PathParam("userId") String userId, @PathParam("path") String path, Session session) throws IOException {
        System.out.println("Session Id " + session.getId());
        allSessions.remove(session);
        if (!path.equals("waiting")) {
            for (Session allSession : allSessions) {
                if (allSession.getUserProperties().get("gameId").equals(gameId)) {
                    if (!allSession.getId().equals(session.getId()) && !allSession.getUserProperties().get("userId").equals(userId)) {
                        allSession.getBasicRemote().sendText("{\"type\":\"status\",\"content\":\"oppleft\"}");
                    }
                }
            }
        }
    }

    @OnMessage
    public void onMessage(@PathParam("gameId") String gameId, @PathParam("userId") String userId, String message, Session session) throws Exception {
        if (message.contains("start")) {
            DbConnector.update("UPDATE challengetable SET Status='2' WHERE gameID='" + gameId + "'");
            System.out.println("setted ! ! ! !");
        }
        if (message.contains("left")) {
            for (Session allSession : allSessions) {
                if (allSession.getUserProperties().get("gameId").equals(gameId)) {
                    if (!allSession.getId().equals(session.getId()) && !allSession.getUserProperties().get("userId").equals(userId)) {
                        allSession.getBasicRemote().sendText(message);
                    }
                }
            }
        } else {
            for (Session allSession : allSessions) {
                if (allSession.getUserProperties().get("gameId").equals(gameId)) {
                    if (!allSession.getId().equals(session.getId())) {
                        allSession.getBasicRemote().sendText(message);
                    }
                }
            }
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
        t.printStackTrace();
    }

    public void sendMove(String move, long gameId) throws IOException {
        String gamId = "" + gameId;
        for (Session allSession : allSessions) {
            if (allSession.getUserProperties().get("gameId").equals(gamId)) {
                System.out.println(move);
                allSession.getBasicRemote().sendText(move);
                System.out.println(" Hurray Move Sent!");
            }
        }
    }

    public void sendStatus(String status, long gameId) throws IOException {
        String gamId = "" + gameId;
        for (Session allSession : allSessions) {
            System.out.println("inside Session");
            System.out.println("sess GameId " + allSession.getUserProperties().get("gameId"));
            if (allSession.getUserProperties().get("gameId").equals(gamId)) {
                allSession.getBasicRemote().sendText(status);
                System.out.println(" Hurray Status Sent!");
            }
        }
    }

}