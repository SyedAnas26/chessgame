package WebServerletLogic.Servlets;

import GameLogic.GameManager;
import GameLogic.PgnGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Random;

public class AiConverter extends HttpServlet {
    PgnGenerator pg=null;
    GameManager gameManager=null;
    DBclass db=new DBclass();
    String move;
    int drawClaim;
    int moveNo;
    int gameId;
    int random=0;
    int i=0;
    int time =0;
    String aimove;
    String responseStep;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            isNew(req);
            if(req.getServletPath().equals("/aimoves")){
                aimove=req.getParameter("aimove");
                gameManager.playGame(aimove);
                responseStep =gameManager.getLastMovementAsString();
            }
           else if(req.getServletPath().equals("/aiMove")){
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(responseStep);
            }
           else{
            addMove(req);
            createMovesArr(req);
           }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

    private void createMovesArr(HttpServletRequest req) throws SQLException {
        HttpSession session=req.getSession();
        String sql = "SELECT * FROM gamemoves WHERE GameID='" + gameId + "'";
        System.out.println(sql);
        PreparedStatement pst = db.con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmetadata = rs.getMetaData();
        int columns = rsmetadata.getColumnCount();
        String[] Moves = new String[columns];
        int j=0;
        while (rs.next()) {
            Moves[j] = rs.getString("Moves");
            System.out.println("moves"+Moves[j]);
            j++;
        }
        String[] Moves1=new String[]{"e4"};
        session.setAttribute("MovesArr",Moves1);
    }

    private void addMove(HttpServletRequest req) {
        String fromPos=req.getParameter("fromMove");
        String toPos=req.getParameter("toMove");
        try {
            if(req.getServletPath().equals("/drawclaim")) {
                String drawclaim = req.getParameter("claimDraw");
                if (drawclaim != null) {
                    drawClaim = Integer.parseInt(drawclaim);
                    move ="Draw Claimed";
                }
            }
            else{
                move=pg.convertToPgn(fromPos,toPos);
            }
            gameId= random;
            db.callDB();
            db.stmt.executeUpdate("insert into gamemoves (GameID,MoveNo,Moves,TimeTaken,DrawClaimedStatus) values('"+gameId+"','"+moveNo+"','"+move+"','"+time+"','"+drawClaim+"')");
            moveNo++;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void isNew(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String NewOrOld = (String) session.getAttribute("NewOrOldGame");
        if(NewOrOld.equals("New")) {
            pg=new PgnGenerator();
            gameManager=new GameManager();
            drawClaim=0;
            moveNo=1;
            try {
                random = CreateNewRandom();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            session.setAttribute("NewOrOldGame", "Old");
        }
    }
    int CreateNewRandom() throws Exception {
        Random rand=new Random();
        int random1=Math.abs(rand.nextInt());
        System.out.println(random1);
        boolean ck=Check(random1);
        if(!ck){
            random1=CreateNewRandom();
        }
        return random1;
    }
    boolean Check(int random) throws Exception {

        db.callDB();
        System.out.println(random);
        String q = "SELECT * FROM gamemoves WHERE GameID='" + random + "'";
        System.out.println(q);
        ResultSet res= db.stmt.executeQuery(q);
        if (!res.next()) {
            return true;
        }
        else {
            return false;
        }
    }
}
