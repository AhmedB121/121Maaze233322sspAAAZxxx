/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.game;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author dell-pc
 */
public class Player {

    private int tileX, tileY;
    private Image player1, player2, player3, player4;

    public Player() {
        player1 = Toolkit.getDefaultToolkit().createImage("Playerside1.png");
        player2 = Toolkit.getDefaultToolkit().createImage("Playerside2.png");
        player3 = Toolkit.getDefaultToolkit().createImage("Playerup.png");
        player4 = Toolkit.getDefaultToolkit().createImage("Playerdown.png");

        tileX = 1;
        tileY = 1;
    }

    public Image getPlayer1() {
        return player1;
    }

    public Image getPlayer2() {
        return player2;
    }
    public Image getPlayer3() {
        return player3;
    }
    public Image getPlayer4() {
        return player4;
    }
    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void move(int dx, int dy) {

        tileX += dx;
        tileY += dy;
    }

}
