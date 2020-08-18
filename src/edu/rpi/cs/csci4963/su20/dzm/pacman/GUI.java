import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
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

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GUI extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

    private int N_GHOSTS = 3;
    private int pacsLeft, score;
    private Image ghost;
    private Image pacmanImage;

    private final short levelData[][] = {
        {19, 18, 26, 26, 26, 26, 18, 26, 18, 26, 26, 26, 26, 18, 22},
        {17, 20,  0,  0,  0,  0, 21,  0, 21,  0,  0,  0,  0, 17, 20},
        {17, 20,  0, 19, 26, 26, 28,  0, 25, 26, 26, 22,  0, 17, 20},
        {17, 20,  0, 21,  0,  0,  0,  0,  0,  0,  0, 21,  0, 17, 20},
        {17, 24, 18, 16, 18, 18, 22,  0, 19, 18, 18, 16, 18, 24, 20},
        {21,  0, 17, 16, 16, 16, 20,  0, 17, 16, 16, 16, 20,  0, 21},
        {21,  0, 17, 16, 24, 24, 24, 26, 24, 24, 24, 16, 20,  0, 21},
        {21,  0, 17, 20,  0,  0,  0,  0,  0,  0,  0, 17, 20,  0, 21},
        {21,  0, 17, 16, 18, 18, 18, 26, 18, 18, 18, 16, 20,  0, 21},
        {21,  0, 17, 16, 16, 16, 20,  0, 17, 16, 16, 16, 20,  0, 21},
        {17, 18, 24, 16, 24, 24, 28,  0, 25, 24, 24, 16, 24, 18, 20},
        {17, 20,  0, 21,  0,  0,  0,  0,  0,  0,  0, 21,  0, 17, 20},
        {17, 20,  0, 25, 26, 26, 22,  0, 19, 26, 26, 28,  0, 17, 20},
        {17, 20,  0,  0,  0,  0, 21,  0, 21,  0,  0,  0,  0, 17, 20},
        {25, 24, 26, 26, 26, 26, 24, 26, 24, 26, 26, 26, 26, 24, 28}
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
        screenData = new short[N_BLOCKS][N_BLOCKS];
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

    private void playGame(Graphics2D g2d) {
        movePacman();
        drawPacman(g2d, 0, 0);
        moveGhosts();
        drawGhost(g2d, 0, 0);
        checkMaze();
        
    }

    /**
    * This function displays the initial start display
    * @param g2d Graphics2D
    */ 
    private void showIntroScreen(Graphics2D g2d) {
        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
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
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (i = 0; i < pacsLeft; i++) {
            g.drawImage(pacmanImage, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {

        short i = 0;
        short j = 0;
        boolean finished = true;
        while (i < N_BLOCKS  && finished) {
            while(j < N_BLOCKS && finished){
                if ((screenData[i][j] & 48) != 0) {
                    finished = false;
                }
                j++;
            }
            i++;
        }
        if (finished) {
            score += 50;
        }
    }

    private void moveGhosts() {

        
    }

    /**
    * This function draws the ghosts using their x and y coordinates
    * @param g2d Graphics2D
    * @param x the x index to draw the ghost at
    * @param y the y index to draw the ghost at
    */ 
    private void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    private void movePacman() {

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

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            j = 0;
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

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
        for (i = 0; i < N_BLOCKS; i++) {
            for(j = 0; j < N_BLOCKS; j++){
                screenData[i][j] = levelData[i][j];
            }
        }
    }

    /**
    * This function loads in the images to represent the pacman and ghosts
    */ 
    private void loadImages() {
        ghost = new ImageIcon("ghost.png").getImage();
        pacmanImage = new ImageIcon("pacman.png").getImage();
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
            playGame(g2d);
        } else {
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
            }
            
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
