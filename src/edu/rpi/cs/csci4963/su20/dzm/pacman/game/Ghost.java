package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import java.util.ArrayList;
import java.util.List;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

public abstract class Ghost {

    protected int moveGap;
    private int tickCounter;
    protected Point curPos;
    private Point prevPos;
    protected GhostMode curMode;
    private boolean leftHouse;

    private final Point[] checkOrder = {Point.UP, Point.LEFT, Point.DOWN, Point.RIGHT};

    protected abstract Point getTarget();

    protected Ghost(Point curPos, boolean inHouse) {
        moveGap = 6;
        tickCounter = 0;

        this.curPos = curPos;
        prevPos = curPos;

        leftHouse = !inHouse;
        curMode = GhostMode.SCATTER;
    }

    public Point getPosition() {
        return new Point(curPos.row, curPos.col);
    }

    public void tick() {
        //Only move every moveGap ticks. Allows customizing ghost speeds (notably for Cruise Elroy mode for Blinkey)
        if (++tickCounter >= moveGap) {
            tickCounter = 0;

            List<Point> potentialPoints = new ArrayList<Point>(4);

            //Make sure list is in the order of preference for when one is selected later
            for (Point diff : checkOrder) {
                    int targetRow = curPos.row + diff.row;
                    int targetCol = curPos.col + diff.col;

                    //Don't allow turning around
                    if (prevPos.row == targetRow && prevPos.col == targetCol)
                        continue;
                    if (Pacman.isLegalGhostMove(targetRow, targetCol, leftHouse))
                        potentialPoints.add(new Point(targetRow, targetCol));
            }

            prevPos = curPos;

            if (potentialPoints.size() == 0)
                return;

            if (potentialPoints.size() == 1)
                curPos = potentialPoints.get(0);
            else if (curMode == GhostMode.FRIGHTENED) //Select random turn if frightened
                curPos = potentialPoints.get((int) (Math.random() * potentialPoints.size()));
            else {
                //Find move that gets closest to target with order of preference being up, left, down, right in ties
                Point target = getTarget();

                double distToTarget = Double.MAX_VALUE;
                for (Point p : potentialPoints) {
                    double dist = Math.sqrt(Math.pow(target.row - p.row, 2) + Math.pow(target.col - p.col, 2));

                    if (dist < distToTarget) {
                        distToTarget = dist;
                        curPos = p;
                    }
                }
            }
        }
    }

}