package org.example.managehttp;

import com.google.protobuf.Descriptors;
import org.example.managehttp.factory.DynamicMsgFactory;
import org.example.managehttp.utils.MD5Utils;
import org.example.managehttp.utils.ProtocTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
public class MD5Test {
    
    @Autowired
    private DynamicMsgFactory dynamicMsgFactory;
    
    private final String message = "syntax = \"proto3\";message Student {string id = 1;string name = 2;}";
    private final String jsonData = "{\"id\":\"2\",\"name\":\"Aa\"}";
    private final static String filePrefix = "src/main/resources/proto/";
    private final static String messageType = "Student";
    
    @Test
    public void test01() throws IOException {
        String hash = MD5Utils.getMD5String(message);
        File protoFile = new File(filePrefix + messageType + ".proto");
        File descFile = new File(filePrefix + messageType + ".desc");

        ProtocTool protocTool = new ProtocTool(descFile.getPath(), ".", protoFile.getPath());
        protocTool.compile();

        System.out.println(hash);
        String protoHash = MD5Utils.calculateFileHash(protoFile);
        String descHash = MD5Utils.calculateFileHash(descFile);
        System.out.println(protoHash);
        System.out.println(descHash);
    }
    
    @Test
    public void test02() throws Descriptors.DescriptorValidationException, IOException {
        byte[] data = dynamicMsgFactory.marshallMessageFromJson(message, jsonData);
        String decodeData = dynamicMsgFactory.unmarshallMessageFromBytes(message, data);
        System.out.println(Arrays.toString(data));
        System.out.println(decodeData);
    }
}
