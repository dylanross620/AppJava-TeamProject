package edu.rpi.cs.csci4963.su20.dzm.pacman;

/*
 *  This is a class represent the coordinate in the Pacman board
 *
 */

public class Point {
	int x;
	int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
