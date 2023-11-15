// integer board represents board from top left to bottom right, read like a book
public class Main {
    public static BitBoard[] allBoards = new BitBoard[8];
    public static short moveInfo;
    // reference the MoveInfo class for what this does
    public static void main(String[] args){
        //allBoards[0] will be 1s for if the piece is white, 0 otherwise
        //allBoards[1] will be 1s for pawns 0 otherwise
        //allBoards[2] will be 1s for bishops 0 otherwise.
        //allBoards[3] will be 1s for knights 0 otherwise..
        //allBoards[4] will be 1s for rooks 0 otherwise.
        //allBoards[5] will be 1s for queen 0 otherwise.
        //allBoards[6] will be 1s for king 0 otherwise.
        //allBoards[7] will be 1s for any piece and 0 otherwise
        moveInfo = 0b0111000000000000;
        allBoards[0] = new BitBoard(0B00000000_00000000_00000000_00000000_00000000_00000000_11111111_11111111L);
        allBoards[1] = new BitBoard(0B00000000_11111111_00000000_00000000_00000000_00000000_11111111_00000000L);
        allBoards[2] = new BitBoard(0B00100100_00000000_00000000_00000000_00000000_00000000_00000000_00100100L);
        allBoards[3] = new BitBoard(0B01000010_00000000_00000000_00000000_00000000_00000000_00000000_01000010L);
        allBoards[4] = new BitBoard(0B10000001_00000000_00000000_00000000_00000000_00000000_00000000_10000001L);
        allBoards[5] = new BitBoard(0B00010000_00000000_00000000_00000000_00000000_00000000_00000000_00010000L);
        allBoards[6] = new BitBoard(0B00001000_00000000_00000000_00000000_00000000_00000000_00000000_00001000L);
        allBoards[7] = new BitBoard(0B11111111_11111111_00000000_00000000_00000000_00000000_11111111_11111111L);
        new ChessGame();
        System.out.println(PrecomputedData.generateMoves(allBoards, moveInfo).size());
        for (short s : PrecomputedData.generateMoves(allBoards, moveInfo)) {
            System.out.println(MoveInfo.moveTranslator(s));
            for (int i = 15; i >= 0; i--) {
                System.out.print(Math.abs((s>>i)%2));
            }
            System.out.println();
            System.out.println();
        }
    }
}