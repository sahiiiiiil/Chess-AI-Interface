import java.util.ArrayList;

public class Piece {
    private final char type;
    private final boolean isWhite;
    private int[] position; //position[0] = rank, position[1] = file

    public Piece(char type, int[] position, boolean isWhite) {
        this.type = type;
        this.position = position;
        this.isWhite = isWhite;
    }
    public char getType() {
        return this.type;
    }
    public boolean isWhite() {return isWhite;}
    public int[] getPosition() {
        return position;
    }
    public ArrayList<String> getAllMoves(String previous_move) {
        ArrayList<String> possibleMoves = new ArrayList<>(5);
        String move;
        if (isWhite) {
            if (type == 'p') {
                if (position[0] == 1 && isWhite) {
                    //2 square jump possible
                    move = "" + type + 1 + position[1] + 3 + position[1];
                    if (tryMove(move)) {possibleMoves.add(move);}
                }
            }
        }
        else {
            if (type == 'p') {
                if (position[0] == 6) {
                    //2 square jump possible
                    move = "" + type + 6 + position[1] + 4 + position[1];
                    if (tryMove(move)) {possibleMoves.add(move);}
                }
            }
        }


        return null;
    }
    public boolean tryMove(String move) {
        return true;
    }
}
