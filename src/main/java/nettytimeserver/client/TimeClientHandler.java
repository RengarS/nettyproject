package nettytimeserver.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class TimeClientHandler extends ChannelHandlerAdapter {
    public static Channel channel = null;

    @Override
    public void channelActive(ChannelHandlerContext context) {
        this.channel = context.channel();
        context.writeAndFlush("朋友已经上线！");
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("对方发送的消息是：" + body);
    }

    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        context.close();
    }
}
