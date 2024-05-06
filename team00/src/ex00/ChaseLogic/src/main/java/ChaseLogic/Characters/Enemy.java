package ChaseLogic.Characters;

import ChaseLogic.CoordinatesPair.CoordinatesPair;
import ChaseLogic.PathFinder.PathFinder;

public class Enemy {
    private CoordinatesPair coordinates;

    public Enemy(CoordinatesPair coordinates) {
        this.coordinates = new CoordinatesPair(coordinates);
    }

    public void move(int[][] map, CoordinatesPair playersCoordinates) {
        PathFinder pathFinder = new PathFinder(map);
        pathFinder.setCoordinates(coordinates.getX(), coordinates.getY(), playersCoordinates.getX(), playersCoordinates.getY(), true);
        CoordinatesPair step;
        if (pathFinder.isPassable()) {
            step = pathFinder.getStep();
            map[step.getX().intValue()][step.getY().intValue()] = 4;
            map[coordinates.getX().intValue()][coordinates.getY().intValue()] = 0;
            coordinates.setX(step.getX().intValue());
            coordinates.setY(step.getY().intValue());
        }
    }
}