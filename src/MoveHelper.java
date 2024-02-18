public class MoveHelper {
    //this class is just to help with data transfer for making moves
    public BitBoard[] boards;
    public short moveInfo;
    public int whiteKing;
    public int blackKing;
    public MoveHelper(BitBoard[] boards, short moveInfo, int whiteKing, int blackKing){
        this.boards = boards;
        this.moveInfo = moveInfo;
        this.whiteKing = whiteKing;
        this.blackKing = blackKing;
    }
    public void makeMove(short move) {
        int fromSquare = Math.abs(move>>6)%64;
        int toSquare = Math.abs(move)%64;
        int pieceIndex = Math.abs(move>>12)%8+1;
        if (pieceIndex > 6) {pieceIndex = 1;}
        int isWhiteBin = this.boards[0].get(fromSquare);

        // Clear the from bit
        this.boards[pieceIndex].board = ChessGame.setBit(this.boards[pieceIndex].board, 63 - fromSquare, (short) 0);
        // Set the to bit
        for (int i = 1; i < 7; i++) {
            short b = 0;
            if (i == pieceIndex) {
                b = 1;
            }
            this.boards[i].board = ChessGame.setBit(this.boards[i].board, 63 - toSquare, b);
        }
        if(pieceIndex==6){
            if(this.boards[0].get(toSquare) == 1){
                this.whiteKing = toSquare;
            } else{
                this.blackKing = toSquare;
            }
        }
        boolean enPassant = Math.abs((move>>12)%2) == 1 && Math.abs((move>>13)%2) == 1
                && Math.abs((move>>14)%2) == 1 && Math.abs((move>>15)%2) == 1;
        if (enPassant && MoveInfo.isWhiteTurn(moveInfo)) {
            this.boards[1].board = ChessGame.setBit(this.boards[1].board, 63 - toSquare - 8, (short)0);
            this.boards[0].board = ChessGame.setBit(this.boards[0].board, 63 - toSquare - 8, (short)0);
            this.boards[7].board = ChessGame.setBit(this.boards[7].board, 63 - toSquare - 8, (short)0);
        }
        else if (enPassant && !MoveInfo.isWhiteTurn(moveInfo)) {
            this.boards[1].board = ChessGame.setBit(this.boards[1].board, 63 - toSquare + 8, (short)0);
            this.boards[0].board = ChessGame.setBit(this.boards[0].board, 63 - toSquare + 8, (short)0);
            this.boards[7].board = ChessGame.setBit(this.boards[7].board, 63 - toSquare + 8, (short)0);
        }


        //edit the color bitboard
        if (isWhiteBin == 1) {
            this.boards[0].board = ChessGame.setBit(this.boards[0].board, 63 - fromSquare, (short) 0);
            this.boards[0].board = ChessGame.setBit(this.boards[0].board, 63 - toSquare, (short) 1);
        }
        //edit the piece board
        this.boards[7].board = ChessGame.setBit(this.boards[7].board, 63 - fromSquare, (short) 0);
        this.boards[7].board = ChessGame.setBit(this.boards[7].board, 63 - toSquare, (short) 1);
        moveInfo = MoveInfo.getMoveInfo(move, moveInfo);
//        PrecomputedData.updateAttacked(!MoveInfo.isWhiteTurn(Main.moveInfo));
    }
    public void copyAll(MoveHelper mh) {
        this.moveInfo = mh.moveInfo;
        this.whiteKing = mh.whiteKing;
        this.blackKing = mh.blackKing;
        for (int i = 0; i < 12; i++) {
            this.boards[i].set(mh.boards[i].getBoard());
        }
    }
}
