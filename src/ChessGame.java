// Some code taken from https://github.com/Khald64/ChessGame/blob/main/src/ChessGame.java
import jdk.jfr.StackTrace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Khouiled
 */
public class ChessGame {

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
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 720, 720);
        frame.setUndecorated(true);
        JPanel p = new JPanel() {
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
        frame.add(p);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}