import java.util.ArrayList;

public class Piece {
    private final String type;
    private final boolean isWhite;
    private int[] position; //position[0] = rank, position[1] = file

    public Piece(String type, int[] position, boolean isWhite) {
        this.type = type;
        this.position = position;
        this.isWhite = isWhite;
    }
    public String getType() {
        return this.type;
    }
    public int[] getPosition() {
        return position;
    }
    public ArrayList<String> getAllMoves() {
        ArrayList<String> possibleMoves = new ArrayList<>(5);
        if (type.equals("p")) {
            if (position[0] == 6) {

            }
        }
        return null;
    }
    public boolean tryMove(String move) {
        return true;
    }
}
