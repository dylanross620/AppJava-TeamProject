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

import edu.rpi.cs.csci4963.su20.dzm.pacman.game.GhostMode;
import edu.rpi.cs.csci4963.su20.dzm.pacman.game.Point;

/**
 * Main GUI class for Pacman game
 * @author Margot Rajkovic
 * @version 1.0
 */
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

    private int score = 0;
    private Image ghostBlinky;
    private Image ghostPinky;
    private Image ghostInky;
    private Image ghostClyde;
    private Image pacmanImage;
    private Image scaredGhost;
    private Image deadGhost;
    private Image energizer1;
    private Image energizer2;
    private Image energizer3;
    private Image energizer4;
    private Image emptyEnergizer;

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
    private void drawScore(Graphics2D g) {
        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_WIDTH / 2 + 96, SCREEN_HEIGHT + 16);
        int pacs = Pacman.getPacsLeft();
        for (i = 0; i < pacs; i++) {
            g.drawImage(pacmanImage, i * 28 + 8, SCREEN_HEIGHT + 1, this);
        }
    }

    /**
    * This function draws the ghosts using their x and y coordinates
    * @param g2d Graphics2D
    */ 
    private void drawGhost(Graphics2D g2d) {
        Point locBlinky = Pacman.getBlinky().getPosition();
        Point locPinky = Pacman.getPinky().getPosition();
        Point locClyde = Pacman.getClyde().getPosition();
        Point locInky = Pacman.getInky().getPosition();

        Image blinkyImage;
        if (Pacman.getBlinky().getIsDead())
            blinkyImage = deadGhost;
        else if (Pacman.getBlinky().getMode() == GhostMode.FRIGHTENED)
            blinkyImage = scaredGhost;
        else
            blinkyImage = ghostBlinky;
        
        Image pinkyImage;
        if (Pacman.getPinky().getIsDead())
            pinkyImage = deadGhost;
        else if (Pacman.getPinky().getMode() == GhostMode.FRIGHTENED)
            pinkyImage = scaredGhost;
        else
            pinkyImage = ghostPinky;
        
        Image clydeImage;
        if (Pacman.getClyde().getIsDead())
            clydeImage = deadGhost;
        else if (Pacman.getClyde().getMode() == GhostMode.FRIGHTENED)
            clydeImage = scaredGhost;
        else
            clydeImage = ghostClyde;

        Image inkyImage;
        if (Pacman.getInky().getIsDead())
            inkyImage = deadGhost;
        else if (Pacman.getInky().getMode() == GhostMode.FRIGHTENED)
            inkyImage = scaredGhost;
        else
            inkyImage = ghostInky;
        
        g2d.drawImage(blinkyImage, locBlinky.col * BLOCK_SIZE, locBlinky.row * BLOCK_SIZE, this);
        g2d.drawImage(pinkyImage, locPinky.col * BLOCK_SIZE, locPinky.row * BLOCK_SIZE, this);
        g2d.drawImage(clydeImage, locClyde.col * BLOCK_SIZE, locClyde.row * BLOCK_SIZE, this);
        g2d.drawImage(inkyImage, locInky.col * BLOCK_SIZE, locInky.row * BLOCK_SIZE, this);
    }
    

    /**
    * This function draws pacman as he moves
    * @param g2d Graphics2D
    */ 
    private void drawPacman(Graphics2D g2d) {
        Point loc = Pacman.getPlayerPos();
        if((screenData[loc.row][loc.col] & 16) != 0){
            screenData[loc.row][loc.col] = (short)(screenData[loc.row][loc.col] & 15);
            score++;
        }
        if(loc.row == 6 && loc.col == 1){
            energizer1 = emptyEnergizer;  
        } else if(loc.row == 6 && loc.col == 26){
            energizer2 = emptyEnergizer;
        } else if(loc.row == 26 && loc.col == 1){
            energizer3 = emptyEnergizer;
        } else if(loc.row == 26 && loc.col == 26){
            energizer4 = emptyEnergizer;
        }

        g2d.drawImage(pacmanImage, loc.col * BLOCK_SIZE, loc.row * BLOCK_SIZE, this);
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
    * This function draws the energizers
    * @param g2d Graphics2D
    */ 
    private void drawEnergizers(Graphics2D g2d) {
        g2d.drawImage(energizer1, 6 * BLOCK_SIZE, 1 * BLOCK_SIZE, this);
        g2d.drawImage(energizer2, 6 * BLOCK_SIZE, 26 * BLOCK_SIZE, this);
        g2d.drawImage(energizer3, 26 * BLOCK_SIZE, 1 * BLOCK_SIZE, this);
        g2d.drawImage(energizer4, 26 * BLOCK_SIZE, 26 * BLOCK_SIZE, this);
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
        scaredGhost = new ImageIcon(filePath + "scaredGhost.png").getImage();
        deadGhost = new ImageIcon(filePath + "deadGhost.png").getImage();
        energizer1 = new ImageIcon(filePath + "energizers.png").getImage();
        energizer2 = new ImageIcon(filePath + "energizers.png").getImage();
        energizer3 = new ImageIcon(filePath + "energizers.png").getImage();
        energizer4 = new ImageIcon(filePath + "energizers.png").getImage();
        emptyEnergizer = new ImageIcon(filePath + "emptyEnergizer.png").getImage();
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
        drawScore(g2d);

        if (inGame) {
            drawPacman(g2d);
            drawGhost(g2d);
            drawEnergizers(g2d);
        } else {
            drawPacman(g2d, 260, 520);
            drawGhost(g2d, 260, 320);
            showIntroScreen(g2d);
            drawEnergizers(g2d);
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
            else if (key == KeyEvent.VK_RIGHT) {
                Point playerPos = Pacman.getPlayerPos();

                int targetCol = playerPos.col + Point.RIGHT.col;
                targetCol = ((targetCol % 28) + 28) % 28; //Wrap around

                if (Pacman.isLegalPlayerMove(playerPos.row, targetCol))
                    Pacman.setPlayerDirection(Point.RIGHT);
            }
            else if (key == KeyEvent.VK_UP) {
                Point playerPos = Pacman.getPlayerPos();
                
                if (Pacman.isLegalPlayerMove(playerPos.row + Point.UP.row, playerPos.col))
                    Pacman.setPlayerDirection(Point.UP);
            }
            else if (key == KeyEvent.VK_LEFT) {
                Point playerPos = Pacman.getPlayerPos();

                int targetCol = playerPos.col + Point.LEFT.col;
                targetCol = ((targetCol % 28) + 28) % 28; //Wrap around

                if (Pacman.isLegalPlayerMove(playerPos.row, targetCol))
                    Pacman.setPlayerDirection(Point.LEFT);
            }
            else if (key == KeyEvent.VK_DOWN) {
                Point playerPos = Pacman.getPlayerPos();

                if (Pacman.isLegalPlayerMove(playerPos.row + Point.DOWN.row, playerPos.col))
                    Pacman.setPlayerDirection(Point.DOWN);
            }
            
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
