// Some code taken from https://github.com/Khald64/ChessGame/blob/main/src/ChessGame.java
import jdk.jfr.StackTrace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
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



}
public class ChessGame extends JLabel {
    private int offsetX, offsetY;
    private ChessPiece draggedPiece;

    private void enableDragAndDrop() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("pressed");
                offsetX = e.getX()%90;
                offsetY = e.getY()%90;
                Point point = new Point(e.getX(), e.getY());
                System.out.println("X Offset: " + offsetX);
                System.out.println("Y Offset: " + offsetY);
                System.out.println("X: " + e.getX());
                System.out.println("Y: " + e.getY());
                //get square--
                original_square = getSquareIndex(point);
                System.out.println("Square: " + original_square);
                //get piece on square
                int pieceVal = Main.getPieceOn(original_square);
                String imagePath = "src/";
                if (pieceVal/8 == 1) {
                    imagePath += "White ";
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
                    int pieceVal = Main.getPieceOn(original_square);
                    pieceIndex = pieceVal % 8;
                    // create a short that represents the move
                    short move = 0;
                    if (Main.allBoards[7].get(toSquare) != 0) {
                        short mask = (short)0b1000000000000000;
                        move = (short)(move | mask);
                    }
                    if (pieceIndex == 1 && move == 0 && (Math.abs(original_square-toSquare) == 7 || Math.abs(original_square-toSquare) == 9)) {
                        //pawn that didn't capture
                        short mask = (short)0b1111000000000000;
                        move = (short)(move | mask);
                    }
                    else if (pieceIndex == 1 && (toSquare >= 56 || toSquare <=7)) {
                        //pawn moving to vertical edge of board
                        short mask = (short)0b0110000000000000;
                        move = (short)(move | mask);
                    }
                    else if (pieceIndex == 2){
                        short mask = (short)0b0001000000000000;
                        move = (short)(move | mask);
                    }
                    else if(pieceIndex == 3){
                        short mask = (short)0b0010000000000000;
                        move = (short)(move | mask);
                    }
                    else if(pieceIndex == 4){
                        short mask = (short)0b0011000000000000;
                        move = (short)(move | mask);
                    }
                    else if(pieceIndex == 5){
                        short mask = (short)0b0100000000000000;
                        move = (short)(move | mask);
                    }
                    else if(pieceIndex == 6){
                        short mask = (short)0b0101000000000000;
                        move = (short)(move | mask);
                    }
                    short mask = (short)(original_square << 6);
                    move = (short)(move | mask);
                    mask = (short)(toSquare);
                    move = (short)(move | mask);
                    // compare it to the allowedMoves
                    // make move if its allowed
                    if (allowedMoves.contains(move)) {
                        // Clear the from bit
                        Main.allBoards[pieceIndex].board = setBit(Main.allBoards[pieceIndex].board, 63 - fromSquare, (short) 0);
                        // Set the to bit
                        for (int i = 1; i < 7; i++) {
                            short b = 0;
                            if (i == pieceIndex) {
                                b = 1;
                            }
                            Main.allBoards[i].board = setBit(Main.allBoards[i].board, 63 - toSquare, b);
                        }


                        //edit the color bitboard
                        Main.allBoards[0].board = setBit(Main.allBoards[0].board, 63 - fromSquare, (short) 0);
                        Main.allBoards[0].board = setBit(Main.allBoards[0].board, 63 - toSquare, (short) (pieceVal / 8));
                        Main.moveInfo = MoveInfo.getMoveInfo(move, Main.moveInfo);
                        allowedMoves = PrecomputedData.generateMoves(Main.allBoards, Main.moveInfo);
                        for (short moves : allowedMoves) {
                            System.out.println(MoveInfo.moveTranslator(moves));
                        }
                    }
                    x = (7 - toSquare / 8) * 90;
                    y = (toSquare % 8) * 90;
                    draggedPiece = null;
                    chessboardPanel.repaint();
                    System.out.println("released" + "x cord:" + x + "y cord:" + y + "toSquare:" + toSquare);
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
        int row = point.y / 90;
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