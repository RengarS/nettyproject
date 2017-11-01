# nettyproject

使用netty做的一个即时通信应用

暂时只支持群聊

Request.class封装了用户发送的消息

Response.class封装了服务端的响应

数据格式：使用了Google Protocol Buffers将对象进行序列化，具有序列化和反序列化速度快、体积小、传输快的特点

