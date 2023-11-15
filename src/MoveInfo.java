public class MoveInfo {
    // first bit is empty
    // second bit is if white can castle (1 is yes)
    // third bit is if black can castle (1 is yes)
    // fourth bit is if it is white's turn or not (1 is yes)
    // bits 5-10 are the starting square of the previous move
    // last 6 bits are the ending square of the previous move
    public static boolean whiteCanCastle(short prev) {return prev>>14 != 0;}
    public static boolean blackCanCaste(short prev) {return prev>>13 != 0;}
    public static boolean isWhiteTurn(short prev) {return prev>>12 != 0;}
    public static byte whiteTurnBinary(short prev) {return (byte)((prev>>12)%2);}
    public static byte previousStartSquare(short prev) {return (byte)((prev>>6)%64);}
    public static byte previousEndSquare(short prev) {return (byte)(prev%64);}
    public static String moveTranslator(short move) {
        String s  = "";
        int end = 0;
        int start = 0;
        int piece = 0;
        for (int i = 0; i < 6; i++) {
            end += Math.abs(((move>>i)%2)) * Math.pow(2, i);
            System.out.println("Current BIN: " + Math.abs((move>>i)%2) + ", current end: " + end);
        }
        for (int i = 6; i < 12; i++) {
            start += Math.abs(((move>>i)%2)) * Math.pow(2, i-6);
        }
        for (int i = 12; i < 15; i++) {
            piece += Math.abs(((move>>i)%2)) * Math.pow(2, i-12);
        }
        //System.out.println(move>>12);
        if (piece == 0) {s+= "Pawn ";}
        else if (piece == 1) {s+= "Bishop ";}
        else if (piece == 2) {s+= "Knight ";}
        else if (piece == 3) {s+= "Rook ";}
        else if (piece == 4) {s+= "Queen ";}
        else if (piece == 5) {s+= "King ";}
        else if (piece == 6) {s+= "Pawn promote ";}
        else {s+= "En passant ";}
        if (move < 0 && !s.equals("En passant ")) {
            s+= "captures ";
        }
        System.out.println(start);
        System.out.println(end);
        System.out.println(piece);
        if (start%8 == 0) {
            s += "from " + 'A' + ((64 - start) / 8);
        }
        else {
            s += "from " + ((char) (65 + (start % 8))) + ((64 - start) / 8 + 1);
        }
        if (end%8 ==0) {
            s += " to " + 'A' + ((64 - end) / 8);
        }
        else {
            s += " to " + ((char) (65 + (end % 8))) + ((64 - end) / 8 + 1);
        }
        return s;
    }
}
