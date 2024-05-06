package edu.school21.tanks.models;


public class GameResult {
    private Long id;
    private Integer shotsHit;
    private Integer shotsShot;
    public GameResult() {}
    public GameResult(Integer shotsHit, Integer shotsShot) {
        this.shotsHit = shotsHit;
        this.shotsShot = shotsShot;
    }
    public Integer getShotsHit() {
        return shotsHit;
    }

    public Integer getShotsShot() {
        return shotsShot;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
