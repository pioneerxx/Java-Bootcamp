package Game.Main;
import ChaseLogic.Characters.Enemy;
import Game.Backend.Arguments;
import Game.Backend.Characters;
import Game.Backend.GameData;
import Game.Map.MapGenerator;
import Game.Map.MapRenderer;
import Game.Exception.IllegalParametersException;
import java.io.IOException;
import java.util.Scanner;

public class GameInterface {
    private GameData gameData;
    private Scanner scanner;
    private MapRenderer mapRenderer;
    private MapGenerator mapGenerator;
    public GameInterface(Arguments arguments, String[] propertiesString) throws IOException, IllegalParametersException {
        gameData = new GameData(arguments, propertiesString);
        scanner = new Scanner(System.in);
        mapGenerator = new MapGenerator(gameData);
    }
    public void gameBootUp() {
        mapRenderer = new MapRenderer(mapGenerator.getMap(), gameData);
        String key;
        int res;
        boolean stepRes = false;
        while (true) {
            mapRenderer.render();
            System.out.println("Your move!");
            key = scanner.nextLine();
            if (key.equals("9")) {
                System.out.println("Game closed.");
                System.exit(-1);
            }
            if (isWay(mapGenerator.getMap()) == 0) {
                res = 2;
                break;
            }
            stepRes = false;
            while (!stepRes) {
                switch (key) {
                    case "w":
                    case "W":
                        stepRes = gameData.getPlayer().moveUp(mapGenerator.getMap());
                        break;
                    case "a":
                    case "A":
                        stepRes = gameData.getPlayer().moveLeft(mapGenerator.getMap());
                        break;
                    case "s":
                    case "S":
                        stepRes = gameData.getPlayer().moveDown(mapGenerator.getMap());
                        break;
                    case "d":
                    case "D":
                        stepRes = gameData.getPlayer().moveRight(mapGenerator.getMap());
                        break;
                    default:
                        System.out.println("Incorrect input");
                }
                if (!stepRes) {
                    System.out.println("Can't go there!");
                    key = scanner.nextLine();
                }
            }
            res = isGameOver(mapGenerator.getMap());
            if (res != 0) {
                break;
            }
            EnemyMoves();
        }
        if (res == 1) {
            System.out.println("You won!!");
        } else {
            System.out.println("You lost :(");
        }
    }

    private void EnemyMoves() {
        for (Enemy enemy : gameData.getEnemies()) {
            enemy.move(mapGenerator.getMap(), gameData.getPlayer().getCoordinates());
        }
    }

    private int isGameOver(int[][] map) {
        int res = 0;
        int playerX = gameData.getPlayer().getCoordinates().getX().intValue();
        int playerY = gameData.getPlayer().getCoordinates().getY().intValue();
        if (playerY == gameData.getGoalCoordinates().getY().intValue()
                && playerX == gameData.getGoalCoordinates().getX().intValue()) {
            res = 1;
        } else {
            if ((playerX - 1) >= 0) {
                res = map[playerX - 1][playerY] == Characters.ENEMY.getCharacter() ? 2 : res;
            }
            if ((playerX + 1) < map.length) {
                res = map[playerX + 1][playerY] == Characters.ENEMY.getCharacter() ? 2 : res;
            }
            if ((playerY - 1) >= 0) {
                res = map[playerX][playerY - 1] == Characters.ENEMY.getCharacter() ? 2 : res;
            }
            if ((playerY + 1) < map.length) {
                res = map[playerX][playerY + 1] == Characters.ENEMY.getCharacter() ? 2 : res;
            }
        }
        return res;
    }

    private int isWay(int[][] map) {
        int res = 4;
        int playerX = gameData.getPlayer().getCoordinates().getX().intValue();
        int playerY = gameData.getPlayer().getCoordinates().getY().intValue();
        if ((playerX - 1) >= 0) {
            res = (map[playerX - 1][playerY] == Characters.GOAL.getCharacter()) || map[playerX - 1][playerY] == 0 ? res : res - 1;
        } else {
            res -= 1;
        }
        if ((playerX + 1) < map.length) {
            res = (map[playerX + 1][playerY] == Characters.GOAL.getCharacter()) || map[playerX + 1][playerY] == 0 ? res : res - 1;
        } else {
            res -= 1;
        }
        if ((playerY - 1) >= 0) {
            res = (map[playerX][playerY - 1] == Characters.GOAL.getCharacter()) || map[playerX][playerY - 1] == 0 ? res : res - 1;
        } else {
            res -= 1;
        }
        if ((playerY + 1) < map.length) {
            res = (map[playerX][playerY + 1] == Characters.GOAL.getCharacter()) || map[playerX][playerY + 1] == 0 ? res : res - 1;
        } else {
            res -= 1;
        }
        return res;
    }

}
