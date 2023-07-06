public class Board {
    private Piece[][] board1;
    public Board(Piece[][] board1){
        this.board1 = board1;
    }
    public Piece[][] getBoard(){
        return this.board1;
    }
}
