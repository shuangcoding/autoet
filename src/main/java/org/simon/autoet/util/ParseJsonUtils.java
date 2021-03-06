package org.simon.autoet.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.simon.autoet.track.Result;

/**
 * 用户解析json的工具类
 * @author simon
 * @since 2017/10/28 12:46
 * @version V1.0
 */
public class ParseJsonUtils {

    private ParseJsonUtils() {
    }

    public static Result parseIndex(String indexResponse) {
        JSONObject jsonResponse = JSONObject.parseObject(indexResponse);

        Long took = jsonResponse.getLong("took");
        String errors = jsonResponse.getString("errors");

        JSONArray items = jsonResponse.getJSONArray("items");
        int total = items.size();
        //每秒入库多少document
        double throughput = total * 1000.0 / took;
        if ("false".equals(errors)) {
            return new Result(took, total, 0, throughput, 0);
        }

        int error = 0;
        for (Object item : items) {
            JSONObject jsonItem = (JSONObject) item;
            JSONObject create = jsonItem.getJSONObject("create");
            String status = create.getString("status");

            if ("400".equals(status)) {
                error++;
            }
        }
        return new Result(took, total, error, throughput, error / total);
    }

    public static Result parseQuery(String queryResponse) {
        JSONObject jsonResponse = JSONObject.parseObject(queryResponse);
        Long took = jsonResponse.getLong("took");
        String timedOut = jsonResponse.getString("timed_out");
        long error = "false".equals(timedOut) ? 0 : 1;
        // 完成一次耗时多少秒
        double throughput = took / 1000.0;
        return new Result(took, 1, error, throughput, error);
    }

    public static String readJsonFile(String fileName) throws IOException {

        return Files.lines(Paths.get(fileName), Charsets.UTF_8)
                .reduce((line1, line2) -> line1 + line2).get();
    }
}
