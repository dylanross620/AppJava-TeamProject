package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

/**
 * Class for the Clyde (orange) ghost
 */
public class Clyde extends Ghost {

    private final Point scatterTarget = new Point(35, 0);
    private final Point startPos;

    /**
     * Initialize a new Clyde instance
     * @param startPos the starting position for Clyde
     */
    public Clyde(Point startPos) {
        super(startPos, true);
        this.startPos = startPos;
    }

    @Override
    protected Point getTarget() {
        //If dead, try to revive
        if (getIsDead())
            return revivePoint;
        
        //If player hasn't scored enough points yet, don't enter the field
        if (((double) Pacman.getPlayerScore()) / Pacman.MAX_SCORE < 1.0/3)
            return startPos;

        //If in scatter mode, go to scatter target
        if (curMode == GhostMode.SCATTER)
            return scatterTarget;

        //Calculate distance to player
        Point playerPos = Pacman.getPlayerPos();
        double dist = Math.sqrt(Math.pow(curPos.row - playerPos.row, 2) + Math.pow(curPos.col - playerPos.col, 2));

        //If farther than 8 tiles from the player, chase them
        if (dist > 8)
            return playerPos;
        //Else, go to scatter target
        return scatterTarget;
    }
    
}