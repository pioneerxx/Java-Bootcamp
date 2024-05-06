package ChaseLogic.CoordinatesPair;

public class CoordinatesPair {
    private Integer x;
    private Integer y;
    public CoordinatesPair(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public CoordinatesPair(CoordinatesPair pair) {
        this.x = pair.x;
        this.y = pair.y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
