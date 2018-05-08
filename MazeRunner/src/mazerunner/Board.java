package mazerunner;

import Model.game.Bigbombs;
import Model.game.Bombs;
import Model.game.Gift;
import Model.game.HealthGift;
import Model.game.Player;
import Model.game.ShieldGift;
import Model.game.SmallBomb;
import Model.game.coin;
import Model.game.dollar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import javax.crypto.Mac;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Map m;
    private Player p;
    private SmallBomb SB;
    private Bombs BB = new Bigbombs();
    private Gift HG = new HealthGift();
    private Gift SG = new ShieldGift();
    private Gift CG = new coin();
    private Gift DG = new dollar();
    private String Finishstr = "";
    private boolean win = false;
    public static int Health = 100;
    public static int score = 0;
    private Image toolbar;
    private boolean up,down,right,left;

    public Board() {
        SB = new SmallBomb();
        p = new Player();
        m = new Map();
        ImageIcon img = new ImageIcon("w.jpg");
        toolbar = img.getImage();
        addKeyListener(new Al());
        setFocusable(true);
        timer = new Timer(25, this);
        timer.start();

    }

    public void actionPerformed(ActionEvent e) {
        if (m.getMap(p.getTileX(), p.getTileY()).equals("f")) {
            Finishstr = "Winner";
            win = true;
        }

        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (win == false && Health > 0) {
            for (int y = 0; y < 31; y++) {
                for (int x = 0; x < 31; x++) {
                    if (m.getMap(x, y).equals("e")) {
                        g.drawImage(toolbar, x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("f")) {
                        g.drawImage(m.getFinishLine(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("S")) {
                        g.drawImage(m.getStartLine(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("g")) {
                        g.drawImage(m.getGrass(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("w")) {
                        g.drawImage(m.getWall(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("s")) {
                        g.drawImage(SB.getbomb(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("h")) {
                        g.drawImage(HG.getimg(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("A")) {
                        g.drawImage(SG.getimg(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("c")) {
                        g.drawImage(CG.getimg(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("d")) {
                        g.drawImage(DG.getimg(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("b")) {
                        g.drawImage(BB.getbomb(), x * 32, y * 32, null);
                    }
                    if (m.getMap(x, y).equals("t")) {
                        g.drawImage(m.getgrassWall(), x * 32, y * 32, null);
                    }

                }

            }
                           

            if (left) {
                g.drawImage(p.getPlayer1(), p.getTileX() * 32, p.getTileY() * 32, null);
            }
            if (right) {
                g.drawImage(p.getPlayer2(), p.getTileX() * 32, p.getTileY() * 32, null);
            }
            if (up) {
                g.drawImage(p.getPlayer3(), p.getTileX() * 32, p.getTileY() * 32, null);
            }
            if (down) {
                g.drawImage(p.getPlayer4(), p.getTileX() * 32, p.getTileY() * 32, null);
            }
        }
        if (win) {
            g.drawString(Finishstr, 500, 500);
        }
        if (Health <= 0) {
            Health = 0;
            g.drawString("YOU LOST ", 500, 500);
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 100, 30);
        g.setColor(Color.GREEN);
        if (Health <= 50) {
            g.setColor(Color.RED);
        }
        if (Health <= 25) {
            g.setColor(Color.BLACK);
        }

        g.fillRect(0, 0, Health, 30);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(Health), 20, 20);
        g.drawString(Integer.toString(score), 150, 20);

    }

    public class Al extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_W) {
                if (!m.getMap(p.getTileX(), p.getTileY() - 1).equals("w")&&!m.getMap(p.getTileX(), p.getTileY() - 1).equals("t")) {
                    p.move(0, -1);
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("s")) {
                        if (SG.ison()) {
                            Health = Health - 5;
                            SG.setIson(false);
                        } else {
                            Health = Health - 10;
                        }
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("h")) {
                        m.set(p.getTileX(), p.getTileY());
                        HG.increaseHealth();
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("A")) {
                        SG.setIson(true);
                        m.set(p.getTileX(), p.getTileY());
                    }

                    if (m.getMap(p.getTileX(), p.getTileY()).equals("c")) {
                        CG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("d")) {
                        DG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("b")) {
                        if (SG.ison()) {
                            Health = Health - 30;
                            SG.setIson(false);
                        } else {
                            Health = Health - 50;
                        }
                        m.set(p.getTileX(), p.getTileY());
                    }
                }
                up=true;down=false;right=false;left=false;
            }
            if (keycode == KeyEvent.VK_S) {
                if (!m.getMap(p.getTileX(), p.getTileY() + 1).equals("w")&&!m.getMap(p.getTileX(), p.getTileY() + 1).equals("t")) {
                    p.move(0, 1);
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("s")) {
                        if (SG.ison()) {
                            Health = Health - 5;
                            SG.setIson(false);
                        } else {
                            Health = Health - 10;
                        }
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("h")) {
                        m.set(p.getTileX(), p.getTileY());
                        HG.increaseHealth();
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("A")) {
                        SG.setIson(true);
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("c")) {
                        CG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("d")) {
                        DG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("b")) {
                        if (SG.ison()) {
                            Health = Health - 30;
                            SG.setIson(false);
                        } else {
                            Health = Health - 50;
                        }
                        m.set(p.getTileX(), p.getTileY());
                    }
                }
                 up=false;down=true;right=false;left=false;
            }
            if (keycode == KeyEvent.VK_A) {
                if (!m.getMap(p.getTileX() - 1, p.getTileY()).equals("w")&&!m.getMap(p.getTileX()-1, p.getTileY() ).equals("t")) {
                    p.move(-1, 0);
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("s")) {
                        if (SG.ison()) {
                            Health = Health - 5;
                            SG.setIson(false);
                        } else {
                            Health = Health - 10;
                        }
                        m.set(p.getTileX(), p.getTileY());

                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("h")) {
                        m.set(p.getTileX(), p.getTileY());
                        HG.increaseHealth();
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("A")) {
                        SG.setIson(true);
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("c")) {
                        CG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("d")) {
                        DG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("b")) {
                        if (SG.ison()) {
                            Health = Health - 30;
                            SG.setIson(false);
                        } else {
                            Health = Health - 50;
                        }
                        m.set(p.getTileX(), p.getTileY());
                    }
                }
                                 up=false;down=false;right=false;left=true;

            }
            if (keycode == KeyEvent.VK_D) {
                if (!m.getMap(p.getTileX() + 1, p.getTileY()).equals("w")&&!m.getMap(p.getTileX()+1, p.getTileY()).equals("t")) {
                    p.move(1, 0);
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("s")) {

                        if (SG.ison()) {
                            Health = Health - 5;
                            SG.setIson(false);
                        } else {
                            Health = Health - 10;
                        }
                        m.set(p.getTileX(), p.getTileY());

                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("h")) {
                        m.set(p.getTileX(), p.getTileY());
                        HG.increaseHealth();
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("A")) {
                        SG.setIson(true);
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("c")) {
                        CG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("d")) {
                        DG.increasescore();
                        m.set(p.getTileX(), p.getTileY());
                    }
                    if (m.getMap(p.getTileX(), p.getTileY()).equals("b")) {
                        if (SG.ison()) {
                            Health = Health - 30;
                            SG.setIson(false);
                        } else {
                            Health = Health - 50;
                        }
                        m.set(p.getTileX(), p.getTileY());
                    }
                }
                                                 up=false;down=false;right=true;left=false;

            }
        }

        public void keyReleased(KeyEvent e) {

        }

        public void keyTyped(KeyEvent e) {

        }
    }
}
