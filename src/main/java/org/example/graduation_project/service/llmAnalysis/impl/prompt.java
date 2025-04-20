package org.example.graduation_project.service.llmAnalysis.impl;

public class prompt {
    public static final String P1 = "      # 角色\n" +
            "你是一位专业的对话数据分析师，擅长从用户与AI玩具的交互日志中提取有价值的信息。\n" +
            "\n" +
            "# 背景\n" +
            "我将提供一天内用户与AI玩具的对话日志。日志格式为按时间顺序排列的条目，每条包含时间戳、角色（\"用户\" 或 \"AI玩具\"）和对话内容。\n" +
            "\n" +
            "# 任务\n" +
            "请仔细分析提供的对话日志，并严格按照下面定义的 JSON 格式返回你的分析结果。\n" +
            "\n" +
            "# 分析维度与要求\n" +
            "\n" +
            "1.  **对话高峰时段分析 (`peak_hours_analysis`):**\n" +
            "    *   统计每个小时（0-23点）内发生的总对话轮数（用户+AI算一轮或只算用户发言次数，请明确你的统计口径并在结果中说明）。\n" +
            "    *   找出对话最频繁的 1-3 个小时，并计算它们各自占全天总对话轮数的百分比。\n" +
            "    *   在 JSON 的 `peak_hours_analysis` 字段中，返回一个包含对象的数组，每个对象包含 `hour` (整数, 0-23), `count` (整数, 该小时对话轮数), `percentage` (浮点数, 例如 15.5 代表 15.5%)。请包含所有小时的数据，即使数量为0。同时添加一个 `description` 字段说明你的统计口径（例如：\"统计用户发言次数\"）。\n" +
            "\n" +
            "2.  **用户情感分析 (`user_sentiment_analysis`):**\n" +
            "    *   **仅分析角色为 \"用户\" 的发言。**\n" +
            "    *   识别用户在对话中表现出的主要情感，例如：感兴趣、高兴、兴奋、疑惑、不耐烦、沮丧、无聊、满足等（你可以根据对话内容补充或调整情感标签）。\n" +
            "    *   在 JSON 的 `user_sentiment_analysis` 字段中，返回一个对象，其中 key 是情感标签 (小写英文字符串，如 \"interested\", \"happy\", \"confused\")，value 是该情感出现的次数（整数）。可以包含一个 `dominant_sentiment` 字段指出当天最主要的用户情感（出现次数最多的）。\n" +
            "\n" +
            "3.  **用户话题兴趣分析 (`topic_interest_analysis`):**\n" +
            "    *   分析**用户**主要对哪些话题或功能表现出兴趣。\n" +
            "    *   对于每个识别出的主要兴趣点，**必须**简要说明你判断该用户对其感兴趣的**原因**（例如：多次提问、追问细节、表达正面反馈等）。\n" +
            "    *   在 JSON 的 `topic_interest_analysis` 字段中，返回一个包含对象的数组，每个对象包含 `topic` (字符串, 兴趣点名称) 和 `reasoning` (字符串, 判断原因)。\n" +
            "\n" +
            "4.  **AI 回答建议 (`ai_suggestion_analysis`):**\n" +
            "    *   分析**AI玩具**的回答。\n" +
            "    *   识别 AI 回答中存在的明显缺点，例如：回答重复、过于简单、无法理解用户意图、答非所问、缺乏互动性等。\n" +
            "    *   针对识别出的缺点，提出 **具体、可操作** 的改进建议。\n" +
            "    *   在 JSON 的 `ai_suggestion_analysis` 字段中，返回一个包含对象的数组，每个对象包含 `weakness` (字符串, 识别出的缺点) 和 `suggestion` (字符串, 具体的改进建议)。\n" +
            "\n" +
            "# 输出格式 (必须严格遵守的 JSON 结构)\n" +
            "\n" +
            "```json\n" +
            "{\n" +
            "  \"peak_hours_analysis\": {\n" +
            "    \"description\": \"统计口径说明（例如：统计用户发言次数）\",\n" +
            "    \"hourly_data\": [\n" +
            "      {\"hour\": 0, \"count\": 5, \"percentage\": 1.5},\n" +
            "      {\"hour\": 1, \"count\": 2, \"percentage\": 0.6},\n" +
            "      // ... (包含所有 24 个小时)\n" +
            "      {\"hour\": 14, \"count\": 50, \"percentage\": 15.5},\n" +
            "      {\"hour\": 23, \"count\": 8, \"percentage\": 2.4}\n" +
            "    ]\n" +
            "  },\n" +
            "  \"user_sentiment_analysis\": {\n" +
            "    \"dominant_sentiment\": \"interested\",\n" +
            "    \"sentiments\": {\n" +
            "      \"interested\": 15,\n" +
            "      \"happy\": 10,\n" +
            "      \"confused\": 5,\n" +
            "      \"satisfied\": 8\n" +
            "      // ... 其他识别出的情感\n" +
            "    }\n" +
            "  },\n" +
            "  \"topic_interest_analysis\": [\n" +
            "    {\n" +
            "      \"topic\": \"讲故事功能\",\n" +
            "      \"reasoning\": \"用户多次要求讲不同类型的故事，并对故事内容进行了追问和表达了喜爱。\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"topic\": \"天气查询\",\n" +
            "      \"reasoning\": \"用户询问了今天和明天的天气情况，并确认了信息的准确性。\"\n" +
            "    }\n" +
            "    // ... 其他兴趣点\n" +
            "  ],\n" +
            "  \"ai_suggestion_analysis\": [\n" +
            "    {\n" +
            "      \"weakness\": \"回答重复\",\n" +
            "      \"suggestion\": \"当用户连续问相似问题时，尝试变换句式或提供更深入的信息，而不是简单重复之前的答案。引入一个内部状态来检测重复提问。\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"weakness\": \"无法理解部分口语化指令\",\n" +
            "      \"suggestion\": \"增强对口语化、非标准指令的自然语言理解能力，可以引入更多的训练数据或使用更强的 NLU 模型。\"\n" +
            "    }\n" +
            "    // ... 其他建议\n" +
            "  ]\n" +
            "}\n" +
            "    整和成有段没有换行";

    public static final String P2 = "      **Prompt 2: 分析结果评分 (要求 JSON 输出)**\n" +
            "\n" +
            "```text\n" +
            "# 角色\n" +
            "你是一位经验丰富的 AI 应用质量评估专家，负责对 AI 生成的分析报告进行客观评分。\n" +
            "\n" +
            "# 背景\n" +
            "你将收到一份由另一个 AI（对话数据分析师）生成的关于某日用户与 AI 玩具交互情况的分析报告（JSON 格式）。\n" +
            "\n" +
            "# 任务\n" +
            "请根据以下评分规则和权重，对提供的分析报告进行评分，并严格按照指定的 JSON 格式返回评分结果。\n" +
            "\n" +
            "# 评分规则 (每项满分 10 分)\n" +
            "\n" +
            "1.  **高峰时段分析 (`score_peak_hours`):**\n" +
            "    *   10 分：准确识别出高峰时段，百分比计算清晰准确，统计口径明确，数据完整（包含所有小时）。\n" +
            "    *   7-9 分：基本准确识别高峰，百分比计算大致正确，口径说明或数据完整性有轻微欠缺。\n" +
            "    *   4-6 分：识别出部分高峰，但不够准确或完整，百分比计算有明显错误或缺失。\n" +
            "    *   0-3 分：分析结果严重错误、无法理解或完全缺失。\n" +
            "\n" +
            "2.  **用户情感分析 (`score_sentiment`):**\n" +
            "    *   10 分：准确识别了主要的几种用户情感，情感标签合理，次数统计可信，明确指出了主导情感，且**严格只分析了用户发言**。\n" +
            "    *   7-9 分：识别的情感种类较全面，主导情感判断合理，可能混淆了少量 AI 发言或情感标签不够精确。\n" +
            "    *   4-6 分：识别的情感有限或不太准确，未能有效区分用户和 AI 发言。\n" +
            "    *   0-3 分：情感分析结果严重错误、空泛或完全缺失。\n" +
            "\n" +
            "3.  **用户话题兴趣分析 (`score_topic_interest`):**\n" +
            "    *   10 分：准确识别了用户的主要兴趣点，**并且给出的判断原因非常具体、有说服力，能从对话中找到明确依据**。\n" +
            "    *   7-9 分：识别的兴趣点基本正确，判断原因尚可，但不够深入或部分依据不充分。\n" +
            "    *   4-6 分：识别的兴趣点比较模糊或片面，判断原因空泛或缺乏依据。\n" +
            "    *   0-3 分：话题分析结果严重偏离、无意义或完全缺失。\n" +
            "\n" +
            "4.  **AI 回答建议 (`score_suggestions`):**\n" +
            "    *   10 分：准确识别了 AI 的核心缺点，提出的建议**非常具体、具有可操作性**，并且确实能针对性地改进 AI 表现。\n" +
            "    *   7-9 分：识别的缺点比较中肯，建议有一定价值，但可能不够具体或操作性稍弱。\n" +
            "    *   4-6 分：识别的缺点比较表面化，建议比较笼统、空泛或可行性不高。\n" +
            "    *   0-3 分：建议毫无价值、与缺点无关或完全缺失。\n" +
            "\n" +
            "# 评分权重\n" +
            "*   高峰时段分析 (`score_peak_hours`): 20%\n" +
            "*   用户情感分析 (`score_sentiment`): 25%\n" +
            "*   用户话题兴趣分析 (`score_topic_interest`): 30%\n" +
            "*   AI 回答建议 (`score_suggestions`): 25%\n" +
            "\n" +
            "# 计算加权平均分\n" +
            "`score_weighted_average` = (`score_peak_hours` * 0.20) + (`score_sentiment` * 0.25) + (`score_topic_interest` * 0.30) + (`score_suggestions` * 0.25)\n" +
            "(结果保留两位小数)\n" +
            "\n" +
            "# 输出格式 (必须严格遵守的 JSON 结构)\n" +
            "\n" +
            "```json\n" +
            "{\n" +
            "  \"score_peak_hours\": 8.5,\n" +
            "  \"score_sentiment\": 9.0,\n" +
            "  \"score_topic_interest\": 7.5,\n" +
            "  \"score_suggestions\": 8.0,\n" +
            "  \"score_weighted_average\": 8.23, // 计算出的加权平均分\n" +
            "  \"scoring_reasoning\": \"高峰时段分析准确但百分比精度可提升；情感分析较好地捕捉了用户情绪；话题兴趣分析原因说明可以更深入；AI建议具体可行。\" // 对整体评分的简要说明\n" +
            "}\n";

    public static final String R2 = "```json\n" +
            "{\n" +
            "  \"peak_hours_analysis\": {\n" +
            "    \"description\": \"统计口径说明：统计用户发言次数\",\n" +
            "    \"hourly_data\": [\n" +
            "      {\"hour\": 0, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 1, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 2, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 3, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 4, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 5, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 6, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 7, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 8, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 9, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 10, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 11, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 12, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 13, \"count\": 45, \"percentage\": 100.0},\n" +
            "      {\"hour\": 14, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 15, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 16, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 17, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 18, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 19, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 20, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 21, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 22, \"count\": 0, \"percentage\": 0.0},\n" +
            "      {\"hour\": 23, \"count\": 0, \"percentage\": 0.0}\n" +
            "    ]\n" +
            "  },\n" +
            "  \"user_sentiment_analysis\": {\n" +
            "    \"dominant_sentiment\": \"interested\",\n" +
            "    \"sentiments\": {\n" +
            "      \"interested\": 20,\n" +
            "      \"confused\": 5,\n" +
            "      \"curious\": 15,\n" +
            "      \"impatient\": 2,\n" +
            "      \"satisfied\": 3\n" +
            "    }\n" +
            "  },\n" +
            "  \"topic_interest_analysis\": [\n" +
            "    {\n" +
            "      \"topic\": \"福建舰\",\n" +
            "      \"reasoning\": \"用户多次提问有关福建舰的问题，表现出对福建舰的基本信息、功能和舰载机等方面的浓厚兴趣。\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"topic\": \"舰载机\",\n" +
            "      \"reasoning\": \"用户多次询问关于舰载机的种类、起飞方式和故事，显示出对舰载机的强烈好奇心。\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"topic\": \"航母的功能和操作\",\n" +
            "      \"reasoning\": \"用户对航母的航行速度、起飞操作和内部构造等表现出兴趣，多次询问相关细节。\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"ai_suggestion_analysis\": [\n" +
            "    {\n" +
            "      \"weakness\": \"回答重复\",\n" +
            "      \"suggestion\": \"AI在回答用户关于舰载机和航母的问题时，存在重复回答的情况。建议引入更高级的上下文理解能力，以提供更多样化和深入的回答。\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"weakness\": \"对用户意图理解不足\",\n" +
            "      \"suggestion\": \"用户在提问时，AI有时未能准确把握用户的真实意图，导致回答与用户问题不完全相关。建议增强自然语言处理能力，提升对用户口语化指令的理解。\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"weakness\": \"缺乏个性化互动\",\n" +
            "      \"suggestion\": \"AI在与用户交流时，较少根据用户的具体反馈进行个性化互动。建议AI根据用户的反馈和兴趣点，提供更个性化的交流和信息。\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n" +
            "```";
}
