package org.example.managehttp.utils;

import java.util.Arrays;

public class FormatUtils {

    public static String removeHexPrefix(String hexString) {
        if (hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }
        return hexString;
    }

    public static byte[] removePaddingZeros(byte[] bytes) {
        int index = bytes.length - 1;
        while (index >= 0 && bytes[index] == 0) {
            index--;
        }
        return Arrays.copyOf(bytes, index + 1);
    }

    public static byte[] toByteArray(byte[][] data) {
        int length = data.length;
        int size = data[0].length;
        byte[] bytes = new byte[length * size];
        for (int i = 0; i < length; i++) {
            System.arraycopy(data[i], 0, bytes, (i * size), size);
        }
        return bytes;
    }
}
