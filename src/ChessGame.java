import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChessGame extends JFrame {
    private final int boardSize = 800;
    private final int numSquares = 8;
    private final int squareSize = boardSize / numSquares;
    private Board board;

    public ChessGame(Board board) {
        setTitle("Chess Game");
        setSize(boardSize, boardSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        this.board = board;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int rank = 0; rank < numSquares; rank++) {
            for (int file = 0; file < numSquares; file++) {
                int x = file * squareSize;
                int y = (boardSize-squareSize)-(rank * squareSize);

                if ((rank + file) % 2 != 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(x, y, squareSize, squareSize);
                if (board.getPiece(rank, file) != null) {
                    String filePath;
                    if (board.getPiece(rank, file).isWhite()) {
                        filePath = "src/White ";
                    } else {
                        filePath = "src/Black ";
                    }
                    filePath += board.getPiece(rank, file).getType() + ".png";
                    Image image;
                    try {
                        image = ImageIO.read(new File(filePath));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    g.drawImage(image, x, y, null);
                }
            }
        }
    }
}
