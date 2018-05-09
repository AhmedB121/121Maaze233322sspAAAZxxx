package mazerunner;

import Model.game.Bigbombs;
import Model.game.Bombs;
import Model.game.Bullets;
import Model.game.Checkpoint;
import Model.game.Gift;
import Model.game.HealthGift;
import Model.game.Player;
import Model.game.ShieldGift;
import Model.game.SmallBomb;
import Model.game.bulletsgift;
import Model.game.coin;
import Model.game.dollar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import javax.crypto.Mac;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    public int currentArticle=0;
    public boolean isCheckPoint;
    CareTaker caretaker=new CareTaker();
    Originator originator=new Originator();
    private Timer timer;
    private Timer tm = new Timer(1000, this);
    JTextField jf = new JTextField(10);
    int time = 0;
    private Map m;
    private Player p;
    private SmallBomb SB;
    private Bombs BB = new Bigbombs();
    private Gift HG = new HealthGift();
    private Gift SG = new ShieldGift();
    private Gift BG = new bulletsgift();
    private Checkpoint checkpoint=new Checkpoint();
    private Gift CG = new coin();
    private Gift DG = new dollar();
    private String Finishstr = "";
    private boolean win = false;
    public static int Health = 100;
    public static int score = 0;
    public static int bullet = 6;
    private Image toolbar;
    private boolean up, down, right, left;
     public static String lastPressed = null;
    private ArrayList<Bullets> bullets = new ArrayList<>();
    private boolean flag = false;

    public Board() {
        add(jf);
        SB = new SmallBomb();
        p = new Player(0,0);
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
        logic();
        
        repaint();
        
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (win == false ) {
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
                    if(m.getMap(x, y).equals("z")){
                       g.drawImage(checkpoint.getflag1(), x * 32, y * 32, null);

                    }
                     if(m.getMap(x, y).equals("Z")){
                       g.drawImage(checkpoint.getflag2(), x * 32, y * 32, null);

                    }
                       if(m.getMap(x, y).equals("B")){
                       g.drawImage(BG.getimg(), x * 32, y * 32, null);

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
            if(isCheckPoint && Health<=0 && currentArticle>=1){
                 Point aPoint=new Point();
                 currentArticle--;
                 
                 aPoint=originator.restoreFromMemento(caretaker.getMemento(currentArticle));
                 p.setTileX(aPoint.x);
                 p.setTileY(aPoint.y);
                 Health=100;
                 g.drawImage(p.getPlayer4(),aPoint.x*32, aPoint.y*32, null);
                 
             }
             
            
        }
        
        g.setColor(Color.red);
        if (win) {
            //Font font = new Font("Jokerman", Font.PLAIN, 35);
            g.drawString(Finishstr, 500, 500);
        }
        if (Health <=0 && isCheckPoint==false) {
            
            //Font font = new Font("Jokerman", Font.PLAIN, 35);
            //  g.setFont(font);
            
            Health=0;
            g.drawString("Game Over", 500, 500);
        }
        g.setColor(Color.BLACK);
        g.drawRect(40, 0, 100, 30);
        g.setColor(Color.GREEN);
        if (Health <= 50) {
            g.setColor(Color.RED);
        }
        if (Health <= 25) {
            g.setColor(Color.BLACK);
        }
        

        if(SG.ison())
        g.drawImage(m.getArmour(), 165,0, null);

        g.fillRect(40, 0, Health, 30);
        g.setColor(Color.BLACK);
        g.drawString("Health", 0, 20);
        g.drawString(Integer.toString(Health), 40, 20);
        g.drawString("Score", 220, 20);
        g.drawString(Integer.toString(score), 270, 20);
        g.drawString("Time", 390, 20);
       g.drawString(Integer.toString(bullet),650, 20);
       g.drawImage(m.getbicon(), 600, 0,null);
        //g.drawImage(m.getclock(), 400,0, null);

        int i = 0;
        if (flag) {

            while (i < bullets.size()) {
                g.drawImage(bullets.get(i).getBullet(), bullets.get(i).getX(), bullets.get(i).getY(), null);
                Bullets b = bullets.get(i);
                bullets.remove(i);
                b.move();
               try{
                if (m.getMap(b.getX()/32, b.getY()/32).equals("t")) {
                   
                    m.set(b.getX()/32, b.getY()/32);
                    bullets.remove(b);
                    
                }
                else if(m.getMap(b.getX()/32, b.getY()/32).equals("w")){
                    bullets.remove(b);
                }
                else {
                    if (b.isVisible()) {
                        bullets.add(b);
                    }
                    i++;
                }
               }
               catch(Exception e){
                   
               }
            }
        }       
    }

    public class Al extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_W) {
                if (!m.getMap(p.getTileX(), p.getTileY() - 1).equals("w") && !m.getMap(p.getTileX(), p.getTileY() - 1).equals("t")) {
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
                    }if(m.getMap(p.getTileX(), p.getTileY()).equals("z")){
                     m.setflag(p.getTileX(), p.getTileY());
                     Point mypoint =new Point();
                     mypoint.x=p.getTileX();
                     mypoint.y=p.getTileY();
                     originator.set(mypoint);
                     caretaker.addMemento(originator.storeInMemento());
                     isCheckPoint=true;
                     currentArticle++;
                     
                    }
                     if (m.getMap(p.getTileX(), p.getTileY()).equals("B")) {
                        if(bullet>=6){  
                        }else{
                            bullet=bullet+1;
                        }
                         m.set(p.getTileX(), p.getTileY());
                    }
                }
                up = true;
                down = false;
                right = false;
                left = false;
                lastPressed = "up";
            }
            if (keycode == KeyEvent.VK_S) {
                if (!m.getMap(p.getTileX(), p.getTileY() + 1).equals("w") && !m.getMap(p.getTileX(), p.getTileY() + 1).equals("t")) {
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
                    if(m.getMap(p.getTileX(), p.getTileY()).equals("z")){
                     m.setflag(p.getTileX(), p.getTileY());
                     Point mypoint =new Point();
                     mypoint.x=p.getTileX();
                     mypoint.y=p.getTileY();
                     originator.set(mypoint);
                     caretaker.addMemento(originator.storeInMemento());
                     isCheckPoint=true;
                     currentArticle++;
                    }
                     if (m.getMap(p.getTileX(), p.getTileY()).equals("B")) {
                        if(bullet>=6){  
                        }else{
                            bullet=bullet+1;
                        }
                         m.set(p.getTileX(), p.getTileY());
                    }
                }
                up = false;
                down = true;
                right = false;
                left = false;
                lastPressed = "down";
            }
            if (keycode == KeyEvent.VK_A) {
                if (!m.getMap(p.getTileX() - 1, p.getTileY()).equals("w") && !m.getMap(p.getTileX() - 1, p.getTileY()).equals("t")) {
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
                    if(m.getMap(p.getTileX(), p.getTileY()).equals("z")){
                     m.setflag(p.getTileX(), p.getTileY());
                     Point mypoint =new Point();
                     mypoint.x=p.getTileX();
                     mypoint.y=p.getTileY();
                     originator.set(mypoint);
                     caretaker.addMemento(originator.storeInMemento());
                     isCheckPoint=true;
                     currentArticle++;
                    }
                     if (m.getMap(p.getTileX(), p.getTileY()).equals("B")) {
                        if(bullet>=6){  
                        }else{
                            bullet=bullet+1;
                        }
                         m.set(p.getTileX(), p.getTileY());
                    }
                }
                up = false;
                down = false;
                right = false;
                left = true;
                lastPressed = "left";

            }
            if (keycode == KeyEvent.VK_D) {
                if (!m.getMap(p.getTileX() + 1, p.getTileY()).equals("w") && !m.getMap(p.getTileX() + 1, p.getTileY()).equals("t")) {
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
                    if(m.getMap(p.getTileX(), p.getTileY()).equals("z")){
                     m.setflag(p.getTileX(), p.getTileY());
                        Point mypoint =new Point();
                     mypoint.x=p.getTileX();
                     mypoint.y=p.getTileY();
                     originator.set(mypoint);
                     caretaker.addMemento(originator.storeInMemento());
                     isCheckPoint=true;
                     currentArticle++;
                    }
                     if (m.getMap(p.getTileX(), p.getTileY()).equals("B")) {
                        if(bullet>=6){  
                        }else{
                            bullet=bullet+1;
                        }
                         m.set(p.getTileX(), p.getTileY());
                    }
                    
                }
                up = false;
                down = false;
                right = true;
                left = false;
                lastPressed = "right";

            }
            if (keycode == KeyEvent.VK_SPACE) {
                 if(bullet!=0){
                flag = true;
                bullets.add(p.getBullet());
                bullet--;
                 }
            }
            if (keycode == KeyEvent.VK_ESCAPE) {
                JFrame pauseFrame = new JFrame("Pause menu");
                // pauseFrame.getContentPane().setLayout(new ViewportLayout());
                ((JComponent) pauseFrame.getContentPane()).setOpaque(false);

              JLabel background=new JLabel(new ImageIcon("Background.jpg"));
                pauseFrame.add(background);

                
                

                pauseFrame.setSize(400, 500);
                pauseFrame.setUndecorated(true);
                pauseFrame.setVisible(true);
                pauseFrame.setLocationRelativeTo(null);
                JButton btncontinue = new JButton("Continue");
                btncontinue.setBounds(120, 50, 130, 50);
                pauseFrame.add(btncontinue);
                btncontinue.setFocusable(false);

                JButton btnexit = new JButton("Exit");
                btnexit.setBounds(120, 140, 130, 50);
                pauseFrame.add(btnexit);
                pauseFrame.setLayout(null);

                  JLabel str = new JLabel("GAME PAUSED");
                str.setBounds(129, 10, 130, 50);
                pauseFrame.add(str);
                
                btnexit.setFocusable(false);

                View.game.MazeRunner.f.setEnabled(false);
                btncontinue.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pauseFrame.dispose();
                        View.game.MazeRunner.f.setVisible(true);
                        View.game.MazeRunner.f.setEnabled(true);
                    }
                });
                btnexit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
            }
        }

        public void keyReleased(KeyEvent e) {

        }

        public void keyTyped(KeyEvent e) {

        }
    }

    public void logic() {
        time++;
        if (time == 3600) {
            JOptionPane.showMessageDialog(null, "Time is up");
            System.exit(0);
        }
        jf.setText("0" + time / 60);

    }
}
