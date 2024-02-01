import java.util.HashMap;

public class Bot {
    private BitBoard[] realBoards;
    public Bot() {
        realBoards = Main.allBoards;
    }
    public short getBestMove(int depth) {
        minimax(depth, realBoards);
        return 0;
    }
    private double minimax(int depth, BitBoard[] boards) {
        if (depth == 0) {return evaluate(boards);}
        return 0;
    }
    private double evaluate(BitBoard[] boards) {
        return 0;
    }
}
