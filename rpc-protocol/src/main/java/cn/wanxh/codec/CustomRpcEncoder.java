package cn.wanxh.codec;

import cn.wanxh.protocol.CustomRpcProtocol;
import cn.wanxh.protocol.MsgHeader;
import cn.wanxh.serialization.RpcSerialization;
import cn.wanxh.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CustomRpcEncoder extends MessageToByteEncoder<CustomRpcProtocol<Object>> {

    /*
    +---------------------------------------------------------------+
    | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
    +---------------------------------------------------------------+
    | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
    +---------------------------------------------------------------+
    |                   数据内容 （长度不定）                          |
    +---------------------------------------------------------------+
    */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CustomRpcProtocol<Object> msg, ByteBuf byteBuf) throws Exception {

        MsgHeader header = msg.getHeader();
        byteBuf.writeByte(header.getMagic());
        byteBuf.writeByte(header.getVersion());
        byteBuf.writeByte(header.getSerialization());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        byteBuf.writeInt(header.getMsgLen());
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());
        byte[] data = rpcSerialization.serialize(msg.getMsgBody());
        byteBuf.writeBytes(data);
    }

}
