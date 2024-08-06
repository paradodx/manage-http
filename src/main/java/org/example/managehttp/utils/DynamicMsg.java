package org.example.managehttp.utils;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class DynamicMsg {


    /**
     * 获取文件描述符
     * @param data 文件字节流
     * @return 文件描述符 FileDescriptor
     */
    public static FileDescriptor makeFileDescriptor(byte[] data) throws IOException, Descriptors.DescriptorValidationException {
        DescriptorProtos.FileDescriptorSet fileDescriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(data);
        FileDescriptorProto fdp = fileDescriptorSet.getFile(0);
        return FileDescriptor.buildFrom(fdp, new FileDescriptor[]{});
    }

    /**
     * 序列化json字符串得到字节数组
     * @param fileDescriptor 文件描述符
     * @param json 传入json字符串
     * @return byte[] 字节数组
     */
    public static byte[] marshallMessage(FileDescriptor fileDescriptor, String json) throws InvalidProtocolBufferException {
        Descriptors.Descriptor messageDescriptor = fileDescriptor.getMessageTypes().get(0);
        DynamicMessage.Builder messageBuilder = DynamicMessage.newBuilder(messageDescriptor);
        JsonFormat.parser().merge(json, messageBuilder);
        DynamicMessage message = messageBuilder.build();
        return message.toByteArray();
    }

    /**
     * 反序列化字节数组得到json字符串
     * @param fileDescriptor 文件描述符
     * @param data 字节数组
     * @return json 字符串
     */
    public static String unMarshallMessage(FileDescriptor fileDescriptor, byte[] data) throws InvalidProtocolBufferException {
        Descriptors.Descriptor messageDescriptor = fileDescriptor.getMessageTypes().get(0);
        DynamicMessage message = DynamicMessage.parseFrom(messageDescriptor, data);
        return JsonFormat.printer().print(message);
    }
}
