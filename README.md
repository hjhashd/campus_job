# 💼 校园求职管理系统

<div align="center">

![图片占位符 - 系统Logo](./docs/images/logo.png)

*专为大学生和企业打造的校园招聘求职管理平台*

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![MyBatis](https://img.shields.io/badge/MyBatis-3.0.4-red.svg)](https://mybatis.org/mybatis-3/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green.svg)](https://spring.io/projects/spring-security)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

</div>

## 🎯 项目简介

校园求职管理系统是一个专为大学生和企业设计的在线招聘管理平台，致力于搭建校园人才与优质企业之间的桥梁。系统提供了完整的招聘求职流程管理，包括职位发布、简历投递、面试安排、人才筛选、消息沟通等全方位功能。

![Uploading 屏幕截图 2025-06-19 233339.png…]()


## ✨ 核心功能

### 👤 用户管理模块
- **多角色注册登录** - 支持学生、企业、管理员三种角色
- **身份认证** - 严格的用户身份验证机制
- **个人中心** - 完善的个人信息管理
- **账户安全** - 密码修改、安全设置

<img width="2559" height="1398" alt="image" src="https://github.com/user-attachments/assets/734fc001-006c-4f92-b740-6fa1f92b5bf6" />
<img width="2559" height="1395" alt="image" src="https://github.com/user-attachments/assets/c35da3a1-7508-4540-9a33-4f4d0b7f06ec" />


### 💼 职位管理模块
- **职位发布** - 企业发布招聘信息
- **职位搜索** - 多维度筛选和智能搜索
- **职位详情** - 详细的职位描述和要求展示
- **职位管理** - 企业职位状态管理后台

<img width="2559" height="1383" alt="image" src="https://github.com/user-attachments/assets/77d9c531-eddd-4369-8043-65b23630365a" />


### 📄 简历投递模块
- **简历管理** - 在线简历编辑和管理
- **一键投递** - 快速投递心仪职位
- **投递记录** - 查看投递状态和企业反馈
- **简历筛选** - 企业HR筛选合适人才

![图片占位符 - 简历投递界面](./docs/images/resume-apply.png)

### 💬 消息沟通模块
- **即时消息** - 求职者与HR实时沟通
- **会话管理** - 统一的消息会话管理
- **消息中心** - 重要消息集中展示
- **通知提醒** - 实时消息推送和提醒

![图片占位符 - 消息沟通界面](./docs/images/message-interface.png)

### 🎯 人才中心模块
- **人才展示** - 优秀求职者技能展示
- **智能推荐** - 基于技能的职位匹配
- **求职状态** - 求职进度实时跟踪
- **职业指导** - 求职建议和职业规划

![图片占位符 - 人才中心界面](./docs/images/talent-center.png)

### 🔍 职位评价模块
- **职位评价** - 求职者对职位和企业的评价
- **评价管理** - 评价内容的审核和管理
- **评价统计** - 企业和职位的评价数据分析

![图片占位符 - 职位评价界面](./docs/images/job-review.png)

### 🛡️ 企业认证模块
- **认证申请** - 企业提交认证申请
- **资质审核** - 管理员审核企业资质
- **认证管理** - 认证状态跟踪和管理
- **权限控制** - 基于认证状态的功能权限

<img width="2559" height="1397" alt="image" src="https://github.com/user-attachments/assets/d8417001-0fbe-4c35-a311-9852fc4f60da" />


### 🔧 管理后台模块
- **用户管理** - 学生和企业用户统一管理
- **职位审核** - 职位信息审核和管理
- **认证审核** - 企业认证申请审核
- **数据统计** - 平台运营数据分析和报表

![图片占位符 - 管理后台界面](./docs/images/admin-panel.png)

## 🏗️ 技术架构

![图片占位符 - 系统架构图](./docs/images/architecture.png)

### 后端技术栈
- **核心框架**: Spring Boot 3.4.7
- **安全框架**: Spring Security 6
- **数据库**: MySQL 8.0
- **持久层**: MyBatis 3.0.4
- **模板引擎**: Thymeleaf
- **构建工具**: Maven
- **云服务**: Spring Cloud
- **工具库**: Lombok

### 前端技术栈
- **基础技术**: HTML5 + CSS3 + JavaScript
- **UI框架**: Bootstrap 5.3.2
- **模板引擎**: Thymeleaf
- **响应式设计**: 支持PC、平板、手机多端适配

### 数据库设计
- **关系型数据库**: MySQL 8.0
- **连接池**: HikariCP
- **数据映射**: MyBatis + 注解/XML配置
- **事务管理**: Spring Transaction

## 🚀 快速开始

### 环境要求
- **JDK**: 17+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **IDE**: IntelliJ IDEA / Eclipse

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/your-username/campus_job.git
cd campus_job
```

2. **配置数据库**
```sql
# 创建数据库
CREATE DATABASE campus_job CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入数据表
mysql -u root -p campus_job < job_screener_db.sql
```

3. **配置应用属性**
```properties
# 修改 src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/campus_job
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. **运行项目**
```bash
mvn spring-boot:run
```

5. **访问系统**
- 系统地址: http://localhost:8080
- 管理员账号: admin/admin123
- 测试企业账号: company/company123
- 测试学生账号: student/student123

![图片占位符 - 项目启动截图](./docs/images/startup-screen.png)

## 📱 功能演示

### 🎓 学生求职流程
1. **注册登录** → **完善简历** → **搜索职位** → **投递简历** → **查看反馈** → **沟通面试**

![图片占位符 - 学生求职流程图](./docs/images/student-job-flow.png)

### 🏢 企业招聘流程
1. **企业认证** → **发布职位** → **筛选简历** → **沟通候选人** → **面试安排** → **录用决策**

![图片占位符 - 企业招聘流程图](./docs/images/company-recruit-flow.png)

### 🛡️ 管理员审核流程
1. **用户管理** → **企业认证审核** → **职位审核** → **内容管理** → **数据统计**

![图片占位符 - 管理审核流程图](./docs/images/admin-review-flow.png)

## 📊 数据库设计

![图片占位符 - 数据库ER图](./docs/images/database-er.png)

### 核心数据表

| 表名 | 描述 | 主要字段 |
|------|------|----------|
| `users` | 用户基础信息表 | id, username, password, email, phone, role |
| `user_roles` | 用户角色关联表 | user_id, role_id |
| `roles` | 角色定义表 | id, name, description |
| `jobs` | 职位信息表 | id, title, description, company_id, salary, location |
| `company_auth` | 企业认证信息表 | id, company_name, business_license, status |
| `auth_application` | 认证申请表 | id, user_id, application_type, status |
| `job_review` | 简历投递记录表 | id, job_id, user_id, resume_path, status |
| `messages` | 消息记录表 | id, sender_id, receiver_id, content, timestamp |
| `conversations` | 会话管理表 | id, participant_ids, last_message_time |

## 🎨 界面展示

### 主要页面功能

| 页面类型 | 页面名称 | 功能描述 | 截图占位符 |
|----------|----------|----------|------------|
| 🏠 **公共页面** | 系统首页 | 职位展示、平台介绍、用户入口 | ![首页](./docs/images/homepage.png) |
| 🔐 **认证页面** | 登录页面 | 多角色统一登录入口 | ![登录页](./docs/images/login-page.png) |
| 📝 **认证页面** | 注册页面 | 学生和企业注册入口 | ![注册页](./docs/images/register-page.png) |
| 🔍 **学生端** | 职位搜索 | 智能职位搜索和筛选 | ![职位搜索](<img width="2559" height="1395" alt="image" src="https://github.com/user-attachments/assets/bd3d3047-97b0-4e66-a2fd-a590d02e3285" />
) |
| 📄 **学生端** | 职位详情 | 详细职位信息和投递 | ![职位详情](./docs/images/job-detail.png) |
| 📋 **学生端** | 简历管理 | 在线简历编辑和管理 | ![简历管理](./docs/images/resume-management.png) |
| ⭐ **学生端** | 人才中心 | 个人技能展示和推荐 | ![人才中心](./docs/images/talent-showcase.png) |
| 📢 **企业端** | 职位发布 | 发布和管理招聘职位 | ![职位发布](./docs/images/job-publish-form.png) |
| 👥 **企业端** | 简历筛选 | 查看和筛选候选人简历 | ![简历筛选](./docs/images/resume-screening.png) |
| 🏛️ **企业端** | 企业认证 | 企业资质认证申请 | ![企业认证申请](./docs/images/company-auth-apply.png) |
| 👤 **管理端** | 用户管理 | 系统用户信息管理 | ![用户管理](./docs/images/user-admin.png) |
| 💼 **管理端** | 职位管理 | 职位审核和管理 | ![职位管理](./docs/images/job-admin.png) |
| 🔍 **管理端** | 认证审核 | 企业认证申请审核 | ![认证审核](./docs/images/auth-admin.png) |

## 🔒 安全特性

### 身份认证与授权
- **多角色认证**: 支持学生、企业、管理员三种角色
- **权限控制**: 基于Spring Security的细粒度权限控制
- **会话管理**: 安全的用户会话管理机制
- **密码安全**: BCrypt加密存储用户密码

### 数据安全
- **输入验证**: 严格的用户输入验证和过滤
- **SQL注入防护**: MyBatis预编译语句防护
- **XSS防护**: Thymeleaf模板引擎自动转义
- **CSRF防护**: Spring Security CSRF令牌保护

### 文件安全
- **文件上传**: 安全的简历和企业资质文件上传
- **文件类型验证**: 严格的文件类型和大小限制
- **文件存储**: 安全的文件存储路径和访问控制

![图片占位符 - 安全架构图](./docs/images/security-architecture.png)

## 📈 系统特色

### 🎯 智能匹配
- **技能匹配**: 基于技能标签的智能推荐
- **地域匹配**: 考虑地理位置的职位推荐
- **薪资匹配**: 符合期望薪资的职位筛选

### 📊 数据分析
- **求职统计**: 投递成功率、面试通过率
- **招聘统计**: 职位热度、简历质量分析
- **平台数据**: 用户活跃度、平台使用情况

### 🌟 用户体验
- **响应式设计**: 完美适配PC、平板、手机
- **实时通知**: 重要消息即时推送
- **操作便捷**: 简洁直观的用户界面

![图片占位符 - 系统特色展示](./docs/images/system-features.png)

## 🚀 部署指南

### 开发环境部署
1. 确保Java 17+、Maven 3.6+、MySQL 8.0+已安装
2. 克隆项目并导入IDE
3. 配置数据库连接信息
4. 运行`mvn spring-boot:run`启动项目

### 生产环境部署
1. **构建项目**
```bash
mvn clean package -Dmaven.test.skip=true
```

2. **部署JAR包**
```bash
java -jar target/campus_job-0.0.1-SNAPSHOT.jar
```

3. **使用Docker部署**
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/campus_job-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

![图片占位符 - 部署架构图](./docs/images/deployment-architecture.png)

## 🤝 贡献指南

我们欢迎任何形式的贡献！请查看 [CONTRIBUTING.md](CONTRIBUTING.md) 了解详细信息。

### 开发规范
- **代码规范**: 遵循阿里巴巴Java开发规范
- **提交规范**: 使用语义化提交信息
- **测试覆盖**: 保证核心功能测试覆盖率


## 🆘 常见问题


## 📜 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。


---

<div align="center">

**⭐ 如果这个项目对您有帮助，请给我们一个Star！⭐**

![图片占位符 - 项目统计徽章](./docs/images/project-stats.png)

</div>
```
