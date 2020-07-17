package WebServerletLogic.Servlets;

import GameLogic.PgnGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AiConverter extends HttpServlet {
    PgnGenerator pg=new PgnGenerator();
    DBclass db=new DBclass();
    int i=0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromPos=req.getParameter("fromMove");
        String toPos=req.getParameter("toMove");

        try {
            String move=pg.convertToPgn(fromPos,toPos);
            System.out.println("Move "+move);
            HttpSession session = req.getSession();
            int gameId= (int) session.getAttribute("random");
            int moveNo= (int) session.getAttribute("moveNo")+i;
            int time =0;
            int dcs=0;
            db.callDB();
            db.stmt.executeUpdate("insert into gamemoves (GameID,MoveNo,Moves,TimeTaken,DrawClaimedStatus) values('"+gameId+"','"+moveNo+"','"+move+"','"+time+"','"+dcs+"')");
            i++;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
