package org.cis1200;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestCases {
    @Test
    public void testPauseAndResume() {
        RunSnake game = new RunSnake();
        game.timer.start();

        KeyEvent pauseEvent = new KeyEvent(
                game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P,
                KeyEvent.CHAR_UNDEFINED
        );
        game.getKeyListeners()[0].keyPressed(pauseEvent);
        assertFalse(game.timer.isRunning());

        KeyEvent resumeEvent = new KeyEvent(
                game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R,
                KeyEvent.CHAR_UNDEFINED
        );
        game.getKeyListeners()[0].keyPressed(resumeEvent);
        assertTrue(game.timer.isRunning());
    }

    @Test
    public void testTimeRemaining() {
        RunSnake game = new RunSnake();
        game.timer.start();

        assertEquals(1000, game.time);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(game.time < 1000);
    }

    @Test
    public void testBonesAdded() {
        RunSnake game = new RunSnake();
        game.timer.start();

        assertEquals(0, game.bonesAdded);

        game.bonesAdded += 5;
        assertEquals(5, game.bonesAdded);
    }

    @Test
    public void testImagesLoaded() {
        RunSnake game = new RunSnake();

        assertNotNull(game.doggo);
        assertNotNull(game.cat);

        ImageIcon img1 = new ImageIcon("files/bone.png");
        assertNotNull(img1);
        ImageIcon img2 = new ImageIcon("files/doggoq.png");
        assertNotNull(img2);
        ImageIcon img3 = new ImageIcon("files/cat.png");
        assertNotNull(img3);
    }

    @Test
    public void timerEnds() {
        RunSnake game = new RunSnake();
        game.timer.start();
        game.time = -1;
        game.checkTime();
        assertTrue(game.inSnake);
    }

    @Test
    public void restartSuccessful() {
        RunSnake game = new RunSnake();
        game.timer.start();

        KeyEvent spaceEvent = new KeyEvent(
                game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE,
                KeyEvent.CHAR_UNDEFINED
        );
        game.getKeyListeners()[0].keyPressed(spaceEvent);
        assertTrue(game.inSnake);
    }

    @Test
    public void testMoveDog() {
        RunSnake game = new RunSnake();
        game.moveDog(30, 50);
        assertEquals(30, game.doggoX);
        assertEquals(50, game.doggoY);
    }

    @Test
    public void testEdgeCases() {
        RunSnake game = new RunSnake();
        game.timer.start();

        game.doggoX = game.boardWidth + 1;
        game.doggoY = game.boardHeight + 1;
        game.checkCollision();
        assertTrue(game.inSnake);
    }

}
