package ConsoleUiLogic;

//public class TheChessGame {
//    public static void main(String[] args) throws Exception {
////        String file = "src/ConsoleUiLogic/ChessPgn.txt";
////        BufferedReader br = new BufferedReader(new FileReader(file));
////        String line = br.readLine();
////        StringBuilder sb = new StringBuilder();
////        String gamePlay = null;
////        while (line != null) {
////            sb.append(line).append("\n");
////            line = br.readLine();
////            gamePlay = sb.toString();
////        }
////        new ConsoleUi(gamePlay);
//        int result = 0;
//        for (int i = 0; i < 5; i++) {
//            if (i == 3) {
//                result += 10;
//            } else {
//                result += i;
//            }
//        }
//        System.out.println(result);
//
//    }
//}
interface calculate
{
    void cal(int item);
}
class display implements calculate
{
    int x;
    public void cal(int item)
    {
        x = item * item;
    }
}
class InterfaceExample
{
    public static void main(String args[])
    {
        display arr = new display();
        arr.x = 0;
        arr.cal(2);
        System.out.print(arr.x);
    }
}


