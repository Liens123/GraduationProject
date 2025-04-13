package org.example.graduation_project.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KimiClient {

    private static final String KIMI_API_URL = "https://api.moonshot.cn/v1/chat/completions";
    private static final String KIMI_API_KEY = "sk-1Z2Rhv9qUzjdbUPyIQrs6jNwCgEcBF7YSxMkUyjwUwJMnPTT";
    private static final String MODEL_8 = "moonshot-v1-8k";
    private static final String MODEL_32 = "moonshot-v1-32k";
    private static final double TEMPERATURE = 0.3;
    private static final int MAX_TOKENS = 20480;

    public static String callKimiMarkdown(String userContent) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL_32);
        requestBody.put("temperature", TEMPERATURE);
        requestBody.put("max_tokens", MAX_TOKENS);

        JSONArray messages = new JSONArray();

        messages.add(new JSONObject()
                .fluentPut("role", "system")
                .fluentPut("content", "你是一个大语言模型分析助手，请用Markdown格式输出：关键词、情绪、摘要、高峰时段、感兴趣话题、自评分（1~10分）。"));

        messages.add(new JSONObject()
                .fluentPut("role","user")
                .fluentPut("content",userContent));

        requestBody.put("messages", messages);


        String result = HttpUtil.postJson(KIMI_API_URL,requestBody.toJSONString(),KIMI_API_KEY);
        JSONObject resp = JSONObject.parseObject(result);
        return resp.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }

}
