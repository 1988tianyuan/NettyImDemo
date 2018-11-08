package utils;

import io.netty.channel.Channel;
import protocal.model.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Constants.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if(LoginUtil.hasLogin(channel) && channel.hasAttr(Constants.SESSION)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Constants.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Constants.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }


}
