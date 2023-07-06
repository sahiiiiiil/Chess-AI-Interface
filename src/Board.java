import java.util.ArrayList;

public class Board {
    private Piece[][] board1;
    public Board(Piece[][] board1){
        this.board1 = board1;
    }
    public Piece[][] getBoard(){
        return this.board1;
    }
    public ArrayList<String> getAllMoves() {
        for (int i = 0; i< getBoard().length; i++){
            for (int j =0; j<getBoard()[0].length; j++){
                if (getBoard()[i][j] != null){
                    getBoard()[i][j].getAllMoves(null);
                }
            }
        }
        return null;
    }
    public Piece getPiece(int rank, int file) {
        return board1[rank][file];
    }
}
