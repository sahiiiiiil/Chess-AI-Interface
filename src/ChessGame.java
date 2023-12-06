// Some code taken from https://github.com/Khald64/ChessGame/blob/main/src/ChessGame.java
import jdk.jfr.StackTrace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Khouiled
 */
class ChessPiece extends JLabel {
    private int offsetX, offsetY;

    public ChessPiece(String pieceImage) {
        super(new ImageIcon(pieceImage));
        setSize(60, 60); // Assuming each piece has a size of 60x60
    }


}
public class ChessGame extends JLabel {
    private int offsetX, offsetY;
    private ChessPiece draggedPiece;

    private void enableDragAndDrop() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offsetX = e.getX();
                offsetY = e.getY();
                System.out.println(offsetX);
                System.out.println(offsetY);
                Component component = getComponentAt(e.getPoint());
                if (component instanceof ChessPiece) {
                    draggedPiece = (ChessPiece) component;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedPiece != null) {
                    int fromSquare = getSquareIndex(draggedPiece.getLocation());
                    int toSquare = getSquareIndex(e.getPoint());

                    // Update main.allBoards based on the drag-and-drop
                    // (You may need to adjust this logic based on your piece representation)
                    int pieceIndex = 0; // Change this to determine the piece type
                    //https://stackoverflow.com/questions/1073318/in-java-is-it-possible-to-clear-a-bit
                    long fromMask = 1L << fromSquare;
                    long toMask = 1L << toSquare;
                    // Clear the from bit
                    Main.allBoards[pieceIndex].board &= ~fromMask;
                    // Set the to bit
                    Main.allBoards[pieceIndex].board |= toMask;

                    draggedPiece.setLocation((7 - toSquare / 8) * 90, (toSquare % 8) * 90);
                    draggedPiece = null;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedPiece != null) {
                    draggedPiece.setLocation(getX() + e.getX() - offsetX, getY() + e.getY() - offsetY);
                }
                repaint();
                System.out.println(1);
            }
        });
    }

    private int getSquareIndex(Point point) {
        int row = 7 - point.y / 90;
        int col = point.x / 90;
        return row * 8 + col;
    }

    public ChessGame() {

        PrecomputedData.precompute();
        Image[] imgs = new Image[12];
        try {

            imgs[0] = ImageIO.read(new File("src/Black b.png"));
            imgs[1] = ImageIO.read(new File("src/Black k.png"));
            imgs[2] = ImageIO.read(new File("src/Black n.png"));
            imgs[3] = ImageIO.read(new File("src/Black p.png"));
            imgs[4] = ImageIO.read(new File("src/Black q.png"));
            imgs[5] = ImageIO.read(new File("src/Black r.png"));
            imgs[6] = ImageIO.read(new File("src/White b.png"));
            imgs[7] = ImageIO.read(new File("src/White k.png"));
            imgs[8] = ImageIO.read(new File("src/White n.png"));
            imgs[9] = ImageIO.read(new File("src/White p.png"));
            imgs[10] = ImageIO.read(new File("src/White q.png"));
            imgs[11] = ImageIO.read(new File("src/White r.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(1);
        enableDragAndDrop();
        System.out.println(1);
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 720, 720);
        frame.setUndecorated(true);
        JPanel chessboardPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                boolean white = true;
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (white) {
                            g.setColor(new Color(235, 235, 208));
                        } else {
                            g.setColor(new Color(119, 148, 85));

                        }
                        g.fillRect(x * 90, y * 90, 90, 90);
                        white = !white;
                    }
                    white = !white;
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[1].get((byte)i)) == 1) {
                        g.drawImage(imgs[3 + Main.allBoards[0].get((byte)i) * 6], ((63-i) % 8) * 90-6, ((63-i) / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[2].get((byte)i)) == 1) {
                        g.drawImage(imgs[Main.allBoards[0].get((byte)i) * 6], ((63-i) % 8) * 90-6, ((63-i) / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[3].get((byte)i)) == 1) {
                        g.drawImage(imgs[2 + Main.allBoards[0].get((byte)i) * 6], ((63-i) % 8) * 90-6, ((63-i) / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[4].get((byte)i)) == 1) {
                        g.drawImage(imgs[5 + Main.allBoards[0].get((byte)i) * 6], ((63-i) % 8) * 90-6, ((63-i) / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[5].get((byte)i)) == 1) {
                        g.drawImage(imgs[4 + Main.allBoards[0].get((byte)i) * 6], ((63-i) % 8) * 90-6, ((63-i) / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[6].get((byte)i)) == 1) {
                        g.drawImage(imgs[1 + Main.allBoards[0].get((byte)i) * 6], ((63-i) % 8) * 90-6, ((63-i) / 8) * 90-10, this);
                    }
                }
            }

        };
        frame.add(chessboardPanel);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGame());
    }
}