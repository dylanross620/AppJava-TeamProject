package edu.rpi.cs.csci4963.su20.dzm.pacman;

import java.util.ArrayList;

import edu.rpi.cs.csci4963.su20.dzm.pacman.game.Point;

public class Pacman {
	
	private final int POINT_SCORES = 10;
	private final int FRUIT_SCORES = 30;
	private final int ENERGIZER_SCORES = 50;
	private final int ENERGIZER_LAST_TICKS = 3;
	private static int energizedCounter = 0;
	private static int scores = 0;
  	private static Tile[][] board;
  	private static Point direction;
   	private static Point location;
   	private static boolean running;
	private static JFrame frame;
    	private static GUI gui;
    
    /**
     * Sets a target tile in the board to a specified type.
     * Useful for pacman since every tile he leaves should become empty. Does nothing if target tile is out
     * of bounds.
     * @param row the row of the target tile
     * @param col the column of the target tile
     * @param newTile the new type for the target tile
     */
    public static void setBoardPos(int row, int col, Tile newTile) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return;
        
        board[row][col] = newTile;
    }

    /**
     * Gets the tile value for a target position in the board. Out of bounds accesses returns a wall.
     * @param row the row of the target tile
     * @param col the column of the target tile
     * @return the value of the tile at the specified location
     */
    public static Tile getBoardPos(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return Tile.WALL;
        
        return board[row][col];
    }

    /**
     * Get whether or not pacman can legally move into a tile, given that he can access that tile from his current location
     * @param row the row of the target tile
     * @param col the column of the target tile
     * @return true if the tile is not a wall or the ghost house, false otherwise
     */
    public static boolean isLegalPlayerMove(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;

        Tile t = board[row][col];
        return !(t == Tile.WALL || t == Tile.GHOST_HOUSE);
    }

    /**
     * Get whether or not a ghost can legally move into a tile, given that it can access that tile from its current location
     * @param row the row of the target tile
     * @param col the column of the target tile
     * @param leftHouse whether or not the ghost has already left the ghost house since spawning
     * @return true if the tile is a legal move, false otherwise
     */
    public static boolean isLegalGhostMove(int row, int col, boolean leftHouse) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;

        Tile t = board[row][col];
        return !(t == Tile.WALL || (t == Tile.GHOST_HOUSE && leftHouse));
    }

    /**
     * Get a copy of the player's current position
     * @return the current position of the player
     */
    public static Point getPlayerPos() {
    	return location;
    }

    /**
     * Get a copy of the current direction of the player
     * A point can also be used to represent a 2D vector
     * @return the player's current direction
     */
    public static Point getPlayerDir() {
    	return direction;
    }

    /**
     * Get the player's current score
     * @return the current score of the player
     */
    public static int getPlayerScore() {
    	return scores;
    }

    /**
     * move the pacman to next tile and calculate the scores, if pacman dies, the pacman back to 0,0 on board
     * @param x row 
     * @param y col 
     * @param ghostPos The location of all ghosts.
     * @return zero and positive for the score gained on this move
     * 			-1 if the pacman is eaten by the ghost
     */
    private int movePacman(int x, int y, ArrayList<Point> ghostPos) {
    	
    	if(!this.isLegalPlayerMove(x, y)) {
    		return 0;
    	}
    	for(int i = 0; i < ghostPos.size();i++) {
    		Point tempGhostPos = ghostPos.get(i);   
//    		If pacman dies.
    		if((tempGhostPos.equals(this.location))&&(this.energizedCounter > 0)) {
    			this.location = new Point(0,0);
    				return -1;
    		}
    	}
    	if(this.energizedCounter > 0) {
    		this.energizedCounter -= 1;
    	}
    	int gainedScore = 0;
    	Tile tempTile = this.board[x][y];
    	if(tempTile == Tile.ENERGIZER) {
    		gainedScore += this.ENERGIZER_SCORES;
    		this.energizedCounter = this.ENERGIZER_LAST_TICKS;
    	}
    	else if(tempTile == Tile.FRUIT) {
    		gainedScore += this.FRUIT_SCORES;
    	}
//    	both wall and ghost house are not allowed to enter
    	else if(tempTile == Tile.WALL||tempTile == Tile.GHOST_HOUSE) {
    		return gainedScore;
    	}
    	else if(tempTile == Tile.POINT) {
    		gainedScore += this.POINT_SCORES;
    	}
    	this.location = new Point(x,y);
    	gui.repaint();
    	scores += gainedScore;
    	return gainedScore;
    }
    
    /*
     * these method move pacmen by one tile one the board in one direction. And return the scores gained on this move. If -1
     * is returned, the pacman is eaten by ghost.
     * 
     * the method required input the location of the all ghost
     * 
     */
    public int moveUp(ArrayList<Point> ghostPos) {
    	this.direction = Point.UP;
    	return this.movePacman(this.location.row+1, this.location.col, ghostPos);
    }
    public int moveDown(ArrayList<Point> ghostPos) {
    	this.direction = Point.DOWN;
    	return this.movePacman(this.location.row-1, this.location.col, ghostPos);
    }
    public int moveLeft(ArrayList<Point> ghostPos) {
    	this.direction = Point.LEFT;
    	return this.movePacman(this.location.row, this.location.col-1, ghostPos);
    }
    public int moveRight(ArrayList<Point> ghostPos) {
    	this.direction = Point.RIGHT;
    	return this.movePacman(this.location.row, this.location.col+1, ghostPos);
    }
    private static void initBoard() {
        board  = new Tile[36][28];

        //TODO
        throw new RuntimeException();
    }

    private static void tick() {
        //TODO
        throw new RuntimeException();
    }

    /**
     * Method to call in order to begin the game loop
     */
    public static void runGame() {
        int ticksPerSec = 60;
        long tickGap = 1000L / ticksPerSec; // time in milliseconds
        
        running = true;
        long lastTick = 0;
        while (running) {
            if (System.currentTimeMillis() - lastTick >= tickGap) {
                lastTick = System.currentTimeMillis();
                tick();
            }
        }
    }

    public static void main(String[] args) {
        frame = new JFrame();
        gui = new GUI();
        frame.add(gui);
        
        frame.setTitle("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(560, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        
        System.out.println("Test");
        System.out.println("Commmit Test");
    }

}
