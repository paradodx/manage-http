package org.example.managehttp;

import com.google.protobuf.*;
import org.example.managehttp.utils.ProtocTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.managehttp.utils.DynamicMsg.*;

@SpringBootTest
public class DynamicMessageTest {
    /*
    * protobuf-dynamic
    * */
    @Test
    public void test01() {
        String protoContent = "syntax = \"proto3\";message Student {string identity = 1;string name = 2;}";

        Pattern pattern = Pattern.compile("message\\s+(\\w+)\\s*\\{");
        Matcher matcher = pattern.matcher(protoContent);
        if (!matcher.find())
            System.out.println("未找到message type");
        String messageType = matcher.group(1);

        String filePath = "src/main/resources/proto/" + messageType + ".proto";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(protoContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    * protoc --descriptor_set_out=src/main/resources/proto/student.desc --proto_path=. src/main/resources/proto/student.proto
    * */

    @Test
    public void test02(){
        String protoContent = "syntax = \"proto3\";message Student {string id = 1;string name = 2;}";

        Pattern pattern = Pattern.compile("message\\s+(\\w+)\\s*\\{");
        Matcher matcher = pattern.matcher(protoContent);
        if (!matcher.find())
            System.out.println("未找到message type");
        String messageType = matcher.group(1);

        String filePath = "src/main/resources/proto/" + messageType + ".proto";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(protoContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ProtocTool protocTool = new ProtocTool("src/main/resources/proto/" + messageType + ".desc",
                ".", "src/main/resources/proto/" + messageType + ".proto");
        protocTool.compile();

        try {
            byte[] dataSet = Files.readAllBytes(Paths.get("src/main/resources/proto/example.protoset"));
            DescriptorProtos.FileDescriptorSet fileDescriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(dataSet);
            DescriptorProtos.FileDescriptorProto fdp = fileDescriptorSet.getFile(0);
            Descriptors.FileDescriptor fileDescriptor = Descriptors.FileDescriptor.buildFrom(fdp, new Descriptors.FileDescriptor[]{});

            String json = "{\"id\":\"1\",\"name\":\"Aa\"}";
            byte[] data = marshallMessage(fileDescriptor, json);

            System.out.println(Arrays.toString(data));
            String resultJson = unMarshallMessage(fileDescriptor, data);
            System.out.println(resultJson);
        } catch (IOException | Descriptors.DescriptorValidationException e) {
            e.printStackTrace();
        }
    }
}
