package Game.Map;

import Game.Backend.*;
import Game.Exception.IllegalParametersException;
import java.util.*;
import ChaseLogic.PathFinder.PathFinder;
import ChaseLogic.CoordinatesPair.CoordinatesPair;
import ChaseLogic.Characters.Enemy;

public class MapGenerator {
    private final int enemiesCount;
    private final int wallsCount;
    private final int mapSize;
    private int[][] map;
    PathFinder pathFinder;
    public int[][] getMap() {
        return map;
    }

    public MapGenerator(GameData gameData) throws IllegalArgumentException {
        if (gameData.getMapSize() <= 0 || gameData.getEnemiesCount() < 0 || gameData.getWallsCount() < 0
                || (gameData.getMapSize() * gameData.getMapSize()) < (gameData.getWallsCount() + gameData.getEnemiesCount() + 2)) {
            throw new IllegalParametersException("Invalid input");
        }
        enemiesCount = gameData.getEnemiesCount();
        wallsCount = gameData.getWallsCount();
        mapSize = gameData.getMapSize();
        generateMap(gameData);
        PathFinder pathFinder = new PathFinder(map);
        pathFinder.setCoordinates(gameData.getPlayer().getCoordinates().getX().intValue(), gameData.getPlayer().getCoordinates().getY().intValue(),
                gameData.getGoalCoordinates().getX().intValue(), gameData.getGoalCoordinates().getY().intValue(), false);
        int count = 0;
        while (!pathFinder.isPassable()) {
            if (count > 100) {
                throw new IllegalParametersException("Too many obstacles, change the amount of enemies of walls");
            }
            count++;
            gameData.resetEnemies();
            generateMap(gameData);
            pathFinder.setCoordinates(gameData.getPlayer().getCoordinates().getX().intValue(), gameData.getPlayer().getCoordinates().getY().intValue(),
                    gameData.getGoalCoordinates().getX().intValue(), gameData.getGoalCoordinates().getY().intValue(), false);
        }
    }

    private void generateMap(GameData data) {
        CoordinatesPair randomCoordinates = new CoordinatesPair(0, 0);
        map = new int[mapSize][mapSize];
        ArrayList<CoordinatesPair> coordinates = new ArrayList<>();
        for(int i = 0; i < enemiesCount; i++) {
            generateCoordinates(randomCoordinates);
            checkCoordinates(randomCoordinates, coordinates);
            addEnemy(randomCoordinates, data);
            map[randomCoordinates.getX().intValue()][randomCoordinates.getY().intValue()] = Characters.ENEMY.getCharacter();
        }
        for(int i = 0; i < wallsCount; i++) {
            generateCoordinates(randomCoordinates);
            checkCoordinates(randomCoordinates, coordinates);
            map[randomCoordinates.getX().intValue()][randomCoordinates.getY().intValue()] = Characters.WALL.getCharacter();
        }
        generateCoordinates(randomCoordinates);
        checkCoordinates(randomCoordinates, coordinates);
        map[randomCoordinates.getX().intValue()][randomCoordinates.getY().intValue()] = Characters.PLAYER.getCharacter();
        data.setPlayerCoordinates(randomCoordinates);
        generateCoordinates(randomCoordinates);
        checkCoordinates(randomCoordinates, coordinates);
        map[randomCoordinates.getX().intValue()][randomCoordinates.getY().intValue()] = Characters.GOAL.getCharacter();
        data.setGoalCoordinates(randomCoordinates);
    }

    private void generateCoordinates(CoordinatesPair randomCoordinates) {
        randomCoordinates.setX((int)(Math.random()  * mapSize));
        randomCoordinates.setY((int)(Math.random()  * mapSize));
    }

    private void checkCoordinates(CoordinatesPair randomCoordinates, ArrayList<CoordinatesPair> coordinates) {
        while(containsCoordinates(randomCoordinates, coordinates)) {
            generateCoordinates(randomCoordinates);
        }
        coordinates.add(new CoordinatesPair(randomCoordinates));
    }

    private static void addEnemy(CoordinatesPair randomCoordinates, GameData data) {
        Enemy enemy = new Enemy(randomCoordinates);
        data.addEnemy(enemy);
    }

    private boolean containsCoordinates(CoordinatesPair randomCoordinates, ArrayList<CoordinatesPair> coordinates) {
        boolean res = false;
        for (CoordinatesPair pair : coordinates) {
            if (randomCoordinates.getX().intValue() == pair.getX().intValue() && randomCoordinates.getY().intValue() == pair.getY().intValue()) {
                res = true;
                break;
            }
        }
        return res;
    }
}