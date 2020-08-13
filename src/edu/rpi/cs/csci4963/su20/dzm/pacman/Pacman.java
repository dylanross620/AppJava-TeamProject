package edu.rpi.cs.csci4963.su20.dzm.pacman;

public class Pacman {

    private static Tile[][] board = new Tile[36][28];

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

    private static void initBoard() {
        //TODO
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        System.out.println("Test");
    }

}