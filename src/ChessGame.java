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




public class ChessGame extends JLabel {
    private ArrayList<Short> allowedMoves;
    private int offsetX, offsetY;
    private int original_square;
    private int x, y;
    private Image draggedPiece;
    private JPanel chessboardPanel;


    private void enableDragAndDrop(JPanel panel) {

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("pressed");
                offsetX = e.getX()%90;
                offsetY = e.getY()%90;
                Point point = new Point(e.getX(), e.getY());
                //get square--
                original_square = getSquareIndex(point);
                //get piece on square
                int pieceVal = Main.getPieceOn(original_square);
                String imagePath = "src/";
                if (pieceVal/8 == 1) {
                    imagePath += "White ";
                }
                else {
                    imagePath += "Black ";
                }
                if (pieceVal%8 ==1) {imagePath += "p.png";}
                if (pieceVal%8 ==2) {imagePath += "b.png";}
                if (pieceVal%8 ==3) {imagePath += "n.png";}
                if (pieceVal%8 ==4) {imagePath += "r.png";}
                if (pieceVal%8 ==5) {imagePath += "q.png";}
                if (pieceVal%8 ==6) {imagePath += "k.png";}
                //get image
                if (imagePath.equals("src/White ") || imagePath.equals("src/Black ")) {
                    draggedPiece = null;
                }
                else {
                    System.out.println("Image Path: " + imagePath);
                    try {
                        draggedPiece = ImageIO.read(new File(imagePath));
                    } catch (IOException f) {
                        f.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedPiece != null) {
                    int fromSquare = original_square;
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
                    System.out.println("Move binary: " + toBinary(move));
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
                        System.out.println(Math.abs((move>>12)%2));
                        System.out.println(Math.abs((move>>13)%2));
                        System.out.println(Math.abs((move>>14)%2));
                        System.out.println(Math.abs((move>>15)%2));
                        boolean enPassant = Math.abs((move>>12)%2) == 1 && Math.abs((move>>13)%2) == 1
                                && Math.abs((move>>14)%2) == 1 && Math.abs((move>>15)%2) == 1;
                        if (enPassant && MoveInfo.isWhiteTurn(Main.moveInfo)) {
                            Main.allBoards[1].board = setBit(Main.allBoards[1].board, 63 - toSquare - 8, (short)0);
                            Main.allBoards[0].board = setBit(Main.allBoards[0].board, 63 - toSquare - 8, (short)0);
                            Main.allBoards[7].board = setBit(Main.allBoards[7].board, 63 - toSquare - 8, (short)0);
                        }
                        else if (enPassant && !MoveInfo.isWhiteTurn(Main.moveInfo)) {
                            Main.allBoards[1].board = setBit(Main.allBoards[1].board, 63 - toSquare + 8, (short)0);
                            Main.allBoards[0].board = setBit(Main.allBoards[0].board, 63 - toSquare + 8, (short)0);
                            Main.allBoards[7].board = setBit(Main.allBoards[7].board, 63 - toSquare + 8, (short)0);
                        }


                        //edit the color bitboard
                        Main.allBoards[0].board = setBit(Main.allBoards[0].board, 63 - fromSquare, (short) 0);
                        Main.allBoards[0].board = setBit(Main.allBoards[0].board, 63 - toSquare, (short) (pieceVal / 8));
                        //edit the piece board
                        Main.allBoards[7].board = setBit(Main.allBoards[7].board, 63 - fromSquare, (short) 0);
                        Main.allBoards[7].board = setBit(Main.allBoards[7].board, 63 - toSquare, (short) 1);
                        Main.moveInfo = MoveInfo.getMoveInfo(move, Main.moveInfo);
                        System.out.println("MoveInfo binary: " + toBinary(Main.moveInfo));
                        System.out.println("Color Board binary: " + toBinary(Main.allBoards[0].board));
                        allowedMoves = PrecomputedData.generateMoves(Main.allBoards, Main.moveInfo);
                        for (short moves : allowedMoves) {
                            System.out.println(MoveInfo.moveTranslator(moves));
                        }
                    }
                    x = (7 - toSquare / 8) * 90;
                    y = (toSquare % 8) * 90;
                    draggedPiece = null;
                    chessboardPanel.repaint();
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedPiece != null) {
                    x = e.getX()-offsetX;
                    y = e.getY()-offsetY;
                }
                chessboardPanel.repaint();
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
        allowedMoves = PrecomputedData.generateMoves(Main.allBoards, Main.moveInfo);
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
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 720, 720);
        frame.setUndecorated(true);
        chessboardPanel = new JPanel() {
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
                    if (draggedPiece != null && i == original_square) {continue;}
                    if (Math.abs(Main.allBoards[1].get((byte)i)) == 1) {
                        g.drawImage(imgs[3 + Main.allBoards[0].get((byte)i) * 6], (i % 8) * 90-6, (i / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (draggedPiece != null && i == original_square) {continue;}
                    if (Math.abs(Main.allBoards[2].get((byte)i)) == 1) {
                        g.drawImage(imgs[Main.allBoards[0].get((byte)i) * 6], (i % 8) * 90-6, (i / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (draggedPiece != null && i == original_square) {continue;}
                    if (Math.abs(Main.allBoards[3].get((byte)i)) == 1) {
                        g.drawImage(imgs[2 + Main.allBoards[0].get((byte)i) * 6], (i % 8) * 90-6, (i / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (draggedPiece != null && i == original_square) {continue;}
                    if (Math.abs(Main.allBoards[4].get((byte)i)) == 1) {
                        g.drawImage(imgs[5 + Main.allBoards[0].get((byte)i) * 6], (i % 8) * 90-6, (i / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[5].get((byte)i)) == 1) {
                        if (draggedPiece != null && i == original_square) {continue;}
                        g.drawImage(imgs[4 + Main.allBoards[0].get((byte)i) * 6], (i % 8) * 90-6, (i / 8) * 90-10, this);
                    }
                }
                for (int i = 0; i < 64; i++) {
                    if (Math.abs(Main.allBoards[6].get((byte)i)) == 1) {
                        if (draggedPiece != null && i == original_square) {continue;}
                        g.drawImage(imgs[1 + Main.allBoards[0].get((byte)i) * 6], (i % 8) * 90-6, (i / 8) * 90-10, this);
                    }
                }
                if (draggedPiece != null) {
                    g.drawImage(draggedPiece, x, y, this);
                }
            }

        };
        enableDragAndDrop(chessboardPanel);
        frame.add(chessboardPanel);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGame());
    }
    public static long setBit(long value, int index, short bit) {
        // Check if the index is within the valid range for a long (0 to 63)
        if (index < 0 || index > 63) {
            throw new IllegalArgumentException("Index must be between 0 and 63 (inclusive)");
        }

        // Use bitwise OR to set the bit at the specified index to 1
        if (bit == 1) {
            return value | (1L << index);
        }
        else {
            long mask = ~(1L << index);
            return value & mask;
        }
    }
    public static String toBinary(short s){
        String ret = "";
        for (int i = 0; i < 16; i++) {
            ret=Math.abs((s>>i)%2) + ret;
        }
        return ret;
    }
    public static String toBinary(long l){
        String ret = "";
        for (int i = 0; i < 64; i++) {
            ret=Math.abs((l>>i)%2) + ret;
        }
        return ret;
    }
}