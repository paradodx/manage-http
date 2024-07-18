package org.example.managehttp;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ConvertTest {


    @Test
    public void test01(){
        byte[][] inputArray = new byte[][]{{
                0, 0, 0, 0, 0, 10, 0, 0, 0, 1, -47, -106, -108, 102, 0, 0, 0, 0, 10, 1, 51, 18, 4, 77, 105, 110, 103, 0, 0, 0, 0, 0
        }};
        byte[] inputBytes = inputArray[0];

        byte encryptByte = inputBytes[0];
        System.out.println("Encrypt Byte: " + encryptByte);
        System.out.println(encryptByte == 1);

        // Parse version
        int version = inputBytes[1];
        System.out.println("Version: " + version);

        // Parse uri
        byte[] uriBytes = new byte[8];
        System.arraycopy(inputBytes, 2, uriBytes, 0, 8);
        ByteBuffer uriBuffer = ByteBuffer.wrap(uriBytes).order(ByteOrder.BIG_ENDIAN);
        long uriLong = uriBuffer.getLong();
        String uri = Long.toString(uriLong);
        System.out.println("URI: " + uri);

        // Parse timestamp
        byte[] timestampBytes = new byte[8];
        System.arraycopy(inputBytes, 10, timestampBytes, 0, 8);
        long timestamp = 0;
        for (int i = 0; i < 8; i++) {
            timestamp |= ((long) timestampBytes[i] & 0xff) << (8 * i);
        }
        System.out.println("Timestamp: " + timestamp);

        // Parse data
        int dataLength = inputBytes.length - 18;
        byte[] data = new byte[dataLength];
        System.arraycopy(inputBytes, 18, data, 0, dataLength);
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02x", b));
        }
        System.out.println("Data: " + sb.toString());
    }
}
