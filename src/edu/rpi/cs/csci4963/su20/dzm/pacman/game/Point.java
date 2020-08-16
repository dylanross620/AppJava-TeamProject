package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

public class Point {

    public static final Point UP = new Point(-1, 0);
    public static final Point LEFT = new Point(0, -1);
    public static final Point DOWN = new Point(1, 0);
    public static final Point RIGHT = new Point(0, 1);
    
    public int row, col;

    public Point() {
        row = 0;
        col = 0;
    }

    public Point(int r, int c) {
        row = r;
        col = c;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;

        return equals((Point) o);
    }

    public boolean equals(Point p) {
        return row == p.row && col == p.col;
    }

}