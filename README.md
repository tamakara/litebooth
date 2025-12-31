# LiteBooth - 虚拟商品自动发货平台

## 项目介绍

LiteBooth 是一个基于 Spring Boot 和 Vue 3 开发的轻量级自动发货/发卡系统。它包含用户端和管理端两个部分，支持商品展示、在线下单、自动发货、库存管理、订单管理等核心功能。

### 主要功能

#### 用户端 (User Frontend)
- **商品浏览**：展示商品列表，支持按分类筛选。
- **商品详情**：查看商品详细信息。
- **在线下单**：支持多种支付方式（模拟），下单后自动扣减库存。
- **订单查询**：通过邮箱和查询密码查询历史订单及卡密/内容。
- **自动发货**：支付成功后自动展示库存内容（如卡密、账号等）。

#### 管理端 (Admin Frontend)
- **仪表盘**：系统概览。
- **商品管理**：商品的增删改查，支持富文本详情。
- **商品组管理**：商品分类管理。
- **库存管理**：
    - 支持单条添加库存。
    - **批量上传**：支持通过 TXT 文件批量上传库存内容。
    - 库存状态管理（未售/已售）。
- **订单管理**：
    - 查看所有订单状态。
    - 订单筛选（按状态、关键词）。
    - 订单详情查看。
- **系统管理**：用户管理、角色管理、菜单管理等（基于 Vue Pure Admin）。

## 技术栈

### 后端 (Backend)
- **开发语言**：Java 17+
- **框架**：Spring Boot 3.x
- **ORM**：MyBatis-Plus
- **数据库**：MySQL 8.0
- **工具**：Lombok, Swagger/Knife4j (API 文档)

### 前端 (Frontend)
- **核心框架**：Vue 3 (Composition API)
- **构建工具**：Vite
- **语言**：TypeScript
- **UI 组件库**：Element Plus
- **状态管理**：Pinia
- **管理端模板**：Vue Pure Admin
- **HTTP 客户端**：Axios

## 部署说明

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+

### 后端部署
1. 进入 `backend` 目录。
2. 修改 `src/main/resources/application.yml` 中的数据库配置。
3. 运行 `mvn clean package` 打包。
4. 运行 `java -jar target/litebooth-backend.jar`。

### 前端部署
1. 进入 `frontend-admin` 或 `frontend-user` 目录。
2. 运行 `pnpm install` 安装依赖。
3. 运行 `pnpm build` 打包。
4. 将 `dist` 目录下的文件部署到 Nginx 或其他 Web 服务器。

### Docker 部署
项目包含 `docker-compose.yml`，可直接使用 Docker Compose 一键部署。
```bash
cd backend/docker
docker-compose up -d
```
