# nettyproject

使用netty做的一个即时通信应用

暂时只支持群聊

Request.class封装了用户发送的消息

Response.class封装了服务端的响应

数据格式：使用了Google Protocol Buffers将对象进行序列化，具有序列化和反序列化速度快、体积小、传输快的特点

服务端部署：使用maven Plugins的 assembly:assembly将程序打成jar包，修改jar包中META-INF目录下的
MANIFEST.MF文件中的Main-Class属性为：nettytimeserver.server.TimeServer
    然后将jar包发送到linux服务器上，chmod 755  nettyproject*****.jar
    最后执行：java -jar nettyproject***.jar即可。
    
客户端运行：执行TimeClient.java中的main即可。


可能出现的问题：如果有三个人连接，随后有一人下线，剩下的两人发送消息服务端将会报错。
解决方案：服务端转发消息之前应当对Channel是否Active进行判断.
