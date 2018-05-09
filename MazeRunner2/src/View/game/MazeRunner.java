package View.game;

import static View.game.MazeRunner.startMenu;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import mazerunner.Board;

public class MazeRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        new MazeRunner();

    }
//new postision here
    public static JFrame startMenu = new JFrame();
    public static JFrame f = new JFrame();

    public MazeRunner() throws InterruptedException, IOException {
        startMenu.setTitle("Main Menu");
             startMenu.getContentPane().setLayout(null);


        startMenu.setSize(500, 500);
        startMenu.setVisible(true);
        startMenu.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("startbtn.png");
        JButton btnStart = new JButton(img);
        img= new ImageIcon("menu.png");
        btnStart.setBounds(150, 100, 150, 30);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(true);
                startMenu.dispose();
            }
        });

        startMenu.add(btnStart);
        
        f.setTitle("Maze Game");
        f.add(new Board());
        f.setSize(1000, 1000);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
