package main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import model.Result;
import model.SSQ;
import org.springframework.web.client.RestTemplate;
import util.SSQUtil;

public final class Main {
    public static final String LATEST_SSQ_ID = "19115";
    public static final String MAX_ID = "153";
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) throws Exception {
        while (true) {
            runOnce();
        }
    }

    private static void runOnce() throws Exception {
        SSQ ssq = fetchLatestSSQ();
        if (ssq.isNew()) {
            recordSSQ(ssq);
            Result result = computeResult(ssq);
            recordResult(result);
        }
    }

    private static void recordResult(Result result) throws Exception {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(ClassLoader.getSystemResource("result.txt").toURI()), StandardOpenOption.TRUNCATE_EXISTING)) {
            bufferedWriter.write(result.toString());
            bufferedWriter.flush();
        }
    }

    private static Result computeResult(SSQ ssq) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("result.txt").toURI()));
        Result result = new Result();
        result.setRedBall1(SSQUtil.parseBall(lines.get(0)));
        result.setRedBall2(SSQUtil.parseBall(lines.get(1)));
        result.setRedBall3(SSQUtil.parseBall(lines.get(2)));
        result.setRedBall4(SSQUtil.parseBall(lines.get(3)));
        result.setRedBall5(SSQUtil.parseBall(lines.get(4)));
        result.setRedBall6(SSQUtil.parseBall(lines.get(5)));
        result.setBlueBall(SSQUtil.parseBall(lines.get(6)));
        return SSQUtil.computeResult(result, ssq);
    }

    private static void recordSSQ(SSQ ssq) throws Exception {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(ClassLoader.getSystemResource("ssq.txt").toURI()), StandardOpenOption.APPEND)) {
            bufferedWriter.write(ssq.toRecordString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    private static SSQ fetchLatestSSQ() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("ssq.txt").toURI()));
        String lastLine = lines.get(lines.size() - 1);
        SSQ ssq = SSQUtil.parseSSQ(lastLine);
        if (!SSQUtil.isLatestSSQ(ssq)) {
            SSQ nextSsq = requestSSQ(SSQUtil.nextSSQId(ssq.getId()));
            nextSsq.setNew(true);
            ssq = nextSsq;
        } else {
            System.exit(0);
        }
        return ssq;
    }

    private static SSQ requestSSQ(String id) {
        String url = "http://www.mxnzp.com/api/lottery/common/aim_lottery?expect=" + id + "&code=ssq";
        String result = restTemplate.getForEntity(url, String.class).getBody();
        JSONObject ssqJson = JSON.parseObject(result);
        String ssqLine = ssqJson.getJSONObject("data").getString("openCode");
        String[] ssqStrs = ssqLine.split("\\+");
        String[] redBalls = ssqStrs[0].split(",");
        String blueBall = ssqStrs[1];
        return new SSQ(id, redBalls[0], redBalls[1], redBalls[2], redBalls[3], redBalls[4], redBalls[5], blueBall);
    }
}
