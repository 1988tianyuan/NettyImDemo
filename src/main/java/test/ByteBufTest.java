package test;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import channelHandler.FirstClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
	private static Logger logger = LoggerFactory.getLogger(FirstClientHandler.class);

	private static final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);


	public static void main(String[] args){
		print("init allocate ByteBuf(9, 100)", buffer);

		buffer.writeBytes(new byte[]{1,2,3,4});
		print("writeBytes(1,2,3,4)", buffer);

		buffer.writeInt(6);
		print("writeInt(6)", buffer);

		buffer.writeBytes(new byte[]{7});
		print("writeBytes(7)", buffer);

		byte[] dst = new byte[2];
		buffer.readBytes(dst);
		print("readBytes to bytes, size:"+dst.length, buffer);

		System.out.println("getByte(1) return: " + buffer.getByte(1));
		System.out.println("getShort(1) return: " + buffer.getShort(1));
		System.out.println("getInt(1) return: " + buffer.getInt(1));
		print("getByte()", buffer);

		buffer.setByte(buffer.readableBytes()+1, 5);
		print("setByte, index: " + buffer.writerIndex() + ", value: " + 5, buffer);


		byte[] newDst = new byte[buffer.readableBytes()];
		buffer.readBytes(newDst);
		print("readBytes to bytes, size:"+dst.length+ ", result: " + printByteArray(newDst), buffer);

		buffer.writeBytes(new byte[]{8});
		print("writeBytes(8)", buffer);


	}

	private static String printByteArray(byte[] dst){
		StringBuffer buffer = new StringBuffer(dst.length);
		for(byte b:dst){
			buffer.append(b+",");
		}
		return buffer.toString();
	}




	private static void print(String action, ByteBuf buffer){
		System.out.println("after ===========" + action + "============");
		//当前容量
		System.out.println("capacity(): " + buffer.capacity());
		//最大容量
		System.out.println("maxCapacity(): " + buffer.maxCapacity());
		//当前read指针
		System.out.println("readerIndex(): " + buffer.readerIndex());
		//当前可读的数据大小
		System.out.println("readableBytes(): " + buffer.readableBytes());
		//该ByteBuf是否有数据可读
		System.out.println("isReadable(): " + buffer.isReadable());
		//当前write指针
		System.out.println("writerIndex(): " + buffer.writerIndex());
		//当前可写数据的剩余空间大小
		System.out.println("writableBytes(): " + buffer.writableBytes());
		//当前ByteBuf是否还可写入数据
		System.out.println("isWritable(): " + buffer.isWritable());
		//可写数据的最大剩余空间
		System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
		System.out.println();
	}

}
