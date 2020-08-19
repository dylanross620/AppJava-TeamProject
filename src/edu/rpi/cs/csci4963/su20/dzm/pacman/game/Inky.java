package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

/**
 * Class for the Inky (blue) ghost
 */
public class Inky extends Ghost {

    private final Point scatterTarget = new Point(35, 27);
    private final Point startPos;
    private final Blinky blinky;

    /**
     * Initialize a new Inky instance
     * @param startPos the position for Inky to start at
     * @param blinky the instance of blinky. Used for targeting
     */
    public Inky(Point startPos, Blinky blinky) {
        super(startPos, true);
        this.startPos = startPos;
        this.blinky = blinky;
    }

    @Override
    protected Point getTarget() {
        //If dead, try to revive
        if (getIsDead())
            return revivePoint;

        //If player hasn't scored enough yet, don't enter field
        if (Pacman.getPlayerScore() < 30)
            return startPos;

        //If in scatter mode, go to scatter target
        if (curMode == GhostMode.SCATTER)
            return scatterTarget;
        
        Point blinkyPos = blinky.getPosition();
        Point playerPos = Pacman.getPlayerPos();
        Point playerDir = Pacman.getPlayerDir();

        //Make playerDir have length of 2 instead of 1
        playerDir.row *= 2;
        playerDir.col *= 2;

        //Create a vector from Blinky's current location to 2 places in front of the player
        Point vector = new Point(playerPos.row + playerDir.row - blinkyPos.row, playerPos.col + playerDir.col - blinkyPos.col);
        //Return 2 spaces in front of the player plus the previously calculated vector 
        //(effectively doubling the vector and going from Blinky)
        return new Point(playerPos.row + playerDir.row + vector.row, playerPos.col + playerDir.col + vector.col);
    }
    
}