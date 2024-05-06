package ChaseLogic.Characters;

import ChaseLogic.CoordinatesPair.CoordinatesPair;

public class Player {

    private CoordinatesPair coordinates;

    public Player(CoordinatesPair coordinates) {
        this.coordinates = new CoordinatesPair(coordinates);
    }

    public CoordinatesPair getCoordinates() {
        return coordinates;
    }

    public boolean moveUp(int[][] map) {
        boolean res = true;
        if (coordinates.getX().intValue() - 1 < 0) {
            res = false;
        } else if (map[coordinates.getX().intValue() - 1][coordinates.getY().intValue()] == 3) {
            res = false;
        } else if (map[coordinates.getX().intValue() - 1][coordinates.getY().intValue()] == 4) {
            res = false;
        } else {
            map[coordinates.getX().intValue() - 1][coordinates.getY().intValue()] = 2;
            map[coordinates.getX().intValue()][coordinates.getY().intValue()] = 0;
            coordinates.setX(coordinates.getX().intValue() - 1);
        }
        return res;
    }
    public boolean moveDown(int[][] map) {
        boolean res = true;
        if (coordinates.getX().intValue() + 1 >= map.length) {
            res = false;
        } else if (map[coordinates.getX().intValue() + 1][coordinates.getY().intValue()] == 3) {
            res = false;
        } else if (map[coordinates.getX().intValue() + 1][coordinates.getY().intValue()] == 4) {
            res = false;
        } else {
            map[coordinates.getX().intValue() + 1][coordinates.getY().intValue()] = 2;
            map[coordinates.getX().intValue()][coordinates.getY().intValue()] = 0;
            coordinates.setX(coordinates.getX().intValue() + 1);
        }
        return res;
    }

    public boolean moveLeft(int[][] map) {
        boolean res = true;
        if (coordinates.getY().intValue() - 1 < 0) {
            res = false;
        } else if (map[coordinates.getX().intValue()][coordinates.getY().intValue() - 1] == 3) {
            res = false;
        } else if (map[coordinates.getX().intValue()][coordinates.getY().intValue() - 1] == 4) {
            res = false;
        } else {
            map[coordinates.getX().intValue()][coordinates.getY().intValue() - 1] = 2;
            map[coordinates.getX().intValue()][coordinates.getY().intValue()] = 0;
            coordinates.setY(coordinates.getY().intValue() - 1);
        }
        return res;
    }

    public boolean moveRight(int[][] map) {
        boolean res = true;
        if (coordinates.getY().intValue() + 1 >= map.length) {
            res = false;
        } else if (map[coordinates.getX().intValue()][coordinates.getY().intValue() + 1] == 3) {
            res = false;
        } else if (map[coordinates.getX().intValue()][coordinates.getY().intValue() + 1] == 4) {
            res = false;
        } else {
            map[coordinates.getX().intValue()][coordinates.getY().intValue() + 1] = 2;
            map[coordinates.getX().intValue()][coordinates.getY().intValue()] = 0;
            coordinates.setY(coordinates.getY().intValue() + 1);
        }
        return res;
    }
}
