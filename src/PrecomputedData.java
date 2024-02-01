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
    public static ArrayList<Short> generateMoves(BitBoard[] boards, short moveInfo) {
        System.out.println("white turn: " + MoveInfo.isWhiteTurn(moveInfo));
        ArrayList<Short> moves = new ArrayList<>(10);
        // 0 (capture or not) 000 (piece moved) 000000 (start square) 000000 (end square)
        // piece moved will be: 000 pawn, 001 bishop, 010 knight, 011 rook, 100 queen, 101 king, 110 promoting pawn, 111 en passant
        for (int square = 0; square < 64; square++) {
            if (boards[1].get(square) == 1 && boards[0].get(square) == MoveInfo.whiteTurnBinary(moveInfo)) {
                //There is a pawn here
                //generate all pawn moves
                if (square >= 48 && boards[0].get(square) == 1 && boards[9].get(whiteKing) == 0) {
                    // first move for this white pawn
                    if (boards[7].get(square-8) == 0 && boards[7].get(square-16) == 0 ) {
                        moves.add((short)((square<<6)+square+2*directions[0]));
                    }
                }
                if (square <= 15 && boards[0].get(square) == 0  && boards[8].get(blackKing) == 0
                        && boards[10].get(square) == 0 ) {
                    // first move for this black pawn
                    if (boards[7].get(square+8) == 0 && boards[7].get(square+16) == 0) {
                        moves.add((short)((square<<6)+square+2*directions[1]));
                    }
                }
                //double jumps done
                if (boards[7].get(square+directions[1-MoveInfo.whiteTurnBinary(moveInfo)])==0
                        && ((boards[0].get(square) ==0
                        && boards[8].get(blackKing) == 0
                        && boards[10].get(square) == 0) || (boards[0].get(square) ==1
                        && boards[9].get(whiteKing) == 0
                        && boards[11].get(square) == 0))) {
                    if (square+directions[1-MoveInfo.whiteTurnBinary(moveInfo)] < 8
                        || square+directions[1-MoveInfo.whiteTurnBinary(moveInfo)] > 56) {
                        // this tastes like promotion
                        moves.add((short)(0b0110000000000000 + (square<<6) + square+directions[1-MoveInfo.whiteTurnBinary(moveInfo)]));
                    }
                    else {
                        moves.add((short)((square<<6) + square + directions[1-MoveInfo.whiteTurnBinary(moveInfo)]));
                    }
                }
                //forward jumps done
                if (MoveInfo.isWhiteTurn(moveInfo) && boards[7].get(square-7)!=0 && boards[0].get(square-7) != MoveInfo.whiteTurnBinary(moveInfo) && square%8 != 7
                        && boards[9].get(whiteKing) == 0
                        && boards[11].get(square) == 0) {
                    if (square-7 < 8) {
                        // this tastes like promotion
                        moves.add((short)(0b1110000000000000 + (square<<6) + square-7));
                    }
                    else {
                        moves.add((short)(0b1000000000000000 + (square<<6) + square-7));
                    }
                }
                if (MoveInfo.isWhiteTurn(moveInfo) && boards[7].get(square-9)!=0 && square%8 != 0) {
                    if (square-9 < 8) {
                        // this tastes like promotion
                        moves.add((short)(0b1110000000000000 + (square<<6) + square-9));
                    }
                    else {
                        moves.add((short)(0b1000000000000000 + (square<<6) + square-9));
                    }
                }
                if (!MoveInfo.isWhiteTurn(moveInfo) && boards[7].get(square+7)!=0 && boards[0].get(square+7) != MoveInfo.whiteTurnBinary(moveInfo) && square%8 != 0
                        && boards[8].get(blackKing) == 0
                        && boards[10].get(square) == 0) {
                    if (square+7 > 56) {
                        // this tastes like promotion
                        moves.add((short)(0b1110000000000000 + (square<<6) + square+7));
                    }
                    else {
                        moves.add((short)(0b1000000000000000 + (square<<6) + square+7));
                    }
                }
                if (!MoveInfo.isWhiteTurn(moveInfo) && boards[7].get(square+9)!=0 && boards[0].get(square+9) != MoveInfo.whiteTurnBinary(moveInfo) && square%8 != 7
                        && boards[8].get(blackKing) == 0
                        && boards[10].get(square) == 0) {
                    if (square+9 >56) {
                        // this tastes like promotion
                        moves.add((short)(0b1110000000000000 + (square<<6) + square+9));
                    }
                    else {
                        moves.add((short)(0b1000000000000000 + (square<<6) + square+9));
                    }
                }
                //regular attacking moves done
                if (square+1 == MoveInfo.previousEndSquare(moveInfo)
                    && square%8 != 7
                    && boards[1].get(square+1) != 0
                    && boards[0].get(square+1) != boards[0].get(square)
                    && Math.abs(MoveInfo.previousStartSquare(moveInfo)-MoveInfo.previousEndSquare(moveInfo)) == 16
                        && ((boards[0].get(square) ==0
                        && boards[8].get(blackKing) == 0
                        && boards[10].get(square) == 0) || (boards[0].get(square) ==1
                        && boards[9].get(whiteKing) == 0
                        && boards[11].get(square) == 0)))  {
                    // en passant
                    if (MoveInfo.isWhiteTurn(moveInfo)) {
                        moves.add((short)(0b1111000000000000 + (square<<6) + square-7));
                        System.out.println("Found an en passant move");
                    }
                    else {
                        moves.add((short)(0b1111000000000000 + (square<<6) + square+9));
                        System.out.println("Found an en passant move");
                    }
                }
                if (square-1 == MoveInfo.previousEndSquare(moveInfo)
                    && square%8 != 0
                    && boards[1].get(square-1) != 0
                    && boards[0].get(square-1) != boards[0].get(square)
                    && Math.abs(MoveInfo.previousStartSquare(moveInfo)-MoveInfo.previousEndSquare(moveInfo)) == 16
                        && ((boards[0].get(square) ==0
                        && boards[8].get(blackKing) == 0
                        && boards[10].get(square) == 0) || (boards[0].get(square) ==1
                        && boards[9].get(whiteKing) == 0
                        && boards[11].get(square) == 0)))  {
                    // en passant
                    if (MoveInfo.isWhiteTurn(moveInfo)) {
                        moves.add((short)(0b1111000000000000 + (square<<6) + square-9));
                        System.out.println("Found an en passant move");
                    }
                    else {
                        moves.add((short)(0b1111000000000000 + (square<<6) + square+7));
                        System.out.println("Found an en passant move");
                    }
                }
            }


            if ((boards[2].get(square) == 1 || boards[5].get(square) == 1)
                && boards[0].get(square) == MoveInfo.whiteTurnBinary(moveInfo)  && ((boards[0].get(square) ==0
                    && boards[8].get(blackKing) == 0
                    && boards[10].get(square) == 0) || (boards[0].get(square) ==1
                    && boards[9].get(whiteKing) == 0
                    && boards[11].get(square) == 0))) {
                //There is a bishop or queen here
                //generate all bishop moves
                for (int direction = 4; direction < 8; direction++) {
                    int nextSquare = square+directions[direction];
                    for (int i = 0; i < distance[square][direction]; i++) {
                        if (boards[7].get(nextSquare) != 0) {
                            if (boards[0].get(nextSquare)==MoveInfo.whiteTurnBinary(moveInfo)) {
                                //it is a teammate
                                break;
                            }
                            else {
                                if (boards[5].get(square) == 1) { // its a queen
                                    moves.add((short) (0b1100000000000000 + (square << 6) + nextSquare));
                                }
                                else {
                                    moves.add((short) (0b1001000000000000 + (square << 6) + nextSquare));
                                }
                                break;
                            }
                        }
                        else {
                            if (boards[5].get(square) == 1) { // its a queen
                                moves.add((short) (0b0100000000000000 + (square << 6) + nextSquare));
                            }
                            else {
                                moves.add((short) (0b0001000000000000 + (square << 6) + nextSquare));
                            }
                        }
                        nextSquare = nextSquare+directions[direction];
                    }
                }
            }


            if (boards[3].get(square) == 1 && boards[0].get(square) == MoveInfo.whiteTurnBinary(moveInfo) && ((boards[0].get(square) ==0
                    && boards[8].get(blackKing) == 0
                    && boards[10].get(square) == 0) || (boards[0].get(square) ==1
                    && boards[9].get(whiteKing) == 0
                    && boards[11].get(square) == 0))) {
                //There is a knight here
                //generate all knight moves
                int rank = square/8;
                int file = square%8;
                if (rank > 1 && file > 0) { //up up left
                    if (boards[7].get(square-17) == 0) { // no capture
                        moves.add((short)(0b0010000000000000 + (square<<6) + square-17));
                    }
                    else if (boards[0].get(square-17) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square-17));
                    }
                }
                if (rank > 1 && file < 7) { //up up right
                    if (boards[7].get(square-15) == 0) { // no capture
                        moves.add((short)(0b0010000000000000 + (square<<6) + square-15));
                    }
                    else if (boards[0].get(square-15) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square-15));
                    }
                }
                if (rank < 6 && file > 0) { //down down left
                    System.out.println("Down down left possible, square: "+ boards[7].get(square+15));
                    if (boards[7].get(square+15) == 0) { // no capture
                        moves.add((short)(0b0010000000000000 + (square<<6) + square+15));
                    }
                    else if (boards[0].get(square+15) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square+15));
                    }
                }
                if (rank < 6 && file < 7) { //down down right
                    System.out.println("Down down right possible, square: " + boards[7].get(square+17));
                    if (boards[7].get(square+17) == 0) { // no capture
                        moves.add((short)(0b0010000000000000 + (square<<6) + square+17));
                    }
                    else if (boards[0].get(square+17) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square+17));
                    }
                }
                if (rank > 0 && file > 1) { //up left left
                    if (boards[7].get(square-10) == 0) { // no capture
                        moves.add((short)(0b0010000000000000 + (square<<6) + square-10));
                    }
                    else if (boards[0].get(square-10) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square-10));
                    }
                }
                if (rank > 0 && file < 6) { //up right right
                    if (boards[7].get(square-6) == 0) { // no capture
                        moves.add((short)(0b0010000000000000 + (square<<6) + square-6));
                    }
                    else if (boards[0].get(square-6) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square-6));
                    }
                }
                if (rank < 7 && file > 1) { //down left left
                    System.out.println("Down left left possible, square: " + boards[7].get(square+6));
                    if (boards[7].get(square+6) == 0) { // no capture
                        moves.add((short)(0b0010000000000000 + (square<<6) + square+6));
                    }
                    else if (boards[0].get(square+6) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square+6));
                    }
                }
                if (rank < 7 && file < 6) { //down right right
                    System.out.println("Down right right possible, square: " + boards[7].get(square+10));
                    if (boards[7].get(square+10) == 0) { // no capture
                        System.out.println("Piece Board: " + ChessGame.toBinary(boards[7].board));
                        System.out.println(boards[7].get(square+10) == 0);
                        moves.add((short)(0b0010000000000000 + (square<<6) + square+10));
                    }
                    else if (boards[0].get(square+10) != MoveInfo.whiteTurnBinary(moveInfo)) {
                        moves.add((short)(0b1010000000000000 + (square<<6) + square+10));
                    }
                }
            }


            if ((boards[4].get(square) != 0 || boards[5].get(square) != 0)
                    && boards[0].get(square) == MoveInfo.whiteTurnBinary(moveInfo) && ((boards[0].get(square) ==0
                    && boards[8].get(blackKing) == 0
                    && boards[10].get(square) == 0) || (boards[0].get(square) ==1
                    && boards[9].get(whiteKing) == 0
                    && boards[11].get(square) == 0))) {
                //There is a rook or queen here
                //generate all rook moves
                for (int direction = 0; direction < 4; direction++) {
                    int nextSquare = square+directions[direction];
                    for (int i = 0; i < distance[square][direction]; i++) {
                        if (boards[7].get(nextSquare) != 0) {
                            if (boards[0].get(nextSquare)==MoveInfo.whiteTurnBinary(moveInfo)) {
                                //it is a teammate
                                break;
                            }
                            else {
                                if (boards[5].get(square) == 1) { // its a queen
                                    moves.add((short) (0b1100000000000000 + (square << 6) + nextSquare));
                                }
                                else {
                                    moves.add((short) (0b1011000000000000 + (square << 6) + nextSquare));
                                }
                                break;
                            }
                        }
                        else {
                            if (boards[5].get(square) == 1) { // its a queen
                                moves.add((short) (0b0100000000000000 + (square << 6) + nextSquare));
                            }
                            else {
                                moves.add((short) (0b0011000000000000 + (square << 6) + nextSquare));
                            }
                        }
                        nextSquare = nextSquare+directions[direction];
                    }
                }
            }


            if (boards[6].get(square) == 1 && boards[0].get(square) == MoveInfo.whiteTurnBinary(moveInfo)) {
                //There is a king here
                //generate all king moves including castling
                for (int i = 0; i < 8; i++) {
                    if (distance[square][i] > 0) {
                        if (boards[7].get(square+directions[i]) == 0) {
                            moves.add((short)(0b0101000000000000 + (square<<6) + square+directions[i]));
                        }
                        else if (boards[0].get(square+directions[i])!=MoveInfo.whiteTurnBinary(moveInfo)) {
                            moves.add((short)(0b1101000000000000 + (square<<6) + square+directions[i]));
                        }
                    }
                }
                // add castling
            }


        }
        return moves;
    }
}
