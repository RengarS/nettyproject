package nettytimeserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import nettytimeserver.domain.Request;
import nettytimeserver.domain.Response;
import nettytimeserver.utils.Const;
import nettytimeserver.utils.SerializableUtils;

import java.util.Map;

/**
 * @author aries
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    Map<String, Channel> channelIdChannelMap = Const.channelIdChannelMap;

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        for (Map.Entry<String, Channel> entry : channelIdChannelMap.entrySet()) {
            System.out.println(entry.getKey());
        }
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        Request request = SerializableUtils.UnSerializableObject(bytes, Request.class);

        if (Const.REQUEST_LOGIN.equals(request.getAction())) {
            channelIdChannelMap.put(request.getSender(), context.channel());
        } else if (Const.REQUEST_SEND.equals(request.getAction())) {
            Response response = new Response(request.getSender(), request.getTime(), request.getContent());
            ByteBuf byteBuf1 = Unpooled.copiedBuffer(SerializableUtils.SerializableObject(response, Response.class));
            for (Map.Entry<String, Channel> entry : channelIdChannelMap.entrySet()) {
                if (!entry.getKey().equals(request.getSender()) && entry.getValue().isActive()) {
                    entry.getValue().writeAndFlush(byteBuf1);
                }
            }

        } else if (Const.REQUEST_LOGOUT.equals(request.getAction())) {
            channelIdChannelMap.remove(request.getSender());
            /**
             * context.write(Unpooled.copiedBuffer("登出成功".getBytes()));
             */
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
