package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

public class Clyde extends Ghost {

    private final Point scatterTarget = new Point(35, 0);
    private final Point startPos;

    public Clyde(Point startPos) {
        super(startPos, true);
        this.startPos = startPos;
    }

    @Override
    protected Point getTarget() {
        if (getIsDead())
            return revivePoint;
        
        if (((double) Pacman.getPlayerScore()) / Pacman.getMaxScore() < 1.0/3)
            return startPos;

        if (curMode == GhostMode.SCATTER)
            return scatterTarget;

        Point playerPos = Pacman.getPlayerPos();
        double dist = Math.sqrt(Math.pow(curPos.row - playerPos.row, 2) + Math.pow(curPos.col - playerPos.col, 2));

        if (dist <= 8)
            return playerPos;
        return scatterTarget;
    }
    
}