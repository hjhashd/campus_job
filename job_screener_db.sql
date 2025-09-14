/*
 Navicat Premium Data Transfer

 Source Server         : demo
 Source Server Type    : MySQL
 Source Server Version : 80040
 Source Host           : localhost:3306
 Source Schema         : job_screener_db

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 19/06/2025 23:54:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company_auth
-- ----------------------------
DROP TABLE IF EXISTS `company_auth`;
CREATE TABLE `company_auth`  (
  `auth_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL COMMENT '企业用户ID',
  `credit_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '统一社会信用代码',
  `company_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业简介',
  `business_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '营业执照',
  `tax_certificate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '税务登记证',
  `status` enum('PENDING','APPROVED','REJECTED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'PENDING' COMMENT '认证状态',
  `credit_score` int UNSIGNED NULL DEFAULT 80 COMMENT '企业信用分(60-100)',
  `last_audit_time` timestamp NULL DEFAULT NULL COMMENT '最后审核时间',
  `auditor_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '审核人ID',
  `reject_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '拒绝原因',
  `legal_case_count` int UNSIGNED NULL DEFAULT 0 COMMENT '法律案件数量',
  `wage_delay_count` int UNSIGNED NULL DEFAULT 0 COMMENT '薪资拖欠投诉',
  PRIMARY KEY (`auth_id`) USING BTREE,
  INDEX `fk_company_auth_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_company_auth_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '企业认证信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company_auth
-- ----------------------------
INSERT INTO `company_auth` VALUES (1, 3, '124569875648236547', '', 'license_comp1.jpg', 'tax_cert_comp1.pdf', 'APPROVED', 92, '2025-06-13 22:10:35', 5, NULL, 0, 0);
INSERT INTO `company_auth` VALUES (3, 26, '56234521563258796548', '', 'gai_business_license.jpg', 'gai_tax_cert.pdf', 'APPROVED', 95, '2025-06-10 09:15:00', 28, NULL, 0, 0);
INSERT INTO `company_auth` VALUES (4, 27, '86206546895478526', '', 'ep_license.jpg', 'ep_tax_cert.png', 'APPROVED', 59, '2025-06-19 23:14:42', 28, NULL, 8, 17);
INSERT INTO `company_auth` VALUES (6, 30, '123456789879456123', '中国最大的互联网综合服务提供商之一', 'licenses/d66eade9-fd7c-452c-a84e-528a5f7d32c4.jpg', 'tax_certs/6ca94016-d9f1-4bfc-8053-935175de9871.jpg', 'REJECTED', 80, '2025-06-19 18:27:22', 5, '不太好\r\n', 0, 0);
INSERT INTO `company_auth` VALUES (7, 31, '123456789879456123', '全球领先的电商平台和云计算服务商', 'licenses/28bca73a-6eea-4df1-911e-bb547155849a.jpg', 'tax_certs/6d9a5e80-a32e-463f-b021-5ca26a4c6ec8.jpg', 'APPROVED', 80, '2025-06-19 15:17:19', 5, NULL, 0, 0);
INSERT INTO `company_auth` VALUES (9, 35, '91330100779747056P', '全球领先的电商平台和云计算服务商', 'license_alibaba.jpg', 'tax_alibaba.jpg', 'APPROVED', 95, '2024-05-20 14:30:00', 37, NULL, 2, 1);
INSERT INTO `company_auth` VALUES (10, 36, '91440300708461136T', '中国最大的互联网综合服务提供商之一', 'license_tencent.jpg', 'tax_tencent.jpg', 'APPROVED', 92, '2024-05-18 11:15:00', 37, NULL, 1, 0);
INSERT INTO `company_auth` VALUES (11, 40, '91110108551385082Q', '专注于智能硬件和电子产品研发的科技公司', 'license_xiaomi.jpg', 'tax_xiaomi.jpg', 'APPROVED', 88, '2024-06-01 10:00:00', 37, NULL, 3, 2);
INSERT INTO `company_auth` VALUES (12, 41, '91110108590697667T', '中国领先的生活服务电子商务平台', 'license_meituan.jpg', 'tax_meituan.jpg', 'PENDING', 85, NULL, NULL, NULL, 5, 3);
INSERT INTO `company_auth` VALUES (13, 42, '91330100799655058N', '全球卓越的移动出行科技平台', 'license_didi.jpg', 'tax_didi.jpg', 'REJECTED', 75, '2024-06-15 14:00:00', 37, '营业执照信息不清晰', 8, 5);

-- ----------------------------
-- Table structure for conversation
-- ----------------------------
DROP TABLE IF EXISTS `conversation`;
CREATE TABLE `conversation`  (
  `conversation_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `company_user_id` bigint UNSIGNED NOT NULL COMMENT '企业用户ID',
  `job_id` bigint UNSIGNED NOT NULL COMMENT '关联职位ID',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '最后一条消息内容',
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`conversation_id`) USING BTREE,
  INDEX `fk_conversation_user`(`user_id` ASC) USING BTREE,
  INDEX `fk_conversation_company`(`company_user_id` ASC) USING BTREE,
  INDEX `fk_conversation_job`(`job_id` ASC) USING BTREE,
  CONSTRAINT `fk_conversation_company` FOREIGN KEY (`company_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_conversation_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_conversation_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '对话表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of conversation
-- ----------------------------
INSERT INTO `conversation` VALUES (1, 5, 26, 5, '2025-06-19 00:28:55', '2025-06-19 01:00:33', '你好', 'admin');
INSERT INTO `conversation` VALUES (3, 5, 26, 5, '2025-06-19 02:51:20', NULL, NULL, 'admin');
INSERT INTO `conversation` VALUES (4, 33, 35, 11, '2024-06-03 09:00:00', '2024-06-03 14:30:00', '请问什么时候可以安排面试？', '李明');
INSERT INTO `conversation` VALUES (5, 34, 36, 12, '2024-06-01 13:20:00', '2024-06-02 10:45:00', '已收到offer，谢谢！', '赵华');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `job_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职位名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职位描述',
  `job_type` enum('FULL_TIME','PART_TIME','INTERNSHIP','REMOTE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职位类型',
  `risk_level` enum('LOW','MEDIUM','HIGH') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'LOW' COMMENT '风险等级(系统评估)',
  `wage_guarantee` tinyint(1) NULL DEFAULT 0 COMMENT '工资保障计划',
  `salary_range` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '薪资范围',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作地点',
  `company_user_id` bigint UNSIGNED NOT NULL COMMENT '企业用户ID',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `experience_requirement` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作经验要求',
  `education_requirement` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学历要求',
  `benefits` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '职位福利(JSON数组)',
  PRIMARY KEY (`job_id`) USING BTREE,
  INDEX `fk_job_company`(`company_user_id` ASC) USING BTREE,
  CONSTRAINT `fk_job_company` FOREIGN KEY (`company_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (1, 'Java开发工程师', '负责后端系统开发与维护，Spring Boot框架', 'FULL_TIME', 'LOW', 1, '15k-25k', '北京', 3, '2025-06-17 11:56:12', '3年经验', '本科及以上', '[\"五险一金\",\"年终奖\",\"带薪年假\"]');
INSERT INTO `job` VALUES (2, '前端开发实习生', '协助开发web应用界面，Vue框架', 'INTERNSHIP', 'LOW', 1, '200/天', '上海', 4, '2025-06-17 11:56:12', '无要求', '在校生', '[\"转正机会\",\"导师指导\"]');
INSERT INTO `job` VALUES (3, '电商运营专员', '负责平台店铺日常运营管理', 'FULL_TIME', 'MEDIUM', 0, '8k-12k', '广州', 4, '2025-06-17 11:56:12', '1年经验', '专科及以上', '[\"绩效奖金\",\"节日福利\"]');
INSERT INTO `job` VALUES (4, '高级人工智能研究员', '负责领导AI创新团队开展以下核心工作：\r\n1. 研发新一代自然语言处理框架，提升中文语义理解准确率15%以上\r\n2. 构建面向金融风控领域的深度学习模型，优化反欺诈识别效率\r\n3. 主导技术白皮书撰写，每年在国际顶级会议（NeurIPS/ICML）发表2篇以上论文\r\n4. 搭建企业级机器学习平台，支持PB级数据训练与实时推理\r\n\r\n团队将提供：\r\n- 专属计算资源：8台A100服务器集群\r\n- 行业数据集：金融/医疗/教育领域脱敏数据\r\n- 国际协作机会：与MIT AI Lab季度交流计划', 'FULL_TIME', 'LOW', 1, '45k-70k+股权', '北京海淀区/深圳南山区', 3, '2025-06-17 11:59:15', '5年以上机器学习研发经验', '博士', '[\"六险二金\",\"年度科研经费50万\",\"弹性工作制\",\"补充商业保险\",\"全球技术大会参会资格\",\"子女教育补贴\"]');
INSERT INTO `job` VALUES (5, 'AI产品经理', '岗位职责：\r\n1. 负责AI产品全生命周期管理，从需求分析到上线推广\r\n2. 设计AI解决方案在医疗/金融场景的应用架构\r\n3. 协调研发、算法、数据团队保证项目落地\r\n\r\n核心要求：\r\n- 熟悉机器学习基本理论\r\n- 3年以上AI产品经验\r\n- 主导过至少1个亿级用户产品\r\n\r\n薪资构成：\r\n月薪30k-50k + 年度业绩奖金 + 股票期权', 'FULL_TIME', 'LOW', 1, '30k-50k', '上海/北京/杭州', 26, '2025-06-17 12:14:00', '5年经验', '本科及以上', '[\"六险二金\",\"年度旅游\",\"学习补贴\",\"弹性工作制\"]');
INSERT INTO `job` VALUES (6, '晚班快递员', '工作说明：\r\n- 负责夜间快递收派件（晚8点-凌晨1点）\r\n- 区域划分：深圳市宝安区西乡街道\r\n- 车辆自备（电动三轮车）\r\n\r\n薪资结构：\r\n底薪2500+计件提成（1.5元/件）\r\n* 月均8000需派送5000件以上\r\n\r\n住宿条件：\r\n提供集体宿舍（8人间）每月扣300元', 'PART_TIME', 'HIGH', 0, '面议', '深圳市宝安区', 27, '2025-06-17 12:14:00', '不限', '初中以上', '[\"提供宿舍\"]');
INSERT INTO `job` VALUES (7, '数据标注实习生', '工作内容：\r\n1. 医疗影像数据标注（CT/MRI图像）\r\n2. 自然语言文本分类与情感标注\r\n3. 协助构建高质量训练数据集\r\n\r\n培训支持：\r\n- AI标注工具专业培训\r\n- 医学影像识别知识讲座\r\n- 实习导师1对1指导', 'INTERNSHIP', 'LOW', 1, '180/天', '远程', 26, '2025-06-17 12:14:00', '无经验要求', '在校生', '[\"实习证明\",\"转正机会\",\"免费课程\"]');
INSERT INTO `job` VALUES (11, '数据分析实习生', '负责电商平台用户行为数据分析', 'INTERNSHIP', 'LOW', 1, '300-400元/天', '杭州', 35, '2025-06-19 22:09:16', '无经验要求', '本科及以上', '[\"免费三餐\", \"住房补贴\", \"专业培训\"]');
INSERT INTO `job` VALUES (12, '游戏客户端开发', '负责手游客户端功能开发与优化', 'FULL_TIME', 'LOW', 1, '25k-40k', '深圳', 36, '2025-06-19 22:09:16', '3年以上经验', '本科及以上', '[\"六险一金\", \"年度旅游\", \"股票期权\"]');
INSERT INTO `job` VALUES (13, '云计算售前顾问', '为企业客户提供云解决方案咨询', 'FULL_TIME', 'LOW', 1, '30k-50k', '北京', 35, '2025-06-19 22:09:16', '5年以上经验', '硕士及以上', '[\"弹性工作制\", \"商业保险\", \"年终奖金\"]');
INSERT INTO `job` VALUES (14, '市场推广实习生', '协助新品上市推广活动执行', 'PART_TIME', 'MEDIUM', 0, '200-300元/天', '北京', 40, '2025-06-19 22:14:09', '无经验要求', '大专及以上', '[\"交通补贴\", \"产品体验\"]');
INSERT INTO `job` VALUES (15, '外卖配送员', '负责区域内美团外卖配送服务', 'FULL_TIME', 'HIGH', 1, '8k-15k', '上海', 41, '2025-06-19 22:14:09', '1年以上经验', '不限', '[\"意外险\", \"绩效奖金\", \"车辆补贴\"]');
INSERT INTO `job` VALUES (16, '数据安全工程师', '保障平台数据安全和用户隐私', 'FULL_TIME', 'LOW', 1, '35k-50k', '北京', 42, '2025-06-19 22:14:09', '5年以上经验', '硕士及以上', '[\"六险一金\", \"股票期权\", \"年度体检\"]');
INSERT INTO `job` VALUES (17, 'UI设计师实习生', '参与MIUI系统界面设计项目', 'INTERNSHIP', 'LOW', 1, '400-500元/天', '北京', 40, '2025-06-19 22:14:09', '无经验要求', '本科及以上', '[\"设计培训\", \"导师指导\", \"转正机会\"]');
INSERT INTO `job` VALUES (18, '前端工程师', '吃苦耐劳', 'FULL_TIME', 'LOW', 0, '8000', '广东省肇庆市', 3, '2025-06-19 23:12:18', '应届毕业生', '不限', '[\"五险一金\",\"带薪年假\",\"节日福利\"]');

-- ----------------------------
-- Table structure for job_review
-- ----------------------------
DROP TABLE IF EXISTS `job_review`;
CREATE TABLE `job_review`  (
  `review_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL COMMENT '评价人(必须是在职/离职员工)',
  `rating` tinyint UNSIGNED NOT NULL COMMENT '评分(1-5星)',
  `wage_promptness` tinyint(1) NULL DEFAULT 1 COMMENT '工资准时发放',
  `env_authenticity` tinyint(1) NULL DEFAULT 1 COMMENT '环境真实性',
  `review_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '详细评价',
  `verified` tinyint(1) NULL DEFAULT 0 COMMENT '平台核验状态',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`) USING BTREE,
  INDEX `fk_review_job`(`job_id` ASC) USING BTREE,
  INDEX `fk_review_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_review_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_review_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职位评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_review
-- ----------------------------
INSERT INTO `job_review` VALUES (1, 1, 1, 5, 1, 1, '团队氛围好，技术提升快，工资按时发', 1, '2025-06-17 11:56:12');
INSERT INTO `job_review` VALUES (2, 2, 2, 4, 1, 1, '实习收获大，工作与描述一致', 1, '2025-06-17 11:56:12');
INSERT INTO `job_review` VALUES (3, 3, 1, 3, 1, 0, '工作内容与JD有差距，但工资准时', 1, '2025-06-17 11:56:12');
INSERT INTO `job_review` VALUES (4, 5, 25, 5, 1, 1, '入职三个月的真实感受：\r\n优势：\r\n✓ 薪资福利：完全按照offer发放，15号准时\r\n✓ 专业成长：公司提供全额付费的AI培训\r\n✓ 工作氛围：团队专业高效，无官僚作风\r\n\r\n不足：\r\n✗ 工作强度大：季度末常需加班至晚9点', 1, '2025-06-17 12:14:00');
INSERT INTO `job_review` VALUES (5, 6, 24, 1, 0, 0, '被虚假宣传欺骗的经历！\r\n招聘广告写月入8000，实际：\r\n- 底薪只发2100（扣各种\"押金\"）\r\n- 每件提成实际只算0.8元\r\n- 油费补贴从未兑现\r\n- 入职扣500工装押金不退\r\n\r\n最终实际月收入不到4000，离职还要扣半月工资', 1, '2025-06-17 12:14:00');
INSERT INTO `job_review` VALUES (7, 11, 33, 5, 1, 1, '导师非常专业，实习期间学到了很多实战技能', 1, '2024-06-01 09:45:00');
INSERT INTO `job_review` VALUES (8, 12, 34, 4, 1, 1, '技术氛围浓厚，但工作强度较大', 1, '2024-05-25 16:20:00');
INSERT INTO `job_review` VALUES (9, 13, 33, 3, 1, 0, '实际工作内容与JD有差异，需谨慎', 0, '2024-06-10 14:15:00');
INSERT INTO `job_review` VALUES (10, 14, 38, 2, 0, 1, '经常加班且无加班费，不推荐', 1, '2024-06-18 11:20:00');
INSERT INTO `job_review` VALUES (11, 15, 39, 3, 1, 0, '实际薪资与宣传不符，订单量不稳定', 0, '2024-06-12 16:45:00');
INSERT INTO `job_review` VALUES (12, 16, 39, 5, 1, 1, '技术氛围好，领导专业，福利完善', 1, '2024-05-28 09:30:00');
INSERT INTO `job_review` VALUES (13, 17, 38, 4, 1, 1, '实习内容充实，能接触实际项目', 1, '2024-06-10 14:20:00');

-- ----------------------------
-- Table structure for legal_assistance
-- ----------------------------
DROP TABLE IF EXISTS `legal_assistance`;
CREATE TABLE `legal_assistance`  (
  `case_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL COMMENT '求助人',
  `case_type` enum('SALARY_DELAY','CONTRACT_DISPUTE','UNFAIR_DISMISSAL','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '案件类型',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '案件描述',
  `status` enum('SUBMITTED','CONSULTING','PROCESSING','RESOLVED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'SUBMITTED',
  `contract_attachment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '合同附件',
  `evidence` json NULL COMMENT '证据材料(JSON数组)',
  `lawyer_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '负责律师',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`case_id`) USING BTREE,
  INDEX `fk_legal_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_legal_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '法律援助表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of legal_assistance
-- ----------------------------
INSERT INTO `legal_assistance` VALUES (1, 1, 'SALARY_DELAY', '实习期满后公司拖欠薪资', 'SUBMITTED', 'contract_sample.pdf', NULL, NULL, '2025-06-17 11:56:12');
INSERT INTO `legal_assistance` VALUES (2, 1, 'UNFAIR_DISMISSAL', '案件经过：\r\n2024年3月入职XX科技公司担任数据分析师，签订3年劳动合同。2025年5月突然收到解除通知，理由为\"组织结构调整\"。但实际公司同期仍在招聘同岗位员工。\r\n关键证据：\r\n1. 5月10日HR口头承诺续约（有录音）\r\n2. 解除通知前2周绩效评为A\r\n3. 在职期间超额完成3个重点项目\r\n\r\n诉求：\r\n1. 违法解除赔偿金（2N=8个月工资）\r\n2. 未休年假3倍补偿\r\n3. 期权行权损失赔偿', 'PROCESSING', 'employment_contract_v3_signed.pdf', '[{\"url\": \"termination_letter.jpg\", \"name\": \"解除通知书\"}, {\"url\": \"performance_review_q2.xlsx\", \"name\": \"绩效评价表\"}, {\"url\": \"hr_conversation.mp3\", \"name\": \"录音文件\"}, {\"url\": \"project_certificates.zip\", \"name\": \"项目成果证明\"}]', NULL, '2025-06-17 11:59:24');
INSERT INTO `legal_assistance` VALUES (4, 33, 'SALARY_DELAY', '实习工资拖欠三个月未发放', 'PROCESSING', 'contract_33.pdf', '[\"pay_slip.jpg\", \"chat_records.json\"]', NULL, '2024-06-05 10:30:00');
INSERT INTO `legal_assistance` VALUES (5, 34, 'CONTRACT_DISPUTE', '离职后竞业协议补偿金未支付', 'SUBMITTED', 'tencent_contract.pdf', '[\"termination_letter.jpg\", \"bank_statement.pdf\"]', NULL, '2024-06-12 15:40:00');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `conversation_id` bigint UNSIGNED NOT NULL COMMENT '对话ID',
  `sender_id` bigint UNSIGNED NOT NULL COMMENT '发送者ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `sent_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '是否已读',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `fk_message_conversation`(`conversation_id` ASC) USING BTREE,
  INDEX `fk_message_sender`(`sender_id` ASC) USING BTREE,
  CONSTRAINT `fk_message_conversation` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`conversation_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_message_sender` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 1, 5, '你好', '2025-06-19 00:28:55', 0);
INSERT INTO `message` VALUES (4, 4, 33, '您好，我对数据分析实习职位很感兴趣', '2024-06-03 09:00:00', 1);
INSERT INTO `message` VALUES (5, 4, 35, '请发送简历至hr@alibaba.com', '2024-06-03 10:15:00', 1);
INSERT INTO `message` VALUES (6, 4, 33, '简历已发送，请查收', '2024-06-03 11:30:00', 1);
INSERT INTO `message` VALUES (7, 5, 34, '游戏开发职位的技术笔试已完成', '2024-06-01 13:20:00', 1);
INSERT INTO `message` VALUES (8, 5, 36, '请本周五下午3点来腾讯大厦面试', '2024-06-01 16:40:00', 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `uniq_role_name`(`role_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'STUDENT', '求职者角色');
INSERT INTO `role` VALUES (2, 'COMPANY', '企业用户角色');
INSERT INTO `role` VALUES (3, 'ADMIN', '系统管理员角色');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `identity_type` enum('STUDENT','COMPANY','ADMIN') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'STUDENT' COMMENT '用户身份',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default-avatar.png' COMMENT '头像',
  `school` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校(仅学生)',
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业(仅学生)',
  `education` enum('专科','本科','硕士','博士') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学历(仅学生)',
  `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业名称(仅企业)',
  `credit_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `company_size` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业规模',
  `contact_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人(仅企业)',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `financing_stage` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '融资阶段',
  `industry` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属行业',
  `headquarters` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '总部地点',
  `status` tinyint NULL DEFAULT 1,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uniq_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uniq_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'STUDENT', 'student1', '123', 'student1@univ.edu', '13800001111', 'default-avatar.png', '北京大学', '计算机科学', '本科', NULL, NULL, NULL, NULL, '2025-06-17 11:56:12', '2025-06-19 21:58:32', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (2, 'STUDENT', 'student2', '456', 'student2@college.edu', '13900002222', 'default-avatar.png', '清华大学', '人工智能', '硕士', NULL, NULL, NULL, NULL, '2025-06-17 11:56:12', '2025-06-19 21:58:29', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (3, 'COMPANY', 'admin1', '123', 'hr@company1.com', '010-88889999', 'default-avatar.png', NULL, NULL, NULL, '创新科技有限公司', NULL, '大型企业', '张经理', '2025-06-17 11:56:12', '2025-06-19 23:06:55', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (4, 'COMPANY', 'company2', '2024', 'career@company2.cn', '021-63337777', 'default-avatar.png', NULL, NULL, NULL, '天天好集团', '123456789987456123', '21-100', '房贷首付', '2025-06-17 11:56:12', '2025-06-19 22:05:31', NULL, 'internet', NULL, 1);
INSERT INTO `user` VALUES (5, 'ADMIN', 'admin', '123', 'admin@outlook.com', '4001234567', 'default-avatar.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2025-06-17 11:56:12', '2025-06-19 22:01:45', NULL, 'finance', NULL, 1);
INSERT INTO `user` VALUES (24, 'STUDENT', 'chen_lei', 'Chen123!', 'chenlei@fudan.edu', '13810203040', 'default-avatar.png', '复旦大学', '人力资源', '本科', NULL, NULL, NULL, NULL, '2025-06-17 12:14:00', '2025-06-19 21:58:15', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (25, 'STUDENT', 'liu_wei', 'Liuwei@2025', 'liuw@zju.edu', '13988997766', 'default-avatar.png', '浙江大学', '物流管理', '硕士', NULL, NULL, NULL, NULL, '2025-06-17 12:14:00', '2025-06-19 21:58:18', NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (26, 'COMPANY', 'global_ai', 'Gai$2025', 'career@globalai.com', '021-68889999', 'default-avatar.png', NULL, NULL, NULL, '环球人工智能科技', NULL, '大型企业', '王总监', '2025-06-17 12:14:00', '2025-06-19 21:58:22', '已上市', 'AI研发', '上海市浦东新区', 1);
INSERT INTO `user` VALUES (27, 'COMPANY', 'express_plus', 'Eplus#123', 'hr@expressplus.cn', '0755-67891011', 'default-avatar.png', NULL, NULL, NULL, '极速物流服务有限公司', NULL, '中小型企业', '赵主管', '2025-06-17 12:14:00', '2025-06-19 21:58:25', '未融资', '快递服务', '深圳市宝安区', 1);
INSERT INTO `user` VALUES (28, 'ADMIN', 'sys_admin', '123', 'admin@careersafe.com', '4001234567', 'admin_avatar.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2025-06-17 12:14:00', '2025-06-19 22:42:11', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (29, 'COMPANY', 'legal_guard', 'Guard!2025', 'legal@justice.cn', '020-87654321', 'default-avatar.png', NULL, NULL, NULL, '正义守护律师事务所', NULL, '中小型企业', '陈律师', '2025-06-17 12:14:00', '2025-06-19 21:58:28', '未融资', '法律服务', '广州市天河区', 1);
INSERT INTO `user` VALUES (30, 'COMPANY', '525655', '123123', 'kjhghghdsd1990@outlook.com', '12345678912', 'default-avatar.png', NULL, NULL, NULL, 'sasaddsa', NULL, '大型企业', NULL, '2025-06-18 20:40:30', NULL, NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (31, 'COMPANY', 'abcd', '123123', 'dsdd564655@qq.com', '1456254642', 'default-avatar.png', NULL, NULL, NULL, 'hjjjhjhhjh', NULL, '中小型企业', NULL, '2025-06-18 21:24:02', '2025-06-19 15:16:38', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (33, 'STUDENT', 'student_li', 'pass789', 'li@edu.cn', '13800003333', 'avatar3.png', '复旦大学', '金融学', '硕士', NULL, NULL, NULL, NULL, '2025-06-19 22:09:16', NULL, NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (34, 'STUDENT', 'student_zhao', 'pass101', 'zhao@edu.cn', '13800004444', 'avatar4.png', '浙江大学', '计算机科学', '本科', NULL, NULL, NULL, NULL, '2025-06-19 22:09:16', NULL, NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (35, 'COMPANY', 'alibaba_hr', 'alipass123', 'alibaba@hr.com', '0571-88889999', 'alibaba.png', NULL, NULL, NULL, '阿里巴巴集团', '91330100779747056P', '10000人以上', '张经理', '2025-06-19 22:09:16', NULL, '上市公司', '互联网', '浙江杭州', 1);
INSERT INTO `user` VALUES (36, 'COMPANY', 'tencent_recruit', 'tenpass456', 'recruit@tencent.com', '0755-86013388', 'tencent.png', NULL, NULL, NULL, '腾讯科技', '91440300708461136T', '5000-9999人', '陈总监', '2025-06-19 22:09:16', NULL, '上市公司', '互联网', '广东深圳', 1);
INSERT INTO `user` VALUES (37, 'ADMIN', 'sysadmin', 'adminsecure', 'admin@system.com', '010-12345678', 'admin.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2025-06-19 22:09:16', NULL, NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (38, 'STUDENT', 'wang_student', 'wangpass', 'wang@edu.cn', '13911112222', 'wang.png', '南京大学', '市场营销', '本科', NULL, NULL, NULL, NULL, '2025-06-19 22:14:09', '2025-06-19 23:13:49', NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (39, 'STUDENT', 'chen_student', 'chenpass', 'chen@edu.cn', '13933334444', 'chen.png', '武汉大学', '软件工程', '硕士', NULL, NULL, NULL, NULL, '2025-06-19 22:14:09', NULL, NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (40, 'COMPANY', 'fggsdsg', '123', 'hr@xiaomi.com', '010-67891234', 'xiaomi.png', NULL, NULL, NULL, '小米科技', '91110108551385082Q', '5000-9999人', '雷经理', '2025-06-19 22:14:09', '2025-06-19 23:06:37', '上市公司', '消费电子', '北京', 1);
INSERT INTO `user` VALUES (41, 'COMPANY', 'meituan_recruit', 'meituanpass', 'campus@meituan.com', '022-55667788', 'meituan.png', NULL, NULL, NULL, '美团', '91110108590697667T', '10000人以上', '王总监', '2025-06-19 22:14:09', NULL, '上市公司', '互联网', '北京', 1);
INSERT INTO `user` VALUES (42, 'COMPANY', 'didi_hr', 'didipass', 'recruit@didi.com', '021-99887766', 'didi.png', NULL, NULL, NULL, '滴滴出行', '91330100799655058N', '5000-9999人', '程主管', '2025-06-19 22:14:09', NULL, '上市公司', '交通出行', '北京', 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint UNSIGNED NOT NULL,
  `role_id` int UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `fk_user_role_role`(`role_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 1);
INSERT INTO `user_role` VALUES (24, 1);
INSERT INTO `user_role` VALUES (25, 1);
INSERT INTO `user_role` VALUES (3, 2);
INSERT INTO `user_role` VALUES (4, 2);
INSERT INTO `user_role` VALUES (26, 2);
INSERT INTO `user_role` VALUES (27, 2);
INSERT INTO `user_role` VALUES (29, 2);
INSERT INTO `user_role` VALUES (30, 2);
INSERT INTO `user_role` VALUES (31, 2);
INSERT INTO `user_role` VALUES (5, 3);
INSERT INTO `user_role` VALUES (28, 3);

SET FOREIGN_KEY_CHECKS = 1;
