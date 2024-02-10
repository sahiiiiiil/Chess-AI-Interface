import java.util.HashMap;

public class Bot {
    public static short getBestMove(int depth, MoveHelper info) {
        minimax(depth, info, false);
        return 0;
    }
    private static double minimax(int depth, MoveHelper info, boolean lastMoveCapture) {
//        boolean wasCapture = prevMove < 0;
//        if ((depth == 0 && !wasCapture) || depth < -3) {return evaluate(boards);}
//
        return 0;
    }
    private static double evaluate(BitBoard[] boards) {return 0;}
}
