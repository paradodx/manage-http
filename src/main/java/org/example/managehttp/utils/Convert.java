package org.example.managehttp.utils;

import com.example.model.extension.ByteArrayKt;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import org.example.managehttp.factory.DynamicMsgFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.example.managehttp.utils.DynamicMsg.*;

@Component
public class Convert {

    @Autowired
    private DynamicMsgFactory dynamicMsgFactory;

    public String BytesArrayToString(byte[][] bytes) {
        String[] stringArray = new String[bytes.length];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int length = bytes[i].length;
            while (length > 0 && bytes[i][length - 1] == 0) {
                length--;
            }
            stringArray[i] = new String(bytes[i], 0, length, StandardCharsets.UTF_8);
        }
        for (String str : stringArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    public byte[] ProtoToBytes(byte[][] data) {
        // byte[][] -> byte[]
        return data[0];
    }

    // encryptKey
    public boolean isEncrypted(byte[] data) {
        return data[0] == 1;
    }

    // version
    public int protocolVersion(byte[] data) {
        return data[1];
    }

    // timestamp
    public String timestamp(byte[] data) {
        byte[] timestampBytes = new byte[8];
        System.arraycopy(data, 10, timestampBytes, 0, 8);
        long timestamp = 0;
        for (int i = 0; i < 8; i++) {
            timestamp |= ((long) timestampBytes[i] & 0xff) << (8 * i);
        }
        return Long.toString(timestamp);
    }

    // data
    public String dataToJson(String message, byte[] data) throws Descriptors.DescriptorValidationException, IOException {
        int dataLength = data.length - 18;
        byte[] decodeData = new byte[dataLength];
        System.arraycopy(data, 18, decodeData, 0, dataLength);
        int length = decodeData.length;
        while (length > 0 && decodeData[length - 1] == 0) {
            length--;
        }
        decodeData = Arrays.copyOf(decodeData, length);
        return dynamicMsgFactory.unmarshallMessageFromBytes(message, decodeData);

    }

    public String[] LedgerToBytes32Array(byte[] data, String uri, int version, String encryptKey) {
        // encryptKey to bytes[]
        byte encryptByte = (byte) ((encryptKey != null && !encryptKey.isEmpty()) ? 1 : 0);

        // uri to bytes[]
        ByteBuffer uriBuffer = ByteBuffer.allocate(Long.BYTES);
        byte[] uriBytes = uriBuffer.order(ByteOrder.BIG_ENDIAN).putLong(Long.parseLong(uri)).array();

        // timestamp to bytes[]
        long timestamp = System.currentTimeMillis() / 1000; // seconds
        byte[] timestampBytes = new byte[8];
        for (int i = 0; i < timestampBytes.length; i++) {
            timestampBytes[i] = (byte) ((timestamp >> (8 * i)) & 0xff);
        }

        // 1: encryptByte, 2: version, 3-10: uri, 11-18: timestamp
        ByteBuffer fixed = ByteBuffer.allocate(18 + data.length);
        fixed.put(encryptByte);
        fixed.put((byte) version);
        fixed.put(uriBytes);
        fixed.put(timestampBytes);
        fixed.put(data);

        return ByteArrayKt.toBytes32Array(fixed.array());
    }
}
