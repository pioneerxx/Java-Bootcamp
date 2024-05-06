package Game.Map;

import Game.Backend.Characters;
import Game.Backend.GameData;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;


public class MapRenderer {
    private int[][] map;
    GameData data;

    public MapRenderer(int[][] map, GameData gameData) {
        this.map = map;
        data = gameData;
    }

    public void render() {
        ColoredPrinter printer = new ColoredPrinter();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map.length; j++) {
                if (map[i][j] == Characters.PLAYER.getCharacter()) {
                    printer.print(data.getPlayerCharacter(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getPlayerColor()));
                } else if (map[i][j] == Characters.WALL.getCharacter()) {
                    printer.print(data.getWallCharacter(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getWallColor()));
                } else if (map[i][j] == Characters.GOAL.getCharacter()) {
                    printer.print(data.getGoalCharacter(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getGoalColor()));
                } else if (map[i][j] == Characters.ENEMY.getCharacter()) {
                    printer.print(data.getEnemyCharacter(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getEnemyColor()));
                } else {
                    printer.print(data.getEmptyCharacter(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(data.getEmptyColor()));
                }
            }
            System.out.println();
        }
    }
}
