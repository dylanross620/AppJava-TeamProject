package edu.rpi.cs.csci4963.su20.dzm.pacman;

import edu.rpi.cs.csci4963.su20.dzm.pacman.game.Point;

public class Pacman {

    private static Tile[][] board;
    private static boolean running;
    
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
        //TODO
        throw new RuntimeException();
    }

    /**
     * Get a copy of the current direction of the player
     * A point can also be used to represent a 2D vector
     * @return the player's current direction
     */
    public static Point getPlayerDir() {
        //TODO
        throw new RuntimeException();
    }

    /**
     * Get the player's current score
     * @return the current score of the player
     */
    public static int getPlayerScore() {
        //TODO
        throw new RuntimeException();
    }

    /**
     * Get the maximum score achievable in the level
     * @return the maximum possible score
     */
    public static int getMaxScore() {
        //TODO
        throw new RuntimeException();
    }

    /**
     * Get a copy of Blinky's current position
     * @return the current position of Blinky
     */
    public static Point getBlinkyPos() {
        //TODO
        throw new RuntimeException();
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
        JFrame frame = new JFrame();
        frame.add(new GUI());
        
        frame.setTitle("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(380, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        
        System.out.println("Test");
        System.out.println("Commmit Test");
    }

}
