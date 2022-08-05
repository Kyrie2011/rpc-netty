package cn.wanxh.codec;

import cn.wanxh.core.RpcRequest;
import cn.wanxh.core.RpcResponse;
import cn.wanxh.protocol.CustomRpcProtocol;
import cn.wanxh.protocol.MsgHeader;
import cn.wanxh.protocol.MsgType;
import cn.wanxh.protocol.ProtocolConstants;
import cn.wanxh.serialization.RpcSerialization;
import cn.wanxh.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class CustomRpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN){
            return;
        }

        short magic = in.readShort();
        if (magic != ProtocolConstants.MAGIC) {
            throw new IllegalArgumentException("magic number is illegal, " + magic);
        }

        byte version = in.readByte();
        byte serialization = in.readByte();
        byte msgType = in.readByte();
        byte status = in.readByte();
        long requestId = in.readLong();
        int len = in.readInt();

        if (in.readableBytes() < len) {
            in.resetReaderIndex();  // 重置索引位置
            return;
        }

        byte[] data = new byte[len];

        in.readBytes(data);

        MsgType messageType = MsgType.findByType(msgType);
        if (messageType == null) {
            throw new IllegalArgumentException("messageType is not null ");
        }

        // rebuild header
        MsgHeader header = new MsgHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setSerialization(serialization);
        header.setMsgType(msgType);
        header.setStatus(status);
        header.setRequestId(requestId);

        // 根据消息类型，rebuild消息对象
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(serialization);
        switch (messageType) {
            case REQUEST:
                RpcRequest rpcRequest = rpcSerialization.deserialize(data, RpcRequest.class);
                // 将 message 向后传递至其他handler
                if (rpcRequest != null) {
                    CustomRpcProtocol<RpcRequest> requestMsg = new CustomRpcProtocol<>();
                    requestMsg.setMsgBody(rpcRequest);
                    requestMsg.setHeader(header);
                    out.add(requestMsg);
                }
                break;

            case RESPONSE:
                RpcResponse rpcResponse = rpcSerialization.deserialize(data, RpcResponse.class);
                if (rpcResponse != null) {
                    CustomRpcProtocol<RpcResponse> responseMsg = new CustomRpcProtocol<>();
                    responseMsg.setHeader(header);
                    responseMsg.setMsgBody(rpcResponse);
                    out.add(responseMsg);
                }
                break;

            case HEARTBEAT:
                break;
        }


    }
}
