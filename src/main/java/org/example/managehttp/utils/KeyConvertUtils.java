package org.example.managehttp.utils;

public class KeyConvertUtils {
    public static String Byte2Hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toLowerCase();
    }

    public static byte[] Hex2Byte(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;

        for(int i = 0; i < b.length; ++i) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte)(parse(c0) << 4 | parse(c1));
        }

        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return c - 97 + 10 & 15;
        } else {
            return c >= 'A' ? c - 65 + 10 & 15 : c - 48 & 15;
        }
    }

    public static byte[] fromHexString(String var0) {
        char[] var1 = var0.toUpperCase().toCharArray();
        int var2 = 0;

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3] >= '0' && var1[var3] <= '9' || var1[var3] >= 'A' && var1[var3] <= 'F') {
                ++var2;
            }
        }

        byte[] var6 = new byte[var2 + 1 >> 1];
        int var4 = var2 & 1;

        for(int var5 = 0; var5 < var1.length; ++var5) {
            if (var1[var5] >= '0' && var1[var5] <= '9') {
                var6[var4 >> 1] = (byte)(var6[var4 >> 1] << 4);
                var6[var4 >> 1] = (byte)(var6[var4 >> 1] | var1[var5] - 48);
            } else {
                if (var1[var5] < 'A' || var1[var5] > 'F') {
                    continue;
                }

                var6[var4 >> 1] = (byte)(var6[var4 >> 1] << 4);
                var6[var4 >> 1] = (byte)(var6[var4 >> 1] | var1[var5] - 65 + 10);
            }

            ++var4;
        }
        return var6;
    }
}
