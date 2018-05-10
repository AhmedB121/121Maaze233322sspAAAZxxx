/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.game;

import Model.game.ShieldGift;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import mazerunner.Board;

/**
 *
 * @author dell-pc
 */
public class PauseMenu {

    public PauseMenu() {
        JButton btnSave=new JButton("Save");
        btnSave.setBounds(120, 300, 130, 50);
       
        JFrame pauseFrame = new JFrame("Pause menu");
        pauseFrame.add(btnSave);
        pauseFrame.setUndecorated(true);
        JButton btncontinue = new JButton("Continue");
        btncontinue.setBounds(120, 50, 130, 50);
        btncontinue.setFocusable(false);
        btncontinue.setVisible(true);
        pauseFrame.add(btncontinue);
        pauseFrame.setSize(400, 500);
        pauseFrame.setLayout(null);
        pauseFrame.setLocationRelativeTo(null);
        JLabel background = new JLabel(new ImageIcon("Background.jpg"));
        pauseFrame.add(background);
        pauseFrame.setVisible(true);
        JButton btnexit = new JButton("Exit");
        btnexit.setBounds(120, 140, 130, 50);
        pauseFrame.add(btnexit);
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
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  boolean  isarmoron =Board.SG.ison();
              // saving with zero score
              
                int savehealth=Board.Health;
                int savescore=Board.score;
                int savebullets=Board.bullet;
              //  boolean armorweared=
                String [] savemap=new String[31];
                for (int i=0;i<31;i++){
                    savemap[i]=Board.m.Map[i];
                    
                }
                
                
              /////////////////////////////////////////////////////////  
                       JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
                   try{
                       BufferedWriter writer=new BufferedWriter(new FileWriter(file));
                    for (int i=0;i<31;i++)
                      writer.write(savemap[i]+System.lineSeparator());
                    
                  writer.write(savehealth+System.lineSeparator());
                  writer.write(savescore+System.lineSeparator());
                  writer.write(savebullets+System.lineSeparator());
                  writer.close();
                   }
                   catch(IOException ex)
                   {
                       ex.printStackTrace();
                   }
            
        }
                
                
                System.exit(0);
            }
        });
        
        
    }
}
