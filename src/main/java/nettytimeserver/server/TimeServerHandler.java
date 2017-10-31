package nettytimeserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import nettytimeserver.utils.Const;

import java.util.Map;

public class TimeServerHandler extends ChannelHandlerAdapter {

    Map<ChannelId, Channel> channelIdChannelMap = Const.channelIdChannelMap;

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        channelIdChannelMap.put(context.channel().id(), context.channel());
        for (Channel channel : channelIdChannelMap.values()) {
            if (channel != context.channel()) {
                channel.writeAndFlush(byteBuf);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) throws Exception {

        context.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        context.close();
    }
}
