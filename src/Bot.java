import java.util.ArrayList;

public class Bot {
    public static short getBestMove(int depth, MoveHelper info) {
        minimax(depth, info, false, -Double.MAX_VALUE, Double.MAX_VALUE);
        return 0;
    }
    private static double minimax(int depth, MoveHelper info, boolean lastMoveCapture, double alpha, double beta) {
        if ((depth == 0 && !lastMoveCapture) || depth < -3) {return evaluate(info.boards);}
        depth--;
        ArrayList<Short> moves = PrecomputedData.generateMoves(info.boards, info.moveInfo, info.whiteKing, info.blackKing);
        double[] scores = new double[moves.size()];
        for (int i = 0; i < moves.size(); i++) {
            MoveHelper mh = new MoveHelper(new BitBoard[0], (short)0, 0, 0);
            mh.copyAll(info);
            mh.makeMove(moves.get(i));
            scores[i] = minimax(depth--, mh, moves.get(i) < 0, alpha, beta);
            if (MoveInfo.isWhiteTurn(info.moveInfo)) {
                alpha = Double.max(alpha, scores[i]);
            } else {
                beta = Double.min(beta, scores[i]);
            }
            if (beta <= alpha) {
                break;
            }
        }
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > max) {max = scores[i];}
            if (scores[i] < min) {min = scores[i];}
        }
        if (MoveInfo.isWhiteTurn(info.moveInfo)) {return max;}
        else{return min;}
    }
    private static double evaluate(BitBoard[] boards) {
        //check stalemate
        //return 0;
        //otherwise
        //1000 points per queen
        long inverseWhiteBits = flipBits(boards[0].getBoard());
        double score = 1000 * (bitCount(boards[0].getBoard() & boards[5].getBoard())-bitCount(inverseWhiteBits & boards[5].getBoard()));
        score += 525 * (bitCount(boards[0].getBoard() & boards[4].getBoard())-bitCount(inverseWhiteBits & boards[4].getBoard()));
        score += 350 * (bitCount(boards[0].getBoard() & boards[3].getBoard())-bitCount(inverseWhiteBits & boards[3].getBoard()));
        score += 350 * (bitCount(boards[0].getBoard() & boards[2].getBoard())-bitCount(inverseWhiteBits & boards[2].getBoard()));
        return score;
    }
    public static long bitCount(long u) {
        u = u - ((u >>> 1) & 0x5555555555555555L);
        u = (u & 0x3333333333333333L) + ((u >>> 2) & 0x3333333333333333L);
        u = (u + (u >>> 4)) & 0x0F0F0F0F0F0F0F0FL;
        u = u + (u >>> 8);
        u = u + (u >>> 16);
        u = u + (u >>> 32);
        return u & 0x7F;
    }
    public static long flipBits(long num) {
        // Create a mask with all bits set to 1
        long mask = ~0L;

        // Use bitwise XOR to flip the bits
        return num ^ mask;
    }
}//
