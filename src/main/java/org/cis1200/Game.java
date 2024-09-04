package org.cis1200;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        RunSnake snakeGame = new RunSnake();
        snakeGame.run();
        JFrame frame = new JFrame();
        frame.add((Component) snakeGame);
        frame.addWindowListener(snakeGame);
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
