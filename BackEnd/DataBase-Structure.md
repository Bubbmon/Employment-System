# 表结构说明

1. 普通用户信息表 userInfo

| 字段     | 限制          | 类型         | 说明                                                                   |
| -------- | ------------- | ------------ | ---------------------------------------------------------------------- |
| id       | PRIMARY KEY   | varchar(16)  | 用户名，应为特殊字符、数字、字母的组合                                 |
| pswd     | NOT NULL      | varchar(16)  | 密码，有条件应 MD5 加密                                                |
| name     | char(5)       | NOT NULL     | 姓名                                                                   |
| IDNO     | varchar(25)   | NOT NULL     | 身份证号                                                               |
| phone    | char(13)      | NOT NULL     | 手机号                                                                 |
| email    | varchar(20)   |              | 邮箱                                                                   |
| age      | char(2)       | default null | 年龄                                                                   |
| interest | varchar(1024) | default null | 空格分割的感兴趣的方向，元素值需要提前约定好，例如 01 表示开发岗位     |
| resume   | varchar(1024) | default null | 一个文件夹地址，里面存的是该用户的简历，与用户针对招聘投放的简历区分开 |

2. 企业表 enterprise

| 字段         | 限制          | 类型        | 说明                                             |
| ------------ | ------------- | ----------- | ------------------------------------------------ |
| id           | varchar(16)   | PRIMARY KEY | 企业 id                                          |
| name         | varchar(20)   | NOT NULL    | 企业名称                                         |
| code         | varchar(10)   | NOT NULL    | 用于 HR 注册的验证码                             |
| introduction | varchar(1024) |             | 简介                                             |
| position     | varchar(1024) | NOT NULL    | 一个数组，与 userInfo 中的 interset 取值应该相同 |
| address      | varchar(20)   |             | 详细地址                                         |

3. HR 用户表 humanResource

| 字段         | 限制        | 类型                                | 说明                                      |
| ------------ | ----------- | ----------------------------------- | ----------------------------------------- |
| id           | varchar(16) | PRIMARY KEY                         | hr 用户名，应为特殊字符、数字、字母的组合 |
| pswd         | varchar(16) | NOT NULL                            | 密码，有条件应 MD5 加密                   |
| name         | char(5)     | NOT NULL                            | 姓名                                      |
| enterpriseId | varchar(16) | NOT NULL 表 enterprise 的 id 的外键 | 企业 id                                   |
| department   | varchar(10) |                                     | 部门名称                                  |
| phone        | char(13)    | NOT NULL                            | 联系电话                                  |
| email        | varchar(20) |                                     | 邮箱                                      |

4. 招聘信息表 positionInfo

| 字段         | 限制        | 类型                                                  | 说明                                                  |
| ------------ | ----------- | ----------------------------------------------------- | ----------------------------------------------------- |
| position     | varchar(8)  | NOT NULL 与 enterpriseId 构成组合主键                 | 一个值，取值与 userInfo 中的 interset 相同            |
| enterpriseId | varchar(16) | 表 enterprise 字段 id 的外键 与 position 构成组合主键 | 发布公司                                              |
| title        | varchar(20) | NOT NULL                                              | 招聘标题                                              |
| content      | TEXT        | NOT NULL                                              | 招聘正文                                              |
| salary       | varchar(10) |                                                       | 薪酬                                                  |
| hrId         | varchar(16) | 表 humanResource 字段 id 的外键                       | 联系人                                                |
| degree       | char(1)     | NOT NULL                                              | 0 为不限 1 为高中 2 为专科 3 为本科 4 为硕士 5 为博士 |
| views        | int(10)     | 无符号 默认 0                                         | 浏览量                                                |

5. 简历表 resume （只放针对某个招聘的简历）

组合主键：(position,enterpriseId,userId)

| 字段                                    | 限制                    | 类型                                                                      | 说明                                   |
| --------------------------------------- | ----------------------- | ------------------------------------------------------------------------- | -------------------------------------- |
| position                                | varchar(8)              | NOT NULL;对应于表 positionInfo 字段 position;与 enterpriseId 构成组合外键 | copy positionInfo 的 postion 字段      |
| enterpriseId                            | varchar(16)             | 对应于表 positionInfo 的字段 enterpriseId;与 position 构成组合外键        | copy positionInfo 的 enterpriseId      |
| userId                                  | varchar(16)             |                                                                           | 用户 id                                |
| constraint userId_c foreign key(userId) | references userInfo(id) |
| resume                                  | varchar(1024)           |                                                                           | 一个文件夹地址，里面存的是该用户的简历 |
| isDealed                                | boolean                 |                                                                           | 处理状态，false 为未处理               |

6. 资讯表 info

| 字段    | 限制        | 类型                       | 说明     |
| ------- | ----------- | -------------------------- | -------- |
| id      | bigint(32)  | PRIMARY Key AUTO_INCREMENT |          |
| title   | varchar(20) | NOT NULL                   | 资讯标题 |
| content | TEXT        | NOT NULL                   | 资讯正文 |

7. 聊天会话表 talk

| 字段   | 限制        | 类型                            | 说明                  |
| ------ | ----------- | ------------------------------- | --------------------- |
| id     | bigint(64)  | PRIMARY key AUTO_INCREMENT      |                       |
| userId | varchar(16) | 表 userInfo 字段 id 的外键      | 会话人 1 应是普通用户 |
| hrId   | varchar(16) | 表 humanResource 字段 id 的外键 | 会话人 2 应是 hr      |

7. 聊天内容表 talkDetail

| 字段    | 限制          | 类型                   | 说明                             |
| ------- | ------------- | ---------------------- | -------------------------------- |
| id      | bigint(64)    | 表 talk 字段 id 的外键 |                                  |
| time    | TIMESTAMP(6)  | NOT NULL               | 消息发送时间                     |
| sender  | char(1)       | NOT NULL               | 发送人 0 为普通用户 1 为企业用户 |
| message | varchar(1024) | NOT NULL               | 消息                             |
