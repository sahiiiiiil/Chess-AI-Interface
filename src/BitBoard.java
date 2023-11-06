//this will work as a 64 bit integer to store 1s and 0s representing pieces
public class BitBoard {
    //0 is A8, 64 is G1
    private long board;
    public BitBoard(long board){
        this.board = board;
    }
    public long getBoard(){
        return this.board;
    }
    public byte get(int index) {
        return (byte)((board>>index)%2);
    }
}
