package edu.rpi.cs.csci4963.su20.dzm.pacman.game;

import edu.rpi.cs.csci4963.su20.dzm.pacman.Pacman;

public class Inky extends Ghost {

    private final Point scatterTarget = new Point(35, 27);
    private final Point startPos;

    public Inky(Point startPos) {
        super(startPos, true);
        this.startPos = startPos;
    }

    @Override
    protected Point getTarget() {
        if (getIsDead())
            return revivePoint;

        if (Pacman.getPlayerScore() < 30)
            return startPos;
        if (curMode == GhostMode.SCATTER)
            return scatterTarget;
        
        Point blinkyPos = Pacman.getBlinkyPos();
        Point playerPos = Pacman.getPlayerPos();
        Point playerDir = Pacman.getPlayerDir();

        playerDir.row *= 2;
        playerDir.col *= 2;

        Point vector = new Point(playerPos.row + playerDir.row - blinkyPos.row, playerPos.col + playerDir.col - blinkyPos.col);
        return new Point(playerPos.row + playerDir.row + vector.row, playerPos.col + playerDir.col + vector.col);
    }
    
}