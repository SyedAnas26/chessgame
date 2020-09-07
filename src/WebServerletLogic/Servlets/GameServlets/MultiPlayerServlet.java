package WebServerletLogic.Servlets.GameServlets;

import GameLogic.Managers.MultiPlayerManager;

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
        int uniqueId;String response;
        try {
        switch (servletPath)
        {
            case "/createChallenge":
            String playAs = req.getParameter("createdPlayAs");
            String challengeType = req.getParameter("challengeType");
            uniqueId = Integer.parseInt(req.getParameter("uniqueId"));
            response=multiPlayerManager.createChallenge(uniqueId, playAs, challengeType);
            out.print(response);

            case "/joinGame":
            String token=req.getParameter("tokenId");
            uniqueId=Integer.parseInt(req.getParameter("uniqueId"));
            System.out.println("token "+token);
            response=multiPlayerManager.acceptChallenge(token,uniqueId);
            System.out.println("response "+response);
            out.print(response);
        }
        }catch (Exception e){
            System.out.println("MultiPlayerServlet Exception "+e);
            System.out.println("Line No "+e.getStackTrace()[0].getLineNumber());
        }
    }
    }
