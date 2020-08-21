package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import java.util.ArrayList;
import java.util.List;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;
import edu.rpi.cs.csci4963.su20.dzm.pacman.Tile;

/**
 * Abstract Ghost super class. Contains most of the ghost logic
 */
public abstract class Ghost {

    protected int moveGap;
    private int tickCounter;

    protected Point curPos, startPos;
    private Point prevPos, mustMove;

    protected GhostMode curMode;
    private boolean leftHouse, isDead;

    //Order to check potential moves in. Because of strictly less distance checking, also is the move priority order
    private final Point[] checkOrder = {Point.UP, Point.LEFT, Point.DOWN, Point.RIGHT};

    //Middle of board location for ghosts to return to if they are dead
    protected final Point revivePoint = new Point(17, 14);

    //Points where ghosts cannot go upwards. There are 4 on the map
    private final Point[] noUpPoints = {new Point(14, 12), new Point(14, 15), new Point(26, 12), new Point(26, 15)};

    //Get the target location for movement decision making
    protected abstract Point getTarget();

    //Protected constructor so it can be called by the ghosts themselves, but not anything else
    protected Ghost(Point curPos, boolean inHouse) {
        moveGap = 6;
        tickCounter = 0;

        this.curPos = curPos;
        startPos = curPos;
        prevPos = curPos;

        leftHouse = !inHouse;
        curMode = GhostMode.SCATTER;
        mustMove = null;
        isDead = false;
    }

    /**
     * Makes the ghost die and attempt to return to the ghost house in order to revive
     */
    public void die() {
        isDead = true;
        leftHouse = false;
    }

    public void reset() {
        curPos = startPos;
        prevPos = startPos;
        leftHouse = Pacman.getBoardPos(curPos.row, curPos.col) != Tile.GHOST_HOUSE;
    }

    /**
     * Get whether or not the ghost is currently dead
     * @return true if the ghost is dead, false otherwise
     */
    public boolean getIsDead() {
        return isDead;
    }

    /**
     * Get a copy of the ghost's current position
     * @return a copy of the current position
     */
    public Point getPosition() {
        return new Point(curPos.row, curPos.col);
    }

    /**
     * Set the ghost's movement mode
     * @param newMode the desired value for the new movement mode
     */
    public void setMode(GhostMode newMode) {
        GhostMode tmp = curMode;
        curMode = newMode;

        //If changing modes out of chase or scatter, the ghost must turn around
        if (!isDead && (tmp == GhostMode.CHASE || tmp == GhostMode.SCATTER))
            mustMove = prevPos;
    }

    /**
     * Get the ghost's current movement mode
     * @return the current movement mode
     */
    public GhostMode getMode() {
        return curMode;
    }

    /**
     * Should be called every game tick. If enough ticks have gone by for the ghost's speed, then the ghost will move.
     */
    public void tick() {
        //Only move every moveGap ticks. Allows customizing ghost speeds (notably for Cruise Elroy mode for Blinkey)
        if (++tickCounter >= moveGap) {
            tickCounter = 0;

            move();
        }
    }

    //Actually move the ghost
    private void move() {
        //Have a forced move, so do it and end turn
        if (mustMove != null) {
            prevPos = curPos;
            curPos = mustMove;
            mustMove = null;
            return;
        }

        List<Point> potentialPoints = new ArrayList<Point>(4);

        //Make sure list is in the order of preference for when one is selected later
        for (Point diff : checkOrder) {
            //If we are on one of the points we can't go up at, don't check up
            if (diff == Point.UP) {
                boolean onPoint = false;

                for (Point noUp : noUpPoints) {
                    if (curPos.equals(noUp)) {
                        onPoint = true;
                        break;
                    }
                }

                if (onPoint)
                    continue;
            }

            int targetRow = curPos.row + diff.row;
            int targetCol = curPos.col + diff.col;

            //Don't allow turning around
            if (prevPos.row == targetRow && prevPos.col == targetCol)
                continue;
            if (Pacman.isLegalGhostMove(targetRow, targetCol, leftHouse))
                potentialPoints.add(new Point(targetRow, targetCol));
        }

        //Update previous position before curPos is overwritten
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

        //Check if we need to revive. If not, make sure leftHouse is accurate
        //Ghosts can never be in the ghost house and be dead since they revive upon entering it
        if (Pacman.getBoardPos(curPos.row, curPos.col) == Tile.GHOST_HOUSE)
            isDead = false;
        else
            leftHouse = true;
    }

}