package util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import main.Main;
import model.Result;
import model.SSQ;

public final class SSQUtil {

    public static boolean isLatestSSQ(SSQ ssq) {
        return Integer.parseInt(ssq.getId()) >= Integer.parseInt(Main.LATEST_SSQ_ID);
    }

    public static SSQ parseSSQ(String line) {
        String[] ssqStr = line.split(":");
        String id = ssqStr[0];
        String[] balls = ssqStr[1].split(",");
        return new SSQ(id, balls[0], balls[1], balls[2], balls[3], balls[4], balls[5], balls[6]);
    }

    public static String nextSSQId(String currentId) {
        String year = currentId.substring(0, 2);
        String id = currentId.substring(2);
        if (Integer.parseInt(id) >= Integer.parseInt(Main.MAX_ID)) {
            return (Integer.parseInt(year) + 1) + "001";
        } else {
            return year + formatId(Integer.parseInt(id) + 1);
        }
    }

    public static Map<String, Integer> parseBall(String line) {
        Map<String, Integer> result = new HashMap<>();
        String[] balls = line.split(",");
        Stream.of(balls).filter(ball -> ball != null && !ball.equals("")).forEach(ball -> {
            String[] ballNumAndTime = ball.split("-");
            result.put(ballNumAndTime[0], Integer.parseInt(ballNumAndTime[1]));
        });
        return result;
    }

    public static Result computeResult(Result result, SSQ ssq) {
        BiFunction<String, Integer, Integer> computeFun = (num, times) -> (times == null ? 1 : times + 1);
        result.getRedBall1().compute(ssq.getRedBalls().get(0), computeFun);
        result.getRedBall2().compute(ssq.getRedBalls().get(1), computeFun);
        result.getRedBall3().compute(ssq.getRedBalls().get(2), computeFun);
        result.getRedBall4().compute(ssq.getRedBalls().get(3), computeFun);
        result.getRedBall5().compute(ssq.getRedBalls().get(4), computeFun);
        result.getRedBall6().compute(ssq.getRedBalls().get(5), computeFun);
        result.getBlueBall().compute(ssq.getBlueBall(), computeFun);
        return result;
    }

    private static String formatId(int id) {
        if (id < 10) {
            return "00" + id;
        } else if (id < 100) {
            return "0" + id;
        } else {
            return String.valueOf(id);
        }
    }
}
