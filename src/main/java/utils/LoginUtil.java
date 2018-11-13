package utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import protocal.model.Session;

import java.util.UUID;

public class LoginUtil {

    public static void markAsLogin(Channel channel, Session session) {
        channel.attr(Constants.LOGIN).set(true);
        channel.attr(Constants.SESSION).set(session);
    }

    public static void markAsLogout(Channel channel) {
        channel.attr(Constants.LOGIN).set(false);
        channel.attr(Constants.SESSION).set(null);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Constants.LOGIN);
        Attribute<Session> sessionAttribute = channel.attr(Constants.SESSION);
        return loginAttr.get() != null && sessionAttribute.get() != null;
    }
}
