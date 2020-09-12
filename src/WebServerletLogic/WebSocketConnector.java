package WebServerletLogic;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/socket/{gameId}")
public class WebSocketConnector {

    private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());


    @OnOpen
    public void onOpen(@PathParam("gameId") String gameId,Session session) {
        System.out.println("onOpen::" + session.getId());
        System.out.println(" Before gameId="+gameId);
        session.getUserProperties().put("gameId",gameId);
        System.out.println("gameId added  OnOpen " +session.getUserProperties().get("gameId"));
        allSessions.add(session);
        System.out.println("Session Added");
    }

    @OnClose
    public void onClose(@PathParam("gameId") String gameId, Session session) throws IOException {
        System.out.println("onClose::" +  session.getId());
        allSessions.remove(session);
        System.out.println("Session Removed");
    }

    @OnMessage
    public void onMessage(@PathParam("gameId") String gameId,String message, Session session) throws Exception {
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);
        String gamId = "" + gameId;
            for (Session allSession : allSessions) {
                if (allSession.getUserProperties().get("gameId").equals(gamId)) {
                    if (!allSession.getId().equals(session.getId())) {
                        allSession.getBasicRemote().sendText(message);
                    }
                }
            }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
        t.printStackTrace();
    }

    public void sendMove(String move,long gameId) throws IOException {
        String gamId=""+gameId;
        for (Session allSession : allSessions) {
            if (allSession.getUserProperties().get("gameId").equals(gamId)) {
                System.out.println(move);
                allSession.getBasicRemote().sendText(move);
                System.out.println(" Hurray Move Sent!");
            }
        }
    }

    public void sendStatus(String status,long gameId) throws IOException {
        String gamId=""+gameId;
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