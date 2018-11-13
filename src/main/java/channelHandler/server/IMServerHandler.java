package channelHandler.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.Command;
import protocal.model.Packet;

import java.util.HashMap;
import java.util.Map;

import static protocal.Command.*;

@ChannelHandler.Sharable
public class IMServerHandler extends SimpleChannelInboundHandler<Packet>{

    public static final IMServerHandler INSTANCE = new IMServerHandler();
    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMServerHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(MESSAGE_REQUEST.getValue(), MessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP.getValue(), CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST.getValue(), JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST.getValue(), LogoutRequestHandler.INSTANCE);
        handlerMap.put(MEMBER_REQUEST.getValue(), MemberListRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST.getValue(), QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MSG_REQUEST.getValue(), GroupMsgRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }


}
