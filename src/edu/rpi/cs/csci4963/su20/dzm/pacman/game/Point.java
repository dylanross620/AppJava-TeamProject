package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

/**
 * Helper class to hold and compare 2 integers.
 * All fields and methods are public because this class should not be used for computation, just storage and comparison
 */
public class Point {

    /**
     * A point that holds the upwards unit vector in (row, col) notation
     */
    public static final Point UP = new Point(-1, 0);
    /**
     * A point that holds the leftwards unit vector in (row, col) notation
     */
    public static final Point LEFT = new Point(0, -1);
    /**
     * A point that holds the downwards unit vector in (row, col) notation
     */
    public static final Point DOWN = new Point(1, 0);
    /**
     * A point that holds the rightwards unit vector in (row, col) notation
     */
    public static final Point RIGHT = new Point(0, 1);
    
    /**
     * The row and column to be stored in this point
     */
    public int row, col;

    /**
     * Initialize a new Point holding the coordinate (0, 0)
     */
    public Point() {
        row = 0;
        col = 0;
    }

    /**
     * Initialize a new point holding a desired coordinate
     * @param r the desired row
     * @param c the desired column
     */
    public Point(int r, int c) {
        row = r;
        col = c;
    }

    /**
     * Get if this point is equal to an object
     * @param o the object to be compared to
     * @return true iff o is a Point and holds the same coordinates as this
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;

        return equals((Point) o);
    }

    /**
     * Get if this point is equal to another point
     * @param p the point to compare to
     * @return true iff this and p hold the same coordinates
     */
    public boolean equals(Point p) {
        return row == p.row && col == p.col;
    }

}