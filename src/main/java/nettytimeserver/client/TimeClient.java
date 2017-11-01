package nettytimeserver.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import nettytimeserver.domain.Request;
import nettytimeserver.utils.Const;
import nettytimeserver.utils.SerializableUtils;

import java.util.Date;

/**
 * @author Aries
 */
public class TimeClient {

    private static Channel channel = null;

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            channel = future.channel();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            try {
                new TimeClient().connect(8888, "127.0.0.1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        try {
            Request request = new Request();
            Thread.sleep(10000);

            while (true) {
                System.out.println("请输入消息：");
                String msg = Const.scanner.nextLine();
                //退出
                if ("quit".equals(msg)) {
                    request.setAction(Const.REQUEST_LOGOUT);
                    request.setSender(TimeClientHandler.send);
                    TimeClient.channel.writeAndFlush(Unpooled.copiedBuffer(SerializableUtils
                            .SerializableObject(request, Request.class)));
                    TimeClient.channel.close();
                    break;
                }
                //发送消息
                Channel channel = TimeClient.channel;
                request.setAction(Const.REQUEST_SEND);
                request.setSender(TimeClientHandler.send);
                request.setTime(Const.FORMAT.format(new Date()));
                request.setContent(msg);
                ByteBuf byteBuf = Unpooled.copiedBuffer(SerializableUtils.SerializableObject(request, Request.class));
                channel.writeAndFlush(byteBuf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
