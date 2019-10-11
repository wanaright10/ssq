package model;

import java.util.Map;

public class Result {
    private Map<String, Integer> redBall1;
    private Map<String, Integer> redBall2;
    private Map<String, Integer> redBall3;
    private Map<String, Integer> redBall4;
    private Map<String, Integer> redBall5;
    private Map<String, Integer> redBall6;
    private Map<String, Integer> blueBall;

    public Map<String, Integer> getRedBall1() {
        return redBall1;
    }

    public void setRedBall1(Map<String, Integer> redBall1) {
        this.redBall1 = redBall1;
    }

    public Map<String, Integer> getRedBall2() {
        return redBall2;
    }

    public void setRedBall2(Map<String, Integer> redBall2) {
        this.redBall2 = redBall2;
    }

    public Map<String, Integer> getRedBall3() {
        return redBall3;
    }

    public void setRedBall3(Map<String, Integer> redBall3) {
        this.redBall3 = redBall3;
    }

    public Map<String, Integer> getRedBall4() {
        return redBall4;
    }

    public void setRedBall4(Map<String, Integer> redBall4) {
        this.redBall4 = redBall4;
    }

    public Map<String, Integer> getRedBall5() {
        return redBall5;
    }

    public void setRedBall5(Map<String, Integer> redBall5) {
        this.redBall5 = redBall5;
    }

    public Map<String, Integer> getRedBall6() {
        return redBall6;
    }

    public void setRedBall6(Map<String, Integer> redBall6) {
        this.redBall6 = redBall6;
    }

    public Map<String, Integer> getBlueBall() {
        return blueBall;
    }

    public void setBlueBall(Map<String, Integer> blueBall) {
        this.blueBall = blueBall;
    }

    @Override
    public String toString() {
        return printBall(redBall1) + "\n" +
                printBall(redBall2) + "\n" +
                printBall(redBall3) + "\n" +
                printBall(redBall4) + "\n" +
                printBall(redBall5) + "\n" +
                printBall(redBall6) + "\n" +
                printBall(blueBall);

    }

    private String printBall(Map<String, Integer> ball) {
        return ball.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(entry -> entry.getKey() + "-" + entry.getValue()).reduce("", (str1, str2) -> str1 + "," + str2);
    }
}
