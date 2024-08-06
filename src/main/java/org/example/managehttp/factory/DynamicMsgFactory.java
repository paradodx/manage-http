package org.example.managehttp.factory;

import com.google.protobuf.Descriptors;
import org.example.managehttp.utils.MD5Utils;
import org.example.managehttp.utils.ProtocTool;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.managehttp.utils.DynamicMsg.*;

@Component
public class DynamicMsgFactory {


    
    private final static String filePrefix = "src/main/resources/proto/";

    /**
     * 序列化proto
     * @param message proto信息
     * @param jsonData proto数据
     * @return byte[] 
     */
    public byte[] marshallMessageFromJson(String message, String jsonData) throws IOException, Descriptors.DescriptorValidationException {
        // 获取messageType
        String messageType = getMessageType(message);
        
        File protoFile = new File(filePrefix + messageType + ".proto");
        File descFile = new File(filePrefix + messageType + ".desc");

        // 计算新内容的Hash
        String newFileHash = MD5Utils.getMD5String(message);
        
        // 检查 proto 文件是否已经存在且内容一致
        if (protoFile.exists()) {
            String protoHash = MD5Utils.calculateFileHash(protoFile);
            if (protoHash.equals(newFileHash) && descFile.exists()) {
                return marshall(descFile, jsonData);
            }
        }
        // 如果proto文件不存在或是内容不一致, 写入新的 proto 文件
        writeFile(protoFile, message);

        // 编译proto文件得到desc
        ProtocTool protocTool = new ProtocTool(descFile.getPath(), ".", protoFile.getPath());
        protocTool.compile();

        return marshall(descFile, jsonData);
    }

    /**
     * 反序列化proto
     * @param message proto信息
     * @param data 序列化字节数组
     * @return json
     */
    public String unmarshallMessageFromBytes(String message, byte[] data) throws IOException, Descriptors.DescriptorValidationException {
        // 获取messageType
        String messageType = getMessageType(message);

        File protoFile = new File(filePrefix + messageType + ".proto");
        File descFile = new File(filePrefix + messageType + ".desc");

        // 计算新内容的Hash
        String newFileHash = MD5Utils.getMD5String(message);

        // 检查 proto 文件是否已经存在且内容一致
        if (protoFile.exists()) {
            String protoHash = MD5Utils.calculateFileHash(protoFile);
            if (protoHash.equals(newFileHash) && descFile.exists()) {
                return unmarshall(descFile, data);
            }
        }
        // 如果proto文件不存在或是内容不一致, 写入新的 proto 文件
        writeFile(protoFile, message);

        // 编译proto文件得到desc
        ProtocTool protocTool = new ProtocTool(descFile.getPath(), ".", protoFile.getPath());
        protocTool.compile();

        return unmarshall(descFile, data);
    }
    
    public String getMessageType(String message){
        Pattern pattern = Pattern.compile("message\\s+(\\w+)\\s*\\{");
        Matcher matcher = pattern.matcher(message);
        if (!matcher.find())
            System.out.println("未找到message type");
        return matcher.group(1);
    }
    
    public byte[] marshall(File file, String jsonData) throws IOException, Descriptors.DescriptorValidationException {
        byte[] dataSet = Files.readAllBytes(file.toPath());
        Descriptors.FileDescriptor fileDescriptor = makeFileDescriptor(dataSet);
        return marshallMessage(fileDescriptor, jsonData);
    }
    
    public String unmarshall(File file, byte[] data) throws IOException, Descriptors.DescriptorValidationException {
        byte[] dataSet = Files.readAllBytes(file.toPath());
        Descriptors.FileDescriptor fileDescriptor = makeFileDescriptor(dataSet);
        return unMarshallMessage(fileDescriptor, data);
    }

    private void writeFile(File file, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
