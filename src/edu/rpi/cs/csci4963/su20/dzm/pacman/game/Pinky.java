package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

/**
 * Class for Pinky (pink) ghost
 * @author Dylan Ross
 */
public class Pinky extends Ghost {

    private final Point scatterTarget = new Point(0, 0);

    /**
     * Initialize a new Pinky instance
     * @param startPos the position for Pinky to start at
     */
    public Pinky(Point startPos) {
        super(startPos, true);
    }

    @Override
    protected Point getTarget() {
        //If dead, try to revive
        if (getIsDead())
            return revivePoint;

        //If in scatter mode, go to scatter target
        if (curMode == GhostMode.SCATTER)
            return scatterTarget;
        
        Point playerDir = Pacman.getPlayerDir();
        Point playerPos = Pacman.getPlayerPos();

        //Make playerDir have length of 4 instead of 1
        playerDir.row *= 4;
        playerDir.col *= 4;

        //Return 4 spaces in front of the player in their current direction
        return new Point(playerPos.row + playerDir.row, playerPos.col + playerDir.col);
    }
    
}