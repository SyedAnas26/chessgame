package WebServerletLogic.Servlets;

import GameLogic.MultiPlayerManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MultiPlayerServlet extends HttpServlet {
MultiPlayerManager multiPlayerManager=new MultiPlayerManager();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath=req.getServletPath();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
        if(servletPath.equals("/createChallenge")) {
            long gameId = System.currentTimeMillis();
            String playAs = req.getParameter("createdPlayAs");
            String challengeType = req.getParameter("challengeType");
            int uniqueId = Integer.parseInt(req.getParameter("uniqueId"));
            String token=multiPlayerManager.createChallenge(gameId, uniqueId, playAs, challengeType);
            String response="{\"gameId\":\""+gameId+"\",\"token\":\""+token+"\"}";
           out.print(response);
        }
        else if(servletPath.equals("/joinGame")){
            String token=req.getParameter("tokenId");
          long gameId=multiPlayerManager.acceptChallenge(token);
            String response="{\"gameId\":\""+gameId+"\"}";
            out.print(response);
        }
        else if(servletPath.equals("/waiting")){
            String token=req.getParameter("tokenId");
            long gameId=multiPlayerManager.checkStatus(token);
            String response="{\"gameId\":\""+gameId+"\"}";
            out.print(response);
        }
        }catch (Exception e){
            System.out.println(e);
        }

    }

    }
