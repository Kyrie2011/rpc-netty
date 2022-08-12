package cn.wanxh.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TestByteBuf01 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10, 20);
        System.out.println("byteBuff的容量为：" + byteBuf.capacity());
        System.out.println("byteBuff的容量为：" + byteBuf.readableBytes());
        System.out.println("byteBuff的可写容量为：" + byteBuf.writableBytes());

        for (int i = 0; i < 5; i++) {
            byteBuf.writeInt(i);  // 写入int类型，一个int类型占4个字符
        }

        System.out.println("byteBuff的容量为：" + byteBuf.capacity());
        System.out.println("byteBuff的容量为：" + byteBuf.readableBytes());
        System.out.println("byteBuff的可写容量为：" + byteBuf.writableBytes());

    }

}
