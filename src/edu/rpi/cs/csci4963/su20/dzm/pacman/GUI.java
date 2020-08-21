package edu.rpi.cs.csci4963.su20.dzm.pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.rpi.cs.csci4963.su20.dzm.pacman.game.Point;

public class GUI extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;

    private final int BLOCK_SIZE = 20;
    private final int W_BLOCKS = 28;
    private final int H_BLOCKS = 36;
    private final int SCREEN_HEIGHT = H_BLOCKS * BLOCK_SIZE;
    private final int SCREEN_WIDTH = W_BLOCKS * BLOCK_SIZE;

    private int N_GHOSTS = 3;
    private int pacsLeft, score;
    private Image ghostBlinky;
    private Image ghostPinky;
    private Image ghostInky;
    private Image ghostClyde;
    private Image pacmanImage;

    private final short levelData[][] = {
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0, 19, 26, 26, 26, 26, 18, 26, 26, 26, 26, 26, 22,  0,  0, 19, 26, 26, 26, 26, 26, 18, 26, 26, 26, 26, 22,  0},
        { 0, 21,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0, 21,  0},
        { 0, 21,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0, 21,  0},
        { 0, 21,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0, 21,  0},
        { 0, 17, 26, 26, 26, 26, 16, 26, 26, 18, 26, 26, 24, 26, 26, 24, 26, 26, 18, 26, 26, 16, 26, 26, 26, 26, 20,  0},
        { 0, 21,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0, 21,  0},
        { 0, 21,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0, 21,  0},
        { 0, 25, 26, 26, 26, 26, 20,  0,  0, 25, 26, 26, 22,  0,  0, 19, 26, 26, 28,  0,  0, 17, 26, 26, 26, 26, 28,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 19, 26, 26, 24, 26, 26, 24, 26, 26, 22,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        {26, 26, 26, 26, 26, 26, 16, 26, 26, 20,  0,  0,  0,  0,  0,  0,  0,  0, 17, 26, 26, 16, 26, 26, 26, 26, 26, 26},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 17, 26, 26, 26, 26, 26, 26, 26, 26, 20,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0},
        { 0, 19, 26, 26, 26, 26, 16, 26, 26, 24, 26, 26, 22,  0,  0, 19, 26, 26, 24, 26, 26, 16, 26, 26, 26, 26, 22,  0},
        { 0, 21,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0, 21,  0},
        { 0, 21,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0, 21,  0},
        { 0, 25, 26, 22,  0,  0, 17, 26, 26, 18, 26, 26, 24, 26, 26, 24, 26, 26, 18, 26, 26, 20,  0,  0, 19, 26, 28,  0},
        { 0,  0,  0, 21,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0, 21,  0,  0,  0},
        { 0,  0,  0, 21,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0, 21,  0,  0,  0},
        { 0, 19, 26, 24, 26, 26, 28,  0,  0, 25, 26, 26, 22,  0,  0, 19, 26, 26, 28,  0,  0, 25, 26, 26, 24, 26, 22,  0},
        { 0, 21,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0},
        { 0, 21,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0},
        { 0, 25, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 24, 26, 26, 24, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 28,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}

    };

    private short[][] screenData;
    private Timer timer;

    /**
    * This function is the GUI class constructor
    */ 
    public GUI() {
        loadImages();
        initVariables();
        initGUI();
    }
    
    /**
    * This function intializes a GUI object and is called from the constructor
    */ 
    private void initGUI() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
    }


    /**
    * This function intializes global variables
    */ 
    private void initVariables() {
        screenData = new short[H_BLOCKS][W_BLOCKS];
        mazeColor = new Color(0, 100, 255);
        d = new Dimension(400, 400);
        
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }


    /**
    * This function displays the initial start display
    * @param g2d Graphics2D
    */ 
    private void showIntroScreen(Graphics2D g2d) {
        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_HEIGHT / 2 - 30, SCREEN_WIDTH - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50,  SCREEN_HEIGHT/ 2 - 30, SCREEN_WIDTH - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_WIDTH - metr.stringWidth(s)) / 2, SCREEN_HEIGHT / 2);
    }

    /**
    * This function draws the score in the bottom right corner of the window
    * @param g Graphics2D
    */ 
    private void drawScore(Graphics2D g, int score) {
        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_WIDTH / 2 + 96, SCREEN_HEIGHT + 16);

        for (i = 0; i < pacsLeft; i++) {
            g.drawImage(pacmanImage, i * 28 + 8, SCREEN_HEIGHT + 1, this);
        }
    }

    /**
    * This function draws the ghosts using their x and y coordinates
    * @param g2d Graphics2D
    */ 
    private void drawGhost(Graphics2D g2d) {
        Point locBlinky = Pacman.getBlinkyPos();
        Point locPinky = Pacman.getPinkyPos();
        Point locClyde = Pacman.getClydePos();
        Point locInky = Pacman.getInkyPos();

        double xScale = getWidth() / 28.0;
        double yScale = getHeight() / 36.0;

        g2d.drawImage(ghostBlinky, (int) (locBlinky.col * xScale), (int) (locBlinky.row * yScale), this);
        g2d.drawImage(ghostPinky, (int) (locPinky.col * xScale), (int) (locPinky.row * yScale), this);
        g2d.drawImage(ghostClyde, (int) (locClyde.col * xScale), (int) (locClyde.row * yScale), this);
        g2d.drawImage(ghostInky, (int) (locInky.col * xScale), (int) (locInky.row * yScale), this);
    }
    

    /**
    * This function draws pacman as he moves
    * @param g2d Graphics2D
    */ 
    private void drawPacman(Graphics2D g2d) {
        Point loc = Pacman.getPlayerPos();
        
        double xScale = getWidth() / 28.0;
        double yScale = getHeight() / 36.0;

        g2d.drawImage(pacmanImage, (int) (loc.col * xScale), (int) (loc.row * yScale), this);
    }

    /**
    * This function draws the ghosts using their x and y coordinates
    * @param g2d Graphics2D
    * @param x the x index to draw the ghost at
    * @param y the y index to draw the ghost at
    */ 
    private void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghostBlinky, x, y, this);
        g2d.drawImage(ghostPinky, x, y, this);
        g2d.drawImage(ghostClyde, x, y, this);
        g2d.drawImage(ghostInky, x, y, this);
    }

    /**
    * This function draws pacman as he moves
    * @param g2d Graphics2D
    * @param x the x index to draw pacman at
    * @param y the y index to draw pacman at
    */ 
    private void drawPacman(Graphics2D g2d, int x, int y) {
        g2d.drawImage(pacmanImage, x, y, this);
    }


   /**
    * This function draws the maze based on the data in screenData[][]
    * @param g2d Graphics2D
    */ 
    private void drawMaze(Graphics2D g2d) {

        int i = 0;
        int j;
        int x, y;

        for (y = 0; y < SCREEN_HEIGHT; y += BLOCK_SIZE) {
            j = 0;
            for (x = 0; x < SCREEN_WIDTH; x += BLOCK_SIZE) {
                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i][j] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i][j] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i][j] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,y + BLOCK_SIZE - 1);
                }

                if ((screenData[i][j] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,y + BLOCK_SIZE - 1);
                }

                if ((screenData[i][j] & 16) != 0) { 
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
                j++;  
            }
            i++;
        }
    }

    /**
    * This function defines the screen data board
    */ 
    private void initGame() {
        pacsLeft = 3;
        score = 0;
        int i, j;
        for (i = 0; i < H_BLOCKS; i++) {
            for(j = 0; j < W_BLOCKS; j++){
                screenData[i][j] = levelData[i][j];
            }
        }
    }

    /**
    * This function loads in the images to represent the pacman and ghosts
    */ 
    private void loadImages() {
        String filePath = ".." + File.separator + "res" + File.separator;

        ghostBlinky = new ImageIcon(filePath + "blinky.png").getImage();
        ghostPinky = new ImageIcon(filePath + "pinky.png").getImage();
        ghostInky = new ImageIcon(filePath + "inky.png").getImage();
        ghostClyde = new ImageIcon(filePath + "clyde.png").getImage();
        pacmanImage = new ImageIcon(filePath + "pacman.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);

        if (inGame) {
            drawPacman(g2d);
            drawGhost(g2d);
            drawScore(g2d, getPlayerScore());
        } else {
            drawPacman(g2d, 260, 520);
            drawGhost(g2d, 260, 320);
            drawScore(g2d, 0);
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == 's' || key == 'S') {
                inGame = true;
                initGame();
                new Thread(() -> Pacman.runGame()).start();
            }
            else if (key == KeyEvent.VK_RIGHT)
                Pacman.setPlayerDirection(Point.RIGHT);
            else if (key == KeyEvent.VK_UP)
                Pacman.setPlayerDirection(Point.UP);
            else if (key == KeyEvent.VK_LEFT)
                Pacman.setPlayerDirection(Point.LEFT);
            else if (key == KeyEvent.VK_DOWN)
                Pacman.setPlayerDirection(Point.DOWN);
            
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}


