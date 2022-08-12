package cn.wanxh.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class TestByteBuf {

    public static void main(String[] args) {

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);

        System.out.println("byteBuf的容量为：" + byteBuf.capacity());

        System.out.println("byteBuf的可读容量为：" + byteBuf.readableBytes());

        System.out.println("byteBuf的可写容量为：" + byteBuf.writableBytes());

        /*while (byteBuf.isReadable()){
            // 1. 内部通过移动readerIndex进行读取
            System.out.println((char) byteBuf.readByte());
        }*/

        // 2. 通过下标直接读取
        for (int i = 0; i < byteBuf.readableBytes(); i++) {
            System.out.println((char) byteBuf.getByte(i));
        }

        // 3. 转换为byte[]进行读取
        byte[] bytes = byteBuf.array();
        for(byte b : bytes) {
            System.out.println((char) b);
        }


    }

}
