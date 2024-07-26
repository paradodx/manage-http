/*
package org.example.managehttp.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class SM4Utils {
    static {
        // 注册Bouncy Castle提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    public SM4Utils() {
    }

    public static byte[] generateKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        return generateKey(128);
    }

    public static byte[] generateKey(int size) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kg = KeyGenerator.getInstance("SM4", "BC");
        kg.init(size, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    public static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateEcbCipher("SM4/ECB/PKCS5Padding", 1, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateEcbCipher("SM4/ECB/PKCS5Padding", 2, key);
        return cipher.doFinal(cipherText);
    }

    public static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithmName, "BC");
        Key sm4key = new SecretKeySpec(key, "SM4");
        cipher.init(mode, sm4key);
        return cipher;
    }
}
*/
