package org.example.managehttp.utils;


import cn.aheca.common.base.HexUtil;
import cn.aheca.common.gm.SM2Util;
import cn.aheca.common.gm.SM4Util;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;



public class CryptoUtils {

    private static final int index = 1;
    private static final int KEY_LENGTH = 113;
    
    public static byte[] sm4Encrypt(byte[] key, byte[] data) {
        byte[] cipher;
        try {
            cipher = SM4Util.encrypt_Ecb_Padding(key, data);
        } catch (InvalidKeyException | NoSuchAlgorithmException |
                 NoSuchProviderException | NoSuchPaddingException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return cipher;
    }

    public static byte[] sm4Decrypt(byte[] key, byte[] cipher) {
        byte[] data;
        try {
            data = SM4Util.decrypt_Ecb_Padding(key, cipher);
        } catch (IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException | NoSuchAlgorithmException |
                 NoSuchProviderException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static byte[] sm2Encrypt(byte[] key, String cert) {
        try {
            return SM2Util.encrypt(SM2Utils.returnPublicKey_Q(cert), key);
        } catch (InvalidCipherTextException | CertificateException | NoSuchProviderException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static byte[] sm2Decrypt(String privateKey, byte[] cipher){
        try {
            return SM2Util.decrypt(HexUtil.Hex2Byte(privateKey), cipher);
        } catch (InvalidCipherTextException | NoSuchAlgorithmException | NoSuchProviderException |
                 InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static byte[] SM2Decrypt(String payload){
        byte[] encryptKey = Hex.decode(FormatUtils.removeHexPrefix(payload));
        byte[] cipherText = new byte[KEY_LENGTH];
        System.arraycopy(encryptKey, index * KEY_LENGTH, cipherText, 0, KEY_LENGTH);
        String priKey = TestCert.getSm2PrivateKey(index);
        return sm2Decrypt(priKey, cipherText);
    }


}
