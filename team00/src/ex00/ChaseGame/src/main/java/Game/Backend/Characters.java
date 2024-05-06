package Game.Backend;

public enum Characters {
    GOAL, PLAYER, WALL, ENEMY;

    public int getCharacter() {
        return ordinal() + 1;
    }
}
