package utils;

import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import protocal.model.Session;

public class Constants {
    public static final AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
