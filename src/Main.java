//move format: piece square square capture piece
//example: p1223xq would mean pawn on rank 2, file 3(c) captures queen rank 3, file 4(d)
//example 2: r1242 would mean rook on rank 2, file 3(c) moves to rank 5, file 3(c)
public class Main {
    public static void main(String[] args) {
        Piece[][] pieces = new Piece[8][8];
        for (int file = 0; file < 8; file++) {
            pieces[1][file] = new Piece('p', new int[] {1, file}, true);
            pieces[6][file] = new Piece('p', new int[] {6, file}, false);
            if (file == 0 || file == 7) {
                pieces[0][file] = new Piece('r', new int[] {0, file}, true);
                pieces[7][file] = new Piece('r', new int[] {7, file}, false);
            }
            else if (file == 1 || file == 6) {
                pieces[0][file] = new Piece('n', new int[] {0, file}, true);
                pieces[7][file] = new Piece('n', new int[] {7, file}, false);
            }
            else if (file == 2 || file == 5) {
                pieces[0][file] = new Piece('b', new int[] {0, file}, true);
                pieces[7][file] = new Piece('b', new int[] {7, file}, false);
            }
            else if (file == 3) {
                pieces[0][file] = new Piece('q', new int[] {0, file}, true);
                pieces[7][file] = new Piece('q', new int[] {7, file}, false);
            }
            else {
                pieces[0][file] = new Piece('k', new int[] {0, file}, true);
                pieces[7][file] = new Piece('k', new int[] {7, file}, false);
            }
        }
        new ChessGame(new Board(pieces));
    }
}