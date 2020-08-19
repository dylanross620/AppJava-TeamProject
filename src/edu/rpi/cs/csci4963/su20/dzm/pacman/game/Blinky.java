package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

public class Blinky extends Ghost {

    private final Point scatterTarget = new Point(0, 27);

    private boolean alwaysChase;

    public Blinky(Point startPos) {
        super(startPos, false);
        alwaysChase = false;
    }

    public void speedup() {
        moveGap = (int) (moveGap * .95);
    }

    public void enterCruiseElroy() {
        alwaysChase = true;
    }

    @Override
    protected Point getTarget() {
        if (getIsDead())
            return revivePoint;
        
        if (curMode == GhostMode.SCATTER && !alwaysChase)
            return scatterTarget;
        return Pacman.getPlayerPos();
    }
    
}