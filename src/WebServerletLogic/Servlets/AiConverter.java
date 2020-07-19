package WebServerletLogic.Servlets;

import GameLogic.GameManager;
import GameLogic.KongAiConnector;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiConverter extends HttpServlet {
    PgnGenerator pg=null;
    GameManager gameManager=null;
    DBclass db=new DBclass();
    String move;
    int drawClaim;
    int moveNo;
    long gameId;
    long random=0;
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
            if(req.getServletPath().equals("/aiMove")){

                List<String> arr= getMovesArr(gameId);

                KongAiConnector kongAI = new KongAiConnector();
                HttpSession httpSession = req.getSession();
                String difficulty = (String) httpSession.getAttribute("Difficulty");
                String move = kongAI.getAIMove(Integer.parseInt(difficulty), gameId, moveNo, arr);
                System.out.println("AIMove" +move);

                gameManager.playGame(move);
                responseStep = gameManager.getLastMovementAsString();
                System.out.println("Responsestep" + responseStep);

                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(responseStep);
            }
           else{
            addMove(req);
           }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

    private List<String> getMovesArr(long gameID) throws Exception
    {
        String sql = "SELECT * FROM gamemoves WHERE GameID='" + gameID + "'";
        System.out.println(sql);

        DBclass dbConnector = new DBclass();
        dbConnector.callDB();
        PreparedStatement pst = dbConnector.con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        List<String> moves = new ArrayList<>();
        int j=0;
        while (rs.next()) {
            moves.add(rs.getString("moves"));
            System.out.println("moves"+moves.get(j));
            j++;
        }

        return moves;
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
                gameManager.playGame(move);
            }
            gameId= random;
            System.out.println("GameID" + gameId + "Random" + random);
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
                gameId = random = System.nanoTime();
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
