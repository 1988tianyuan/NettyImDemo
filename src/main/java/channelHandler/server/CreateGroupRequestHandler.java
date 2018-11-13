package channelHandler.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import protocal.model.CreateGroupRequestPacket;
import protocal.model.CreateGroupResponsePacket;
import protocal.model.Session;
import utils.SessionUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
    private CreateGroupRequestHandler() {}


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) throws Exception {
        List<String> userIds = packet.getUserIdList();
        List<String> userNameList = new LinkedList<>();
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        Session currentSession = SessionUtil.getSession(ctx.channel());

        for(String id:userIds) {
            Channel channel = SessionUtil.getChannel(id);
            channelGroup.add(channel);
            userNameList.add(SessionUtil.getSession(channel).getUserName());
        }

        String groupId = UUID.randomUUID().toString().substring(0, 10);
        createGroupResponsePacket.setUserNameList(userNameList);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setSuccess(true);

        //保存群聊成员的channel
        SessionUtil.setChannelGroup(groupId, channelGroup);
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("创建群聊成功，id是：" + createGroupResponsePacket.getGroupId());
        System.out.println("群里有：" + userNameList);

    }
}
