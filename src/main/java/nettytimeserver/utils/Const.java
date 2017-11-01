package nettytimeserver.utils;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author aries
 */
public class Const {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final Scanner scanner = new Scanner(System.in

    );
    public static Map<String, Channel> channelIdChannelMap = new HashMap<String, Channel>();

    public static final String REQUEST_LOGIN = "REQUEST_LOGIN";
    public static final String REQUEST_SEND = "REQUEST_SEND";
    public static final String REQUEST_LOGOUT = "REQUEST_LOGOUT";

    public static Channel serverChannel = null;
}
