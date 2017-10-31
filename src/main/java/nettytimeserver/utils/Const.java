package nettytimeserver.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Const {
    public static final Scanner scanner = new Scanner(System.in);
    public static Map<ChannelId, Channel> channelIdChannelMap = new HashMap<ChannelId, Channel>();
}
