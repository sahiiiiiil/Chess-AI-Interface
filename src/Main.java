// integer board represents board from top left to bottom right, read like a book
public class Main {
    public static BitBoard[] allBoards = new BitBoard[8];
    public static short moveInfo;
    // reference the MoveInfo class for what this does
    public static void main(String[] args){
        //allBoards[0] will be 1s for if the piece is white, 0 otherwise.
        //allBoards[1] will be 1s for pawns 0 otherwise.
        //allBoards[2] will be 1s for bishops 0 otherwise.
        //allBoards[3] will be 1s for knights 0 otherwise.
        //allBoards[4] will be 1s for rooks 0 otherwise.
        //allBoards[5] will be 1s for queen 0 otherwise.
        //allBoards[6] will be 1s for king 0 otherwise.
        //allBoards[7] will be 1s for any piece and 0 otherwise.
        //allBoards[8] will be 1s if white is attacking that square
        //allBoards[9] will be 1s if black is attacking that square
        //allBoards[10] will be 1s if the black piece on that square is pinned to the black king
        //allBoards[11] will be 1s if the white piece on that square is pinned to the white king
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
        }
    }
    public static int getPieceOn(int square) {
        for (int i = 1; i < 7; i++) {
            if (allBoards[i].get(square) == 1){
                if (allBoards[0].get(square) == 1) {i+=8;}
                return i;
            }
        }
        return 0;
    }


}