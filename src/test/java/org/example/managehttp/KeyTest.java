package org.example.managehttp;

import com.esaulpaugh.headlong.abi.Tuple;
import com.esaulpaugh.headlong.abi.TupleType;
import com.esaulpaugh.headlong.util.FastHex;
import com.google.protobuf.Descriptors;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.example.managehttp.factory.DynamicMsgFactory;
import org.example.managehttp.pojo.ReadLedger.LedgerData;
import org.example.managehttp.utils.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class KeyTest {
    @Autowired
    private ConvertUtils convertUtils;
    @Autowired
    private DynamicMsgFactory dynamicMsgFactory;
    
    private static final int index = 1;
    private static final int KEY_LENGTH = 113;
    private static final String payload = "0x049420ccbe7cfdcae7d2e54050593dac753548f98e7f36cd6dc5bf85b24f6fdafb1cd885dc162d94035877c1f3301980485bf88b47b69c676636661745403e0c5768d71c936fd09abc158a668df3f9975deb764b7f78d590641549ae27be09ac41100a1b049a59167dc6f6e1825d16200304b90086a74ae2dde95779ddec17a21c3d3aaead6c65bcd65ac49377c7e30de3a4c4ba674ccbe964f7d1c5ed762275499c8f98f83e4d588bbc9a2a2a623aa6c9a401ccc0427b86323f7ebe0657d4ca4ae639dee587977e16ec8ef368cff29fdb31da7b8c0649e75a55378e55b35d3c007c0448bb0ad99ea610e60e7098177f7d87656cb62cc14a8a1ed5eb0c6c9807723273e54a729a8fc797a8d779707d7a96dc984b9931384b7e972b01f2c8e9007d2e02711ecc7305aea6ef2cc52834de5ee5f9be6f9a62aa2d01a98dee91d47118d396ad7b33b2c648700012f2cb1d9963117f042bb5cb25511df3deb2926e65cf0683ef1a8b1968a1db5ac01b0773588d80b1d8c7147101f1e5d9fbba32f96ca1d8c439429ea377f3a99bf94ca57d2e3fdea9d940b88926be41abf9833bff050c3e6a9b6b4260d5942c4fbb7005b8abbccfb7f27a4a27e84c5674d9be33a1f3315e814f04a76d19b848591a740d23af924d5c8352fba66f8e392276085f6bb3580d2ed3209360bd5cfd69d271abd27b9aa31fcc64140484cdd673ef395fbc2f624105df0dd4a257e6004b5c4d34b0d461cdc2823c118f241fdeb50679a92d54170afe17aafdf886bbfcdba5e8b237e4dcdd4e76f2047091b80571b7ee2a2fc8bf3cb8733df7e0caecf05197207a081f9331a4d9692e6e6db26e06df4e365e1f74528960719d9babbda0640ca4048b0605e833e96ad797b9318f5b1568dbec282cc799ca66e6f487c7cef140cc653fabc030ba383cae904ba581dc245a3253520192b13e719404281bbd646da383364c230cdbbe3c3de6c830d6db7e12413b3f1b92a82cdade8008b256ac28d77e08eba1687580c7be6979df4a239700dc00bb37f3081e1fa4802f82c69078f5c77ffd60975b4b95e1777ee93b3e16244a26c67b6dfc85049f8fc4cc7e762c6b897e3d1e0cb1bf06664004d17c0c9625ba59af34e68932ce7adea28ad9cd7109f12f544f1ef07d3d5afa9818d087b8fad21709c6cf911f212cfb62538ba80ff7589daf303ebaf094c461a5e94e16c05402ad0947510fedc13d68da777babb0605cc8389ebeeb5aeadf4c20950b0cea3cd566c987e8a4c2d7eea0c9";
    private static final String code = "0x000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000001e0000000000000000000000000000000000000000000000000000000a000000010000000000000000000000005f2be9a02b43f748ee460bf36eed24fafa1099200000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000000201000000000a00000001f22da26600000000f3b78ebfa46ce0563acef7a9d757c398000000000000000000000000000000000000000000000000000000000000";
    private static final String message = "\"syntax = \\\"proto3\\\";message Student {string id = 1;string name = 2;}\"";

    @Test
    public void decrypt() throws Descriptors.DescriptorValidationException, IOException {
        byte[] encryptKey = Hex.decode(ConvertUtils.removeHexPrefix(payload));
        byte[] cipherText = new byte[KEY_LENGTH];
        System.arraycopy(encryptKey, index * KEY_LENGTH, cipherText, 0, KEY_LENGTH);
        String priKey = TestCert.getSm2PrivateKey(index);
        
        byte[] SM2priKey = CryptoUtils.sm2Decrypt(priKey, cipherText);
        TupleType<Tuple> tt = TupleType.parse("((uint64,uint64,address,bytes32[])[])");
        ByteBuffer buffer = ByteBuffer.wrap(FastHex.decode(code.substring(2)));
        Tuple[] outputs = tt.decode(buffer).get(0);
        for (int i = 0; i < outputs.length; i++) {
            byte[][] bytes = outputs[i].get(3);
            byte[] bytesData = convertUtils.toByteArray(bytes);
            bytesData = convertUtils.removePaddingZeros(bytesData);
            byte[] decodeData = new byte[bytesData.length - 18];
            
            System.arraycopy(bytesData, 18, decodeData, 0, decodeData.length);
            System.out.println(Arrays.toString(decodeData));
            
            byte[] decryptData = CryptoUtils.sm4Decrypt(SM2priKey, decodeData);
            System.out.println(Arrays.toString(decryptData));
            
            String messageType = dynamicMsgFactory.generateProtoFile(message);
            String json = convertUtils.dataToJson(messageType, decryptData);
            System.out.println(json);
        }
    }
    
    
}
