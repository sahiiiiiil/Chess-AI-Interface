// integer board represents board from top left to bottom right, read like a book
public class Main {
    public static BitBoard[] allBoards = new BitBoard[7];
    public static byte moveInfo;
    // first bit is if white can castle (1 is yes)
    // second bit is if black can castle (1 is yes)
    // last 6 bits are the ending square of the previous move if it was a pawn double jump
    public static void main(String[] args){
        //allBoards[0] will be 1s for if the piece is white, 0 otherwise
        //allBoards[1] will be 1s for pawns 0 otherwise
        //allBoards[2] will be 1s for bishops 0 otherwise.
        //allBoards[3] will be 1s for knights 0 otherwise..
        //allBoards[4] will be 1s for rooks 0 otherwise.
        //allBoards[5] will be 1s for queen 0 otherwise.
        //allBoards[6] will be 1s for king 0 otherwise.
        moveInfo = (byte) 0b11000000;
        allBoards[0] = new BitBoard(0B00000000_00000000_00000000_00000000_00000000_00000000_11111111_11111111L);
        allBoards[1] = new BitBoard(0B00000000_11111111_00000000_00000000_00000000_00000000_11111111_00000000L);
        allBoards[2] = new BitBoard(0B00100100_00000000_00000000_00000000_00000000_00000000_00000000_00100100L);
        allBoards[3] = new BitBoard(0B01000010_00000000_00000000_00000000_00000000_00000000_00000000_01000010L);
        allBoards[4] = new BitBoard(0B10000001_00000000_00000000_00000000_00000000_00000000_00000000_10000001L);
        allBoards[5] = new BitBoard(0B00010000_00000000_00000000_00000000_00000000_00000000_00000000_00010000L);
        allBoards[6] = new BitBoard(0B00001000_00000000_00000000_00000000_00000000_00000000_00000000_00001000L);
        new ChessGame();
    }
}