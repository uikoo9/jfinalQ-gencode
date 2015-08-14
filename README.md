jfinalQ-gencode
==========================================
1. jfinalQ代码生成工具
2. 采用velocity模版技术
3. 可以多表同时生成

[jfinalQ](http://uikoo9.com/jfinalQ)
---
1. 基于[jfinal1.9](http://www.jfinal.com/)，易学，开发快速，功能强大
2. 基于[bootstrap3.x](http://v3.bootcss.com/)，简洁美观，完美适配移动端
3. [jfinalQ-blog](http://git.oschina.net/uikoo9/jfinalQ-blog)：jfinalQ最简化示例
4. [jfinalQ-gencode](http://git.oschina.net/uikoo9/jfinalQ-gencode)：jfinalQ自带代码生成工具
5. [jfinalQ-encrypt](http://git.oschina.net/uikoo9/jfinalQ-encrypt)：jfinalQ自带tomcat加密部署工具
6. [bootstrapQ](http://uikoo9.com/bootstrapQ)：jfinalQ自带bootstrap增强组件

表命名规范
---
1. 数据库规范：db\_开头，例如：db\_blog
2. 表名规范：t\_开头，中间为模块名，结尾为功能名，例如：t\_ucenter\_user
3. 字段规范：模块名\_表名\_开头，字段结尾，例如：ucenter\_user\_name
4. 必备字段：
	1. id：id,int,10,not null,pk,ac 
	2. 创建日期：cdate,datetime,not null
	3. 创建人id：cuser_id,int,10,not null
	4. 创建人姓名：cuser_name,varchar,200,not null

开始使用
---
1. 将src和lib复制到一个java project中
2. 修改db.properties中的数据库连接信息
3. 运行com.uikoo9.gcode.gui.QGenerateCodeFrame
4. 点击浏览按钮，选择要保存代码的文件夹
5. 选择要生成代码的表，点击生成
6. 将生成的代码直接拷贝到项目下

注意
---
1. 可以通过修改com.uikoo9.tmp下的模版文件修改生成模版
2. 为了适应32位，64位，linux等操作系统，所以没有生成jar

捐助
---
希望得到您的捐助：

（支付宝捐助）

![zhifubao](http://uikoo9.qiniudn.com/@/img/donate/zhifu2.png)

（微信捐助）

![weixin](http://uikoo9.qiniudn.com/@/img/donate/zhifu1.png)