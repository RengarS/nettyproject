package nettytimeserver.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import nettytimeserver.domain.Request;
import nettytimeserver.domain.Response;
import nettytimeserver.utils.Const;
import nettytimeserver.utils.SerializableUtils;

/**
 * @author Aries
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    public static String send = "";

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        Request request = new Request();
        request.setAction(Const.REQUEST_LOGIN);
        System.out.print("请输入你的id:");
        String msg = Const.scanner.nextLine();
        send = msg;
        request.setSender(msg);
        context.writeAndFlush(Unpooled.copiedBuffer(SerializableUtils.SerializableObject(request, Request.class)));
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        Response response = SerializableUtils.UnSerializableObject(req, Response.class);
        System.out.println(response.getTime() + ":" + response.getSender() + "->" + response.getContent());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        context.close();
    }
}
