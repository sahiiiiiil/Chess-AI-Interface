import java.util.ArrayList;

public class Bot {
    public static short getBestMove(int depth, MoveHelper info) {
        minimax(depth, info, false, -Double.MAX_VALUE, Double.MAX_VALUE);
        return 0;
    }
    private static double minimax(int depth, MoveHelper info, boolean lastMoveCapture, double alpha, double beta) {
        if ((depth == 0 && lastMoveCapture) || depth < -3) {return evaluate(info.boards);}
        depth--;
        ArrayList<Short> moves = PrecomputedData.generateMoves(info.boards, info.moveInfo, info.whiteKing, info.blackKing);
        //double[] scores =
        return 0;
    }
    private static double evaluate(BitBoard[] boards) {
        //check stalemate
        //otherwise
        return 0;
    }
}
