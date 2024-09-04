package org.cis1200;

// imports+++++++++++++++++++++++++++++++++++

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Rectangle;
import java.io.PrintWriter;
import java.io.*;
import java.util.Scanner;

// RunSnake Class+++++++++++++++++++++++++++++++++++
public class RunSnake extends JPanel implements ActionListener, WindowListener, Runnable {

    // All variables used+++++++++++++++++++++++++++++++++++

    final int boardWidth = 600;
    final int boardHeight = 600;
    private final int unitSize = 5;
    private final int delay = 20;

    int doggoX = boardWidth / 2;
    int doggoY = boardHeight / 2;

    int doggoSize = 1;
    int catX;
    int catY;

    private boolean moveLeft = false;
    private boolean moveRight = true;
    private boolean moveUp = false;
    private boolean moveDown = false;
    boolean inSnake = true;

    Timer timer;
    int bonesAdded = 0;
    int time = 0;

    // Images
    Image bones;
    Image cat;
    Image doggo;

    // Bone Arrays+++++++++++++++++++++++++++++++++++

    ArrayList<Point> boneList = new ArrayList<Point>();

    Random rand = new Random();
    int randomX = rand.nextInt(500);
    int randomY = rand.nextInt(450);
    int boneX = randomX - 100;
    int boneY = randomY - 100;

    int[][] pathArray = new int[boardWidth][boardHeight];

    // IO Exceptions+++++++++++++++++++++++++++++++++++

    public void saveGame() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter("gamestate.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pw.println(doggoX + " " + doggoY); // the first line will represent the dog coordinates
        pw.println(catX + " " + catY); // the second line will represent the cat coordinates
        pw.println(boneList.size()); // the third line tells us how many bones there are
        for (Point p : boneList) { // on each following line, we print out the coordinates of the
            // bones
            pw.println(p.x + " " + p.y);
        }
        pw.println(bonesAdded); // next line will be the number of bones we got
        pw.println(time); // next line will be the current time

        pw.close(); // save the file
    }

    public void loadGame() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("gamestate.txt"));

            doggoX = sc.nextInt(); // the dog coordinates are the first two numbers in the file
            doggoY = sc.nextInt();

            catX = sc.nextInt(); // the cat coordinates are the next two numbers in the file
            catY = sc.nextInt();

            boneList.clear(); // clear the bonelist in case there's shit in it
            int numBones = sc.nextInt(); // the next number in the file is the number of bones
            for (int i = 0; i < numBones; i++) { // read in a bone and add it to the list
                int x, y;
                x = sc.nextInt();
                y = sc.nextInt();
                boneList.add(new Point(x, y));
            }

            bonesAdded = sc.nextInt(); // Next number in file is bones retrieved

            time = sc.nextInt(); // Next number in file is time
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No previous save file was found!");
        }

    }

    // Running the program itself+++++++++++++++++++++++++++++++++++

    void moveDog(int x, int y) {
        if (x >= 25 && x <= 575 && y >= 45 && y <= 550) {
            doggoX = x;
            doggoY = y;
            pathArray[doggoX][doggoY] = 1;
        }
    }

    public RunSnake() {
        loadImages();
        timer = new Timer(1000, this);
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        initGame();
    }

    private void loadImages() {
        ImageIcon img1 = new ImageIcon("files/bone.png");
        bones = img1.getImage();
        ImageIcon img3 = new ImageIcon("files/doggo.png");
        doggo = img3.getImage();
        ImageIcon img2 = new ImageIcon("files/cat.png");
        cat = img2.getImage();
    }

    private void initGame() {
        doggoSize = 1;
        doggoX = boardWidth / 2;
        doggoY = boardHeight / 2;
        bonesAdded = 0;
        bonesNeeded = 10;
        time = 1000;
        boneList.clear();
        inSnake = true;
        locateCat();
        for (int doggoX = 0; doggoX < pathArray.length; doggoX++) {
            for (int doggoY = 0; doggoY < pathArray[doggoX].length; doggoY++) {
                pathArray[doggoX][doggoY] = 0;
            }
        }
        timer = new Timer(delay, this);
        timer.start();

        loadGame(); // If a previous save exists, use it. Otherwise the numbers above will be used.
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inSnake) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.ITALIC, 12));
            g.drawString("Time Remaining: " + time + " miliseconds", 400, 580);

            g.setColor(Color.blue);
            g.drawRect(140, 7, 240, 20);
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("Press 'P' to pause & Press 'R' to resume", 145, 20);

            g.setColor(Color.blue);
            g.drawRect(5, 7, 117, 20);
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("Bones Retrieved: " + bonesAdded, 10, 20);

            for (Point bone : boneList) {
                g.drawImage(bones, bone.x, bone.y, 50, 50, this);
            }

            // cat is 10 x 10
            g.drawImage(
                    cat, catX - (cat.getWidth(this) / 2), catY -
                            (cat.getHeight(this) / 2),
                    this
            );

            for (int doggoX = 0; doggoX < boardWidth; doggoX++) {
                for (int doggoY = 0; doggoY < boardHeight; doggoY++) {
                    if (pathArray[doggoX][doggoY] == 1) {
                        g.setColor(Color.RED);
                        g.fillOval(doggoX, doggoY, 10, 10);
                    }
                }
            }
            g.drawImage(
                    doggo,
                    doggoX - (doggo.getWidth(this) * doggoSize) / 2,
                    doggoY - (doggo.getHeight(this) * doggoSize) / 2,
                    doggo.getWidth(this) * doggoSize,
                    doggo.getHeight(this) * doggoSize,
                    this
            );
            System.out.println("paintComponent running");
        } else {
            snakeOver(g);
        }
    }

    private void snakeOver(Graphics g) {
        String msg = "GAME OVER";
        String restartMsg = "Press the SPACEBAR to try again";
        String finalCount = "Total Bones Retrieved: " + bonesAdded;

        Font small = new Font("Arial", Font.BOLD, 20);
        FontMetrics met = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (boardWidth - met.stringWidth(msg)) / 2, boardHeight / 2);
        g.drawString(
                restartMsg, (boardWidth - met.stringWidth(restartMsg)) / 2, boardHeight / 2 + 100
        );
        g.drawString(
                finalCount, (boardWidth - met.stringWidth(finalCount)) / 2, boardHeight / 2 + 30
        );

        // Delete the save file because we want to start a new game
        File save = new File("gamestate.txt");
        save.delete();
    }

    int bonesNeeded = 10;

    private void move() {
        if (moveLeft) {
            doggoX -= unitSize;
        }
        if (moveRight) {
            doggoX += unitSize;
        }
        if (moveUp) {
            doggoY -= unitSize;
        }
        if (moveDown) {
            doggoY += unitSize;
        }
        if (bonesAdded >= bonesNeeded) {
            doggoSize += 1;
            bonesAdded = 0;
            bonesNeeded += 10;
        }
        if (doggoX == catX && doggoY == catY) {
            doggoSize += 0.5;
            bonesAdded++; // increment the bonesAdded variable
            boneList.add(new Point(boneX, boneY));
            bonesAdded++;
            locateCat();
        }
        if (doggoX < 0 || doggoX > boardWidth || doggoY < 0 || doggoY > boardHeight) {
            inSnake = false;
            timer.stop();
        }
        if (doggoX < 0 || doggoX > boardWidth - (doggoSize * unitSize) || doggoY < 0
                || doggoY > boardHeight -
                        (doggoSize * unitSize)) {
            inSnake = false;
        }
        checkCollision();
        repaint();
    }

    void checkCollision() {
        if (doggoX >= boardWidth) {
            doggoX = 0;
        }
        if (doggoX < 0) {
            doggoX = boardWidth;
        }
        if (doggoY >= boardHeight) {
            doggoY = 0;
        }
        if (doggoY < 0) {
            doggoY = boardHeight;
        }
        repaint();
    }

    private final int catYmin = 20;
    private final int catYmax = 500;

    private void locateCat() {
        catX = rand.nextInt(500);
        catY = rand.nextInt(450);
        if (catX > 500 || catX < 50 || catY > 450 || catY < 80) {
            locateCat();
        }
    }

    private boolean gamePaused = false;

    public void pauseGame() {
        gamePaused = true;
        if (gamePaused) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public void resumeGame() {
        if (gamePaused) {
            gamePaused = false;
            timer.start();
        }
    }

    private void catEaten() {
        locateCat();
        boneList.add(new Point(randomX, randomY));
    }

    int collisionX = 0;
    int collisionY = 0;
    private boolean collisionHappened = false;

    public void actionPerformed(ActionEvent e) {
        if (inSnake) {
            Rectangle doggoLimit = new Rectangle(doggoX, doggoY, 50, 50);
            Rectangle catLimit = new Rectangle(catX, catY, 50, 50);
            if (doggoLimit.intersects(catLimit)) {
                catEaten();
                bonesAdded++;
                collisionHappened = true;
                int collisionX = catX;
                int collisionY = catY;
                boneList.add(
                        new Point(collisionX, collisionY)
                );
            }
            checkCollision();
            checkBones();
            move();
            repaint();
            time--;
        }
        if (time <= 0) {
            inSnake = false;
        }
    }

    private void checkBones() {
        for (int i = 0; i < boneList.size(); i++) {
            Point bone = boneList.get(i);
            if ((doggoX == bone.x) && (doggoY == bone.y)) {
                boneList.remove(i);
                bonesAdded++;
                doggoSize++;
                break;
            }
        }
    }

    public void checkTime() {
    }

    // Key Listeners Class+++++++++++++++++++++++++++++++++++
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                moveLeft = true;
                moveRight = false;
                moveUp = false;
                moveDown = false;
                doggoX -= unitSize;
                moveDog(doggoX, doggoY);
            }
            if (key == KeyEvent.VK_RIGHT) {
                moveRight = true;
                moveLeft = false;
                moveUp = false;
                moveDown = false;
                doggoX -= unitSize;
                moveDog(doggoX, doggoY);
            }
            if (key == KeyEvent.VK_UP) {
                moveUp = true;
                moveLeft = false;
                moveRight = false;
                moveDown = false;
                doggoX -= unitSize;
                moveDog(doggoX, doggoY);
            }
            if (key == KeyEvent.VK_DOWN) {
                moveDown = true;
                moveLeft = false;
                moveRight = false;
                moveUp = false;
                doggoX -= unitSize;
                moveDog(doggoX, doggoY);
            }
            if (key == KeyEvent.VK_P) {
                pauseGame();
            }
            if (key == KeyEvent.VK_R) {
                resumeGame();
            }
            if (key == KeyEvent.VK_SPACE) {
                timer.stop();
                initGame();
            }
        }
    }

    public void run() {
    }

    // Window Listener Class+++++++++++++++++++++++++++++++++++

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
        saveGame();
    }

    public void windowDeactivated(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowOpened(WindowEvent arg0) {
    }

}
