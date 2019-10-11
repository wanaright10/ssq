package model;

import java.util.ArrayList;
import java.util.List;

public class SSQ {
    private String id;
    private List<String> redBalls;
    private String blueBall;
    private boolean isNew;

    public SSQ(String id, String redBall1, String redBall2, String redBall3, String redBall4, String redBall5, String redBall6, String blueBall) {
        this.id = id;

        List<String> redBalls = new ArrayList<>(6);
        redBalls.add(redBall1);
        redBalls.add(redBall2);
        redBalls.add(redBall3);
        redBalls.add(redBall4);
        redBalls.add(redBall5);
        redBalls.add(redBall6);
        this.redBalls = redBalls;

        this.blueBall = blueBall;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRedBalls() {
        return redBalls;
    }

    public void setRedBalls(List<String> redBalls) {
        this.redBalls = redBalls;
    }

    public String getBlueBall() {
        return blueBall;
    }

    public void setBlueBall(String blueBall) {
        this.blueBall = blueBall;
    }

    @Override
    public String toString() {
        return redBalls.get(0) + "," +
                redBalls.get(1) + "," +
                redBalls.get(2) + "," +
                redBalls.get(3) + "," +
                redBalls.get(4) + "," +
                redBalls.get(5) + "," +
                blueBall;
    }

    public String toRecordString() {
        return this.id + ":" + toString();
    }
}
