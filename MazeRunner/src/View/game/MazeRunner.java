package View.game;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import javax.swing.*;
import mazerunner.Board;

public class MazeRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        new MazeRunner();

    }

    public MazeRunner() throws InterruptedException {
        JFrame f = new JFrame();
//        Thread thread=new Thread();
//        int i;
//        for (i=60;i>0;i--)
//        {
//            thread.sleep(20);
//            System.out.println(i);
//        }


       f.setTitle("Maze Game");
        f.add(new Board());
        f.setSize(1000, 1000);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
