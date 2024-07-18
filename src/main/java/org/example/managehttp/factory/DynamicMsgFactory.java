package org.example.managehttp.factory;

import com.google.protobuf.Descriptors;
import org.example.managehttp.utils.ProtocTool;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.managehttp.utils.DynamicMsg.*;

@Component
public class DynamicMsgFactory {


    private final static String filePrefix = "src/main/resources/proto/";
    /*
    * return: message type
    * */
    public String generateProtoFile(String message){
        Pattern pattern = Pattern.compile("message\\s+(\\w+)\\s*\\{");
        Matcher matcher = pattern.matcher(message);
        if (!matcher.find())
            System.out.println("未找到message type");
        String messageType = matcher.group(1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePrefix + messageType + ".proto"))){
            writer.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return messageType;
    }

    public byte[] marshallMessageFromProto(String messageType, String jsonData) throws IOException, Descriptors.DescriptorValidationException {
        // compile
        ProtocTool protocTool = new ProtocTool(filePrefix + messageType + ".desc",
                ".", filePrefix + messageType + ".proto");
        protocTool.compile();
        // marshall
        byte[] dataSet = Files.readAllBytes(Paths.get(filePrefix + messageType + ".desc"));
        Descriptors.FileDescriptor fileDescriptor = makeFileDescriptor(dataSet);
        return marshallMessage(fileDescriptor, jsonData);
    }

    public String unmarshallMessageFromBytes(String messageType, byte[] data) throws IOException, Descriptors.DescriptorValidationException {
        // compile
        ProtocTool protocTool = new ProtocTool(filePrefix + messageType + ".desc",
                ".", filePrefix + messageType + ".proto");
        protocTool.compile();
        // unmarshall
        byte[] dataSet = Files.readAllBytes(Paths.get(filePrefix + messageType + ".desc"));
        Descriptors.FileDescriptor fileDescriptor = makeFileDescriptor(dataSet);
        return unMarshallMessage(fileDescriptor, data);
    }


}
