package Game.Backend;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Arguments {
    @Parameter(names = "--enemiesCount", required = true)
    private int enemiesCount;
    @Parameter(names = "--wallsCount", required = true)
    private int wallsCount;
    @Parameter(names = "--size", required = true)
    private int size;
    @Parameter(names = "--profile", required = true)
    private String profile;

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public int getSize() {
        return size;
    }

    public String getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return "--enemiesCount=" + enemiesCount +
                "\n--wallsCount=" + wallsCount +
                "\n--size=" + size +
                "\n--profile=" + profile;
    }

}
