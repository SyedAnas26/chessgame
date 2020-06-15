package WebServerletLogic;

import GameLogic.GameManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChessServlet extends HttpServlet {

    GameManager manager= null;
    int step = 0;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String gamePlay = "1.e4 c5 2.Nf3 Nc6 3.d4 cxd4 4.Nxd4 Nf6 5.Nc3 d6 6.Be3 Ng4 7.Bg5 Qb6 8.Bb5 Bd7 " +
                "9.O-O Qxd4 10.Bxc6 Qxd1 11.Bxd7 Kxd7 12.Raxd1 g6 13.h3 Ne5 14.Nd5 Nc6 15.b4 h6 " +
                "16.Bh4 f5 17.f4 Rg8 18.b5 Na5 19.e5 Nc4 20.Rd4 Rc8 21.e6 Ke8 22.b6 axb6 " +
                "23.Rb1 g5 24.Rb4 b5 25.Rxb5 Bg7 26.Rxc4 Rxc4 27.Rxb7 Bd4 28.Bf2 Bxf2 29.Kxf2 Kf8 " +
                "30.Rxe7 Rg7 31.Rd7 Rxc2 32.Kf3 Rg6 33.Rd8 Kg7 34.e7 g4 35.hxg4 fxg4 36.Kg3 ";

        //gamePlay = req.getParameter("game_play");

        try {

            if(manager == null || Boolean.valueOf(req.getParameter("restart_game")))
            {
                manager = new GameManager(gamePlay);
                step = 0;
            }



            manager.conductGame(step);
            step++;

            printBoardInClient(manager, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object gamePlay = req.getParameter("game_play");
        System.out.println("Hello world Done" + gamePlay);
    }

    void printBoardInClient(GameManager manager, HttpServletResponse response) throws IOException {
       System.out.println("Im in printboard");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Chess State!</title>");
        out.println("</head>");
        out.println("<body>");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece=manager.b.cell[i][j].getPieceType();
                if(piece==null){
                    out.printf("\t|   |");
                }
                else {
                    out.printf("\t|%3s|",piece);
                }
                if (j == 7) {
                    out.printf("<br>");
                }
            }
        }

        out.println("</body>");
        out.println("</html>");
    }


}
