CREATE DATABASE IF NOT EXISTS `graduation_project_db`;

-- --- 认证用户表 (原 admin_user) ---
-- 存储系统用户信息，用于登录认证和权限管理
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名 (登录名)',
    `name` VARCHAR(100) NULL COMMENT '用户姓名/昵称',
    `password` VARCHAR(100) NOT NULL COMMENT '加密后的密码 (BCrypt)',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '用户状态 (1:激活, 0:禁用)',
    `operator` VARCHAR(50) NULL COMMENT '最后操作员 (创建或更新者)',
    `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`) COMMENT '用户名唯一索引',
    INDEX `idx_status` (`status`) COMMENT '状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='认证用户表'; -- 修改表注释

-- --- 对话日志表 (原 t_excelcontent_text) ---
-- 存储从 Excel 导入的原始对话记录
DROP TABLE IF EXISTS `conversation_log`;
CREATE TABLE `conversation_log` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '日志主键ID',
    `log_time` TIMESTAMP NOT NULL COMMENT '对话发生时间', -- 字段名也做了调整
    `role` VARCHAR(50) NOT NULL COMMENT '对话角色 (例如: 用户, 客服)',
    `status` VARCHAR(50) NULL COMMENT '对话状态 (来自Excel)',
    `content` TEXT NOT NULL COMMENT '对话内容文本',
    `voice_url` TEXT NULL COMMENT '语音文件URL', -- 字段名也做了调整
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录入库时间',
    -- `uploader_user_id` BIGINT UNSIGNED NULL COMMENT '上传者用户ID', -- 可选
    PRIMARY KEY (`id`),
    INDEX `idx_log_time` (`log_time`) COMMENT '对话时间索引 (用于分析)', -- 索引名也做了调整
    INDEX `idx_role` (`role`) COMMENT '角色索引'
    -- FOREIGN KEY (`uploader_user_id`) REFERENCES `auth_user`(`id`) -- 如果添加了 uploader_user_id
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='对话日志表'; -- 修改表注释


-- --- 每日对话统计表 (原 daily_conversation_stats) ---
-- 存储按天聚合的对话次数统计结果
DROP TABLE IF EXISTS `stat_conversation_daily`;
CREATE TABLE `stat_conversation_daily` (
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `total_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '当日对话总次数',
    `last_updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计记录最后更新时间',
    PRIMARY KEY (`stat_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='每日对话统计表'; -- 修改表注释


-- --- 每小时对话统计表 (原 hourly_conversation_stats) ---
-- 存储按小时聚合的对话次数统计结果
DROP TABLE IF EXISTS `stat_conversation_hourly`;
CREATE TABLE `stat_conversation_hourly` (
    `stat_datetime` DATETIME NOT NULL COMMENT '统计日期和小时 (YYYY-MM-DD HH:00:00)',
    `hourly_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '该小时对话次数',
    `last_updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计记录最后更新时间',
    PRIMARY KEY (`stat_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='每小时对话统计表'; -- 修改表注释


-- --- LLM每日分析表 (原 daily_llm_analysis) ---
-- 存储每日对话通过 LLM 分析得出的结果
DROP TABLE IF EXISTS `analysis_llm_daily`;
CREATE TABLE `analysis_llm_daily` (
    `analysis_date` DATE NOT NULL COMMENT '分析对应的日期',
    `summary` TEXT NULL COMMENT 'LLM生成的对话摘要',
    `sentiment` VARCHAR(50) NULL COMMENT 'LLM分析的情感倾向 (Positive, Negative, Neutral)',
    `key_topics` JSON NULL COMMENT 'LLM提取的关键主题 (JSON数组)', -- 或 TEXT
    `analysis_report_md` LONGTEXT NULL COMMENT 'LLM分析生成的Markdown格式报告',
    `raw_llm_response` LONGTEXT NULL COMMENT 'LLM API返回的原始响应体',
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '分析任务状态 (PENDING, PROCESSING, COMPLETED, FAILED)',
    `error_message` TEXT NULL COMMENT '如果状态为FAILED, 记录错误信息',
    `processing_started_at` TIMESTAMP NULL COMMENT '任务开始处理时间',
    `processing_ended_at` TIMESTAMP NULL COMMENT '任务处理结束时间',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后更新时间',
    PRIMARY KEY (`analysis_date`),
    INDEX `idx_status` (`status`) COMMENT '状态索引',
    INDEX `idx_sentiment` (`sentiment`) COMMENT '情感倾向索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='LLM每日对话分析结果表'; -- 修改表注释