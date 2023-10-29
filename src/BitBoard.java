//this will work as a 64 bit integer to store 1s and 0s representing pieces
public class BitBoard {
    private long board;
    public BitBoard(long board){
        this.board = board;
    }
    public long getBoard(){
        return this.board;
    }
}
