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

    public static FileDescriptor makeFileDescriptor(byte[] data) throws IOException, Descriptors.DescriptorValidationException {
        DescriptorProtos.FileDescriptorSet fileDescriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(data);
        FileDescriptorProto fdp = fileDescriptorSet.getFile(0);
        return FileDescriptor.buildFrom(fdp, new FileDescriptor[]{});
    }

    public static byte[] marshallMessage(FileDescriptor fileDescriptor, String json) throws InvalidProtocolBufferException {
        Descriptors.Descriptor messageDescriptor = fileDescriptor.getMessageTypes().get(0);
        DynamicMessage.Builder messageBuilder = DynamicMessage.newBuilder(messageDescriptor);
        JsonFormat.parser().merge(json, messageBuilder);
        DynamicMessage message = messageBuilder.build();
        return message.toByteArray();
    }

    public static String unMarshallMessage(FileDescriptor fileDescriptor, byte[] data) throws InvalidProtocolBufferException {
        Descriptors.Descriptor messageDescriptor = fileDescriptor.getMessageTypes().get(0);
        DynamicMessage message = DynamicMessage.parseFrom(messageDescriptor, data);
        return JsonFormat.printer().print(message);
    }
}
