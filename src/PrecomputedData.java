import java.util.ArrayList;
public class PrecomputedData {
    public static final byte[] directions = {-8, 8, -1, 1, -9, -7, 7, 9};
    //up, down, left, right, up-left, up-right, down-left, down-right
    public static byte distance[][] = new byte[64][8];
    //same as before
    public static void precompute() {
        for (byte i = 0; i < 64; i++) {
            distance[i][0] = (byte)(i / 8);
            distance[i][1] = (byte)(7 - i / 8);
            distance[i][2] = (byte)(i % 8);
            distance[i][3] = (byte)(7 - (i % 8));
            distance[i][4] = (byte)(Math.min(distance[i][0], distance[i][2]));
            distance[i][5] = (byte)(Math.min(distance[i][0], distance[i][3]));
            distance[i][6] = (byte)(Math.min(distance[i][1], distance[i][2]));
            distance[i][7] = (byte)(Math.min(distance[i][1], distance[i][3]));
        }
    }
    public static ArrayList<Byte[]> generateMoves(BitBoard[] boards) {
        ArrayList<Byte[]> moves = new ArrayList<>(10);
        for (int square = 0; square < 64; square++) {
            if (boards[1].get((byte)square)) {
                //There is a pawn here
                //generate all pawn moves
            }
            if (boards[2].get((byte)square)) {
                //There is a bishop here
                //generate all bishop moves
            }
        }
        return moves;
    }
}
