package Game.Backend;

import java.io.IOException;
import java.util.ArrayList;

import ChaseLogic.Characters.Player;
import ChaseLogic.CoordinatesPair.CoordinatesPair;
import ChaseLogic.Characters.Enemy;

public class GameData {
    private int enemiesCount;
    private Player player;
    private CoordinatesPair goalCoordinates;
    private ArrayList<Enemy> enemies;
    private int wallsCount;
    private int mapSize;
    private String profile;
    private String enemyCharacter;
    private String playerCharacter;
    private String wallCharacter;
    private String goalCharacter;
    private String emptyCharacter;
    private String enemyColor;
    private String playerColor;
    private String wallColor;
    private String goalColor;
    private String emptyColor;
    public GameData(Arguments arguments, String[] propertiesString) throws IOException, ArrayIndexOutOfBoundsException {
        enemiesCount = arguments.getEnemiesCount();
        wallsCount = arguments.getWallsCount();
        mapSize = arguments.getSize();
        profile = arguments.getProfile();
        enemyCharacter = propertiesString[0].replace(" ", "");
        playerCharacter = propertiesString[1].replace(" ", "");
        wallCharacter = propertiesString[2].replace(" ", "");
        goalCharacter = propertiesString[3].replace(" ", "");
        emptyCharacter = propertiesString[4].equals("") ? " " : propertiesString[4];
        enemyColor = propertiesString[5].replace(" ", "");
        playerColor = propertiesString[6].replace(" ", "");
        wallColor = propertiesString[7].replace(" ", "");
        goalColor = propertiesString[8].replace(" ", "");
        emptyColor = propertiesString[9].replace(" ", "");
        enemies = new ArrayList<>();
    }

    public String getEmptyColor() {
        return emptyColor;
    }

    public String getEnemyColor() {
        return enemyColor;
    }

    public String getGoalColor() {
        return goalColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getWallColor() {
        return wallColor;
    }

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getMapSize() {
        return mapSize;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public String getEmptyCharacter() {
        return emptyCharacter;
    }

    public String getEnemyCharacter() {
        return enemyCharacter;
    }

    public String getGoalCharacter() {
        return goalCharacter;
    }

    public String getPlayerCharacter() {
        return playerCharacter;
    }

    public String getProfile() {
        return profile;
    }

    public String getWallCharacter() {
        return wallCharacter;
    }
    public void setPlayerCoordinates(CoordinatesPair coordinates) {
        player = new Player(coordinates);
    }

    public Player getPlayer() {
        return player;
    }
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void setGoalCoordinates(CoordinatesPair coordinates) {
        goalCoordinates = new CoordinatesPair(coordinates);
    }

    public CoordinatesPair getGoalCoordinates() {
        return goalCoordinates;
    }

    public void resetEnemies() {
        enemies = new ArrayList<>();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}