package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

public class Pinky extends Ghost {

    private final Point scatterTarget = new Point(0, 0);

    public Pinky(Point startPos) {
        super(startPos, true);
    }

    @Override
    protected Point getTarget() {
        if (getIsDead())
            return revivePoint;

        if (curMode == GhostMode.SCATTER)
            return scatterTarget;
        
        Point playerDir = Pacman.getPlayerDir();
        Point playerPos = Pacman.getPlayerPos();

        playerDir.row *= 4;
        playerDir.col *= 4;

        return new Point(playerPos.row + playerDir.row, playerPos.col + playerDir.col);
    }
    
}