package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

/**
 * Class for the Blinky (red) ghost
 * @author Dylan Ross
 * @version 1.0
 */
public class Blinky extends Ghost {

    private final Point scatterTarget = new Point(0, 27);

    private boolean alwaysChase;

    /**
     * Initialize a new Blinky instance
     * @param startPos the position to start at
     */
    public Blinky(Point startPos) {
        super(startPos, false);
        alwaysChase = false;
    }

    /**
     * Make blinky move 5% faster
     */
    public void speedup() {
        moveGap = (int) (moveGap * .95);
    }

    /**
     * Make blinky enter Cruise Elroy mode.
     * When in this mode, Blinky will not scatter.
     * Should be called after speedup has been called once
     */
    public void enterCruiseElroy() {
        alwaysChase = true;
    }

    @Override
    protected Point getTarget() {
        //Try to revive if dead
        if (getIsDead())
            return revivePoint;
        
        //If in scatter and not in elroy mode, go to scatter target
        if (curMode == GhostMode.SCATTER && !alwaysChase)
            return scatterTarget;

        //Just chase the player as close as possible
        return Pacman.getPlayerPos();
    }
    
}